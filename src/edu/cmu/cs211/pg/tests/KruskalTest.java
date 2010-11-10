package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.fail;

import edu.cmu.cs211.pg.graph.WeightedEdge;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.algorithms.Kruskal;

import java.util.HashSet;
import java.util.Arrays;


public class KruskalTest {

	@Test
	public void sanityTest()
	{
		Kruskal k = new Kruskal();
		
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C" };
		HashSet<WeightedEdge<String>> edges = new HashSet<WeightedEdge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
	
		assertEquals(edges.add(new WeightedEdge<String>("A", "B", 1)), true);
		assertEquals(edges.add(new WeightedEdge<String>("C", "B", 2)), true);
		assertEquals(edges.add(new WeightedEdge<String>("A", "C", 3)), true);
		
		assertEquals(g.addEdges(edges), true);
		
		Graph<String, WeightedEdge<String>> mst = k.MST(g);
		assertEquals("MST shouldn't be null", mst!=null, true);
		
		assertEquals("Checking whether MST contains A->B...", 
				mst.adjacent("A", "B"), new WeightedEdge<String>("A", "B", 1));
		assertEquals("Checking whether MST contains C->B...", 
				mst.adjacent("C", "B"), new WeightedEdge<String>("C", "B", 2));
		assertEquals("Checking whether MST contains A->C...", 
				mst.adjacent("A", "C"), null);
	}
	
	@Test
	public void noMstTest()
	{
		Kruskal k = new Kruskal();
		
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D" };
		HashSet<WeightedEdge<String>> edges = new HashSet<WeightedEdge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
	
		assertEquals(edges.add(new WeightedEdge<String>("A", "B", 1)), true);
		assertEquals(edges.add(new WeightedEdge<String>("C", "D", 2)), true);
		
		assertEquals(g.addEdges(edges), true);
		
		Graph<String, WeightedEdge<String>> mst = k.MST(g);
		assertEquals("MST should be null", mst, null);
	}
	
	@Test
	public void doubleEdgeTest()
	{
		Kruskal k = new Kruskal();
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
		
		Graph<String, WeightedEdge<String>> mst = k.MST(g);
		
		System.out.println(g.toString());
		
	}
	
	
}
