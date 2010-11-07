package edu.cmu.cs211.pg.game;

import java.util.Set;

public class Team
{
	public Pirate cartographer;
	public Pirate captain;
	public TreasureMap map;
	public String name;
	public int spot;
	public int phase;
	
	public Set<Integer> hasGold;
	public boolean hasNitrite;
	
	public TurnInformation queued;
	
	public String toString()
	{
		return name;
	}
}
