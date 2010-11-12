/**
 * GraphTest
 * TODO: I still need to add stress tests
 * 
 * @author: Kellie Medlin
 * @andrewID: kmmedlin
 */
package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.fail;

import edu.cmu.cs211.pg.graph.Edge;
import edu.cmu.cs211.pg.graph.WeightedEdge;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;

import java.util.Set;
import java.util.Iterator;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class GraphTest {
	
	@Test (expected=NullPointerException.class)
	public void Constructor_nullVertexTest()
	{
		Collection nullColl = null;
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>(nullColl);
		fail();
	}
	
	@Test (expected=NullPointerException.class)
	public void Constructor_nullGraphTest()
	{
		MyDirectedGraph<String,Edge<String>> nullGraph = null;
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>(nullGraph);
		fail();
	}
	
	@Test (expected=NullPointerException.class)
	public void Constructor_nullVerticesTest()
	{
		Collection<String> nullColl = null;
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>(nullColl, new HashSet<Edge<String>>());
		fail();
	}
	
	@Test (expected=NullPointerException.class)
	public void Constructor_nullEdgesTest()
	{
		Collection<Edge<String>> nullColl = null;
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>(new HashSet<String>(), nullColl);
		fail();
	}
	
	
	
	@Test
	public void createTest ()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
	}
	
	@Test
	public void simpleAddVertexTest ()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		for (int i = 0; i < vertices.length; i++)
			assertEquals(g.addVertex(vertices[i]), true);
		
		Set<String> myVertices = g.vertices();
		assertEquals(myVertices.size(), vertices.length);
		
		for (int i = 0; i < vertices.length; i++)
			assertEquals(myVertices.contains(vertices[i]), true);
	}
	@Test
	public void simpleAddEdgeTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
		
		Iterator<Edge<String>> iterate = edges.iterator();
		while (iterate.hasNext())
			assertEquals(g.addEdge(iterate.next()), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.contains(g.adjacent(vertices[0], vertices[i])), true);	
	}
	
	/**
	 * Check that, if we add edges with different weights, we only add one
	 */
	@Test
	public void weightedEdgeAddTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C"};
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		assertEquals(g.addEdge(new WeightedEdge<String>("A", "B", 1)), true);
		assertEquals(g.addEdge(new WeightedEdge<String>("A", "B", 2)), false);
	}
	
	/**
	 * Check that, if we take away edges with different weights, we only remove one
	 */
	@Test
	public void weightedEdgeRemoveTest()
	{
		MyDirectedGraph<String,WeightedEdge<String>> g = new MyDirectedGraph<String,WeightedEdge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C"};
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		assertEquals(g.addEdge(new WeightedEdge<String>("A", "B", 1)), true);
		assertEquals(g.addEdge(new WeightedEdge<String>("A", "B", 2)), false);
		
		assertEquals(g.removeEdge("A", "B"), true);
		assertEquals(g.removeEdge("A", "B"), false);		
	}
	
	/**
	 * Test that we can remove edges, and that we can't re-remove edges
	 */
	@Test
	public void simpleRemoveEdgeTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
		assertEquals(g.addEdges(edges), true);
		
		for (int i = 1; i < vertices.length; i++)
		{
			assertEquals(g.removeEdge(vertices[0], vertices[i]), true);
			assertEquals(g.removeEdge(vertices[0], vertices[i]), false);
		}
		
		assertEquals(g.outgoingEdges(vertices[0]).size(), 0);
	}
	
	/**
	 * Test for clearing edges from our graph
	 */
	@Test
	public void clearEdgesTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
		assertEquals(g.addEdges(edges), true);
		
		g.clearEdges();
		
		//assertEquals(g.numEdges(), 0);
		
		Set<String> myVertices = g.vertices();
		Iterator<String> iterate = myVertices.iterator();
		while (iterate.hasNext())
			assertEquals(g.outgoingEdges(iterate.next()).size(), 0);
	}
	
	
	
	
	/**
	 *  Add by the collectionAdd method, to test mass addition of vertices
	 */
	@Test
	public void simpleCollectionAddVertexTest ()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
			assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		Set<String> myVertices = g.vertices();
		assertEquals(myVertices.size(), vertices.length);
		
		for (int i = 0; i < vertices.length; i++)
			assertEquals(myVertices.contains(vertices[i]), true);
	}
	
	/**
	 *  Add by the collectionAdd method, to test mass addition of edges
	 */
	@Test
	public void simpleCollectionAddEdgeTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
		
		assertEquals(g.addEdges(edges), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.contains(g.adjacent(vertices[0], vertices[i])), true);	
	}
	
	/**
	 * Test for asserting that we can't add an edge twice
	 */
	@Test
	public void duplicateAddEdgeTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		for (int i = 1; i < vertices.length; i++)
		{
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), false);
		}
		
		Iterator<Edge<String>> iterate = edges.iterator();
		while (iterate.hasNext())
			assertEquals(g.addEdge(iterate.next()), true);
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.contains(g.adjacent(vertices[0], vertices[i])), true);	
	}
	
	
	// What happens if we try to add a duplicate vertex?
	@Test
	public void duplicateAddVertexTest ()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "D" };
		for (int i = 0; i < vertices.length-1; i++)
			assertEquals(g.addVertex(vertices[i]), true);
		assertEquals(g.addVertex(vertices[vertices.length-1]), false);
		
		Set<String> myVertices = g.vertices();
		assertEquals(myVertices.size(), vertices.length-1);
		
		for (int i = 0; i < vertices.length; i++)
			assertEquals(myVertices.contains(vertices[i]), true);
	}
	
	// What happens if we have a duplicate item in our collection?
	@Test
	public void duplicateCollectionAddVertexTest ()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "D" };
			assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		
		Set<String> myVertices = g.vertices();
		assertEquals(myVertices.size(), vertices.length-1);
		
		for (int i = 0; i < vertices.length; i++)
			assertEquals(myVertices.contains(vertices[i]), true);
	}
	
	/**
	 * We shouldn't be able to add illegal edges
	 */
	@Test (expected=IllegalArgumentException.class)
	public void illegalAddEdgeTest()
	{
		MyDirectedGraph<String,Edge<String>> g = new MyDirectedGraph<String,Edge<String>>();
		assertEquals(0, g.vertices().size());
		
		String[] vertices = { "A", "B", "C", "D", "E" };
		
		assertEquals(g.addVertices(Arrays.asList(vertices)), true);
		assertEquals(g.addEdge(new Edge<String>(vertices[0], "AA")), true);
		fail();
	}
	
	/**
	 * Check that we return the appropriate edges
	 */
	@Test
	public void outgoingEdgesTest()
	{
		String[] vertices = { "A", "B", "C", "D", "E" };
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		for (int i = 1; i < vertices.length; i++)
			assertEquals(edges.add(new Edge<String>(vertices[0], vertices[i])), true);
		
		MyDirectedGraph<String,Edge<String>> g = 
			new MyDirectedGraph<String,Edge<String>>(Arrays.asList(vertices), edges);
		
		assertEquals(g.outgoingEdges(vertices[0]).containsAll(edges), true);
		assertEquals(edges.containsAll(g.outgoingEdges(vertices[0])), true);
	}
	
	/**
	 * Check that we return the appropriate vertices
	 */
	@Test
	public void outgoingVerticesTest()
	{
		String[] vertex = { "A", "B", "C", "D", "E" };
		String[] neighborsOfA = { "B", "C", "D", "E" };
		List<String> vertices = Arrays.asList(vertex);
		HashSet<Edge<String>> edges = new HashSet<Edge<String>>();
		
		for (int i = 1; i < vertices.size(); i++)
			assertEquals(edges.add(new Edge<String>(vertex[0], vertex[i])), true);
		
		MyDirectedGraph<String,Edge<String>> g = 
			new MyDirectedGraph<String,Edge<String>>(vertices, edges);
		
		vertices = Arrays.asList(neighborsOfA);
		
		assertEquals(g.outgoingNeighbors(vertex[0]).containsAll(vertices), true);
		assertEquals(vertices.containsAll(g.outgoingNeighbors(vertex[0])), true);
	}
	
	/**
	 * Test functionality on a simple graph
	 */
	@Test
	public void simpleGraphTest()
	{
		String[] vertex = { "A", "B", "C", "D", "E" };
		
		MyDirectedGraph<String,Edge<String>> g = 
			new MyDirectedGraph<String,Edge<String>>(Arrays.asList(vertex));
		
		// Can't add edges to ourself...but the staff code says otherwise :(
		// CAN OR CAN WE NOT ADD EDGES TO OURSELF???
		for (int i = 0; i < vertex.length; i++)
			assertEquals(g.addEdge(new Edge<String>(vertex[i], vertex[i])), true);
		
		// Can't add multiple edges between nodes
		for (int i = 1; i < vertex.length; i++)
		{
			assertEquals(g.addEdge(new Edge<String>(vertex[0], vertex[i])), true);
			assertEquals(g.addEdge(new Edge<String>(vertex[0], vertex[i])), false);
		}
	}
	
	/**
	 * Do we get the right adjacent edges?
	 */
	@Test
	public void adjacencyTest()
	{
		String[] vertex = { "A", "B", "C", "D", "E" };
		
		MyDirectedGraph<String,Edge<String>> g = 
			new MyDirectedGraph<String,Edge<String>>(Arrays.asList(vertex));
		
		// Can't add edges to ourself
		for (int i = 0; i < vertex.length-1; i++)
			assertEquals(g.addEdge(new Edge<String>(vertex[i], vertex[i+1])), true);

		// Can't add multiple edges between nodes
		for (int i = 0; i < vertex.length-1; i++)
			assertEquals(g.adjacent(vertex[i], vertex[i+1]), new Edge(vertex[i], vertex[i+1]));
	}
	
	/**
	 * Test functionality of graph when given a lot of elements
	 */
	@Test
	public void stressTest()
	{
		String uniqueString;
		uniqueString = "A";
			
		HashSet<Edge<String>> addedEdges = new HashSet<Edge<String>>();
		HashSet<String> addedVertices = new HashSet<String>();
		
		int stressCap = 1000;
		float random = 0.f;
		Random r = new Random();
		
		addedVertices.add("A");
		
		for (int i = 0; i < stressCap; i++)
		{
			random = r.nextFloat();
			if (random > .7)
				addedEdges.add(new Edge<String>(uniqueString + "A", uniqueString));
			
			uniqueString += "A";
			addedVertices.add(uniqueString);	
		}
		
		MyDirectedGraph<String,Edge<String>> g = 
			new MyDirectedGraph<String,Edge<String>>(addedVertices, addedEdges);
		
		Iterator<Edge<String>> iterateEdge = addedEdges.iterator();
		while (iterateEdge.hasNext())
		{
			Edge<String> tempEdge = iterateEdge.next();
			assertEquals(g.addEdge(tempEdge), false);
			assertEquals(g.removeEdge(tempEdge.src(), tempEdge.dest()), true);
			assertEquals(g.removeEdge(tempEdge.src(), tempEdge.dest()), false);
			
			assertEquals(g.addEdge(tempEdge), true);
		}
	}
}
