package edu.cmu.cs211.pg.bots.given;

import edu.cmu.cs211.pg.bots.student.MyTreasureMap;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;

public class CaptainBrainSlug implements Pirate
{
	private MyTreasureMap map;
	@SuppressWarnings("unused")
	private GameInformation game;
	
	public void init(TreasureMap _map, GameInformation _game)
	{
		this.map = (MyTreasureMap)_map;
		this.game = _game;
	}

	/* Do a random walk of the map*/
	public PirateNode next()
	{
		return map.pickRandomAdjacentNode();
	}

}
