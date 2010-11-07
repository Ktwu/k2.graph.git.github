package edu.cmu.cs211.pg.game;

import java.awt.geom.Ellipse2D;

public class PirateNode implements Comparable<PirateNode>
{
    public Ellipse2D.Double placement;
	public int identity;
	public int degree;
	
	public PirateNode(int x, int y, int radius){
		placement = new Ellipse2D.Double(x, y, radius, radius);
	}
	public int getID()
	{
		return identity;
	}
	public Ellipse2D.Double getLocation()
	{
		return placement;
	}
	public boolean contains(int x, int y){
		return placement.contains(x,y);
	}
	public boolean intersects(PirateNode n){
		return placement.intersects(n.placement.getBounds2D());
	}
	public int compareTo(PirateNode o)
	{
		return this.identity - o.identity;
	}
	public boolean equals(Object o)
	{
		if(this == o) return true;
		if(!(o instanceof PirateNode)) return false;
		PirateNode po = (PirateNode)o;
		return po.getID() == getID();
	}
	public int hashCode()
	{
		return getID();
	}
	public String toString()
	{
		return String.format("%d",getID());
	}
}
