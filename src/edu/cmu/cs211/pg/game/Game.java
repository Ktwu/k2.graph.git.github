package edu.cmu.cs211.pg.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

import edu.cmu.cs211.pg.game.GUI.PGFrame;
import edu.cmu.cs211.pg.graph.*;

public class Game
{
	private Team robotTeam;
	private Team studentTeam;
	
	private Properties properties;
	
	private List<PirateNode> nodes = new ArrayList<PirateNode>();
	
	private int LENGTH_OF_PHASE_ONE = -1;
	private int TURN_NUMBER;
	private int CURRENT_PLAYER; //0 for Student, 1 for Robot
	
	private int[] PLAYER_BUSY;
	
	private int PORT = -1;
	private int NATIVES = -1;
	private int GOLD = -1;
	private int PIRITE1 = -1;
	private int PIRITE2 = -1;
	
	private Random r;
	private Graph<PirateNode, WeightedEdge<PirateNode>> map;
	private PGFrame GUI;
	
	public Game(Team r, Team s, Properties p)
	{
		robotTeam = r;
		studentTeam = s;
		properties = p;
		
		//Initialize random
		this.r = new Random();
		robotTeam.phase = 1;
		studentTeam.phase = 1;
	}
	public void begin() throws IllegalMoveException
	{
		initializeGame();
		mainLoop();
	}
	private void generateMapLayout(int numberOfNodes)
	{
		int height = properties.height-60;
		int width = properties.width-60;
		nodes.clear();
		
		while(nodes.size()<2)
		{
			int x = r.nextInt(width-9)+10;
			int y = r.nextInt(height-9)+10;
				
			PirateNode me = new PirateNode(x,y, 80);
			boolean intersects = false;
			for(PirateNode p : nodes){
				if(p.intersects(me)){
					intersects = true;
					break;
				}
			}
			if (!intersects)
			{
				me.placement.height-=20;
				me.placement.width-=20;
				nodes.add(me);
			}
		}
		
		
		while(nodes.size()<numberOfNodes)
		{
			int x = r.nextInt(width-29)+30;
			int y = r.nextInt(height-29)+30;
				
			PirateNode me = new PirateNode(x,y,50);
			boolean intersects = false;
			for(PirateNode p : nodes){
				if(p.intersects(me)){
					intersects = true;
					break;
				}
			}
			if (!intersects)
			{
				me.placement.height-=20;
				me.placement.width-=20;
				nodes.add(me);
			}
		}
	
		//Record special nodes before shuffling
		PirateNode port = nodes.get(0);
		PirateNode natives = nodes.get(1);
		
		Collections.shuffle(nodes);
		
		int identity = 0;
		for(PirateNode n : nodes){
			if(n == port)
				PORT = identity;
			if(n == natives)
				NATIVES = identity;
			n.identity = identity;
			identity++;
		}
	}
	@SuppressWarnings("unchecked")
	private void initializeGame()
	{
		//Make Real Map
		generateMapLayout(properties.mapSize);
		map = GraphGeneration.generateGraph(properties, new Random(properties.randomSeed), nodes);
		
		double totalWeight = 0;
		int edgeCount = 0;
		for( PirateNode n : map.vertices() )
		{
			for( WeightedEdge<PirateNode> e : map.outgoingEdges(n) )
			{
				totalWeight += e.weight();
				++edgeCount;
			}
		}
		totalWeight /= 2.0;
		edgeCount /= 2;
		double averageWeight = totalWeight / edgeCount;
		double numVerts = map.vertices().size();
		
		//Set Constants
		LENGTH_OF_PHASE_ONE = (int)(averageWeight*numVerts*properties.explorationFactor);
		TURN_NUMBER = 0 - LENGTH_OF_PHASE_ONE;
		CURRENT_PLAYER = (properties.startingPlayer==-1) ? r.nextInt(2) : properties.startingPlayer;
		PLAYER_BUSY = new int[2];
		
		while(GOLD==-1){
			int poss = r.nextInt(nodes.size());
			if(PORT!=poss && NATIVES!=poss)
				GOLD = poss;
		}
		while(PIRITE1==-1){
			int poss = r.nextInt(nodes.size());
			if(PORT!=poss && NATIVES!=poss && GOLD!=poss)
				PIRITE1 = poss;
		}
		while(PIRITE2==-1){
			int poss = r.nextInt(nodes.size());
			if(PORT!=poss && NATIVES!=poss && GOLD!=poss && PIRITE1!=poss)
				PIRITE2 = poss;
		}
		//Set Team information for both Teams
		studentTeam.spot = PORT;
		studentTeam.hasGold = new HashSet();
		studentTeam.hasNitrite = false;
		studentTeam.queued = makeTurnInformation(PORT);
		
		robotTeam.spot = PORT;
		robotTeam.hasGold = new HashSet();
		robotTeam.hasNitrite = false;
		robotTeam.queued = makeTurnInformation(PORT);
		
		//If we want GUI...set it up and show it.
		if(properties.hasGUI)
		{
			GUI = new PGFrame(properties, robotTeam, studentTeam, map, nodes, GOLD, PIRITE1, PIRITE2, NATIVES);
			GUI.setPlayer(0, studentTeam.name);
			GUI.setPlayer(1, robotTeam.name);
		}
	}
	public static int euclidianDistance(double x1, double y1, double x2, double y2){
		return (int)(Math.sqrt(Math.pow(x1-x2, 2)+Math.pow(y1-y2, 2)));
	}

	public int phase()
	{
		return TURN_NUMBER<0 ? 1 : 2;
	}
	
	/* 0 Based Numbering... */
	public int turn()
	{
		return TURN_NUMBER<0 ? (LENGTH_OF_PHASE_ONE + TURN_NUMBER) : TURN_NUMBER;
	}
	public boolean gameOver()
	{
		if(
		   studentTeam.phase == 3
		&& robotTeam.phase == 3
		&& PLAYER_BUSY[0]==0
		&& PLAYER_BUSY[1]==0
		)
		{
			//both captains are "done"
			return true;
		}
		
		return (studentTeam.spot == PORT && studentTeam.hasGold.contains(GOLD) && PLAYER_BUSY[0]==0) ||
		 	   (robotTeam.spot == PORT && robotTeam.hasGold.contains(GOLD)  && PLAYER_BUSY[1]==0);
	}
	public boolean studentTeamWon()
	{
		return (studentTeam.spot == PORT && studentTeam.hasGold.contains(GOLD)) &&
	 	  !(robotTeam.spot == PORT && robotTeam.hasGold.contains(GOLD));
	}
	public int current()
	{
		return CURRENT_PLAYER==0 ? studentTeam.spot : robotTeam.spot;
	}
	public int height()
	{
		return properties.height;
	}
	public int width()
	{
		return properties.width;
	}
	public int numberOfVertices()
	{
		return properties.mapSize;
	}
	public int lengthPhaseOne()
	{
		return LENGTH_OF_PHASE_ONE;
	}
	private int getMove()
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		int move = -1;
		
		if( move == -1 && t.phase == 1 )
		{
			PirateNode next = t.cartographer.next();
			if( next == null )
			{
				if(t.spot != PORT)
				{
					throw new IllegalStateException(
						"A cartographer cannot be done if he is not at the port"
					);
				}
				t.phase = 2;
			}
			else
			{
				move = next.getID();
			}
		}
		if( move == -1 && t.phase == 2 && phase() == 2 )
		{
			PirateNode next = t.captain.next();
			if( next == null )
			{
				if(t.spot != PORT)
				{
					throw new IllegalStateException(
						"A captain cannot be done if he is not at the port"
					);
				}
				t.phase = 3;
			}
			else
			{
				move = next.getID();
			}
		}
		
		if(properties.hasGUI)
		{
			GUI.setPhase(CURRENT_PLAYER, t.phase);
		}
		
		return move;
	}
	private int checkForFoundGold()
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		//GOLD? (flag:  -1 if have no gold/pyrite, 0 if have something--but it's unidentified, 1 if have real gold, 2 if have pyrite)
		if(t.hasGold.isEmpty())
		{
			return -1; /* t doesn't have any possible gold to check */
		}
		else if(t.hasNitrite)
		{
			
			if(t.hasGold.contains(GOLD))
				return TurnInformation.REAL_GOLD;
			else
				return TurnInformation.FAKE_GOLD;
		}
		else
		{
			return 0; /* t doesn't have nitrite to check with--but has something... */
		}
	}
	private int checkForGold()
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		
		if(t.spot==GOLD || t.spot==PIRITE1 || t.spot==PIRITE2)
		{
			t.hasGold.add(t.spot);
			return TurnInformation.YES_PLACE;
		}
		else
		{
			return TurnInformation.NO_PLACE;
		}
	}
	private int checkForNatives()
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		
		if(t.spot==NATIVES)
		{
			if(phase() == 2)
				t.hasNitrite = true;
			return TurnInformation.YES_PLACE;
		}
		else
			return TurnInformation.NO_PLACE;
	}
	private int checkForPort()
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		
		if(t.spot==PORT)
		{
			if(t.hasGold.contains(GOLD))
				return TurnInformation.REAL_GOLD;
			else
				return TurnInformation.FAKE_GOLD;
		}
		else
			return TurnInformation.NO_PLACE;
	}
	private TurnInformation makeTurnInformation(int future)
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		int turn = TURN_NUMBER;
		int gold = (t.phase<2 || phase()<2) ? TurnInformation.WRONG_PHASE : checkForGold();
		int natives = checkForNatives();
		int port = checkForPort();
		int foundGold = (t.phase<2 || phase()<2) ? TurnInformation.WRONG_PHASE : checkForFoundGold();
		PirateNode futureNode = nodes.get(future);
		Set<WeightedEdge<PirateNode>> edges = map.outgoingEdges(futureNode);
		
		return new TurnInformation(turn,futureNode,edges,gold,natives,port, foundGold);
	}
	private void takeTurn() throws IllegalMoveException
	{
		Team t = CURRENT_PLAYER==0 ? studentTeam : robotTeam;
		if(PLAYER_BUSY[CURRENT_PLAYER]<=0)
		{
			/* Send new information to player */
			if(t.queued != null)
			{
				//Update GUI (if we should)
				if(properties.hasGUI)
				{
					GUI.setLoc(CURRENT_PLAYER, nodes.get(t.spot));
					GUI.setGold(CURRENT_PLAYER, (t.hasGold.contains(GOLD)?1:0));
					GUI.setFoundPyrite(CURRENT_PLAYER, (t.hasGold.contains(PIRITE1)||t.hasGold.contains(PIRITE2))?1:0);
				}
				t.map.update(t.queued);
				t.queued = null;
			}
			int current = t.spot;
			int future = getMove();
			//If move is -1, user has either messed up or finished phase one early...
			//In either case, ignore this turn.
			if(future!=-1)
			{
				/* Check for legality of move */
				PirateNode src = nodes.get(current);
				PirateNode dest = nodes.get(future);
				WeightedEdge<PirateNode> move = map.adjacent(src,dest);
				if( move == null )
				{
					throw new IllegalMoveException(String.format("No edge between %s and %s",t.spot,nodes.get(future).identity));
				}
					
				if(move.src()!=null)
				{
					//Update Team spot
					t.spot = future;
					//Queue TurnInformation for when traversal finishes
					t.queued = makeTurnInformation(future);
					//Update BUSY count with edge weight
					PLAYER_BUSY[CURRENT_PLAYER] = map.adjacent(nodes.get(current), nodes.get(future)).weight();
					if(properties.hasGUI)
					{
						GUI.setDest(CURRENT_PLAYER, nodes.get(t.spot), map.adjacent(nodes.get(current), nodes.get(future)));
					}
				}
			}
		}
		else
		{
			PLAYER_BUSY[CURRENT_PLAYER]--;
		}
		if(properties.hasGUI)
		{
			GUI.setTurnsLeft(CURRENT_PLAYER, PLAYER_BUSY[CURRENT_PLAYER]);
		}
	}
	private void mainLoop() throws IllegalMoveException
	{		
		while(!gameOver())
		{
			//One of the players moves, switch players, the other player moves
			takeTurn();
			CURRENT_PLAYER = 1 - CURRENT_PLAYER;
			takeTurn();
			CURRENT_PLAYER = 1 - CURRENT_PLAYER;
			//Increment Turn Count
			TURN_NUMBER++;
			if(properties.hasGUI)
			{
				try{
					Thread.sleep(properties.msBetweenTurns);
				}catch(Exception e){};
				GUI.setTurnNumber(TURN_NUMBER);
			}
		}
		if( GUI != null )
		{
			GUI.dispose();
		}
	}
}
