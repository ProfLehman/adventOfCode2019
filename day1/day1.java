/*
 * 12.1.2019
 * day N 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
 
public class day1
{
    int data[];
	
    public day1() {
		ArrayList al = adventLibrary.readData("day1.txt");
		data = adventLibrary.intArray1( al );
    }

    public static void main( String args[] ) {
        day1 myApp = new day1();
        myApp.test();   
    }//main
     
    public void test() {    
     
		//adventLibrary.show( data );

		//part 1 tests
		assert getFuel(12) == 2 : " getFuel(12)";
		assert getFuel(14) == 2 : " getFuel(14)"; 
		assert getFuel(1969) == 654 : " getFuel(1969)"; 
		assert getFuel(100756) ==  33583 : " getFuel(100756)"; 
		
		//part 2 tests
		assert getFuel2(12) == 2 : " getFuel(12)";
		assert getFuel2(14) == 2 : " getFuel(14)"; 
		assert getFuel2(1969) == 966 : " getFuel(1969)"; 
		assert getFuel2(100756) ==  50346 : " getFuel(100756)"; 
		
		int sum = 0;
		int sum2 = 0;
		for (int i=0; i<100; i++)
		{
			sum += getFuel( data[i] );
			sum2 += getFuel2( data[i] );
		}
		System.out.println("Day1 Part 1: " + sum);
		System.out.println("Day1 Part 2: " + sum2);
    }//test
              
    public int getFuel(int n)
	{
		return n / 3 - 2;
	}
	
    public int getFuel2(int n)
	{
		int temp = n / 3 - 2;
		if (temp <= 0)
			return 0;
		else
			return temp + getFuel2(temp);
	}
     
}//dayN