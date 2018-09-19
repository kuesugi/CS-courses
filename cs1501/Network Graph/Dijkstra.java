import java.util.*;
/*  This is a revised version of Dijkstra class to fit operations 
 *  in NetworkAnalysis.java 
 *  The original authors are Robert Sedgewick and Kevin Wayne,
 *  the textbook authors
 */
public class Dijkstra{
    private int V;
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;
    private Bag<Edge> list;
    private Vertex[] vertices;

    public Dijkstra(int s, int vertexNumber, Bag<Edge> el, Vertex[] vl){
        V = vertexNumber;
        vertices = vl;  list = el;
        for (Edge e : list){
            if (e.getTime() < 0)
                throw new IllegalArgumentException("edge " + e + " has negative weight");
        }
        distTo = new double[V];
        edgeTo = new Edge[V];

        validateVertex(s);

        for (int v = 0; v < V; v++)
            distTo[v] = Double.POSITIVE_INFINITY;
        distTo[s] = 0.0;

        pq = new IndexMinPQ<Double>(V);
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge e : vertices[v].adjacencyList)
                relax(e);
        }
        assert check(s);
    }

    private void relax(Edge e){
        int v = e.getSource().getIndex(), w = e.getTarget().getIndex();
        if (distTo[w] > distTo[v] + e.getTime()){
            distTo[w] = distTo[v] + e.getTime();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else                pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v){
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v){
        validateVertex(v);
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v){
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.getSource().getIndex()]) {
            path.push(e);
        }
        return path;
    }

    private boolean check(int s){
        for (Edge e : list){
            if (e.getTime() < 0){
                System.err.println("negative edge weight detected");
                return false;
            }
        }
        if (distTo[s] != 0.0 || edgeTo[s] != null){
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < V; v++){
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY){
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }
        for (int v = 0; v < V; v++){
            for (Edge e : vertices[v].adjacencyList){
                int w = e.getTarget().getIndex();
                if (distTo[v] + e.getTime() < distTo[w]){
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }
        for (int w = 0; w < V; w++){
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            int v = e.getSource().getIndex();
            if (w != e.getTarget().getIndex()) return false;
            if (distTo[v] + e.getTime() != distTo[w]){
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    private void validateVertex(int v){
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}