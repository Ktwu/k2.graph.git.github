package edu.cmu.cs211.pg.bots.student;

import java.util.List;

import edu.cmu.cs211.pg.algorithms.Dijkstra;
import edu.cmu.cs211.pg.algorithms.Kruskal;
import edu.cmu.cs211.pg.algorithms.MstTspApproximation;
import edu.cmu.cs211.pg.game.GameInformation;
import edu.cmu.cs211.pg.game.Pirate;
import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.TreasureMap;

public class MSTCaptainPirate implements Pirate
{
	private MyTreasureMap map;
	@SuppressWarnings("unused")
	private GameInformation game;
	private List<PirateNode> tour;
	
	/**
	 * Uses the MST-TSP-TOUR algorithm described in the documentation
	 * to create a list of Edges that represents the pirate's list of
	 * moves until he gets back to the port.
	 * 
	 * @param map 
	 * 			The TreasureMap that represents the game board
	 */
	public void init(TreasureMap _map, GameInformation _game)
	{
		this.map = (MyTreasureMap)_map;
		this.game = _game;
	}

	public PirateNode next()
	{
		if( tour == null )
		{
			//This is the first time next() was called
			Kruskal k = new Kruskal();
			Dijkstra d = new Dijkstra();
			MstTspApproximation<PirateNode> approx = new MstTspApproximation<PirateNode>(k,d);
			this.tour = approx.approximateTour(map.island, map.island.vertices(), map.myloc);
		}
		
		if( tour.isEmpty() )
		{
			//What do we do if the tour didn't find us gold?!?
			//It sucks, but we use DFS to continue searching for gold
			// If we find all possible spots of gold, then go back to port
			if (map.foundAllGold())
				return map.travelTo(map.port);
			
			return map.DFS();
		}
		
		//Follow the tour
		PirateNode move = tour.remove(0);
		return move;
	}
}
