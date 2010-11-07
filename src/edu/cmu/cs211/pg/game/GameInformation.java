package edu.cmu.cs211.pg.game;

public class GameInformation
{
	private Game game;
	
	public GameInformation(Game g)
	{
		game = g;
	}
	
	public int height()
	{
		return game.height();
	}
	public int width()
	{
		return game.width();
	}
	/**
	 * @return the number of vertices on the island
	 */
	public int numberOfVertices()
	{
		return game.numberOfVertices();
	}
	public int phase()
	{
		return game.phase();
	}
	/**
	 * Turn count since the beginning of the phase. Recall that
	 * it takes w turns to travel an edge with weight w.
	 * 
	 * @return the number of turns since the beginning of this phase.
	 */
	public int turn()
	{
		return game.turn();
	}
	/**
	 * @return true if the game has ended
	 */
	public boolean gameOver()
	{
		return game.gameOver();
	}
	/**
	 * @return the number of turns for phase one.
	 */
	public int lengthOfPhaseOne()
	{
		return game.lengthPhaseOne();
	}
}
