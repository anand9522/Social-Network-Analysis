/**
 * 
 */
package graph;

import java.util.*;

/**
 * @author anand
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	HashMap<Integer,HashSet<Integer>> edges=new HashMap<>();
	@Override
	public void addVertex(int num) {
		if(!edges.containsKey(num)){
			edges.put(num,new HashSet<>());
		}

	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		if (edges.containsKey(from)){
			edges.get(from).add(to);
		}

	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {

		if(!edges.containsKey(center)){
			return null;
		}

		Graph g=new CapGraph();
		HashSet<Integer> egoVertices=edges.get(center);

		for (int egoVertex:egoVertices){
			g.addVertex(egoVertex);
		}

		for (int vertex:egoVertices){
			for (int connected:edges.get(vertex)){
				if (egoVertices.contains(connected)){
					g.addEdge(vertex,connected);
				}
			}
		}

		return g;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {

		List<Graph> graphs=new ArrayList<>();
		Stack s=new Stack();
		HashSet<Integer> visited=new HashSet<>();

		for (int v : edges.keySet()) {

			if (!visited.contains(v)) {
				SCCStackFill(v,s,visited);
			}

		}

		visited.clear();

		HashMap transpose=graphTranspose();
		List<Integer> vertices = new ArrayList<>();

		while (!s.isEmpty()){
			int v= (int) s.pop();
			if (!visited.contains(v)) {
				SCCDFSUtil(v, visited,transpose,vertices);
				Graph g=graphFromVertices(vertices);
				vertices.clear();
				graphs.add(g);
			}
		}

		return graphs;

	}

	public void SCCDFSUtil(int v, HashSet visited, HashMap<Integer, HashSet<Integer>> transpose, List vertices) {
		vertices.add(v);
		visited.add(v);
		for (int neighbor : transpose.get(v)){
			if (!visited.contains(neighbor)){
				SCCDFSUtil(neighbor,visited,transpose,vertices);
			}
		}

	}

	public void SCCStackFill(int v,Stack s, HashSet visited){

		visited.add(v);

		for(int vertex:edges.get(v)){

			if (!visited.contains(vertex)) {
				SCCStackFill(vertex,s,visited);
			}

		}

		s.push(v);
	}

	public HashMap<Integer, HashSet<Integer>> graphTranspose() {

		HashMap<Integer,HashSet<Integer>> graphT=new HashMap<>();

		for (int v:edges.keySet()){
			graphT.put(v,new HashSet<Integer>());
		}

		for (int to:edges.keySet()){
			for (int from:edges.get(to)){
				graphT.get(from).add(to);
			}
		}

		return graphT;

	}


	public Graph graphFromVertices(List<Integer> vertices) {

		Graph g=new CapGraph();

		for (int v : vertices){
			g.addVertex(v);
		}

		for (int v : vertices){
			HashSet<Integer> neighbours=edges.get(v);
			for (int v1:vertices){
				if (neighbours.contains(v1)){
					g.addEdge(v,v1);
				}
			}
		}
		return g;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		return new HashMap<>(edges);
	}

}
