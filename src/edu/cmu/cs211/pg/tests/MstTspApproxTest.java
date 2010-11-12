package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.LinkedList;

import org.junit.Test;

import edu.cmu.cs211.pg.algorithms.Dijkstra;
import edu.cmu.cs211.pg.algorithms.Kruskal;
import edu.cmu.cs211.pg.algorithms.MstTspApproximation;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.WeightedEdge;

public class MstTspApproxTest
{
	private static class Node implements Comparable<Node>
	{
		private String id;
		public Node(String id)
		{
			this.id = id;
		}
		public int compareTo(Node n)
		{
			return id.compareTo(n.id);
		}
		public String toString()
		{
			return id;
		}
	}
	
	public void addMirroredEdges(
		Graph<Node,WeightedEdge<Node>> g,
		Node a,
		Node b,
		int w
	)
	{
		g.addEdge(new WeightedEdge<Node>(a,b,w));
		g.addEdge(new WeightedEdge<Node>(b,a,w));
	}
	
	/**
	 * Test for functionality of our MstTspTest
	 */
	@Test (timeout=1000)
	public void basicMstTspTest()
	{
		Dijkstra dij = new Dijkstra();
		Kruskal kru = new Kruskal();
		Graph<Node,WeightedEdge<Node>> g = new MyDirectedGraph<Node,WeightedEdge<Node>>();
		MstTspApproximation<Node> tsp = new MstTspApproximation<Node>(kru,dij);
		
		Node a = new Node("a");
		Node b = new Node("b");
		Node c = new Node("c");
		Node d = new Node("d");
		Node e = new Node("e");
		g.addVertex(a);
		g.addVertex(b);
		g.addVertex(c);
		g.addVertex(d);
		g.addVertex(e);
		
		addMirroredEdges(g,a,b,1);
		addMirroredEdges(g,e,a,2);
		addMirroredEdges(g,e,d,3);
		addMirroredEdges(g,e,c,4);
		addMirroredEdges(g,e,b,5);
		addMirroredEdges(g,d,c,6);
		
		List<Node> tour = tsp.approximateTour(g, g.vertices(), e);
		assertEquals("tour = " + tour + ". tour was wrong size.",7,tour.size());
		
		assertEquals("1st node in tour wrong",a,tour.get(0));
		assertEquals("2nd node in tour wrong",b,tour.get(1));
		assertEquals("3rd node in tour wrong",a,tour.get(2));
		assertEquals("4th node in tour wrong",e,tour.get(3));
		assertEquals("5th node in tour wrong",c,tour.get(4));
		assertEquals("6th node in tour wrong",d,tour.get(5));
		assertEquals("7th node in tour wrong",e,tour.get(6));
	}
	
	/**
	 * Does our code break if we have a lot of elements in our graph?
	 */
	@Test
	public void stressTest()
	{
		Dijkstra dij = new Dijkstra();
		Kruskal kru = new Kruskal();
		Graph<Integer,WeightedEdge<Integer>> g = new MyDirectedGraph<Integer,WeightedEdge<Integer>>();
		MstTspApproximation<Integer> tsp = new MstTspApproximation<Integer>(kru,dij);
		
		g.addVertex(0);
		for (int i = 1; i < 100; i++)
		{
			assertEquals(g.addVertex(i), true);
			assertEquals(g.addEdge(new WeightedEdge<Integer>(i-1, i, 3)), true);
			assertEquals(g.addEdge(new WeightedEdge<Integer>(i, i-1, 2)), true);
		}
		
		List<Integer> tour = tsp.approximateTour(g, g.vertices(), 0);
		List<Integer> correctTour = new LinkedList<Integer>();
		
		for (int i = 1; i < 99; i++)
			correctTour.add(i);
		
		for (int i = 99; i >= 0; i--)
			correctTour.add(i);
		
		assertEquals(tour.size(), correctTour.size());
		for (int i = 0; i < tour.size(); i++)
			assertEquals(tour.get(i), correctTour.get(i));
	}
}
