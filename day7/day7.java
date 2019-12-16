/*
 * 12.1.2019
 * day 7
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 *
 */

import java.util.ArrayList;
import java.util.Scanner;

public class day7
{
	int data[];
	int save[];

	public day7() {
		ArrayList al = adventLibrary.readData("day7.txt");
		data = adventLibrary.intArray1CSV( al );

		//save data
		save = new int[ data.length ];
		for (int i=0; i<data.length; i++)
			save[i] = data[i];
	}

	public static void main( String args[] ) {
		day7 myApp = new day7();
		myApp.test();
	}//main

	public void test() {

		adventLibrary.show( data );

		System.out.println( "part 1: " );

		int s1 = -1;
		int s2 = -1;
		int s3 = -1;
		int s4 = -1;
		int s5 = -1;

		int max = -1;

		NumberCombo nc = new NumberCombo(5);
    while(!nc.done() )
    {
			s1 = process( nc.getValue(0), 0) ;
			s2 = process( nc.getValue(1), s1) ;
			s3 = process( nc.getValue(2), s2) ;
			s4 = process( nc.getValue(3), s3) ;
			s5 = process( nc.getValue(4), s4) ;

			if (s5 > max)
			{
				System.out.print( nc.toString() + " - ");
				System.out.println( s5 );
				max = s5;
			}

    	nc.next();
    }

	}//test

	public int process(int phase, int signal)
	{
		restoreData();

		int i = 0;
		int output = -1;
		boolean halt = false;

		while (!halt)
		{
			//System.out.println("\nprocessing: " + data[i] );
			String ABC = "0000" + data[i]; //convert to string with 0 padding
			ABC = ABC.substring(ABC.length()-5); //get right x5
			//System.out.println( "ABC = " + ABC );

			int opcode = Integer.parseInt( ABC.substring(ABC.length()-2 ) );
			//System.out.printf( "opcode = %d  %c%c \n", opcode, ABC.charAt(0), ABC.charAt(1) );

			int a = -1;
			int b = -1;

			if (opcode == 1 || opcode == 2 || opcode == 5 || opcode == 6 || opcode == 7 || opcode == 8)
			{
				if ( ABC.charAt(2) == '0' )
					a = data[ data[i+1] ];
				else
					a = data[ i+1 ];

				if ( ABC.charAt(1) == '0' )
					b = data[ data[i+2] ];
				else
					b = data[ i+2 ];
			}


			switch (opcode)
			{
				case 99:
					//System.out.println("HALT");
					halt=true; break;

				case 1:
					  data[data[i+3]] = a + b;
					  //return process(i+4, phase, signal, output);
						i = i + 4;
						break;

				case 2:
					  data[data[i+3]] = a * b;
					  //return process(i+4, phase, signal, output);
						i = i + 4;
					  break;

				case 3: Scanner scn = new Scanner(System.in);
					//System.out.print("Input: ");
					//data[ data[i+1] ] = Integer.parseInt( scn.nextLine() );

					//use phase for first input, signal for second input
					if (phase != -1)
					{
						data[ data[i+1] ] = phase;
						phase = -1;
					}	else if (signal != -1)
					{
						data[ data[i+1] ] = signal;
						signal = -1;
					}	else
					System.out.println( "Error: phase and signal both used");

					//return process(i+2, phase, signal, output);
					i = i + 2;
					break;

				case 4:
					//System.out.println("Ouput: " + data[ data[i+1] ]);
					output = data[ data[i+1] ];
					//return process(i+2, phase, signal, output);
					i = i + 2;
					break;

				case 5:
					if ( a != 0)
						//return process(b, phase, signal, output);
						i = b;
					else
						//return process(i+3, phase, signal, output);
						i = i + 3;
					break;

				case 6:
					if ( a == 0)
						//return process(b, phase, signal, output);
						i = b;
					else
						//return process(i+3, phase, signal, output);
						i = i + 3;
					break;

				case 7:
					if ( a < b)
						data[ data[i+3] ] = 1;
					else
						data[ data[i+3] ] = 0;

					//return process(i+4, phase, signal, output);
					i = i + 4;
					break;

				case 8:
					if ( a ==  b)
						data[ data[i+3] ] = 1;
					else
						data[ data[i+3] ] = 0;

					//return process(i+4, phase, signal, output);
					i = i + 4;
					break;

				default:
					System.out.println("Error: process switch: ");
					System.out.println( i + ": " + data[i]);
					halt = true;
			}//switch

		}//while

		return output;
	}//process

public void restoreData()
{
	//restore data
	for (int i=0; i<data.length; i++)
		data[i] = save[i];
}

}//day7
