package edu.cmu.cs211.pg.algorithms;
/**
 * @author: Kellie Medlin
 * @andrewID: kmmedlin
 * 
 * UnionFind implementation: Given a set of object T, construct a map from Objects to integers
 * I'm intrigued to see how this works, since I copied most of the code from lecture notes
 */

import java.util.Set;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;

public class UnionFind<T> 
{	
	HashMap<T, T> unionTree;
	int setCount;
	
	public UnionFind(Collection<T> stuff) 
	{
		if (stuff == null)
			throw new NullPointerException("UnionFind(stuff): stuff is null!");
		
		// Initialize our map
		unionTree = new HashMap<T, T>();
		
		Iterator<T> iStuff = stuff.iterator();

		// Add our Ts to our map for object->small int conversion
		while (iStuff.hasNext())
			unionTree.put(iStuff.next(), null);
		
		setCount = unionTree.size();
	}
	
	/**
     * Unions the sets containing elements a and b together. If a and b
     * are already in the same set, this method will do nothing.
     *
     * @throws NullPointerException if a or b is null
     */
	public void union(T a, T b)
	{
		if (a == null || b == null)
			throw new NullPointerException("union(a,b): a and/or b are null!");
		
		a = find(a);
		b = find(b);
		
		if (a != b)
		{
			if (myCompare(unionTree.get(a), unionTree.get(b)) == -1)
			{	unionTree.put(a, unionTree.get(b));	unionTree.put(b, a);	}
			else
			{	unionTree.put(b, unionTree.get(a));	unionTree.put(a, b);	}
			
			setCount--;
		}
	}
	
	/**
     * Returns the canonical element representing the set a is in.
     * 
     * @param a     the element to be queried
     * @return      the canonical element representing the set a is in
     * @throws      NullPointerException if a is null
	 */
	public T find(T a) 
	{
		if (a == null)
			throw new NullPointerException("find(a): a is null!");
		
		if (!unionTree.containsKey(a))
			throw new IllegalArgumentException("find(a): a is not part of UnionFind set!");
		
		// Base case of recursion: our T is a root
		if (unionTree.get(a) == null)	return a;
		
		// Flatten our tree!
		T temp = find(unionTree.get(a));
		unionTree.put(a, temp);
		return temp;
	}

	/**
	 * Resets this Union-Find so that each element is its own canonical element (ie.
     * each element is in its own set).
     */
	public void clear() 
	{
		Set<T> temp = unionTree.keySet();
		Iterator<T> iTemp = temp.iterator();
		
		while (iTemp.hasNext())
			unionTree.put(iTemp.next(), null);
		
		setCount = unionTree.size();
	}
	
	/**
	 * Returns the number of unique sets currently in our map
	 * @return The number of unique sets
	 */
	public int uniqueSets()
	{
		return setCount;
	}
	
	/**
	 * Simple method for comparing T objects
	 * Because I can't treat them as pointers :/
	 */
	public int myCompare(T a, T b)
	{
		if (a == null && b == null)
			return 0;
		else if (a == null)
			return -1;
		else if (b == null)
			return 1;
		else
		{
			if (a.hashCode() < b.hashCode())
				return -1;
			else if (a.hashCode() > b.hashCode())
				return 1;
			else 
				return 0;
		}
		
	}
}
