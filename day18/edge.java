
public class edge {

	public node start;
	public node stop;
	
	public String path;
	public String letters;
		
	public edge( node a, node b, String p, String l )
	{
		start = a;
		stop = b;
		path = p;
		letters = l;
	}
	
	int getCost()
	{
		return path.length();
	}
	
	String getLetters()
	{
		return letters;
	}
	
	boolean isReachable( String keys )
	{
		boolean reachable = true;	
		
		for (int i=1; i<letters.length()-1; i++)
		{
			char c = letters.charAt(i);
			
			if ( Character.isUpperCase(c) )
			{
				char lower = Character.toLowerCase(c);
				if ( keys.indexOf(""+lower) == -1)
					reachable = false;
			}
		}
		return reachable;
	}

	public String toString()
	{
		return start + " to " + stop + " cost " + this.getCost() + " " + letters;
	}
	
}//class
