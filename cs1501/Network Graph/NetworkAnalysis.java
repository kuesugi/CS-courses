import java.util.*;
import java.io.*;

public class NetworkAnalysis{
	// number of vertices in the graph
	public static int vertexNumber = 0;
	// used in checking copper only connection
	public static boolean noCopper = true;

	public static int count = 0;
	public static ArrayList<Edge> edges;
	public static ArrayList<Edge> edgeMST;
	public static Bag<Edge> listD;
	public static Vertex[] vertices;
	public static ArrayList<Vertex> verticesCnt;

	public static void main(String args[]){
		// arg check and loading file
		if (args.length == 0){
			System.out.println("\tError: no arg file\n\tExiting...");
			System.exit(1);
		}
		File f = new File(args[0]);
		System.out.print("\n\tLoading file ...\t");
		// initializing lists to store vertices and edges, respectively
		loadFile(f);
		// below is revised based on my p3 main()
		int userSelection = -1;
		Scanner s = new Scanner (System.in);
		// showing the menu options
		showMenu();
		while (userSelection != 6){	// constant asking until handling the "exiting" input
			// reading user selection and do corresponding work
			s = new Scanner (System.in);
			System.out.print("\t--> Your selection: ");
			try{
				userSelection = s.nextInt();
				if (userSelection == 1)				path();
				else if (userSelection == 2)		copperOnly();
				else if (userSelection == 3)		maxData();
				else if (userSelection == 4)		findMST();
				else if (userSelection == 5)		connected();
				else if (userSelection == 6)
					System.out.println("\tExiting...");
				else
					System.out.println("\tInvalid option, please select again");
			}
			catch (Exception e){
				System.out.println("\tError Occurs, please check your input");
				System.out.println("\tExiting...");
				System.exit(1);
			}
		}
	}

	public static void showMenu(){
		System.out.println("\n");
		System.out.println("\t\t* * * Network Analysis Menu* * *");
		System.out.println("\t1. The lowest latency path between two vertices");
		System.out.println("\t2. A copper-only connected graph?");
		System.out.println("\t3. Mamximum amount of data that can be transmitted from one vertex to another");
		System.out.println("\t4. Minimum avg latency spanning tree");
		System.out.println("\t5. Remain connected if any two vertices fail?");
		System.out.println("\t6. Quit");
		System.out.println("\t\t * * * * * * * * * * * * * * * *");
		System.out.println("\tPlease select one from above (number only)");
	}

	public static void loadFile(File f){
		try{
			List<String> dataList = new ArrayList<String>();
	        Scanner s = new Scanner(f);
	        // initializing two lists storing vertices/edges
	        vertexNumber = s.nextInt();
	        vertices = new Vertex[vertexNumber];
	        while(count < vertexNumber)
	            vertices[count] = new Vertex(count++);
	        edges = new ArrayList<Edge>();
	        edgeMST = new ArrayList<Edge>();
	        count = 0;
	        // start to load the file
	        s.nextLine();
	        while(s.hasNextLine()){
	        	dataList = Arrays.asList(s.nextLine().split(" "));
	            // Takes in the buffer and tokenizes the buffer to the data needed
	            Vertex sVertex = vertices[Integer.parseInt( dataList.get(0) )];
	            Vertex tVertex = vertices[Integer.parseInt( dataList.get(1) )];
	            Edge edge = new Edge(); Edge egde = new Edge();
	            if(dataList.get(2).equals("copper"))
	                noCopper = false;
	            // direction 1
	            edge.setSource(sVertex);
	            edge.setTarget(tVertex);
	            edge.setCable( dataList.get(2) );
	            edge.setWidth( Integer.parseInt( dataList.get(3) ) );
	            edge.setLength( Integer.parseInt( dataList.get(4) ) );
	            edge.setTime(dataList.get(2), Integer.parseInt( dataList.get(4) ));
	            // direction 2
	            egde.setSource(tVertex);
	            egde.setTarget(sVertex);
	            egde.setCable( dataList.get(2) );
	            egde.setWidth( Integer.parseInt( dataList.get(3) ) );
	            egde.setLength( Integer.parseInt( dataList.get(4) ) );
	            egde.setTime(dataList.get(2), Integer.parseInt( dataList.get(4) ));
	            // add to the two vertices' adjacencyLists, respectively
	            sVertex.adjacencyList.add(edge); tVertex.adjacencyList.add(egde);

	            /* * * * * * *
	            test:
	            System.out.println(edge.getCable()+" "+edge.getSource().getIndex()+" "+edge.getWidth()+" "+edge.getLength());
	            System.out.print(sVertex.adjacencyList.get(0).getSource().getIndex()+" ");
	            System.out.println(sVertex.adjacencyList.get(0).getTarget().getIndex());
				System.out.println(edge.getTime());
	            * * * * * * */

	            edges.add(edge); edgeMST.add(edge);
	            dataList = new ArrayList<String>();
	        }
	        listD = new Bag<Edge>();
	        for(int i = 0; i < vertexNumber; i++){
	        	int selfLoop = 0;
	        	for(Edge e : vertices[i].adjacencyList){
	        		if(e.other(i) > i){
	        			listD.add(e);
	        		}
	        		else if(e.other(i) == i){
	        			if(selfLoop % 2 == 0)
	        				listD.add(e);
	        			++selfLoop;
	        		}
	        	}
	        }
	        System.out.println("Done!");
        }
    	catch (FileNotFoundException e1){
        	System.out.println("\n\tError: cannot find the network data file");
        	System.out.println("\tExiting...");
        	System.exit(1);
    	}
    	catch (Exception e2){
        	System.out.println("\n\tError: cannot load the network data file");
        	System.out.println("\tExiting...");
        	System.exit(1);
    	}
	}

	public static void path(){
		double minWidth = Double.MAX_VALUE;
		Scanner s = new Scanner (System.in);
		System.out.print("\t--> The first vertex is: ");
		int v1 = s.nextInt();
		//Vertex start = new Vertex();
		while(!validVertex(v1)){
			System.out.println("\tError: wrong vertex number");
			System.out.print("\t--> The first vertex is: ");
			v1 = s.nextInt();
		}
		System.out.print("\t--> The second vertex is: ");
		int v2 = s.nextInt();
		//Vertex end = new Vertex();
		while(!validVertex(v2)){
			System.out.println("\tError: wrong vertex number");
			System.out.print("\t--> The second vertex is: ");
			v2 = s.nextInt();
		}
		if(v1 == v2){
			System.out.println("\n\tThe two vertices are the same\n\tPlease select 1 again if you want to");
			showMenu(); return;
		}
		System.out.println("\n\tResult:\n\tPath:");
		Dijkstra dPath = new Dijkstra(v1, vertexNumber, listD, vertices);
		for(Edge e : dPath.pathTo(v2)){
            System.out.print("\t"+e.getSource().getIndex()+" - "+e.getTarget().getIndex()+"\t"+e.getWidth()+" Mbit/s");
            System.out.format("\t%.15f\n", e.getTime());
            //System.out.println("\t===="+e.getWidth()+"=====");
            if(e.getWidth() < minWidth)
                minWidth = e.getWidth();
        }
		System.out.println("\tThe minimum bandwidth of the path is "+minWidth+" Mbit/s");
		showMenu();
	}

	public static void copperOnly(){
		boolean connection = true;
		int copperNum = 0;
		System.out.print("\n\tResult:\n\tThis network graph is ");
		// this bool val is determined in loadFile()
		if(noCopper == true){
			System.out.println("NOT copper connected since there's no copper cable");
			showMenu(); return;
		}
        for(Vertex vertex : vertices){
            for(Edge edge : vertex.adjacencyList)
                if(edge.getCable().equals("copper"))
                    copperNum++;
            if(copperNum == 0)
                connection = false;
            copperNum = 0;
        }
        // if there's no copper cable of this vertex
        // then the graph is not copper connected
        if(connection == false)
            System.out.println("NOT copper connected");
        else System.out.println("copper connected");
        showMenu();
    }

	public static void maxData(){
		Scanner s = new Scanner (System.in);
		System.out.print("\t--> The first vertex is: ");
		int v1 = s.nextInt();
		//Vertex start = new Vertex();
		while(!validVertex(v1)){
			System.out.println("\tError: wrong vertex number");
			System.out.print("\t--> The first vertex is: ");
			v1 = s.nextInt();
		}
		System.out.print("\t--> The second vertex is: ");
		int v2 = s.nextInt();
		//Vertex end = new Vertex();
		while(!validVertex(v2)){
			System.out.println("\tError: wrong vertex number");
			System.out.print("\t--> The second vertex is: ");
			v2 = s.nextInt();
		}
		FordFulkerson flow = new FordFulkerson(v1, v2, vertexNumber, vertices);
		System.out.println("\n\tResult:");
		System.out.println("\tMaximum amount of data from "+v1+" to "+v2+" is "+flow.value());
		flow.reset(); resetEdges();
		showMenu();
	}

	public static void findMST(){
		System.out.println("\n\tRESULT:");
		System.out.println("\t Edge\t     Latency");
		// For some reasons, the implementation of MinPQ always gives
		// errors about generic stuff, so here I take a different 
		// approach other than using MinPQ.java. But, still, the basic 
		// idea is same with the code by the textbook author
		Collections.sort(edgeMST);
		Union u = new Union(vertexNumber);
		double mstWeight = 0;                      
     	ArrayList<Edge> mst = new ArrayList<Edge>();
     	// code below consults a part of alg in KruskalMST.java
     	while(mst.size() < vertexNumber - 1 && count < edgeMST.size()-1){
     		Edge edge = edgeMST.get(count);
     		int v = edge.getSource().getIndex();
     		int w = edge.getTarget().getIndex();
     		if(!u.connected(v, w)){
     			u.union(v, w);
     			mst.add(edge);
     			mstWeight += edge.getTime();
     		}
     		++count;
     	}
     	count = 0;
     	for(Edge e : mst){
     		System.out.print("\t" + e.getSource().getIndex() + " - " + e.getTarget().getIndex() + "\t");
     		System.out.format("%.15f\n", e.getTime());
     	}
     	System.out.format("\tTotal:\t%.15f\n", mstWeight);
		showMenu();
	}

	public static void connected(){
		System.out.println("\n\tResult:");
		boolean remaincnt = true;
		// copy vertices to a new list
		verticesCnt = new ArrayList<Vertex>();
		for(Vertex v : vertices)
			verticesCnt.add(v);
		// Any pair of vertices together function as
		// an articulation pt, so we iterate through
		// all pairs of vertices here
		for(int v = 0; v < vertexNumber; v++){
			for(int w = v + 1; w < vertexNumber; w++){
				Vertex v1 = verticesCnt.get(v);
				Vertex v2 = verticesCnt.get(w);

				/* test:
				System.out.println(v1.getIndex()+" "+v2.getIndex());
				*/

				verticesCnt.remove(v1); verticesCnt.remove(v2);
				
				/* test:
				for(int i = 0; i<verticesCnt.size(); i++)
					System.out.println(verticesCnt.get(i).getIndex());
				*/

				// dfs from the first not removed vertex
				Vertex v3 = verticesCnt.get(0);
				if(!isConnected(v1,v2,v3))
					remaincnt = false;
				// initialize again for next pair
				verticesCnt = new ArrayList<Vertex>();
				for(Vertex u : vertices)
					verticesCnt.add(u);
			}
		}
		if(remaincnt)
			System.out.println("\tThis network graph remains connected");
		else System.out.println("\tthis network graph cannot remain connected");
		showMenu();
	}

	/******************************************************************
      * Helper functions
      - isConnected() for connected()
      - dfs() 		  for connected()
      - validVertex() for operations' vertex index check
      - resetEdges()  for maxData()
    ********************************************************************/
	
	public static boolean isConnected(Vertex v1, Vertex v2, Vertex v3){
		boolean c = true;
		boolean[] marked = new boolean[vertexNumber];
		for(boolean m : marked)
			m = false;
		marked[v1.getIndex()]=true; marked[v2.getIndex()]=true;
		dfs(v3, marked);
		// check if any vertex is not connected
		for(boolean m : marked)
			if(!m)
				c = false;
		// print this pair of vertices out since their lost
		// can make the graph unconnected
		if(!c)
			System.out.println("\tIf "+v1.getIndex()+" and "+v2.getIndex()+" fail,");
		return c;
	}

	public static void dfs(Vertex v, boolean[] marked){
		// code below consult part of alg in DFS.java by the
		// textbook author
		if(marked[v.getIndex()])
			return;
		marked[v.getIndex()] = true;
		for(Edge e : v.adjacencyList){
			Vertex t = e.getTarget();
			if(!marked[t.getIndex()])
				dfs(t, marked);
		}
	}

	public static boolean validVertex(int v){
		return v < vertexNumber;
	}

	public static void resetEdges(){
		for(Vertex v : vertices)
			for(Edge e : v.adjacencyList)
				e.setFlow(0);
	}
}