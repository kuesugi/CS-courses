/*  This is a revised version of FordFulkerson class to fit operations 
 *  in NetworkAnalysis.java 
 *  The original authors are Robert Sedgewick and Kevin Wayne,
 *  the textbook authors
 */
import java.util.*;

public class FordFulkerson{
    private static final double FLOATING_POINT_EPSILON = 1E-11;

    private int V;
    private boolean[] marked;
    private Edge[] edgeTo;
    private double value;
    private Vertex[] vertices;
  
    public FordFulkerson(int s, int t, int vertexNumber, Vertex[] list){
        vertices = list;
        V = vertexNumber;
        validate(s);
        validate(t);
        if (s == t)            throw new IllegalArgumentException("Source equals sink");
        if (!isFeasible(s, t)) throw new IllegalArgumentException("Initial flow is infeasible");

        value = excess(t);
        while (hasAugmentingPath(s, t)) {
            double bottle = Double.POSITIVE_INFINITY;
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
            }
            for (int v = t; v != s; v = edgeTo[v].other(v)) {
                edgeTo[v].addResidualFlowTo(v, bottle); 
            }
            value += bottle;
        }
        assert check(s, t);
    }

    public double value(){
        return value;
    }

    public boolean inCut(int v){
        validate(v);
        return marked[v];
    }

    private void validate(int v){
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }

    private boolean hasAugmentingPath(int s, int t){
        edgeTo = new Edge[V];
        marked = new boolean[V];

        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(s);
        marked[s] = true;
        while (!queue.isEmpty() && !marked[t]){
            int v = queue.dequeue();

            for (Edge e : vertices[v].adjacencyList){
                int w = e.other(v);

                if (e.residualCapacityTo(w) > 0){
                    if (!marked[w]){
                        edgeTo[w] = e;
                        marked[w] = true;
                        queue.enqueue(w);
                    }
                }
            }
        }
        return marked[t];
    }

    private double excess(int v){
        double excess = 0.0;
        for (Edge e : vertices[v].adjacencyList){
            if (v == e.getSource().getIndex()) excess -= e.getFlow();
            else               excess += e.getFlow();
        }
        return excess;
    }

    private boolean isFeasible(int s, int t){
        for (int v = 0; v < V; v++){
            for (Edge e: vertices[v].adjacencyList){
                if (e.getFlow() < -FLOATING_POINT_EPSILON || e.getFlow() > e.getWidth() + FLOATING_POINT_EPSILON){
                    System.err.println("Edge does not satisfy capacity constraints: " + e);
                    return false;
                }
            }
        }

        if (Math.abs(value + excess(s)) > FLOATING_POINT_EPSILON){
            System.err.println("Excess at source = " + excess(s));
            System.err.println("Max flow         = " + value);
            return false;
        }
        if (Math.abs(value - excess(t)) > FLOATING_POINT_EPSILON){
            System.err.println("Excess at sink   = " + excess(t));
            System.err.println("Max flow         = " + value);
            return false;
        }
        for (int v = 0; v < V; v++){
            if (v == s || v == t) continue;
            else if (Math.abs(excess(v)) > FLOATING_POINT_EPSILON){
                System.err.println("Net flow out of " + v + " doesn't equal zero");
                return false;
            }
        }
        return true;
    }

    private boolean check(int s, int t){
        if (!isFeasible(s, t)){
            System.err.println("Flow is infeasible");
            return false;
        }

        if (!inCut(s)){
            System.err.println("source " + s + " is not on source side of min cut");
            return false;
        }
        if (inCut(t)){
            System.err.println("sink " + t + " is on source side of min cut");
            return false;
        }

        double mincutValue = 0.0;
        for (int v = 0; v < V; v++){
            for (Edge e : vertices[v].adjacencyList){
                if ((v == e.getSource().getIndex()) && inCut(e.getSource().getIndex()) && !inCut(e.getTarget().getIndex()))
                    mincutValue += e.getWidth();
            }
        }

        if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON){
            System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
            return false;
        }
        return true;
    }

    public void reset(){
        V = 0; value = 0;
        for(boolean b : marked)
            b = false;
        for(Edge e : edgeTo)
            e = new Edge();
        for(Vertex v : vertices)
            v = new Vertex();
    }
}