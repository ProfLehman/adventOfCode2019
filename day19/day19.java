/*
 * 12.30.2019
 * day 19
 * lehman
 * Advent of Code 2019
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;

public class day19
{
    public static void main( String args[] ) {
        day19 myApp = new day19();
        myApp.test();   
    }//main
     
    public void test() {    
	
		//load data for IntComputer program
        ArrayList al = adventLibrary.readData("day19.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

		//get new instance of IntComputer
		IntComputer amp1 = new IntComputer( data1);
		//amp1.process();
		//amp1.part1();
		//amp1.part2(1000);
		//amp1.find(591,435);
		amp1.find(0,400);
		System.out.println( "Part 1 - Done" );
		
    }//test
	
	
}//main