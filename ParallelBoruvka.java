import java.util.ArrayList;
import java.util.Arrays;

public class ParallelBoruvka {
    public static void main(String[] args) {
        String[] paths = {"data/small.txt", "data/medium.txt", "data/large1.txt", "data/large2.txt", "data/large3.txt"};
        for(String path: paths){

            Graph graph = ReadData.readfile(path);
            int[] threadNumList = {1, 2, 4, 6, 8};
            for(int i = 0; i< threadNumList.length; i++){
                ParaBoruvka(threadNumList[i], graph);
            }
        }
    }

    public static void ParaBoruvka(int threadsNum, Graph graph){
        long start_time = System.currentTimeMillis();

        int graphVerticesNum = graph.getVertexNum();
        int graphEdgesNum = graph.getEdgeNum();
        UnionFind uf = new UnionFind(graph.getVertexNum());
        Edge[] shortestEdges = new Edge[graph.getVertexNum()];
        Graph mst = new Graph();

        FindThread[] fThreads = new FindThread[threadsNum];
        UnionThread[] uThreads = new UnionThread[threadsNum];

        // rough evenly distribute workload using static block method
        int findBlockSize = graphEdgesNum/threadsNum;
        int remain = graphEdgesNum % threadsNum;
        if(remain > threadsNum/2){
            findBlockSize += 1;
        }

        int UnionBlockSize = graphVerticesNum/threadsNum;
        remain = graphVerticesNum % threadsNum;
        if(remain > threadsNum/2){
            UnionBlockSize += 1;
        }

        while(mst.getEdgeNum() < graphVerticesNum - 1) {
            Arrays.fill(shortestEdges, null);

            for(int i = 0; i < threadsNum; i++){

                int start = findBlockSize * i;
                int end = start + findBlockSize -1;
                if(i == threadsNum -1){
                    end = graphEdgesNum -1;
                }
                FindThreadParameter ftParameter = new FindThreadParameter(uf, shortestEdges, graph.edges, start, end);
                fThreads[i] = new FindThread(ftParameter);
                fThreads[i].start();
            }

            for(int i = 0; i < threadsNum; i++){
                try{
                    fThreads[i].join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            for(int i = 0; i < threadsNum; i++){
                int start = UnionBlockSize * i;
                int end = start + UnionBlockSize -1;
                if(i == threadsNum - 1){
                    end = graphVerticesNum -1;
                }
                UnionThreadParameter utParameter = new UnionThreadParameter(uf, mst, shortestEdges, start, end);
                uThreads[i] = new UnionThread(utParameter);
                uThreads[i].start();
            }

            for(int i = 0; i < threadsNum; i++){
                try{
                    uThreads[i].join();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println((System.currentTimeMillis() - start_time)/1000.0);
    }

}

class UnionThread extends Thread {
    UnionThreadParameter p;

    public UnionThread(UnionThreadParameter parameter) {
        this.p = parameter;
    }

    public void run() {

        for(int i = p.start; i<= p.end; i++){
            Edge e = p.shortestEdges[i];
            if((e!=null) && (!p.uf.connected(e.u, e.v))){
                p.uf.union(e.u, e.v);
                p.mst.addEdge(e);
            }
        }
    }
}

class UnionThreadParameter {
    UnionFind uf;
    Graph mst;
    Edge[] shortestEdges;
    int start;
    int end;

    public UnionThreadParameter(UnionFind uf, Graph mst, Edge[] shortestEdges, int start, int end){
        this.uf = uf;
        this.mst = mst;
        this.shortestEdges = shortestEdges;
        this.start = start;
        this.end = end;
    }
}

class FindThreadParameter {
    UnionFind uf;
    Edge[] shortestEdges;
    ArrayList<Edge> graphEdges;
    int start;
    int end;

    public FindThreadParameter(UnionFind uf, Edge[] shortestEdges, ArrayList<Edge> graphEdges, int start, int end){
        this.uf = uf;
        this.shortestEdges = shortestEdges;
        this.graphEdges = graphEdges;
        this.start = start;
        this.end = end;
    }
}

class FindThread extends Thread {
    FindThreadParameter p;

    public FindThread(FindThreadParameter parameter) {
        this.p = parameter;
    }

    public void run() {

		for (int i = p.start; i <= p.end; i++) {
			Edge e = p.graphEdges.get(i);
            int uRoot = p.uf.find(e.u);
            int vRoot = p.uf.find(e.v);

            if(uRoot != vRoot) {
                synchronized (p.shortestEdges){

                    if(p.shortestEdges[uRoot] == null || p.shortestEdges[uRoot].w > e.w){
                        p.shortestEdges[uRoot] = e;
                    }
                    if(p.shortestEdges[vRoot] == null || p.shortestEdges[vRoot].w > e.w){
                        p.shortestEdges[vRoot] = e;
                    }
                }

            }
        }

    }

}
