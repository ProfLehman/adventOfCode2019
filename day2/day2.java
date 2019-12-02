/*
 * 12.1.2019
 * day N 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
 
public class day2
{
	int data[];
    
    public day2() {
		ArrayList al = adventLibrary.readData("day2.txt");
		data = adventLibrary.intArray1CSV( al );
    }

    public static void main( String args[] ) {
        day2 myApp = new day2();
        myApp.test();   
    }//main
     
    public void test() {    

		int save[] = new int[ data.length ];
		
		//save data
		for (int i=0; i<data.length; i++)
			save[i] = data[i];
		
		data[1] = 12;
		data[2] = 2;
		System.out.println( "part1 test: " + part1( 0 ) );
		//assert( part1(0) == 3500 );
		//adventLibrary.show( data );

		//part 2
		for (int noun=0; noun<=99; noun++)
		{
			
			for (int verb=0; verb<=99; verb++)
			{
				//restore data
				for (int i=0; i<data.length; i++)
					data[i] = save[i];
			
				data[1] = noun;
				data[2] = verb;
				
				if (part1(0) == 19690720)
				{
					System.out.println( "noun: " + noun );
					System.out.println( "verb: " + verb );
				}
			}//for verb

		}//for noun

		}//test

	public int part1(int i)
	{
		//adventLibrary.show( data );
		if (data[i] == 99)
			return data[0];
		else
			if (data[i] == 1)
			{
				data[data[i+3]] = data[data[i+1]] + data[data[i+2]];
				return part1(i+4);
			}
		else
			if (data[i] == 2)
			{
				data[data[i+3]] = data[data[i+1]] * data[data[i+2]];
				return part1(i+4);
			}
			else
			{
				System.out.println("Error in part1 recursion");
				System.out.println( i + ": " + data[i]);
				
				return -1;
			}
	}
	
}//day2