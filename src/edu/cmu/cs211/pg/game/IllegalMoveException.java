package edu.cmu.cs211.pg.game;

@SuppressWarnings("serial")
public class IllegalMoveException extends RuntimeException 
{

	public IllegalMoveException(String string)
	{
		super(string);
	}

}
