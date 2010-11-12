package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.TeamFactory;
import edu.cmu.cs211.pg.game.TreasureMap;

/**
 * MstTeam: Pirate team that uses our MST Pirate Captain
 * @author Kellie Medlin
 *
 */
public class MstTeam implements TeamFactory
{
	public Pirate getCartographerPirate()
	{
		return new MyFirstCartographerPirate();
	}
	public Pirate getCaptainPirate()
	{
		return new MSTCaptainPirate();
	}
	public TreasureMap getBlankTreasureMap()
	{
		return new MyTreasureMap();
	}
	public String getTeamName()
	{
		return "CakeDogg";
	}
}
