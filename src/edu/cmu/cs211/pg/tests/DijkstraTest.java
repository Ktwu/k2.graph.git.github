package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.fail;

import edu.cmu.cs211.pg.graph.*;
import edu.cmu.cs211.pg.algorithms.Dijkstra;

import java.util.Arrays;
import java.util.Map;

public class DijkstraTest {
	
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
	
	@Test
	public void noPathTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		
		Dijkstra myD = new Dijkstra();
		assertEquals(myD.shortestPath(g, "A", "B"), null);
	}
	
	@Test (expected=IllegalArgumentException.class)
	public void negativeEdgeTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		g.addVertex("A");
		g.addVertex("B");
		
		g.addEdge(new WeightedEdge<String>("A", "B", -1));
		
		Dijkstra myD = new Dijkstra();
		myD.shortestPath(g, "A", "B");
		fail();
	}
	
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
		
		// For some reason FrontDesk doesn't like the vertices() method
		/*assertEquals(myMap.get("a").vertices().isEmpty(), true);
		assertEquals(myMap.get("b").vertices(), Arrays.asList("b"));
		assertEquals(myMap.get("c").vertices(), Arrays.asList("e", "c"));
		assertEquals(myMap.get("d").vertices(), Arrays.asList("e", "d"));
		assertEquals(myMap.get("e").vertices(), Arrays.asList("e"));*/
	}
}
