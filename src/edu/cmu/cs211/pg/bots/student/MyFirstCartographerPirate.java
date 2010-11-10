package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.bots.student.MyTreasureMap;

import java.util.HashSet;

public class MyFirstCartographerPirate implements Pirate
{
	private MyTreasureMap map;
	private GameInformation game;
	private int numberOfNodes;
	HashSet<PirateNode> traversedNods;
	
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
		this.game = game;
		
		// Set up the nodes where we have gold
		numberOfNodes = game.numberOfVertices();
		game.
		
		//throw new RuntimeException ("You need to implement this method");
	}

	public PirateNode next()
	{
		throw new RuntimeException ("You need to implement this method");
	}
}
