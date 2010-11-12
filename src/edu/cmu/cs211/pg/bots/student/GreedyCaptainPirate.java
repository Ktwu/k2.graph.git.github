package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.graph.Path;
import edu.cmu.cs211.pg.graph.WeightedEdge;

public class GreedyCaptainPirate implements Pirate
{
	private MyTreasureMap map;
	
	/**
	 * Uses a greedy algorithm to find the gold, as quickly as possible
	 * then go back to the port.
	 * 
	 * I don't use any game info.  Maybe that's a bad thing?
	 * 
	 * @param map 
	 * 			The TreasureMap that represents the game board
	 */
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
	}

	/**
	 * Our pirate captain first looks for nitrite (if he knows where it is)
	 * then looks for gold using a DFS search, all while following edges of the least
	 * weight
	 */
	public PirateNode next()
	{		
		// First, have we found all our gold spots
		// or did we find real gold?
		if (map.foundAllGold() || map.foundRealGold())
			return map.travelTo(map.port);
		
		// Do we know where our nitrite is? 
		// If so, go get it!
		if (!map.foundNitrite() && map.nitrite != null)
		{
			PirateNode next = map.travelTo(map.nitrite);
			
			// Since travelTo returns null if we've reached our destination
			// Go look for gold once we got nitrite
			if (next != null)
				return next;
		}
		
		// We don't have all of our gold?  
		// Then do a DFS search, which automatically picks lowest-weight edges
		// So it's a greedy DFS
		return map.DFS();
	}
}
