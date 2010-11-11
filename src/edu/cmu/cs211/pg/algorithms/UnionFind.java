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

public class UnionFind<T> 
{	
	// We keep track of both roots of nodes
	// and sizes of sets
	// We only care about the size of sets 
	// for canonical nodes
	HashMap<T, T> unionTree;
	HashMap<T, Integer> unionSize;
	
	int setCount;
	
	public UnionFind() 
	{
		// Initialize our map
		unionTree = new HashMap<T, T>();
		unionSize = new HashMap<T, Integer>();
		
		setCount = 0;
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
		
		// Find the defining element for both sets
		// If they're not included, then find() will add them to our set
		a = find(a);
		b = find(b);
		
		int sizeA = unionSize.get(a);
		int sizeB = unionSize.get(b);
		
		// We don't care if the two sets are the same size -- then it doesn't matter
		// which guy is the new root
		// However, we want smaller sets to point directly to the roots of larger sets
		T newRoot = sizeB > sizeA ? b : a;
		T newBranch = sizeB > sizeA ? a : b;
		
		if (!a.equals(b))
		{
			// Our branch points to our root, and we set the size of our
			// new root
			unionTree.put(newBranch, newRoot);
			unionSize.put(newRoot, sizeA + sizeB);
			
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
		
		// If a isn't a part of our set, add it!
		// Coincidentally, it's also its own set
		if (addElement(a))
			return a;
		
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
		
		for(T key : temp)
		{
			unionTree.put(key, null);
			unionSize.put(key, 1);
		}
		
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
	 * Method for adding a new element to our union find.  It's in its own set.
	 * @param a The element we want to add
	 * @return True if the element was added, false if the element already exists in our set
	 */
	private boolean addElement(T a)
	{
		if (!unionTree.containsKey(a))
		{	
			unionTree.put(a, null);	
			unionSize.put(a, 1);
			
			setCount++; 
			return true;
		}
		
		return false;
	}
	
	/**
	 * Simple method for comparing T objects
	 * Because I can't treat them as pointers :/
	 */
	private int myCompare(T a, T b)
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
