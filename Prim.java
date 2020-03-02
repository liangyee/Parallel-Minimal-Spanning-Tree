public class Prim {

    public static void main(String[] args) {

        // TODO: generator graph here
        Graph graph = new Graph();

        Edge e1 = new Edge(0, 1, 2);
        Edge e2 = new Edge(0, 3, 8);
        Edge e3 = new Edge(0, 4, 4);
        Edge e4 = new Edge(1, 2, 3);
        Edge e5 = new Edge(2, 3, 5);
        Edge e6 = new Edge(2, 4, 1);
        Edge e7 = new Edge(3, 4, 7);

        graph.addEdge(e1);
        graph.addEdge(e2);
        graph.addEdge(e3);
        graph.addEdge(e4);
        graph.addEdge(e5);
        graph.addEdge(e6);
        graph.addEdge(e7);


        // TODO: initialize a vertex as root
        long s = System.nanoTime();
        Graph mst = new Graph();
        mst.addVertex(graph.getVertices().get(0));

        // TODO: loop to find the nearest vertex
        while(mst.getVertexNum() < graph.getVertexNum()){
            Edge e = graph.findAndDeleteNearestVertex(mst);
            mst.addEdge(e);
        }
        System.out.println(System.nanoTime() - s);

    }

}
