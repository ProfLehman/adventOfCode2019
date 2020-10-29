/*
 * 12.1.2019
 * day N 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class day14
{
	HashMap<String, String> em; //available map
	HashMap<String, Long> am; //needed map
	
	long oreCount;
	
    public day14() {
		
		em = new HashMap<String, String> ();
		am = new HashMap<String, Long> ();
		oreCount = 0;
	
		ArrayList al = adventLibrary.readData("day14.txt");
		
		int x = 0;
        while (x < al.size())
        {
			String s = String.valueOf( al.get(x) );
			
			//System.out.println();
			//System.out.println( s );
			String t[] = s.split(", |\\s+");
			
			String key = t[ t.length-1 ]; 
			
			String value = t[t.length-2];
			for (int i=0; i<t.length-3; i++)
			{
				//System.out.println( i + ":" + t[i] );
				value += "," + t[i];
			}
			
			System.out.print( "key: " + key + " => " );
			System.out.println( "value: " + value );
			//System.out.println( s );
			em.put( key, value ); //element map
			am.put( key, 0L ); //available map ie. "extra" not used
		
			x++;
		}		
		System.out.println();
    }

    public static void main( String args[] ) {
        day14 myApp = new day14();
        myApp.test();   
    }//main
	
	public void test()
	{
		//react( "FUEL", 55860232L);
		react( "FUEL", 2371700L);
		
		System.out.println("ORE count: 1000000000000"); //
		System.out.println("ORE count: " + oreCount); 
		
		System.out.println( 1000000000000L / (long) oreCount );
	}//test
	  
	public void react( String element, long needed )
	{
		System.out.println( "    Element: " + element );
		System.out.println( "     Needed: " + needed );
		boolean baseCase = false;
		
		if (element.equals("ORE")) //base case when need ORE
		{
			System.out.println( "Need " + needed + " ORE" );
			oreCount += needed;
		}
		else
		{
			//adjust needed based on available
			long available = getAvailable( element );
			System.out.println( "  available: " + available );
			if (available >= needed)
			{
				baseCase = true; //no need for further reacting
				subAvailable(element, needed ); 	
			}
			else //available < needed
			{
				needed = needed - available; //use all available
				am.put( element, 0L); //set available to zero
				System.out.println( "     Needed: " + needed );		
		
				String value = em.get( element );
				String items[] = value.split(",");
				int produced = Integer.parseInt( items[0] );
								
				long multiplier = (long) Math.ceil( (double)needed / (double)produced );
				long totalProduced = produced * multiplier;
				long extra = (produced * multiplier) - needed;
				
				System.out.println( "x1 Produced: " + produced );		
				System.out.println( " Multiplier: " + multiplier );
				System.out.println( "xM Produced: " + totalProduced);
				
				System.out.println( "      extra: " + extra );		
				if (extra > 0)
					addExtra( element, extra );
						
				for (int i=1; i<items.length; i+=2)
				{
					System.out.println( "  Sub Need " + items[i] + " : " + items[i+1] );
					react( items[i+1], Integer.parseInt( items[i] ) * multiplier );
				}				
			}
		}		
	}//react
	
	public long getAvailable( String element)
	{
		long amount = 0;
		
		if (am.get( element )!= null)
			amount = am.get( element );
		
		return amount;
	}
	
	public void addExtra( String element, long extra)
	{
		long amount = 0;
		
		if (am.get( element )!= null)
			amount = am.get( element );
		
		amount += extra;
		
		am.put( element, amount );
		System.out.println( "Extra of " + element + " = " + amount );
	}
	
	public void subAvailable( String element, long extra)
	{
		long amount = 0;
		
		if (am.get( element )!= null)
			amount = am.get( element );
		
		amount -= extra;
		
		am.put( element, amount );
		System.out.println( "After sub Extra of " + element + " = " + amount );
	}
	
}//day14


