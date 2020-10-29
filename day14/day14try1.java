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
	String data[];
	HashMap<String, Integer> am; //available map
	HashMap<String, Integer> nm; //needed map
	
	int oreCount;
	
    public day14() {
		
		am = new HashMap<String, Integer> ();
		nm = new HashMap<String, Integer> ();
		oreCount = 0;
	
		ArrayList al = adventLibrary.readData("day14t5.txt");
		
		data = new String[al.size()];
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
			
			//System.out.println( key );
			//System.out.println( value );
			//System.out.println( s );
			
			data[x] = key + "," + value;
			System.out.println( data[x] );
			
			am.put( key, 0 );
			nm.put( key, 0 );
			
			x++;
		}		
		nm.put("FUEL", 1);
        
		am.put("ORE", 1000000);
		nm.put("ORE", 0);
		
        System.out.println();
		for (int y=0; y<data.length; y++)
		{
			System.out.print( data[y] + " --- " );
			String k = data[y].substring(0, data[y].indexOf(','));
			System.out.print( nm.get(k) + ", " );
			System.out.println( am.get(k) );
		}

    }

    public static void main( String args[] ) {
        day14 myApp = new day14();
        myApp.test();   
    }//main
   

/*   
    public void test() {    
		Scanner kbd = new Scanner( System.in );
		
		int count = 0;
		
		int i = 0;
		while( am.get("FUEL") != 1)
		//while (count < 25)
		{
			count++;
			String element = getKey( data[i] );
			//System.out.println( element );
			
			//see if element is needed
			if (nm.get( element ) > am.get( element ) )
			{
				System.out.println( element + " is needed" );
				
				//see if all elements needed are available
				if ( haveSupplies( data[i] ) )
				{
					//produced
					//System.out.println( "have supplies for " + element );
					produce( data[i] );
				}
				else
				{
					//add requests
					//System.out.println( "do NOT have supplies for " + element );					
					addRequests( data[i] );
				}
			}
			
			i++;
			if (i == data.length)
				i = 0;
			
			//System.out.println("press any key to continue ...");
			//int temp = kbd.nextInt();
			
		}//main while			
		
		System.out.println( "ORE count: " + oreCount );
		System.out.println( "ORE available: " + am.get("ORE"));
		
		for (int p=0; p<data.length; p++)
		{
			String element = getKey( data[p] );
			System.out.println ( element + " " + nm.get(element) + ", " + am.get(element));
		}
		
    }//test
	*/
	
	public String getKey( String s )
	{
		String t[] = s.split(",");
		return t[0];
	}
	
	public boolean haveSupplies( String s ) 
	{
		boolean result = true;
		String t[] = s.split(",");
		
		for (int x=2; x<t.length; x+=2)
		{
			int needed = Integer.parseInt( t[x] );
			if ( needed > am.get( t[x+1] ) )
				result = false;
		}
				
		return result;
	}
	
	public void addRequests( String s ) 
	{
		String t[] = s.split(",");	
		for (int x=2; x<t.length; x+=2)
		{
			int needed = Integer.parseInt( t[x] );
			updateNeeded( t[x+1], needed );
			System.out.println( " adding request for " + needed + " " + t[x+1] );
		}
	} 
	
	public void produce( String s ) 
	{
		String t[] = s.split(",");
		System.out.println( "producing " + t[1] + " of " + t[0] );		
		updateAvailable( t[0], Integer.parseInt( t[1] ) );
		
		
		for (int x=2; x<t.length; x+=2)
		{
			int needed = Integer.parseInt( t[x] );
			subtractNeeded( t[x+1], needed );
			subtractAvailable( t[x+1], needed );
			
			System.out.println( " using " + needed + " " + t[x+1] );
			if ( t[x+1].equals("ORE"))
				oreCount += needed;
		}
	}	
	
	public void updateNeeded( String element, int amount )
	{
		int current = nm.get( element );
		
		if (amount > current)
			nm.put( element, amount );	
		
		//nm.put( element, amount+current );	
	}

	public void subtractNeeded( String element, int amount )
	{
		int current = nm.get( element );	
		current = current - amount;
		nm.put( element, current );	
	}
	
	public void subtractAvailable( String element, int amount )
	{
		int current = am.get( element );	
		current = current - amount;
		am.put( element, current );	
	}

	
	public void updateAvailable( String element, int amount )
	{
		int current = am.get( element );
		current += amount;
		am.put( element, current );
	}
	

/*
	public int addAvailable( String e, int amount )
	{
		if (sm.get(e) != null)
		{
			//get current amount
			int currentAmount = getAvailable( e );
			
			currentAmount += amount;
			
			String temp = sm.get(e);
			int c = temp.indexOf(',');
			currentAmount = Integer.parseInt( 0,temp.substring(c+1) );
					
		}
		else
			System.out.println("Error: addAvailable key not found");
		
	}//addAvailable
	*/
	
	/*
	public void react(int n, String e)
	{
		if ( e.equals("ORE") )
		{
			System.out.println("Adding " + n + " ORE"); //base case
			oreCount += n;
		} 
		else if ( getSurplus(e) >= n) //base case, surplus covers need
		{
			int left = getSurplus(e) - n;
			saveSurplus( e, left );
			System.out.println( e + " using surplus, " + left + " now left");
		}
		else if ( hm.get( e ) == null )
		{
				System.out.println( "Error: " + e + " not found" );
		}
		else
		{
			System.out.println( "react: " + e );
				
			String s = hm.get( e );
			String t[] = s.split(",");

			int np = Integer.parseInt( t[0] ); //amount produced
				
			//see of more produced than needed
			if (np > n )  	
			{
				System.out.println("Adding to surpus for " + e);
				saveSurplus( e, (np-n) ); //add to surplus
			}
			else
				System.out.println("n < np for " + e);
			//if (np < n)
			//{
			//	int left = getSurplus(e);
			//	saveSurplus(e,0);
			//	n = n - left;
			//}
			
			//int rp = (int) Math.ceil( (double)n / (double)np );
			
			while (n >0)
			{
				for (int i=1; i<t.length-1; i+=2)
				{
					int q = Integer.parseInt( t[i] );
					String el = t[i+1];
					System.out.println( " Needs x" + q + " " + el);
					react( q, el );
				}//for
				n = n - np;
			}
		}//last else			

	}//react
	
	public int getSurplus( String e )
	{
		int amount = 0;
		if (sm.get(e) != null) //see if surplus is available
			amount = (int) sm.get(e);
			
		return amount;
	}//getSurplus
	
	public void saveSurplus( String e, int amount )
	{
		System.out.println("Adding to surpus for " + e);
		if ( sm.get(e) != null )
		{
			int current = (int) sm.get(e);
			amount = amount + current;	
		}
		sm.put(e, amount);
		
	}
	*/

	  
}//day14


