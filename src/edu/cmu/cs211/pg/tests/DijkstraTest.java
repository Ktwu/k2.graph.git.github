package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.fail;

import edu.cmu.cs211.pg.graph.*;
import edu.cmu.cs211.pg.algorithms.Dijkstra;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DijkstraTest {
	
	// 2nd stress test
	@Test
	public void stress2Test()
	{
		MyDirectedGraph<Integer,WeightedEdge<Integer>> g = new MyDirectedGraph<Integer,WeightedEdge<Integer>>();
		Dijkstra myD = new Dijkstra();
		Random r = new Random();
		
		for (int i = 0; i < 50; i++)
			g.addVertex(Integer.valueOf(i));
		
		int numEdges = r.nextInt(300);
		for (int i = 0; i < numEdges; i++)
			g.addEdge(new WeightedEdge<Integer>(Integer.valueOf(r.nextInt(50)), Integer.valueOf(r.nextInt(50)), r.nextInt(50)));
			
		Map<Integer,Path<Integer, WeightedEdge<Integer>>> paths = myD.allShortestPaths(g, Integer.valueOf(49));
		for (int i = 0; i < 49; i++) {
			if (paths.containsKey(Integer.valueOf(i)));
		}
	}
	
	/*************************** NULL TESTS *********************************/
	@Test (expected=NullPointerException.class)
	public void nullGraphTest()
	{
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(null, "B", "A");	
	}
	
	@Test (expected=NullPointerException.class)
	public void nullStartTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(g, null, "A");	
	}
	@Test (expected=NullPointerException.class)
	public void nullGoalTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(g, "A", null);	
	}
	
	
	
	
	/**
	 * Do we return null if there is no path between two nodes?
	 */
	@Test
	public void noPathTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		
		Dijkstra myD = new Dijkstra();
		assertEquals(myD.shortestPath(g, "A", "B"), null);
	}
	
	/**
	 * If our graph has a negative edge
	 * even if we never consider it from a given node
	 * we still want to throw an illegalArgumentException
	 */
	@Test (expected=IllegalArgumentException.class)
	public void negativeEdgeTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("X");
		g.addVertex("Y");
		
		g.addEdge(new WeightedEdge<String>("A", "B", 1));
		g.addEdge(new WeightedEdge<String>("A", "C", 1));
		g.addEdge(new WeightedEdge<String>("C", "D", 1));
		
		// Even though we never consider this edge
		// We should still throw an illegalArgumentException
		g.addEdge(new WeightedEdge<String>("X", "Y", -1));
		
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(g, "A", "D");
		fail();
	}
	
	/**
	 * 'Complex' just means that it's a little more complex than the above test
	 */
	@Test (expected=IllegalArgumentException.class)
	public void ComplexNegativeEdgeTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		g.addVertex("D");
		g.addVertex("E");
		
		g.addEdge(new WeightedEdge<String>("A", "B", 7));
		g.addEdge(new WeightedEdge<String>("A", "C", 8));
		g.addEdge(new WeightedEdge<String>("D", "E", -1));
		g.addEdge(new WeightedEdge<String>("B", "E", -1));
		
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(g, "A", "E");
		fail();
	}
	
	/**
	 * Simplest case, just to check for functionality
	 */
	@Test 
	public void sanityTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");
		
		g.addEdge(new WeightedEdge<String>("A", "B", 1));
		g.addEdge(new WeightedEdge<String>("B", "C", 1));
		g.addEdge(new WeightedEdge<String>("A", "C", 3));
		
		Dijkstra myD = new Dijkstra();
		assertEquals(myD.shortestPath(g, "A", "C").pathWeight(), 2);
	}
	
	/**
	 * I'm using a more complex graph I found on Wikipedia, which demonstrated
	 * what Dijkstra's should return on this given graph.  This just tests for more
	 * basic functionality, but on a more distinct graph
	 */
	@Test
	public void complexTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = 
			new MyDirectedGraph<String,WeightedEdge<String>>(Arrays.asList("1", "2", "3", "4", "5", "6"));
		
		g.addEdge(new WeightedEdge<String>("1", "2", 7));
		g.addEdge(new WeightedEdge<String>("2", "1", 7));
		
		g.addEdge(new WeightedEdge<String>("1", "6", 14));
		g.addEdge(new WeightedEdge<String>("6", "1", 14));
		
		g.addEdge(new WeightedEdge<String>("1", "3", 9));
		g.addEdge(new WeightedEdge<String>("3", "1", 9));
		
		g.addEdge(new WeightedEdge<String>("2", "3", 10));
		g.addEdge(new WeightedEdge<String>("3", "2", 10));
		
		g.addEdge(new WeightedEdge<String>("2", "4", 15));
		g.addEdge(new WeightedEdge<String>("4", "2", 15));
		
		g.addEdge(new WeightedEdge<String>("3", "4", 11));
		g.addEdge(new WeightedEdge<String>("4", "3", 11));
		
		g.addEdge(new WeightedEdge<String>("3", "6", 2));
		g.addEdge(new WeightedEdge<String>("6", "3", 2));
		
		g.addEdge(new WeightedEdge<String>("4", "5", 6));
		g.addEdge(new WeightedEdge<String>("5", "4", 6));
		
		g.addEdge(new WeightedEdge<String>("5", "6", 9));
		g.addEdge(new WeightedEdge<String>("6", "5", 9));
		
		Dijkstra myD = new Dijkstra();
		assertEquals(myD.shortestPath(g, "1", "1").pathWeight(), 0);
		assertEquals(myD.shortestPath(g, "1", "2").pathWeight(), 7);
		assertEquals(myD.shortestPath(g, "1", "3").pathWeight(), 9);
		assertEquals(myD.shortestPath(g, "1", "6").pathWeight(), 11);
		assertEquals(myD.shortestPath(g, "1", "4").pathWeight(), 20);
		assertEquals(myD.shortestPath(g, "1", "5").pathWeight(), 20);
	}
	
	// I copied this graph from MstTspApproxTest
	@Test
	public void doubleEdgeTest()
	{
		Graph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		
		g.addVertex("a");
		g.addVertex("b");
		g.addVertex("c");
		g.addVertex("d");
		g.addVertex("e");
		
		g.addEdge(new WeightedEdge<String>("b", "a", 1));
		g.addEdge(new WeightedEdge<String>("a", "b", 1));
		
		g.addEdge(new WeightedEdge<String>("e", "a", 2));
		g.addEdge(new WeightedEdge<String>("a", "e", 2));
		
		g.addEdge(new WeightedEdge<String>("e", "d", 3));
		g.addEdge(new WeightedEdge<String>("d", "e", 3));
		
		g.addEdge(new WeightedEdge<String>("e", "c", 4));
		g.addEdge(new WeightedEdge<String>("c", "e", 4));
		
		g.addEdge(new WeightedEdge<String>("e", "b", 5));
		g.addEdge(new WeightedEdge<String>("b", "e", 5));
		
		g.addEdge(new WeightedEdge<String>("d", "c", 6));
		g.addEdge(new WeightedEdge<String>("c", "d", 6));
		
		Dijkstra myD = new Dijkstra();
		Map<String, Path<String,WeightedEdge<String>>> myMap = myD.allShortestPaths(g, "a");
		
		assertEquals(myMap.get("a").pathWeight(), 0);
		assertEquals(myMap.get("b").pathWeight(), 1);
		assertEquals(myMap.get("c").pathWeight(), 6);
		assertEquals(myMap.get("d").pathWeight(), 5);
		assertEquals(myMap.get("e").pathWeight(), 2);
	}
	
	// This code helped me realize to ALWAYS USE equals() instead of ==
	// unless I'm sure I'm dealing with primitives
	@Test
	public void stressTest()
	{
		Graph<Integer,WeightedEdge<Integer>> g = new MyDirectedGraph<Integer,WeightedEdge<Integer>>();
		
		g.addVertex(0);
		for (int i = 1; i < 1000; i++)
		{
			assertEquals(g.addVertex(i), true);
			assertEquals(g.addEdge(new WeightedEdge<Integer>(i-1, i, 3)), true);
			assertEquals(g.addEdge(new WeightedEdge<Integer>(i, i-1, 2)), true);
		}
		
		Dijkstra myD = new Dijkstra();
		for (int i = 1; i < 1000; i++)
		{
			Path<Integer, WeightedEdge<Integer>> path = myD.shortestPath(g, 0, i);
			assertEquals(path.pathWeight(), i*3);
			
			path = myD.shortestPath(g, i, 0);
			assertEquals(path.pathWeight(), i*2);
		}
	}
}
