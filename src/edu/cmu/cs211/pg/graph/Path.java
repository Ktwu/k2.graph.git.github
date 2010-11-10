package edu.cmu.cs211.pg.graph;

import java.util.LinkedList;
import java.util.List;

public class Path<V extends Comparable<V>, E extends WeightedEdge<V>>
  implements Comparable<Path<V,E>>
{
	private final V vertex;
	private final int distance;
	private final Path<V,E> pred;
	private final E predEdge;
	
	/**
	 * Creates an empty path
	 */
	public Path(V vert)
	{
		vertex = vert;
		distance = 0;
		pred = null;
		predEdge = null;
	}
	
	/**
	 * Returns the source node of the path
	 * @return the src of the first edge of the path
	 */
	public V src()
	{
		Path<V,E> r = this;
		while( r.pred != null )
		{
			r = r.pred;
		}
		return r.dest();
	}
	
	/**
	 * Returns the destination node of the path
	 * @return the dest of the first edge of the path
	 */
	public V dest()
	{
		return vertex;
	}
	
	/**
	 * Returns the sum of the weights of the edges in the path 
	 * @return the sum of the edge weights in the path
	 */
	public int pathWeight()
	{
		return distance;
	}
	
	/**
	 * Returns the list of edges in the path
	 * @return the list of edges in the path
	 */
	public List<E> edges()
	{
		LinkedList<E> path = new LinkedList<E>();
		
		Path<V,E> cur = this;
		while(true)
		{
			Path<V,E> pred = cur.pred;
			if( pred == null )
			{
				//cur is the start vertex
				break;
			}
			
			path.add(0, cur.predEdge);
			cur = pred;
		}
		
		return path;
	}
	
	/**
	 * Creates a path by appending an edge to the current path.
	 * 
	 * @param e the edge to append
	 * @return the new path.
	 */
	public Path<V,E> cons(E e)
	{
		return new Path<V,E>(e.dest(),e,this);
	}
	
	private Path(V vert, E predEdge, Path<V,E> pred)
	{
		this.vertex = vert;
		this.predEdge = predEdge;
		this.pred = pred;
		this.distance = pred.distance + predEdge.weight;
	}

	@Override
	public int hashCode()
	{
		return vertex.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if( this == o ) return true;
		if(!(o instanceof Path<?,?>)) return false;
		Path<?,?> v = (Path<?,?>)o;
		return this.vertex.equals(v.vertex);
	}
	
	public int compareTo(Path<V,E> vd)
	{
		if( distance < vd.distance )
		{
			return -1;
		}
		if( distance > vd.distance )
		{
			return 1;
		}
		if( !vertex.equals(vd.vertex) )
		{
			//make compareTo consistent with equals to workaround
			//java5 PQ bug
			return 1;
		}
		return 0;
	}
	
	/**
	 * list of vertices in the path, in order
	 * @return list of vertices (without first vertex in path)
	 */
	public List<V> vertices()
	{
		if (pred == null)
			return new LinkedList<V>();
		List<V> ret = pred.vertices();
		ret.add(vertex);
		return ret;
	}
	
	public String toString()
	{
		if (pred == null)
			return vertex.toString();
		return pred.toString() + " >> " + vertex;
	}
}
