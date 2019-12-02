/*
 * 12.1.2019
 * day N 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
 
public class dayN
{
	//int data[];
    char data[][];
	
    public dayN() {
		ArrayList al = adventLibrary.readData("day15_2018.txt");
		//data = adventLibrary.intArray1( al );
		data = adventLibrary.charArray2( al );
    }

    public static void main( String args[] ) {
        dayN myApp = new dayN();
        myApp.test();   
    }//main
     
    public void test() {    
     
		adventLibrary.show( data );

		//part 1 tests
		//assert getFuel(12) == 2 : " getFuel(12)";
    }//test
                  
}//dayN