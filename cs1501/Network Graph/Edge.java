import java.util.*;

public class Edge implements Comparable<Edge>{
	private static final double FLOATING_POINT_EPSILON = 1E-10;
	private Vertex source;
	private Vertex target;
	private String cable;
	private int width;
	private int length;
	private double time;
	private double flow;

	// default constructor
	public Edge(){}
 	
 	public Vertex getSource(){
		return source;
	}

	public Vertex getTarget(){
		return target;
	}

	public int getWidth(){
		return width;
	}

	public double getTime(){
		return time;
	}

	public int getLength(){
		return length;
	}

	public String getCable(){
		return cable;
	}

	public double getFlow(){
		return flow;
	}

	public void setSource(Vertex s){
		source = s;
	}

	public void setTarget(Vertex t){
		target = t;
	}

	public void setWidth(int w){
		width = w;
	}

	public void setLength(int l){
		length = l;
	}

	public void setCable(String c){
		cable = c;
	}

	public void setFlow(double f){
		flow = f;
	}

	public double setTime(String c, int l){
		if(c.equals("optical"))
            return time = (double) l / 200000000; 
        else return time = (double) l / 230000000;
	}

	public int compareTo(Edge e){
		return Double.compare(time, e.getTime());
	}

	public int other(int vertex) {
        if      (vertex == this.getSource().getIndex()) return this.getTarget().getIndex();
        else if (vertex == this.getTarget().getIndex()) return this.getSource().getIndex();
        else throw new IllegalArgumentException("invalid endpoint");
    }

    public double residualCapacityTo(int vertex) {
        if      (vertex == this.getSource().getIndex()) return flow;              
        else if (vertex == this.getTarget().getIndex()) return width - flow;
        else throw new IllegalArgumentException("invalid endpoint");
    }

    public void addResidualFlowTo(int vertex, double delta) {
        if (!(delta >= 0.0)) throw new IllegalArgumentException("Delta must be nonnegative");

        if      (vertex == this.getSource().getIndex()) flow -= delta;           
        else if (vertex == this.getTarget().getIndex()) flow += delta;           
        else throw new IllegalArgumentException("invalid endpoint");

        if (Math.abs(flow) <= FLOATING_POINT_EPSILON)
            flow = 0;
        if (Math.abs(flow - width) <= FLOATING_POINT_EPSILON)
            flow = width;

        if (!(flow >= 0.0))      throw new IllegalArgumentException("Flow is negative");
        if (!(flow <= width)) throw new IllegalArgumentException("Flow exceeds capacity");
    }
}