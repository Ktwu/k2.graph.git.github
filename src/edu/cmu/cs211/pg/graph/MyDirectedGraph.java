/**
 * MyDirectedGraph: Custom class for representing directed graphs.  
 * I use an adjacency list here, which I've implemented as a hashset from nodes to sets of nodes 
 * @author: Kellie Medlin
 * @andrewID: kmmedlin
 */
package edu.cmu.cs211.pg.graph;

import java.util.Collection;
import java.util.Set;

import java.util.HashMap;
import java.util.HashSet;

import java.util.Iterator;

public class MyDirectedGraph<V extends Comparable<V>,E extends Edge<V>> implements Graph<V,E>
{
	// Stores a map of vertices to sets of vertices
	// If we have a vertex, then the set it points to represents
	// all the edges it can take to adjacent nodes
	HashMap<V, Set<E>> adjacencyList;
	
	/**
	 * Creates an empty graph (a graph with no vertices or edges).
	 */
	public MyDirectedGraph()
	{
		adjacencyList = new HashMap<V, Set<E>>();
	}
	
	/**
	 * Creates a graph with pre-defined vertices.
	 * 
	 * @param vertices 
	 * 			The list of the vertices in the graph.
	 * @throws NullPointerException 
	 * 			If vertices is null, or contains null items
	 */
	public MyDirectedGraph (Collection<? extends V> vertices)
	{
		checkForNull(vertices, "MyDirectedGraph(vertices): given a null collection!");
		adjacencyList = new HashMap<V, Set<E>>();
		
		this.addVertices(vertices);
	}
	
	/**
	 * Creates a graph with pre-defined edges and vertices.
	 * 
	 * @param vertices
	 * 			A list of the vertices in the graph.
	 * @param edges
	 * 			A list of edges for the graph.
	 * @throws IllegalArgumentException 
	 * 			If edges contains invalid edges.
	 * @throws NullPointerException
	 *          If vertices or edges is null, or either contain null items
	 */
	public MyDirectedGraph (Collection<? extends V> vertices, Collection<? extends E> edges)
	{
		checkForNull(vertices, "MyDirectedGraph(vertices, edges): given a null collection of vertices!");
		checkForNull(edges, "MyDirectedGraph(vertices, edges): given a null collection of edges!");
		adjacencyList = new HashMap<V, Set<E>>();
		
		// First, add our nodes to our set
		this.addVertices(vertices);
		
		// Next try adding the appropriate edges
		this.addEdges(edges);
	}
	
	// QUESTION: should I worry about shallow copying?
	// Edges/nodes aren't mutable from what I can see, so no (probably)
	/**
	 * Copy Constructor
	 * 
	 * @param g
	 *            A graph to copy
	 * @throws IllegalArgumentException if g violates Graph invariants by
	 *            returning illegal edges in its outgoingEdge methods
	 * @throws NullPointerException
	 *             If g is null, or g's methods violates Graph invariants
	 *             by returning null items in vertices or outgoingEdges
	 */
	public MyDirectedGraph (Graph <V, E> g)
	{
		checkForNull(g, "MyDirectedGraph(g): graph is null!");
		adjacencyList = new HashMap<V, Set<E>>();
		
		// Create our collection of vertices
		Collection<V> vertices = g.vertices();
		
		// Add our nodes to our set
		Iterator<? extends V> vertexIterator = vertices.iterator();
		while (vertexIterator.hasNext())
			this.addVertex(vertexIterator.next());
		
		// Next add our edges
		vertexIterator = vertices.iterator();
		while (vertexIterator.hasNext())
			this.addEdges(g.outgoingEdges(vertexIterator.next()));
	}

	public boolean addVertex(V vertex)
	{
		checkForNull(vertex, "addVertex(vertex): Vertex is null!");
		
		// Check to see if we already contain the vertex
		if (!adjacencyList.containsKey(vertex))
		{
			adjacencyList.put(vertex, new HashSet<E>());
			return true;			
		}
		
		// Can't add vertices we already have
		return false;
	}
	
	public boolean addVertices(Collection<? extends V> vertices)
	{
		checkForNull(vertices, "addVertices(vertices): Given a null collection!");
		
		// If we already contain all the elements...
		if (adjacencyList.keySet().containsAll(vertices))
			return false;
		
		// Add each edge at a time
		Iterator<? extends V> vertexIterator = vertices.iterator();
		while (vertexIterator.hasNext())
			this.addVertex(vertexIterator.next());
		
		return true;
	}
	
	public boolean addEdge (E e)
	{
		checkForNull(e, "addEdge(e): Given a null edge!");
		
		// Don't add illegal edges!
		if (!adjacencyList.containsKey(e.dest()) 
			|| !adjacencyList.containsKey(e.src()))
				throw new IllegalArgumentException();
				
		// We're dealing with a simple graph--we can't point to ourself
		// But the staff code seems to say that you CAN add edges to yourself...
		// Utter confusion...
		// if (e.src().equals(e.dest()))
		//		return false;
		
		// We have to iterate through our list of edges 
		// Since we only care about whether src/dest match up
		// But custom edges might be defined to be different 
		// Like WeightedEdges, which take into account weights for comparison
		Set<E> edges = adjacencyList.get(e.src());
		for (E edge : edges)
			if (e.dest().equals(edge.dest()))
				return false;
		
		adjacencyList.get(e.src()).add(e);
		return true;
	}
	
	public boolean addEdges (Collection<? extends E> edges)
	{
		checkForNull(edges, "addEdges(edges): Given a null collection!");
		
		// First check that we have new edges
		if (adjacencyList.values().containsAll(edges))
			return false;
		
		// Add each edge at a time
		Iterator<? extends E> edgeIterator = edges.iterator();
		while (edgeIterator.hasNext())
			this.addEdge(edgeIterator.next());
		
		return true;
	}

	public boolean removeEdge(V src, V dest)
	{
		checkForNull(src, "removeEdge(src, dest): src is null!");
		checkForNull(dest, "removeEdge(src, dest): dest is null!");
		
		// We can't remove illegal edges!
		if (!adjacencyList.containsKey(src)
			|| !adjacencyList.containsKey(dest))
				throw new IllegalArgumentException();
		
		Set<E> edges = adjacencyList.get(src);
		for (E tempEdge : edges)
		{
			if (tempEdge.dest().equals(dest))
			{
				adjacencyList.get(src).remove(tempEdge);
				return true;
			}
		}
		
		return false;
	}

	public void clearEdges ()
	{
		Set<V> vertices = adjacencyList.keySet();
		if (vertices == null)
			return;
		
		Iterator<V> vertexIterator = vertices.iterator();
		while (vertexIterator.hasNext())	
			adjacencyList.get(vertexIterator.next()).clear();
	}

    public Set<V> vertices ()
	{
    	return adjacencyList.keySet();
    }
    
	public E adjacent (V i, V j)
	{
		checkForNull(i, "adjacent(i, j): i is null!");
		checkForNull(j, "adjacent(i, j): j is null!");
		
		// We're never adjacent to illegal edges
		if (!adjacencyList.containsKey(i)
			|| !adjacencyList.containsKey(j))
				throw new IllegalArgumentException();
		
		// Look at each edge at a time
		Set<E> edges = adjacencyList.get(i);
		for (E tempEdge : edges)
			if (tempEdge.dest().equals(j))
				return tempEdge;
		
		return null;
	}
	
	public Set<V> outgoingNeighbors (V vertex)
	{
		checkForNull(vertex, "outgoingNeighbors(vertex): vertex is null!");
		if (!adjacencyList.containsKey(vertex))
			throw new IllegalArgumentException();
		
		HashSet<V> neighbors = new HashSet<V>();
		Iterator<E> edges = adjacencyList.get(vertex).iterator();
		while (edges.hasNext())
			neighbors.add(edges.next().dest());
		
		return neighbors;
	}
	public Set<E> outgoingEdges (V vertex)
	{
		checkForNull(vertex, "outgoingEdges(vertex): vertex is null!");
		if (!adjacencyList.containsKey(vertex))
			throw new IllegalArgumentException();
		
		return adjacencyList.get(vertex);
	}
	
	/**
	 * checkForNull(): Convenient method for checking null objects
	 * and throwing exceptions
	 * @param o The object we want to check
	 * @param message What to print if our object is null
	 * @throws NullPointerException
	 */
	private void checkForNull(Object o, String message) throws NullPointerException
	{
		if (o == null)
			throw new NullPointerException(message);
	}
	
	/**
	 * numEdges(): returns the number of edges in our graph
	 * @return number of edges
	 */
	public int numEdges()
	{
		int size = 0;
		
		for (V vertex : adjacencyList.keySet())
			size += adjacencyList.get(vertex).size();
		
		return size;
	}
}

