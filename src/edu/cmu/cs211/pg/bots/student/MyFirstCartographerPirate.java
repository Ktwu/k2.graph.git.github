package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.bots.student.MyTreasureMap;

import edu.cmu.cs211.pg.graph.WeightedEdge;

import java.util.HashSet;
import java.util.Set;


public class MyFirstCartographerPirate implements Pirate
{
	private MyTreasureMap map;
	
	private int numberOfNodes;
	private int numberOfTurns;
	
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
		numberOfNodes = game.numberOfVertices();
		numberOfTurns  = game.lengthOfPhaseOne();
		
		//throw new RuntimeException ("You need to implement this method");
	}

	// We essentially implement depth-first search
	// Priority is given to the edge with least weight
	public PirateNode next()
	{
		PirateNode travelTo = null;
		
		// Figure our where we want to go
		if (map.traversedNodes.size() == numberOfNodes)
			travelTo = map.travelTo(map.port);
		else
		{
			travelTo = map.pickLowestWeightNewNode();
			
			if (travelTo == null)
				travelTo = map.travelTo(map.port);
		}
		
		numberOfTurns -= map.island.adjacent(map.myloc, travelTo).weight();
		
		return travelTo;
		//throw new RuntimeException ("You need to implement this method");
	}
}
