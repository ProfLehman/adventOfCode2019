/*
 * 1.20.2020
 * day 21
 * lehman
 * Advent of Code 2019
 * 
 */

import java.util.ArrayList;

public class day21
{
    public static void main( String args[] ) {
        day21 myApp = new day21();
        myApp.test();   
    }//main

    public void test() {    

        //load data for IntComputer program
        ArrayList al = adventLibrary.readData("day21.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

        //get new instance of IntComputer
        IntComputer amp1 = new IntComputer( data1);
        amp1.part1();
        System.out.println( "Part 1 - Done" );

    }//test

}//main