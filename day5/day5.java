/*
 * 12.1.2019
 * day 5 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */

import java.util.ArrayList;
import java.util.Scanner;

public class day5
{
	int data[];

	public day5() {
		ArrayList al = adventLibrary.readData("day5.txt");
		data = adventLibrary.intArray1CSV( al );
	}

	public static void main( String args[] ) {
		day5 myApp = new day5();
		myApp.test();   
	}//main

	public void test() {    

		adventLibrary.show( data );
		System.out.println( "part1 test: " );
		part1( 0 );

	}//test

	public void part1(int i)
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
			case 99:  System.out.println("HALT"); break;

			case 1:
				  data[data[i+3]] = a + b;
				  part1(i+4);
				  break;

			case 2:
				  data[data[i+3]] = a * b;
				  part1(i+4);
				  break;


			case 3: Scanner scn = new Scanner(System.in);
				System.out.print("Input: "); 
				data[ data[i+1] ] = Integer.parseInt( scn.nextLine() ); 
				part1(i+2);
				break;

			case 4: System.out.println("Ouput: " + data[ data[i+1] ]);
				part1(i+2);
				break;

			case 5:
				if ( a != 0)
					part1(b);
				else
					part1(i+3);
				break;

			case 6:
				if ( a == 0)
					part1(b);
				else
					part1(i+3);
				break;

			case 7:
				if ( a < b)
					data[ data[i+3] ] = 1;
				else
					data[ data[i+3] ] = 0;

				part1(i+4);
				break;

			case 8:
				if ( a ==  b)
					data[ data[i+3] ] = 1;
				else
					data[ data[i+3] ] = 0;

				part1(i+4);
				break;
			default:
				System.out.println("Error: part1 switch: ");
				System.out.println( i + ": " + data[i]);

		}//switch
	}//part1

}//day5
