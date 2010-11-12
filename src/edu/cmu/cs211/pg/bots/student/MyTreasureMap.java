package edu.cmu.cs211.pg.bots.student;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import edu.cmu.cs211.pg.algorithms.Dijkstra;
import edu.cmu.cs211.pg.algorithms.Kruskal;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.game.TurnInformation;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.WeightedEdge;
/**
 * A partial implementation of a storage structure for information during the game
 * 
 * Our TreasureMap is our data storage class.
 * Using it, we determine what our island is,
 * where everything is,
 * and which nodes we've checked for gold
 */
public class MyTreasureMap implements TreasureMap
{	
	//Interesting state
	Graph<PirateNode,WeightedEdge<PirateNode>> island = new MyDirectedGraph<PirateNode,WeightedEdge<PirateNode>>();
	
	// Stuff to keep track of the location of
	PirateNode port = null;
	PirateNode nitrite = null;
	PirateNode myloc = null;
	Stack<PirateNode> gold = new Stack<PirateNode>();
	
	// Now what have we actually found?
	boolean hasNitrite = false;
	boolean hasRealGold = false;
	
	// We need some variables for running DFS on our map
	HashSet<PirateNode> traverseDFS = new HashSet<PirateNode>();
	Stack<PirateNode> queuedDFS = new Stack<PirateNode>();
	
	//Other classes
	Dijkstra dijkstra = new Dijkstra();
	Kruskal kruskal = new Kruskal();
	
	public void update(TurnInformation t)
	{
		PirateNode loc = t.loc;
		myloc = loc;
		ensureNodeAdded(t,loc);
		
		// We've traversed the node we're at!
		traverseDFS.add(loc);
		
		// Did we strike gold?
		if (t.haveRealGold())
			hasRealGold = true;
		if (t.atNatives())
			hasNitrite = true;
		
		// Look at our surroundings and try to add them to our map!
		for( WeightedEdge<PirateNode> e : t.edges )
		{
			PirateNode dest = e.dest();
			ensureNodeAdded(null,dest);
			
			//add edge and symmetric edge
			island.addEdge(new WeightedEdge<PirateNode>(e.src(),e.dest(),e.weight()));
			island.addEdge(new WeightedEdge<PirateNode>(e.dest(),e.src(),e.weight()));
		}
	}
	
	public void ensureNodeAdded(TurnInformation t, PirateNode loc)
	{		
		if( t != null )
		{
			if( port == null && t.atPort() )
				port = loc;

			if (!gold.contains(loc) && t.atFakeOrRealGold())
				gold.add(loc);
			
			if( nitrite == null && t.atNatives() )
				nitrite = loc;
		}
		island.addVertex(loc);
	}
	
	// Do you know where you're going?  There's a method for that
	// Meaning that, this method will tell you where to go
	public PirateNode travelTo(PirateNode destination)
	{
		// If we're already at our destination, return null -- we've already found it!
		if (destination.equals(myloc))
			return null;
		
		return dijkstra.shortestPath(island, myloc, destination).edges().get(0).dest();
	}
	
	// When filling out our map
	// Use DFS to determine how we traverse our map!
	// We're biased towards edges with the smallest weight
	public PirateNode DFS()
	{
		WeightedEdge<PirateNode> lowest = null;
		Set<WeightedEdge<PirateNode>> edges = island.outgoingEdges(myloc);
		
		for (WeightedEdge<PirateNode> e : edges)
		{
			// We don't consider nodes that we've already been to
			// Or edges that weigh more than our chosen edge
			if ((lowest == null || e.weight() < lowest.weight()) 
				&& !traverseDFS.contains(e.dest()))
					lowest = e;
		}
		
		if (lowest == null)
		{
			// If our stack is empty
			// Then we're done traveling through our map
			// So return null
			if (queuedDFS.isEmpty())
				return null;
			else
				return queuedDFS.pop();
		}
		else
			queuedDFS.push(myloc);
		
		return lowest.dest();
	}
	
	// Reset our map for DFS search
	public void resetDFS()
	{
		queuedDFS.clear();
		traverseDFS.clear();
	}
	
	// Have we found all of our nodes?
	public boolean foundAllGold()
	{
		if (gold.size() == 3)
			return true;
		return false;
	}
	
	// Have we found real gold?
	public boolean foundRealGold()
	{
		return hasRealGold;
	}
	
	// How about nitrite?  Do we have that?
	public boolean foundNitrite()
	{
		return hasNitrite;
	}
}
