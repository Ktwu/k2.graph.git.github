package edu.cmu.cs211.pg.game;

public interface TreasureMap
{
	/**
	 * Updates the TreasureMap based on the new information we get
	 * from TurnInformation t.
	 * 
	 * @param TurnInformation t 
	 * 			Information about this current turn
	 */
	public void update(TurnInformation t);
}
