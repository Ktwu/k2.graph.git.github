package edu.cmu.cs211.pg.game;

import java.util.Set;
import edu.cmu.cs211.pg.graph.*;

public class TurnInformation 
{
	public static final int WRONG_PHASE = -2;
	public static final int NO_PLACE = -1;
	public static final int YES_PLACE = 0;
	public static final int REAL_GOLD = 1;
	public static final int FAKE_GOLD = 2;
	
	//	WAS: GOLD? (flag: -2 if not right phase, -1 if nothing there, 0 if gold, but don't know which, 1 if real gold, 2 if pyrite)
	//  UPDATED: GOLD? (flag: -2 if not right phase, -1 if nothing there, 0 if at gold or pyrite)
	private final int gold;
	
	// GOLD? (flag: -2 if not right phase, -1 if have no gold/pyrite, 0 if have gold/pyrite--but no nitrite, 1 if have real gold, 2 if have pyrite)
	// NOTE: If you possess 
	private final int foundAndIdentifiedGold;
	
	//	NATIVES? (flag: -2 if not right phase, -1 if not natives, 0 if natives (and gave nitrite)
	private final int natives;
	
	//	PORT? (flag: -1 if not port, 1 if port (and have gold), 2 if port (and don't possess real gold))
	private final int port;
	
	//	TURN_NUMBER (<0 means we are in the Cartographer phase, 0 means we are about to start the Captain phase, >0 means we are in the Captain phase)
	public final int TURN_NUMBER;
	
	//  Your pirate's current location
	public final PirateNode loc;
	
	//	LIST of outgoing EDGES to go to with COSTS to get to them
	public final Set<WeightedEdge<PirateNode>> edges;
	
	public TurnInformation(
		int turnNum,
		PirateNode loc,
		Set<WeightedEdge<PirateNode>> edges,
		int gold,
		int natives,
		int port,
		int foundGold
	)
	{
		this.TURN_NUMBER = turnNum;
		this.loc = loc;
		this.edges = edges;
		this.gold = gold;
		this.natives = natives;
		this.port = port;
		this.foundAndIdentifiedGold = foundGold;
	}
	
	public boolean atFakeOrRealGold()
	{
		return gold == YES_PLACE;
	}
	
	public boolean haveSomeGold()
	{
		return foundAndIdentifiedGold>=0;
	}
	
	public boolean haveFakeGold()
	{
		return foundAndIdentifiedGold == FAKE_GOLD;
	}
	
	public boolean haveRealGold()
	{
		return foundAndIdentifiedGold == REAL_GOLD;
	}
	
	public boolean atNatives()
	{
		return natives >= 0;
	}
	
	public boolean atPortWithRealGold()
	{
		return port == REAL_GOLD;
	}
	
	public boolean atPortWithFakeGold()
	{
		return port == FAKE_GOLD;
	}

	public boolean atPort()
	{
		return port >= 0;
	}
}
	
