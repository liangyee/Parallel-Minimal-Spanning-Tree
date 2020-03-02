/**
 * Graph generator for Algorithm problems
 * generates edge list of connected graph at file "graph"
 * Created by Mirsiss on 2016-10-10.
 * Modified by Jun Liang Dec 20 2018
 **/
import java.io.*;
import java.util.*;

public class GraphGenerator {
    public static void main(String args[]) throws IOException{
        Scanner sc = new Scanner(System.in);
        File file = new File("graph.txt");
        FileWriter out = new FileWriter(file);
        String output = new String();
        Random rand = new Random();

        System.out.print("# of vertices : ");
        int N = sc.nextInt();
        System.out.print("# of edges : ");
        int M = sc.nextInt();
        //output += N + " " + M + "\n";
        System.out.print("max weight (bigger than 1): ");
        int W = sc.nextInt();

        if(M < N-1) {
            System.out.println("# of edges must be equal or bigger than # of vertices - 1");
            System.exit(1);
        }
        else if(M > N * (N-1) / 2)
            M = N * (N-1) / 2;
        else if(W < 1) {
            System.out.println("max weight must be bigger than 0");
            System.exit(1);
        }

        boolean[][] Adj = new boolean[N][N];

        for(int i = 0; i < N; i++) {
            output += i + " ";
            while(true) {
                int v = rand.nextInt(N);
                if(v != i && !Adj[v][i] && !Adj[i][v]) {
                    output += v + " ";
                    Adj[v][i] = Adj[i][v] = true;
                    break;
                }
            }

            if(W != 1)
                output +=  Integer.toString(rand.nextInt(W-1) + 1);
            output += "\n";
        }

        for(int i=0; i < M-N; i++) {
            int v1 = rand.nextInt(N);
            int v2 = rand.nextInt(N);;
            int cnt = 0;
            while(true) {
                if(v1 != v2 && !Adj[v1][v2] && !Adj[v2][v1])
                    break;
                else if(cnt == N)
                    break;
                else if(v2 == 0) {
                    v2 += N - 1;
                    cnt++;
                }
                else {
                    v2--;
                    cnt++;
                }
            }

            if(cnt != N) {
                output += v1 + " " + v2 + " " + (rand.nextInt(W - 1) + 1) + "\n";
                Adj[v1][v2] = Adj[v2][v1] = true;
            }
            else {
                i--;
                continue;
            }
        }
        System.out.println(output);
        out.write(output);
        out.flush();
    }
} 