import java.util.Random;

public class Edge implements Comparable<Edge>{
    int u;          // first vertex
    int v;          // second vertex
    float w;        // edge weight

    private Random random = new Random();

    public Edge() {
        this.u = 0;
        this.v = 0;
        this.w = 0;
    }
    public Edge(final int u, final int v) {
        this.u = u;
        this.v = v;
        this.w = random.nextFloat();
    }

    public Edge(final int u, final int v, final float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }

    public Object clone() {
        Edge e = new Edge(u, v, w);
        return e;
    }

    @Override
    public int compareTo(Edge other) {
        if (w < other.w){
            return -1;
        }

        if (w > other.w) {
            return 1;
        }

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        Edge e = (Edge) o;
        return u == e.u && v == e.v;
    }
}
