package edu.cmu.cs211.pg.bots.given;


import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;

public class CartographerBrainSlug implements Pirate
{
	public void init(TreasureMap _map, GameInformation _game)
	{
		//There's nothing to init...we don't care about any of this.
	}

	public PirateNode next()
	{
		//The cartographer doesn't do anything
		return null;
	}
}
