package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.bots.student.MyTreasureMap;

public class GreedyCartographerPirate implements Pirate
{
	private MyTreasureMap map;
	
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
	}

	// Go around the map, using greedy DFS,
	// until we find the nitrite!
	public PirateNode next()
	{
		if (map.nitrite == null)
			return map.DFS();
		else
		{
			map.hasNitrite = false;;
			map.resetDFS();
			return map.travelTo(map.port);
		}
	}
}
