package edu.cmu.cs211.pg.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static org.junit.Assert.fail;

import edu.cmu.cs211.pg.algorithms.UnionFind;
import java.util.Arrays;

public class UnionFindTest {

	@Test
	public void sanityTest()
	{
		UnionFind<String> uf = new UnionFind<String>();
		
		// Add A, B, C, D with Find()
		assertEquals("Checking that A root is A", uf.find("A"), "A");
		assertEquals("Checking that B root is B", uf.find("B"), "B");
		assertEquals("Checking that C root is C", uf.find("C"), "C");
		assertEquals("Checking that D root is D", uf.find("D"), "D");
		
		//assertEquals("Checking set size", uf.uniqueSets(), 4);
		
		uf.union("A", "B");
		
		assertEquals("Checking that B root is A root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that C root is STILL C", uf.find("C"), "C");
		assertEquals("Checking that D root is STILL D", uf.find("D"), "D");
		
		//assertEquals("Checking set size", uf.uniqueSets(), 3);
		
		uf.union("D", "C");
		
		assertEquals("Checking that A root is B root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that D root is C root", uf.find("C"), uf.find("D"));
		assertEquals("Checking that we have two different sets", uf.find("A").equals(uf.find("C")), false);
		assertEquals("Checking that we have two different sets", uf.find("B").equals(uf.find("D")), false);
		
		//assertEquals("Checking set size", uf.uniqueSets(), 2);
		
		uf.union("A", "C");
		assertEquals("Checking that A root is B root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that A root is C root", uf.find("A"), uf.find("C"));
		assertEquals("Checking that A root is D root", uf.find("A"), uf.find("D"));
		
		//assertEquals("Checking set size", uf.uniqueSets(), 1);
	}
	
	@Test
	public void clearTest()
	{
		UnionFind<String> uf = new UnionFind<String>();
	
		// Add A, B, C, D with Find()
		assertEquals("Checking that A root is A", uf.find("A"), "A");
		assertEquals("Checking that B root is B", uf.find("B"), "B");
		assertEquals("Checking that C root is C", uf.find("C"), "C");
		assertEquals("Checking that D root is D", uf.find("D"), "D");
		//assertEquals("Checking set size", uf.uniqueSets(), 4);
		
		uf.union("A", "B");
		uf.union("A", "C");
		uf.union("A", "D");
		
		assertEquals("Checking that A root is B root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that A root is C root", uf.find("A"), uf.find("C"));
		assertEquals("Checking that A root is D root", uf.find("A"), uf.find("D"));	
		//assertEquals("Checking set size", uf.uniqueSets(), 1);
		
		uf.clear();
		
		assertEquals("Checking that A root is A", uf.find("A"), "A");
		assertEquals("Checking that B root is B", uf.find("B"), "B");
		assertEquals("Checking that C root is C", uf.find("C"), "C");
		assertEquals("Checking that D root is D", uf.find("D"), "D");
		//assertEquals("Checking set size after clear", uf.uniqueSets(), 4);
	}
	
	@Test
	public void doubleUnionTest()
	{
		UnionFind<String> uf = new UnionFind<String>();
		
		// Add A, B, C, D with Find()
		assertEquals("Checking that A root is A", uf.find("A"), "A");
		assertEquals("Checking that B root is B", uf.find("B"), "B");
		assertEquals("Checking that C root is C", uf.find("C"), "C");
		assertEquals("Checking that D root is D", uf.find("D"), "D");
		//assertEquals("Checking set size", uf.uniqueSets(), 4);
		
		uf.union("A", "B");
		assertEquals("Checking that A root is B root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that A root is C root", uf.find("C"), "C");
		assertEquals("Checking that A root is D root", uf.find("D"), "D");
		//assertEquals("Checking set size", uf.uniqueSets(), 3);
		
		uf.union("A", "B");
		assertEquals("Checking that A root is B root", uf.find("A"), uf.find("B"));
		assertEquals("Checking that A root is C root", uf.find("C"), "C");
		assertEquals("Checking that A root is D root", uf.find("D"), "D");
		//assertEquals("Checking set size", uf.uniqueSets(), 3);
	}
	
	
	
	// ********************** NULL TESTS **************************
	@Test (expected=NullPointerException.class)
	public void nullUnionTest()
	{
		UnionFind<String> uf = new UnionFind<String>();
		uf.union(null, "F");
		fail();
	}
	
	@Test (expected=NullPointerException.class)
	public void nullFindTest()
	{
		UnionFind<String> uf = new UnionFind<String>();
		uf.find(null);
		fail();
	}
}
