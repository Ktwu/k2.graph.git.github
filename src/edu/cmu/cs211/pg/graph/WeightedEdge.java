package edu.cmu.cs211.pg.graph;

public class WeightedEdge<V extends Comparable<V>> extends Edge<V> implements Comparable<WeightedEdge<V>>{
	protected final int weight;
	
	public WeightedEdge(V src, V dest, int weight)
	{
		super(src, dest);
		this.weight = weight;
	}
	
	public int weight()
	{
		return weight;
	}
	
	public boolean equals (Object obj)
	{
		if (!(obj instanceof WeightedEdge<?>))
			return false;

		WeightedEdge<?> e = (WeightedEdge<?>) obj;
		return
		   e.weight == weight && super.equals(obj);
	}

	@Override
	public int hashCode ()
	{
		return super.hashCode() ^ weight;
	}
	
	@Override
	public String toString()
	{
		return "[" + super.toString() + ", " + weight + "]";
	}
	
	public int compareTo(WeightedEdge<V> obj)
	{
		if(this.src.compareTo(obj.src)==0)
			if(this.dest.compareTo(obj.dest)==0)
				return ((Integer)this.weight).compareTo(obj.weight);
			else
				return this.dest.compareTo(obj.dest);
		else
			return this.src.compareTo(obj.src);			
	}
}
