package edu.cmu.cs211.pg.algorithms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.Path;
import edu.cmu.cs211.pg.graph.WeightedEdge;

public class MstTspApproximation<V extends Comparable<V>>
{
	private Dijkstra dijkstra;
	private Kruskal kruskal;
	
	protected MstTspApproximation(){}
	
	public MstTspApproximation(Kruskal kruskal, Dijkstra dijkstra)
	{
		this.kruskal = kruskal;
		this.dijkstra = dijkstra;
	}
	
	/**
	 * Generates a tour of a specified set of nodes in the graph using the
	 * MST-TSP 2-competitive approximation algorithm.
	 * 
	 * In order to aid in the unit testing of this class, we require
	 * that the order you visit neighbors in the DFS portion of
	 * the algorithm is the same as the natural ordering of the vertex
	 * type V. You should also use the instances of Kruskal and
	 * Dijkstra passed into the constructor of this class, not your own.
	 * 
	 * @param g the graph to generate the tour on. This UNDIRECTED
	 *          graph may not be complete, but must be connected.
	 *          
	 * @param verts the vertices in the graph that you want to tour.
	 *              This may be a subset of the vertices in the graph.
	 *                       
	 * @param start the vertex at which the tour must begin and end.
	 *        start may not be in the set of vertices to tour, but
	 *        your tour must begin and end at start.
	 * 
	 * @throws IllegalArgumentException if any of the vertices in the
	 *              verts set are not in the graph. 
	 * @throws NullPointerException if g, verts, or start are null
	 * 
	 * @return A list of vertices representing the order you would visit
	 *         vertices in the original graph such that every vertex in
	 *         the specified verts set is visited at least once.
	 */
	public List<V> approximateTour(Graph<V,WeightedEdge<V>> g, Set<V> verts, V start)
	{
		throw new RuntimeException ("You need to implement this method");
	}
}
