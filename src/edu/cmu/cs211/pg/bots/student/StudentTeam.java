package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.TeamFactory;
import edu.cmu.cs211.pg.game.TreasureMap;

public class StudentTeam implements TeamFactory
{
	public Pirate getCartographerPirate()
	{
		return new MyFirstCartographerPirate();
	}
	public Pirate getCaptainPirate()
	{
		return new GreedyCaptainPirate();
	}
	public TreasureMap getBlankTreasureMap()
	{
		return new MyTreasureMap();
	}
	public String getTeamName()
	{
		throw new RuntimeException ("You need to implement this method");
	}
}
