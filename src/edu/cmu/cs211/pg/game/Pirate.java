package edu.cmu.cs211.pg.game;

public interface Pirate
{
	/**
	 * Initializes a pirate at the beginning of a game. All pirates on
	 * your team will receive the same map object.
	 * 
	 * @param map 
	 * 			The TreasureMap that represents the game board
	 */
	public void init(TreasureMap map, GameInformation gi);
	
	/**
     * Returns the vertex that the pirates should move to next.
     * The response will be information (in the form of TurnInformation) about what
     * they found.
     * 
     * @return the next vertex to go to or
     *         null if the Pirate is done with his duties
     *              and is back at the port
     */
	public PirateNode next();
}
