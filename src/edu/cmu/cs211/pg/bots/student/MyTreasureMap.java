package edu.cmu.cs211.pg.bots.student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import edu.cmu.cs211.pg.algorithms.Dijkstra;
import edu.cmu.cs211.pg.algorithms.Kruskal;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.game.TurnInformation;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.Path;
import edu.cmu.cs211.pg.graph.WeightedEdge;
/**
 * A partial implementation of a storage structure for information during the game
 * 
 * NOTE: You can change anything you like in this class...this is only a sample partial implementation.
 */
public class MyTreasureMap implements TreasureMap
{	
	//Interesting state
	Graph<PirateNode,WeightedEdge<PirateNode>> island = new MyDirectedGraph<PirateNode,WeightedEdge<PirateNode>>();
	PirateNode port = null;
	PirateNode natives = null;
	PirateNode myloc = null;
	
	TreeSet<PirateNode> gold = new TreeSet<PirateNode>();
	HashSet<PirateNode> traversedNodes = new HashSet<PirateNode>();
	
	//Other classes
	Dijkstra dijkstra = new Dijkstra();
	Kruskal kruskal = new Kruskal();
	
	public void update(TurnInformation t)
	{
		PirateNode loc = t.loc;
		myloc = loc;
		ensureNodeAdded(t,loc);
		traversedNodes.add(loc);
		
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
			{
				port = loc;
			}
			if( natives == null && t.atNatives() )
			{
				natives = loc;
			}
			if (gold.contains(loc) && t.atFakeOrRealGold())
			{
				gold.add(loc);
			}
		}
		island.addVertex(loc);
	}
	
	public PirateNode pickRandomAdjacentNode()
	{
		List<PirateNode> next = new ArrayList<PirateNode>(island.outgoingNeighbors(myloc));
		return next.get(new Random().nextInt(next.size()));
	}
	
	public PirateNode travelTo(PirateNode destination)
	{
		return dijkstra.shortestPath(island, myloc, destination).dest();
	}
	
	public PirateNode pickLowestWeightNewNode()
	{
		WeightedEdge<PirateNode> lowest = null;
		Set<WeightedEdge<PirateNode>> edges = island.outgoingEdges(myloc);
		
		for (WeightedEdge<PirateNode> e : edges)
		{
			if ((lowest == null || e.weight() < lowest.weight()) 
				&& !traversedNodes.contains(e.dest()))
					lowest = e;
		}
		
		if (lowest == null)
			return null;
		return lowest.dest();
	}
}
