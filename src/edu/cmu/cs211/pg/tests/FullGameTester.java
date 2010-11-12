package edu.cmu.cs211.pg.tests;

/** This is an example test for the game...we run a very similar one on FrontDesk.
 *  Feel free to edit this file. 
 */
import org.junit.Test;

import edu.cmu.cs211.pg.bots.given.BrainSlugsTeam;
import edu.cmu.cs211.pg.bots.student.StudentTeam;
import edu.cmu.cs211.pg.bots.student.MstTeam;
import edu.cmu.cs211.pg.game.Game;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.IllegalMoveException;
import edu.cmu.cs211.pg.game.Properties;
import edu.cmu.cs211.pg.game.Team;
import edu.cmu.cs211.pg.game.TeamFactory;

import javax.swing.JOptionPane;
import static org.junit.Assert.assertTrue;

public class FullGameTester
{
	@Test
	public void brainSlugs() throws IllegalMoveException
	{
		runXGames(500, new MstTeam(), new StudentTeam());
	}
	
	public void runXGames(int number, TeamFactory robotTeam, TeamFactory studentTeam) throws IllegalMoveException
	{
		int winCounter = 0;
		
		Properties p = new Properties();
		p.explorationFactor = .5;
		p.mapSize = 40;
		p.height = 750;
		p.width = 1200;
		p.minNodeDegree = 3;
		p.maxNodeDegree = 6;
		p.startingPlayer = 0;
		p.hasGUI = true; //Change this line to stop the GUI from showing up
		p.proportionToWin = 0.60;
		
		for(int i=0; i<number; i++)
		{
			Team robots = makeTeam(robotTeam);
			Team students = makeTeam(studentTeam);
			
			Game g = new Game(robots, students, p);
			GameInformation gi = new GameInformation(g);
			
			initTeam(robots, gi);
			initTeam(students, gi);
			
			g.begin();
			winCounter += g.studentTeamWon()?1:0;
			if(p.hasGUI)
			{
				JOptionPane.showMessageDialog(null, "Student Team has won " + winCounter + " games.", "Game Ended!", JOptionPane.INFORMATION_MESSAGE);
				
			}
		}
			
		assertTrue("Student Bot won " + winCounter + " games.  You needed at least " + (int)((p.proportionToWin)*number) + " out of " + number + ".", winCounter>=(int)((p.proportionToWin)*number));
	}
	
	public static Team makeTeam(TeamFactory f)
	{
		Team t = new Team();
		t.captain = f.getCaptainPirate();
		t.cartographer = f.getCartographerPirate();
		t.map = f.getBlankTreasureMap();
		t.name = f.getTeamName();
		return t;
	}
	public static void initTeam(Team t, GameInformation gi)
	{
		t.captain.init(t.map, gi);
		t.cartographer.init(t.map, gi);
	}
}
