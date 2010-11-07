package edu.cmu.cs211.pg.bots.given;

import edu.cmu.cs211.pg.bots.student.MyTreasureMap;
import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.TeamFactory;
import edu.cmu.cs211.pg.game.TreasureMap;

public class BrainSlugsTeam implements TeamFactory
{
	public Pirate getCartographerPirate()
	{
		return new CartographerBrainSlug();
	}
	
	public Pirate getCaptainPirate()
	{
		return new CaptainBrainSlug();
	}

	public TreasureMap getBlankTreasureMap()
	{
		return new MyTreasureMap();
	}

	public String getTeamName()
	{
		return "Brain Slugs";
	}
}
