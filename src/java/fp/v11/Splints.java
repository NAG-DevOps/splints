package fp.v11;

import fp.v11.splints.bitbucketcloud.BitbucketCloud;

public class Splints {
	
	
	public static void main(String args[])
	{
		try
		{
			// just for testing
			BitbucketCloud client = new BitbucketCloud();
			client.editIssue();
		}
		catch(Exception e)
		{
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}
