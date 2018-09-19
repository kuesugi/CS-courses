import java.util.*;

public class Vertex{
	LinkedList<Edge> adjacencyList;
	boolean marked = false; 
	private int vertexIndex; // from 0 - v-1 

	// constructors
	public Vertex(){}

	public Vertex(int i){
		vertexIndex = i;
		adjacencyList = new LinkedList<Edge>();
	}
 
 	public int getIndex(){
 		return vertexIndex;
 	}
}