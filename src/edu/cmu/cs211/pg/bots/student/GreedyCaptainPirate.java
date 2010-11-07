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
	 * @param map 
	 * 			The TreasureMap that represents the game board
	 */
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
		throw new RuntimeException ("You need to implement this method");
	}

	public PirateNode next()
	{
		throw new RuntimeException ("You need to implement this method");
	}
}
