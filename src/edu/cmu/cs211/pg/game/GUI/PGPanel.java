package edu.cmu.cs211.pg.game.GUI;

import java.awt.*;
import javax.swing.*;

import edu.cmu.cs211.pg.game.PirateNode;
import edu.cmu.cs211.pg.game.Properties;
import edu.cmu.cs211.pg.game.Team;
import edu.cmu.cs211.pg.graph.Graph;
import edu.cmu.cs211.pg.graph.WeightedEdge;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


@SuppressWarnings("serial")
public class PGPanel extends JPanel implements MouseListener{
	Graph<PirateNode,WeightedEdge<PirateNode>> graph;
	Set<WeightedEdge<PirateNode>> edges = new HashSet<WeightedEdge<PirateNode>>();
	
	Random r = new Random();
	
	PirateNode gold;
	PirateNode pyrite1;
	PirateNode pyrite2;
	PirateNode natives;
	
	PirateNode player1;
	PirateNode player2;
	
	int player1Wait;
	int player1TotalWait;
	int player2Wait;
	int player2TotalWait;
	
	WeightedEdge<PirateNode> player1Edge;
	WeightedEdge<PirateNode> player2Edge;
	
	Team one;
	Team two;
	
	Graphics bufferGraphics;
    Image offscreen;
    Dimension dim;
    int curX, curY;
    
    boolean displayAllEdges;
	
	public PGPanel(Properties p, Team a, Team fightingMongooses, Graph<PirateNode,WeightedEdge<PirateNode>> graph, List<PirateNode> orderedVertices, int gold, int pyrite1, int pyrite2, int natives)
	{
		setSize(p.width, p.height);
		
		this.graph = graph;
		this.gold = orderedVertices.get(gold);
		this.pyrite1 = orderedVertices.get(pyrite1);
		this.pyrite2 = orderedVertices.get(pyrite2);
		this.natives = orderedVertices.get(natives);
		
		this.one = a;
		this.two = fightingMongooses;
		
		dim = getSize(); 
		////////
		setVisible(true);
		
		
        addMouseListener(this);
        displayAllEdges = true;
	}
	public void setLoc(int player, PirateNode p){
		if(player==0)
		{
			player1 = p;
		}
		else
		{
			player2 = p;
		}
		repaint();
	}
	public void setEdge(int player, WeightedEdge<PirateNode> p){
		if(player==0)
		{
			player1Edge = p;
			player1TotalWait = p.weight();
		}
		else
		{
			player2Edge = p;
			player2TotalWait = p.weight();
		}
		repaint();
	}
	public void setWait(int player, int wait)
	{
		if(player==0)
		{
			player1Wait = wait;
		}
		else
		{
			player2Wait = wait;
		}
		repaint();
	}
	public void update(Graphics g)
	{
		paint(g);
	}
	public void paint(Graphics g)
	{
		//Init Double Buffering if neccessary
		if(offscreen==null)
		{
			offscreen = createImage(dim.width,dim.height);
			bufferGraphics = offscreen.getGraphics();
		}
        
//		 Wipe off everything that has been drawn before
        // Otherwise previous drawings would also be displayed.
        bufferGraphics.clearRect(0,0,dim.width,dim.width);

		//super.paint(g);
        bufferGraphics.setColor(Color.black);
		
		Graphics2D g2 = (Graphics2D)bufferGraphics;
		
		if(displayAllEdges)
		{
			for (PirateNode p : graph.vertices())
			{
				for( WeightedEdge<PirateNode> e : graph.outgoingEdges(p) )
				{
					if( e == player1Edge || e == player2Edge ) continue;
				
					PirateNode p1 = e.dest();
					PirateNode p2 = e.src();

					int w = Math.min(210,e.weight()*10);
				
					bufferGraphics.setColor(new Color(w, w, w, 100));
					bufferGraphics.drawLine((int)(p1.getLocation().x+p1.getLocation().width/2), (int)(p1.getLocation().y+p1.getLocation().height/2), 
											(int)(p2.getLocation().x+p2.getLocation().width/2), (int)(p2.getLocation().y+p2.getLocation().height/2));
					bufferGraphics.setColor(Color.black);
				}
			}
		}
		
		for (PirateNode p : graph.vertices())
		{
			if(p==player1)
				bufferGraphics.setColor(Color.blue);
			if(p==player2)
				bufferGraphics.setColor(Color.red);
			if(p==player1 && p==player2)
				bufferGraphics.setColor(Color.magenta);
			if(p==gold)
				bufferGraphics.setColor(Color.yellow);
			if(p==pyrite1 || p==pyrite2)
				bufferGraphics.setColor(Color.orange);
			if(p==natives)
				bufferGraphics.setColor(Color.green);
			
			g2.fill(p.getLocation());
			bufferGraphics.setColor(Color.black);
		}
		for (WeightedEdge<PirateNode> e : edges)
		{
			PirateNode p1 = e.dest();
			PirateNode p2 = e.src();
			
			bufferGraphics.drawLine((int)(p1.getLocation().x+p1.getLocation().width/2), (int)(p1.getLocation().y+p1.getLocation().height/2), 
					   (int)(p2.getLocation().x+p2.getLocation().width/2), (int)(p2.getLocation().y+p2.getLocation().height/2));
			bufferGraphics.setColor(Color.green);
			bufferGraphics.drawString(((Integer)e.weight()).toString(), (int)(p1.getLocation().x+p1.getLocation().width/2)-10, (int)(p1.getLocation().y+p1.getLocation().height/2)+8);
			bufferGraphics.setColor(Color.black);
		}
		
		if(player1Edge != null)
		{
			paintPartialEdge(player1Edge, player1TotalWait, player1Wait, Color.blue);
			bufferGraphics.setColor(Color.black);
		}
		if(player2Edge != null)
		{
			paintPartialEdge(player2Edge, player2TotalWait, player2Wait, Color.red);
			bufferGraphics.setColor(Color.black);
		}
		if(player1Edge != null && player1Edge == player2Edge)
		{
			paintEdge(player2Edge, Color.magenta);
			bufferGraphics.setColor(Color.black);
		}
        // draw the offscreen image to the screen like a normal image.
        // Since offscreen is the screen width we start at 0,0.
        g.drawImage(offscreen,0,0,this); 
	}
	public void paintEdge(WeightedEdge<PirateNode> edge, Color color)
	{
		PirateNode p1 = edge.dest();
		PirateNode p2 = edge.src();
		bufferGraphics.setColor(color);
		bufferGraphics.drawLine((int)(p1.getLocation().x+p1.getLocation().width/2), (int)(p1.getLocation().y+p1.getLocation().height/2), 
				   (int)(p2.getLocation().x+p2.getLocation().width/2), (int)(p2.getLocation().y+p2.getLocation().height/2));
	}
	public void paintPartialEdge(WeightedEdge<PirateNode> edge, int total, int partial, Color color)
	{
		PirateNode p1 = edge.src();
		PirateNode p2 = edge.dest();
		double x1 = (p1.getLocation().x+p1.getLocation().width/2);
		double y1 = (p1.getLocation().y+p1.getLocation().height/2);
		double x2 = (p2.getLocation().x+p2.getLocation().width/2);
		double y2 = (p2.getLocation().y+p2.getLocation().height/2);
		
		double x_comp = x2 - x1;
		double y_comp = y2 - y1;
		
		double factor = 1 - (((double)partial)/total);
		
		double scaled_x_comp = x_comp * factor;
		double scaled_y_comp = y_comp * factor;
		
		bufferGraphics.setColor(color);
		bufferGraphics.drawLine((int)x1, (int) y1, (int)(scaled_x_comp + x1), (int)(scaled_y_comp + y1));
	}
	
     public void mousePressed(MouseEvent e) {}
     public void mouseReleased(MouseEvent e) {}
     public void mouseEntered(MouseEvent e) {}
     public void mouseExited(MouseEvent e) {}
     public void mouseClicked(MouseEvent e) 
     {
    	 	int x = e.getX();
    	 	int y = e.getY();
    	 	PirateNode intersects = null;
			for(PirateNode p : graph.vertices()){
				if(p.contains(x,y)){
					intersects = p;
					break;
				}
			}
			if (intersects!=null)
			{
				edges = graph.outgoingEdges(intersects);
			}
			else
			{
				edges = new HashSet<WeightedEdge<PirateNode>>();
			}
			repaint();
     }
	public void flipEdges(){
		displayAllEdges = !displayAllEdges;
	}
}

 

