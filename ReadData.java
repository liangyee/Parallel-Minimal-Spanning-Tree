import java.io.BufferedReader;
import java.io.FileReader;

public class ReadData {

    public static Graph readfile(String path){
        Graph graph = new Graph();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(path));
            String line;
            while((line = reader.readLine()) != null){

                String[] a = line.split(" ");
                Edge e = new Edge(Integer.parseInt(a[0]), Integer.parseInt(a[1]), Integer.parseInt(a[2]));
                graph.addEdge(e);
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

        return graph;
    }

}
