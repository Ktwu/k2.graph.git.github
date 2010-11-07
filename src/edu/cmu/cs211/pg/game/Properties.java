package edu.cmu.cs211.pg.game;

import java.util.Random;

public class Properties 
{
	/* This is the seed that the random number generator uses for the game...as is obvious,
	 * we are not currently using a constant one
	 */
	public long randomSeed = new Random().nextLong();
	
	/* These are the physical height and width of the map*/
	public int height = 750;
	public int width = 1200;
	
	/* This is the number of nodes on the map */
	public int mapSize = 50;
	
	/* This factor goes into calculating how much time the Cartographer has to explore */
	public double explorationFactor = .5;
	
	/* This denotes the player that "goes first"...could matter when the bots are very
	 * evenly matched...-1 means random, 0 means student, 1 means robot
	 */
	public int startingPlayer = -1;
	
	/* Use this to toggle the appearance of the GUI */
	public boolean hasGUI = false;
	
	/* These affect the density of the graph */
	public int minNodeDegree = 3;
	public int maxNodeDegree = 6;
	
	/* When the GUI is on, this is the time that should
	 * be waited, between making moves, so the user
	 * can see what is going on.
	 */
	public int msBetweenTurns = 100;
	
	/* This is the proportion of games that the unit
	 * test requires a bot to have, for the unit 
	 * test to pass.
	 */
	public double proportionToWin = 1;
}
