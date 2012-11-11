package gmusic.api.impl;

public class InvalidCredentialsException extends Exception
{

	InvalidCredentialsException(IllegalStateException ise, String string)
	{
		super(string, ise);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 7121443984182451964L;

}
