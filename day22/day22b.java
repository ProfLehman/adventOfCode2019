/*
 * 12.1.2020
 * day 21
 * lehman
 * Advent of Code 2019
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;

public class day22b
{
    public static void main( String args[] ) {
        day22b myApp = new day22b();
			
        myApp.test();   
    }//main

    public void test() {    

		//*** test and actual data
		String f = "day22.txt";
		//f = "sample4.txt";

		//deckSize = 10;
		long deckSize = 119315717514047L;
		//long deckSize = 10007L;

		long p = 2020;
		


		HashMap<Long, Long> hm = new HashMap<Long, Long>();

		
		
		
		//*** read data
		//load data for IntComputer program
		
        ArrayList al = adventLibrary.readData(f);
		
		
long r = 0L;

while (r < 101741582076661L)
//while (r < 1L)
{
	
	
	//see if need to perform inner loop
	/*
	if ( hm.get(p) != null )
	{
		p = (long) hm.get(p);
		System.out.println( hm.size() );
	}
	*/
	
	/*
	Object temp = hm.get(p); 
	if ( temp != null )
	{
		p = (long) temp;
		System.out.println( hm.size() );
	}
	else		
	{
		long pStart = p;
		
		int i = 0;
		while (i < al.size())
		{
			//get next line
			String s = al.get(i).toString();
			//System.out.println( s );
			String line[] = s.split(" ");
			
			if (line[0].equals("cut"))
			{
				
				long number = Integer.parseInt( line[1] );
				//System.out.println( "cut: " + number );
				
				
				//*** cut positive
				if (number < 0)
					p = (p + (number*-1)) % deckSize;
				else
				{
					p = p - number;
					if (p < 0)
						p += deckSize;
				}

			}
			else
				//****** deal with increment ******
				if (line[1].equals("with"))
				{
					
					int number = Integer.parseInt( line[3] );
					//System.out.println( "deal: " + number );
					p = (p * number) % deckSize;
				}
				else
				//****** deal into new stack ******
				{
					//System.out.println( "deal new stack: " );	
					p = deckSize - p -1;
				}
			
			//System.out.println( "in-loop: p at position: " + p);
			
			//System.out.println(p);
			
			//store
			if (hm.get(pStart) == null)
			{
				hm.put(pStart, p);
				//System.out.println( "Adding pStart  => " + p + "   hm size "  + hm.size());				
			}
			//else
				//System.out.println( "Found pStart  => " + p );
			
			i++;
		}//inner while loop

	}//if for inner loop
	
	*/
	if (r % 10000000000L == 0)
		System.out.println(r);
	
	
	
	r = r + 1;
}//outer while loop

		//showDeck( deck );
		
		System.out.println( "p at position: " + p);
						
		System.out.println( "**** finished test 1 ****" );

    }//test
	


}//main