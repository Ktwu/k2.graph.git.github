package edu.cmu.cs211.pg.bots.student;

import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.TeamFactory;
import edu.cmu.cs211.pg.game.TreasureMap;

/**
 * MstTeam: Pirate team that uses our Greedy Pirate Captain
 * @author Kellie
 *
 */
public class StudentTeam implements TeamFactory
{
	public Pirate getCartographerPirate()
	{
		return new GreedyCartographerPirate();
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
		return "PresentCatt";
	}
}
