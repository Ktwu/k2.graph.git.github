package edu.cmu.cs211.pg.algorithms;

import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.WeightedEdge;

import edu.cmu.cs211.pg.algorithms.UnionFind;

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.Comparator;

/**
 * An undirected graph MST interface.
 * 
 * V and E are the vertex and edge types for the graph that we will be searching.
 * That is, this function returns a graph (Graph<V,E>), and takes a graph.
 */
public class Kruskal 
{
    /**
     * Returns a minimum spanning tree of g.
     * 
     * Note: You should only add the edges in one direction.  
     * (Make sure the graph you return has the property |V|=|E|+1)
     * 
     * @param g The graph of which to get the minimum spanning tree
     * 
     * @throws NullPointerException if g is null
     * 
     * @return A graph that is an MST of g or null if no MST exists
     */
	public <V extends Comparable<V>,E extends WeightedEdge<V>> Graph<V,E> MST(Graph<V,E> g) 
	{
		if (g == null)
			throw new NullPointerException("MST(g): g is null!");
		
		int numEdges = 0; // we count the number of edges in the final MST
		
		Graph<V,E> kruskal = new MyDirectedGraph<V,E>(g);
		UnionFind<V> uf = new UnionFind<V>();
				
		// B|
		// I need to implement a comparator that sorts based on weight
		// Not on vertices
		Iterator<V> iNodes = kruskal.vertices().iterator();
		PriorityQueue<E> edges = new PriorityQueue<E>(kruskal.vertices().size(), new edgeComparator<E>());
		
		while (iNodes.hasNext())
			edges.addAll(g.outgoingEdges(iNodes.next()));
		
		kruskal.clearEdges();
		
		// Just go through our edges and select the least weighted ones first
		while (!edges.isEmpty())
		{
			WeightedEdge<V> popped = edges.poll();
			
			// We just add elements to our union find whenever we try to
			// union two elements or find a given element
			if (!uf.find(popped.dest()).equals(uf.find(popped.src())))
			{
				uf.union(popped.dest(), popped.src());
				kruskal.addEdge((E)popped);
				numEdges++;
			}
		}
		
		// Check that we've connected all of our nodes
		if (uf.uniqueSets() != 1)
			return null;
		
		// check that |V| = |E| + 1
		if (kruskal.vertices().size() != numEdges + 1)
			return null;
		
		return kruskal;
	}
	
	// B|
	// The WeightedEdge compare judges on VERTICES first, not weight
	// Since I don't know whether I should change WeightedEdge
	// I just made a personal comparator instead
	private static class edgeComparator<E extends WeightedEdge> implements Comparator<E>
	{
		public int compare(E o1, E o2)
		{
			if (o1 == null && o2 == null)
				return 0;
			else if (o1 == null)
				return -1;
			else if (o2 == null)
				return 1;
			
			if (o1.weight() < o2.weight())
				return -1;
			else if (o1.weight() > o2.weight())
				return 1;
			else
				return 0;
		}
	}
}
