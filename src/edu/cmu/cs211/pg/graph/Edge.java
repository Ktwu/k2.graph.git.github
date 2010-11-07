package edu.cmu.cs211.pg.graph;


public class Edge<V extends Comparable<V>> {
	protected final V src;
	protected final V dest;
	
	public Edge(V src, V dest)
	{
		this.src = src;
		this.dest = dest;
	}
	
	public V  src()
	{
		return src;
	}
	
	public V dest()
	{
		return dest;
	}
	
	public boolean equals (Object obj)
	{
		if (!(obj instanceof Edge<?>))
			return false;

		Edge<?> e = (Edge<?>) obj;
		return ( src == null && e.src == null || src != null && e.src.equals(src) ) &&
		  	   ( dest == null && e.dest == null || dest != null && e.dest.equals(dest) );
	}

	@Override
	public int hashCode ()
	{
		return src.hashCode() ^ (dest.hashCode()<<1);
	}
	
	@Override
	public String toString()
	{
		return "("+src+","+dest+")";
	}
}
