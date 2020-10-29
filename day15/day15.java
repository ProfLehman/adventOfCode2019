/*
 * 12.30.2019
 * day 15 
 * lehman
 * Advent of Code 2019
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;

public class day15
{
	int curX = 500;
	int curY = 500;
	HashMap<String, Integer> dm;
	
	int max;
	
    public day15() {
		max = 0;
    }

    public static void main( String args[] ) {
        day15 myApp = new day15();
        myApp.test();   
    }//main
     
    public void test() {    
	
		//load data for IntComputer program
        ArrayList al = adventLibrary.readData("day15.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

		dm = new HashMap<String, Integer>(); //display map

		Queue<String> q = new LinkedList<>(); 
		
        System.out.println( "BFS part 1: " );
		String path = "4";
		q.add( path );

		boolean found = false;
		
		while (!found && q.size() > 0)
		{
			String temp = q.remove();
			
			//get new instance of IntComputer
			IntComputer amp1 = new IntComputer( data1, dm );
			
			amp1.setPath( temp );
			amp1.process();
			//System.out.println(" status: " + amp1.status );
			
			if (amp1.status.equals("found"))
			{
				found = true;
				System.out.println( " Found Path: " + temp );
				System.out.println( "Path Length: " + temp.length() );
				System.out.println( "   Location: " + amp1.curX + "," + amp1.curY );
				display();	
			}
			else if (amp1.status.equals("open"))
			{
				//display();
				int y = amp1.curY;
				int x = amp1.curX;
				int ty = -1;
				int tx = -1;
				
				tx = x;
				ty = y+1;
				if ( dm.get(tx+","+ty) == null) //only add path if it has not already been discovered
					q.add( temp + "1" ); //N
				
				tx = x+1;
				ty = y;
				if ( dm.get(tx+","+ty) == null)
					q.add( temp + "4" ); //E
					
				tx = x;
				ty = y-1;
				if ( dm.get(tx+","+ty) == null)
					q.add( temp + "2" ); //S
					
				tx = x-1;
				ty = y;
				if ( dm.get(tx+","+ty) == null)
					q.add( temp + "3" ); //W
			}
			
		}//while
		display();
		
		System.out.println( "Flood Fill part 2: " );
		fill(517,482,0);
		
		display();
		System.out.println( "Max: " + max );
		
    }//test
	
	public void fill(int x, int y, int m)
	{
		if ( dm.get(x+","+y) == 0) //open
		{
			dm.put(x+","+y, 2);
			
			if (m > max)
				max = m;
			fill( x, y+1, m+1 );//N
			fill( x, y-1, m+1 );//S
			fill( x+1, y, m+1 );//E
			fill( x-1, y, m+1 );//W
		}
	}//fill
	
	public void display()
	{
		int minX = 475;
		int maxX = 520;
		int minY = 480;
		int maxY = 525;
		
		for (int k=maxY; k>=minY; k--)
		{
			for (int j=minX; j<=maxX; j++)
			{
				//if ( curX == j && curY == k)
				//	System.out.print("D");
				//else 
				if ( dm.get(j+","+k) == null )
					System.out.print(" ");
				else if (dm.get(j+","+k) == 1)
					System.out.print("#");
				else if (dm.get(j+","+k) == 0)
					System.out.print(".");
				else if (dm.get(j+","+k) == 2)
					System.out.print("S");
				else
					System.out.print( "E" );			
			}
			System.out.println();
		}
	}//display	
	
}//main