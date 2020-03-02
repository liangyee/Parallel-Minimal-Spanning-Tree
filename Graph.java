// Graph
import java.util.ArrayList;
public class Graph {
    ArrayList<Edge> edges;
    private ArrayList<Integer> vertices;
    private int vertexNum;
    private int edgeNum;

    public Graph(){
        vertexNum = 0;
        edgeNum = 0;
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public int getEdgeNum() {
        return edgeNum;
    }

    public int getVertexNum(){
        return vertexNum;
    }

    public ArrayList<Integer> getVertices() {
        return vertices;
    }

    public Edge findAndDeleteNearestVertex(Graph mst) {

        Edge targetEdge = new Edge();
        float minWeight = Integer.MAX_VALUE;
        for (int v : mst.getVertices()) {
            ArrayList<Edge> t_edges = findEdgesByVertex(v);
            for (Edge e : t_edges) {
                if((!mst.getVertices().contains(e.v)) && e.w < minWeight){
                    minWeight = e.w;
                    targetEdge = (Edge)e.clone();
                }
            }
        }
        if(! (targetEdge.u == 0 && targetEdge.v == 0 && targetEdge.w == 0)){
            removeEdge(targetEdge);
        }
        return targetEdge;
    }

    public ArrayList<Edge> findEdgesByVertex(int vertex){
        ArrayList<Edge> t_edges = new ArrayList<>();
        for (Edge e:edges) {
            if(e.u == vertex || e.v == vertex) {
                t_edges.add(e);
            }
        }
        return t_edges;
    }

    public void addVertex(int vertex){
        if(!vertices.contains(vertex)){
            vertices.add(vertex);
            vertexNum += 1;
        }
    }

    public synchronized void addEdge(Edge e){

        if(edges.add(e)){
            edgeNum += 1;

            if (!vertices.contains(e.u)){
                vertices.add(e.u);
                vertexNum += 1;
            }
            if (!vertices.contains(e.v)){
                vertices.add(e.v);
                vertexNum += 1;
            }
        }
    }

    public synchronized void safeAddEdge(Edge e){

        if(!edges.contains(e)){
            addEdge(e);

            if (!vertices.contains(e.u)){
                vertices.add(e.u);
                vertexNum += 1;
            }
            if (!vertices.contains(e.v)){
                vertices.add(e.v);
                vertexNum += 1;
            }

        }

    }

    public void removeEdge(Edge e){
        if(edges.remove(e)){
            edgeNum -= 1;
        }
    }


}
