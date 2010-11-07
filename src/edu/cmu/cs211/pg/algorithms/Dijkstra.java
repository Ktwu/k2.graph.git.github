package edu.cmu.cs211.pg.algorithms;

import java.util.Map;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.Path;
import edu.cmu.cs211.pg.graph.WeightedEdge;

import java.util.PriorityQueue;
import java.util.Iterator;
import java.util.HashMap;

public class Dijkstra
{
	/**
	 * Returns the shortest path on the given graph using Dijkstra's Algorithm
	 * In this case, shortest path is defined as the path for which the total weight is minimized
	 *
	 * @param g The graph that we are searching for a path on
	 * @param start The vertex that begins the path
	 * @param goal The vertex we are trying to find a path to
	 * @throws IllegalArgumentException if either start or goal is not a vertex in g
	 * @throws IllegalArgumentException
	 *             if g has negative edge weights.
	 * @return A list of Edges that represent a path between start and goal if one exists
	 * 		   Otherwise, returns null
	 */
	public <V extends Comparable<V>,E extends WeightedEdge<V>> Path<V,E> shortestPath(Graph<V,E> g, V start, V goal)
	{		
		if (goal == null)
			throw new NullPointerException("shortestPath(g, start, goal): goal is null");
		if (!g.contains(goal))	
			throw new IllegalArgumentException("shortestPath(g, start, goal): goal is not in graph");

		return allShortestPaths(g, start).get(goal);
	}

	/**
	 * Returns the shortest path on the given graph from start to all nodes using Dijkstra's Algorithm
	 * In this case, shortest path is defined as the path for which the total weight is minimized
	 *
	 * @param g The graph that we are searching for paths
	 * @param start The vertex that begins the path
	 * @throws IllegalArgumentException if start is not a vertex in g
	 * @throws IllegalArgumentException
	 *             if g has negative edge weights
	 * @return A mapping from all vertices v to lists of paths that begin at start and end at v
	 * 			  if a path from start to v exists
	 * 			  otherwise, that mapping should be null
	 */
	public <V extends Comparable<V>,E extends WeightedEdge<V>> Map<V,Path<V,E>> allShortestPaths(Graph<V,E> g, V start)
	{
		// Throws exceptions
		if (start == null
			|| g == null)
				throw new NullPointerException("allShortestPaths(g, start): null argument!");
			
			if (!g.contains(start))
					throw new IllegalArgumentException("allShortestPaths(g, start): g does not contain start!");
			
			
			
			
		// Create our priority queues for processed children
		PriorityQueue<WeightedEdge<V>> childrenEdges = new PriorityQueue<WeightedEdge<V>>();
		
		HashMap<V, Integer> distanceMap = new HashMap<V, Integer>();
		HashMap<V, V> previousMap = new HashMap<V, V>();
		
		// Set up our return variable
		HashMap<V, Path<V,E>> returnMap = new HashMap<V, Path<V, E>>();
		
		
		
		
		// First, we add all of our nodes to our map
		// Pretend nothing is connected and set to infinite (MAX_VALUE)
		Iterator<V> iterate = g.vertices().iterator();
		while (iterate.hasNext())
		{
			V tempNode = iterate.next();
			returnMap.put(tempNode, null);
			
			distanceMap.put(tempNode, Integer.MAX_VALUE);
			previousMap.put(tempNode, start);
			
			if (tempNode == start)
				childrenEdges.add(new WeightedEdge<V>(start, tempNode, 0));
			else
				childrenEdges.add(new WeightedEdge<V>(start, tempNode, Integer.MAX_VALUE));
		}
		
		
		
		
		// Add all of our start edges to our queue
		// and set startWeight = 0
		distanceMap.put(start, 0);
		returnMap.put(start, new Path<V,E>(start));
		while(!childrenEdges.isEmpty())
		{
			// The edge we just popped off means we've finished processing 
			// the destination node.
			E tempEdge = (E)childrenEdges.poll();
			
			// If we have an infinite edge
			// Then we're done
			if (tempEdge.weight() == Integer.MAX_VALUE)
				break;
			
			// Rework our edge into something that we can add to our path
			// Meaning, it's the original edge from the graph
			tempEdge = (E)new WeightedEdge<V>
				(previousMap.get(tempEdge.dest()), 
				 tempEdge.dest(), 
				 tempEdge.weight() - distanceMap.get(previousMap.get(tempEdge.dest())));
			
			//tempEdge = g.adjacent(previousMap.get(tempEdge.dest()), tempEdge.dest());
			
			if (returnMap.get(tempEdge.src()) == null)
				returnMap.put(tempEdge.dest(), new Path<V,E>(start).cons(tempEdge));
			else if (tempEdge.dest() != start)
				returnMap.put(tempEdge.dest(), returnMap.get(tempEdge.src()).cons(tempEdge));
			
			V tempNode = tempEdge.dest();
			
			// relax the edges we can reach	
			Iterator<E> iEdge = g.outgoingEdges(tempNode).iterator();
			while (iEdge.hasNext())
			{
				tempEdge = iEdge.next();
				
				// Check the edge's cost
				if (tempEdge.weight() < 0)
					throw new IllegalArgumentException();
				
				// We only relax if the edge's weight is less than what we have
				int localWeight = tempEdge.weight() + distanceMap.get(tempEdge.src());
				int oldWeight = distanceMap.get(tempEdge.dest());
				if (localWeight < oldWeight)
				{
					// When relaxing edges, we first have to remove the node from our queue
					// Also, we reset weights
					childrenEdges.remove(new WeightedEdge<V>(start, tempEdge.dest(), oldWeight));
					distanceMap.put(tempEdge.dest(), localWeight);
					childrenEdges.add(new WeightedEdge<V>(start, tempEdge.dest(), localWeight));
					previousMap.put(tempEdge.dest(), tempEdge.src());
				}
			}
		}
		return returnMap;
	}
}

