/*
 * 12.1.2020
 * day 21
 * lehman
 * Advent of Code 2019
 * 
 */

import java.util.ArrayList;

public class day22
{
    public static void main( String args[] ) {
        day22 myApp = new day22();
		
		System.out.println( -3 % 10 );
		
        //myApp.test();   
    }//main

    public void test() {    

		//*** test and actual data
		String f = "day22.txt";
		//f = "sample4.txt";

		int deckSize = 10007;
		//deckSize = 10;



		//*** setup deck
			
		int deck[] = new int[deckSize];
		for(int x=0; x<deck.length; x++)
			deck[x] = x;
		//showDeck(deck);
		
		
		//*** read data
		//load data for IntComputer program
		
        ArrayList al = adventLibrary.readData(f);
		
		int i = 0;
		while (i < al.size())
		{
			//get next line
			String s = al.get(i).toString();
			System.out.println( s );
			String line[] = s.split(" ");
			
			if (line[0].equals("cut"))
			{
				
				int number = Integer.parseInt( line[1] );
				System.out.println( "cut: " + number );
				
				int temp[] = new int[deckSize];
				
				//*** cut positive
				
				int position = number;
				
				//*** cut positive
				if (number < 0)
				{
					position = deck.length + number;
				}
					
				int count = 0;				
				while (count < deck.length)
				{
					temp[count] = deck[position];
					
					position++;
					
					if (position == deck.length)
						position = 0;
						
					count++;
				}//while
				
				//set deck to temp
				deck = temp;
			
				
				//showDeck( deck );
				
			}
			else
				//****** deal with increment ******
				if (line[1].equals("with"))
				{
					int number = Integer.parseInt( line[3] );
					System.out.println( "deal: " + number );

					int temp[] = new int[deckSize];
					int position = 0;
					
					for(int x=0; x<deck.length; x++)
					{
						temp[position] = deck[x];
						position = (position + number) % deckSize;
					}	
					
					//set deck to temp
					deck = temp;
				}
				else
				//****** deal into new stack ******
				{
					System.out.println( "deal new stack: " );	
					int temp[] = new int[deckSize];
					int position = deck.length-1;
					
					for(int x=0; x<deck.length; x++)
					{
						temp[position] = deck[x];
						position--;
					}	
					
					//set deck to temp
					deck = temp;
				}
			
			i++;
		}//while loop
		
		//showDeck( deck );
		
		for(int x=0; x<deck.length; x++)
			if (deck[x] == 2019)
				System.out.println( "2019 at position: " + x);
						
		System.out.println( "**** finished test 1 ****" );

    }//test
	
	public static void showDeck(int temp[])
	{
		for(int x=0; x<temp.length; x++)
			System.out.print( temp[x] + " ");
		System.out.println();
	}

}//main