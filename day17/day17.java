/*
 * 12.30.2019
 * day 17 
 * lehman
 * Advent of Code 2019
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;

public class day17
{
    public static void main( String args[] ) {
        day17 myApp = new day17();
        myApp.test();   
    }//main
     
    public void test() {    
	
		//load data for IntComputer program
        ArrayList al = adventLibrary.readData("day17.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

		//get new instance of IntComputer
		IntComputer amp1 = new IntComputer( data1);
		amp1.process();
		
		System.out.println( "Part 1 - Done" );
		
    }//test
	
	
}//main