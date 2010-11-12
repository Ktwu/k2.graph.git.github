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
	private GameInformation game;
	
	private int numberOfTurns;
	
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
		this.game = game;
	
		numberOfTurns  = 0;
	}

	// We essentially implement depth-first search
	// Priority is given to the edge with least weight
	// So this means that our cartographer is GREEDY
	public PirateNode next()
	{
		// Go back to port if phase 2 is about to begin
		// We use a simple heuristic for determining if we have enough time to 
		// go back to port
		if (game.lengthOfPhaseOne()/3 * 2 <= numberOfTurns)
		{
			// reset our map and nitrite flag
			// since our captains will need to make use of DFS
			// and will need to find the nitrite themselves
			map.resetDFS();
			map.hasNitrite = false;
			
			return map.travelTo(map.port);
		}
		
		// Otherwise explore the island via DFS
		PirateNode travelTo = map.DFS();
		
		// Increase our timer
		if (travelTo != null)
			numberOfTurns += map.island.adjacent(map.myloc, travelTo).weight();
		else
		{
			map.hasNitrite = false;
			map.resetDFS();
		}
		
		return travelTo;
	}
}
