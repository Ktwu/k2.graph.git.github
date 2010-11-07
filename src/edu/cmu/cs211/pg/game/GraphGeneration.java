package edu.cmu.cs211.pg.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.MyDirectedGraph;
import edu.cmu.cs211.pg.graph.WeightedEdge;

import static edu.cmu.cs211.pg.game.Game.*;

public class GraphGeneration
{
	public static Graph<PirateNode,WeightedEdge<PirateNode>> 
	generateGraph(
		Properties p,
		Random r,
		List<PirateNode> _nodes
	)
	{
		//Defensively copy
		List<PirateNode> nodes = new ArrayList<PirateNode>(_nodes);
		Collections.shuffle(nodes);
		
		//Assign the Identities
		Graph<PirateNode,WeightedEdge<PirateNode>> map = new MyDirectedGraph<PirateNode,WeightedEdge<PirateNode>>();
		for(PirateNode n : nodes)
		{
			map.addVertex(n);
		}
		
		//Generate edges using "Given Degree-Sequence Model"
		//First, generate node degrees
		ArrayList<PirateNode> nodesToBeConnected = new ArrayList<PirateNode>(nodes);
		for(PirateNode n : map.vertices())
		{
			n.degree = p.minNodeDegree
			  + r.nextInt(p.maxNodeDegree+1-p.minNodeDegree);
		}
		
		//First ensure everything is connected by performing a pairing
		//algorithm and placing an edge between pairs
		ArrayList<ArrayList<PirateNode>> components = new ArrayList<ArrayList<PirateNode>>();
		for( PirateNode n : nodesToBeConnected )
		{
			ArrayList<PirateNode> comp = new ArrayList<PirateNode>();
			comp.add(n);
			components.add(comp);
		}
		
		//until there is only one component
		while( components.size() > 1 )
		{
			Collections.shuffle(components);
			
			ArrayList<ArrayList<PirateNode>> componentsNext = new ArrayList<ArrayList<PirateNode>>();
			for(int i = 0; i < components.size()-2; i += 2)
			{
				//pick two components to join
				ArrayList<PirateNode> c1 = components.get(i);
				ArrayList<PirateNode> c2 = components.get(i+1);
				Collections.shuffle(c1);
				Collections.shuffle(c2);
				//in case there is nothing needing more edges
				PirateNode n1 = c1.get(0);
				PirateNode n2 = c1.get(0);
				//find something that needs an edge
				for( PirateNode n : c1 )
				{
					if( n.degree > 0 )
					{
						n1 = n;
						break;
					}
				}
				for( PirateNode n : c2 )
				{
					if( n.degree > 0 )
					{
						n2 = n;
						break;
					}
				}
				//join n1,n2
				int weight = genWeight(r,n1,n2);
				map.addEdge(new WeightedEdge<PirateNode>(n1,n2,weight));
				map.addEdge(new WeightedEdge<PirateNode>(n2,n1,weight));
				--n1.degree;
				--n2.degree;
				c1.addAll(c2);
				componentsNext.add(c1);
			}
			components = componentsNext;
		}
		
		//Second, sort by degree
		Collections.sort(nodesToBeConnected, new Comparator<PirateNode>(){
			public int compare(PirateNode a, PirateNode b)
			{
				return b.degree - a.degree;
			}
		});
		
		//fill in the rest of the nodes
		while( nodesToBeConnected.size() > 1 )
		{
			int ntbc_last = nodesToBeConnected.size()-1;
			final PirateNode n = nodesToBeConnected.get(ntbc_last);
			if( n.degree <= 0 )
			{
				nodesToBeConnected.remove(ntbc_last);
				n.degree = map.outgoingNeighbors(n).size();
				continue;
			}
			
			//create edges to close nodes before edges to far nodes
			Collections.sort(nodesToBeConnected,new Comparator<PirateNode>(){
				public int compare(PirateNode a, PirateNode b)
				{
					double aDist = euclidianDistance(n.placement.x, n.placement.y, a.placement.x, a.placement.y);
					double bDist = euclidianDistance(n.placement.x, n.placement.y, b.placement.x, b.placement.y);
					
					if( aDist < bDist ) return -1;
					if( aDist > bDist ) return 1;
					return 0;
				}
			});
			
			PirateNode nC = null;
			
			//not index 0, because that would be a self-loop
			int i = 1;
			do {
			  nC = nodesToBeConnected.get(i++);
			  if( map.adjacent(n,nC) != null )
			  {
				  //don't try to put another edge between
				  //two nodes which already have a connecting edge
				  nC = null;
			  }
			} while( nC == null && i < nodesToBeConnected.size() );
			
			if( nC == null )
			{
				//oops, nothing left to connect to
				nodesToBeConnected.remove(ntbc_last);
				continue;
			}
			
			//Note that since we don't look at the current degree of nC,
			//The later nodes *may* end up with higher degree then planned.
			int weight = genWeight(r,n,nC);
			map.addEdge(new WeightedEdge<PirateNode>(n, nC, weight));
			map.addEdge(new WeightedEdge<PirateNode>(nC, n, weight));
			n.degree--;
			nC.degree--;
			Collections.shuffle(nodesToBeConnected);
		}
		
		return map;
	}
	
	private static int genWeight(Random r, PirateNode n, PirateNode nC)
	{
		int weight = -1;
		while( weight < 30 )
		  weight = euclidianDistance(n.placement.x,n.placement.y,nC.placement.x,nC.placement.y) + r.nextInt(100)-40;
		return weight/30;
	}
}
