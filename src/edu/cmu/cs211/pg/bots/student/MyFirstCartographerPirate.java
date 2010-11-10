package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;
import edu.cmu.cs211.pg.bots.student.MyTreasureMap;

public class MyFirstCartographerPirate implements Pirate
{
	private MyTreasureMap map;
	private GameInformation game;
	
	public void init(TreasureMap _map, GameInformation game)
	{
		this.map = (MyTreasureMap)_map;
		this.game = game;
		throw new RuntimeException ("You need to implement this method");
	}

	public PirateNode next()
	{
		throw new RuntimeException ("You need to implement this method");
	}
}
