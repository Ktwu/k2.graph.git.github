package edu.cmu.cs211.pg.game;

public interface TeamFactory
{
	public String getTeamName();
	public Pirate getCartographerPirate();
	public Pirate getCaptainPirate();
	public TreasureMap getBlankTreasureMap();
}
