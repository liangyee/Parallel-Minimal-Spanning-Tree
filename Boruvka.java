import java.util.Arrays;
public class Boruvka {
    // run over 5 graph datasets
    // timing does not include reading graph files
    public static void main(String[] args) {
        String[] paths = {"data/small.txt", "data/medium.txt", "data/large1.txt", "data/large2.txt", "data/large3.txt"};
        for(String path: paths){
            Graph graph = ReadData.readfile(path);
            run(graph);
        }
    }
    public static void run(Graph graph) {
        long start_time = System.currentTimeMillis();
        int graphVerticesNum = graph.getVertexNum();
        UnionFind uf = new UnionFind(graph.getVertexNum());
        Edge[] shortestEdges = new Edge[graph.getVertexNum()];
        Graph mst = new Graph();

        // recursively find and union shorest edges
        while(mst.getEdgeNum() < graphVerticesNum - 1) {
            Arrays.fill(shortestEdges, null);
            for(Edge e: graph.edges) {
                int uRoot = uf.find(e.u);
                int vRoot = uf.find(e.v);

                if(uRoot == vRoot) continue;
                if(shortestEdges[uRoot] == null || e.w < shortestEdges[uRoot].w){
                    shortestEdges[uRoot] = e;
                }
                if(shortestEdges[vRoot] == null || e.w < shortestEdges[vRoot].w){
                    shortestEdges[vRoot] = e;
                }
            }

            for(int i = 0; i< graphVerticesNum; i++){
                Edge e = shortestEdges[i];
                if((e!=null) && (!uf.connected(e.u, e.v))){
                    uf.union(e.u, e.v);
                    mst.addEdge(e);
                }
            }
        }
        System.out.println((System.currentTimeMillis() - start_time)/1000.0);
    }

}
