/**
 * Programmer's node: Added a method contains() to check whether a graph contains a given node
 */
package edu.cmu.cs211.pg.graph;

import java.util.Collection;
import java.util.Set;

public interface Graph<V extends Comparable<V>,E extends Edge<V>> {
	
	/**
	 * Add a vertex to the graph.
	 * @param vertex The vertex to add
	 * @throws NullPointerException if vertex is null.
	 * @return true if vertex was not present already.
	 */
	public boolean addVertex (V vertex);
	
	/**
	 * Adds multiple vertices to a graph.
	 * 
	 * @throws NullPointerException if vertices is null, or any vertex is null.
	 * @return true if and only if the set of vertices was changed by the operation.
	 */
	public boolean addVertices (Collection<? extends V> vertices);
	
	/**
	 * Adds edge e to the graph.
	 * 
	 * @param e
	 *            The edge to add.
	 * @throws IllegalArgumentException
	 *             If e is not a valid edge (eg. refers to vertices not in the graph).
	 * @throws NullPointerException
	 *             If e is null
	 * @return true If e was not already present; false if it was (in which case the graph is not updated).
	 */
	public boolean addEdge (E e);
	
	/**
	 * Adds multiple edges to a graph.
	 * 
	 * @throws NullPointerException
	 *             if edges is null or any edge in the list is null
	 * @throws IllegalArgumentException if any edge in the collection is invalid.
	 * @return true if and only if the set of edges was changed by the operation.
	 */
	public boolean addEdges (Collection<? extends E> edges);
	
	/**
	 * Remove an edge from src to dest from the graph.
	 * 
	 * @throws NullPointerException if src or dest is null.
	 * @throws IllegalArgumentException if src or dest is not in the graph.
	 * @return true if an edge from src to dest edge was present.
	 */
	public boolean removeEdge (V src, V dest);
	
	/**
	 * Returns the set of vertices in the graph.
	 * @return The set of all vertices in the graph. 
	 */
	public Set<V> vertices ();
	
	/** Removes all edges from the graph */
	public void clearEdges ();
	
	/**
	 * Tests if vertices i and j are adjacent, returning the edge between
	 * them if so.
	 * 
	 * @throws IllegalArgumentException if i or j are not vertices in the graph.
	 * @throws NullPointerException if i or j is null.
	 * @return The edge from i to j if it exists in the graph;
	 * 		   null otherwise.
	 */
	public E adjacent (V i, V j);
	
	/**
	 * Return the neighbours of a given vertex when this graph is treated as
	 * DIRECTED; that is, vertices to which vertex has an outgoing edge.
	 * 
	 * @param vertex The vertex the neighbours of which to return.
	 * @throws NullPointerException if vertex is null.
	 * @throws IllegalArgumentException if vertex is not in the graph.
	 * @return The set of neighbors of vertex.
	 */
	public Set<V> outgoingNeighbors (V vertex);
	
	/**
	 * Returns the outgoing edges from vertex. 
	 * @param vertex The vertex the outgoing edges of which to return.
	 * @throws NullPointerException if vertex is null.
	 * @throws IllegalArgumentException if vertex is not in the graph.
	 * @return The set of edges leaving vertex.
	 */
	public Set<E> outgoingEdges (V vertex);
	
	/**
	 * Checks whether the graph contains the node
	 * @param vertex The vertex that we want to check
	 * @throws NullPointerException if the vertex is null.
	 * @return True if the vertex is in the graph, false otherwise
	 */
	public boolean contains (V vertex);
}
