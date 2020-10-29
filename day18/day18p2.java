/*
 * 1.5.2020
 * day 18 
 * lehman
 * Advent of Code 2019
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;


public class day18p2
{
	char data[][];
	HashMap<String, String> kdMap;
	HashMap<String, Boolean> vMap;
	Random rn;
	String nodes;
	
    public day18p2() {
		rn = new Random();
		
		nodes = "@bcfo"; //part a - upper left
	
    }

    public static void main( String args[] ) {
        day18p2 myApp = new day18p2();
        myApp.loadData(); 
		myApp.buildGraph();
		myApp.testPaths();
		//myApp.interactive();
		
    }//main
     
    public void loadData() {    
		ArrayList al = adventLibrary.readData("day18p2.txt");
        data = adventLibrary.charArray2( al );
		adventLibrary.show( data );
		showPositions();
    }//loadData

	public String getPosition( char sc )
	{
		String pos = "-1,-1";
		for (int r=0; r<data.length; r++)
			for (int c=0; c<data[r].length; c++)
				if (data[r][c] == sc)
				{
					pos = r+","+c;					
				}
		return pos;
	}//getPosition
	
	public void showPositions()
	{
		System.out.println( "@ at " + getPosition('@') );
		for (int letter=0; letter<26; letter++)
		{
			System.out.println( (char) (letter+65+32) + " at " + getPosition( (char) (letter+65+32) ) );
			System.out.println( (char) (letter+65) + " at " + getPosition( (char) (letter+65) ) );
		}
	}//showPositions
	
	
	public void buildGraph()
	{
		
		kdMap = new HashMap<String, String>(); //store paths		
		for (int i=0; i<nodes.length(); i++)
		{
			bfs( nodes.charAt(i) );
			//System.out.println();
		}
		
		
		for(int h=0; h<nodes.length(); h++)
			System.out.printf( "%4c ", nodes.charAt(h) );
				
		System.out.println();
		
		for(int a=0; a<nodes.length(); a++)
		{
			System.out.printf( "%c ", nodes.charAt(a) );
			
			for(int b=0; b<nodes.length(); b++)
			{
				String temp = kdMap.get( nodes.charAt(a)+","+nodes.charAt(b) );
				System.out.printf( "%4d ", getPath(temp).length() );
			}	
			System.out.println();
		}
		
		
	}//buildGraph
	
	public void interactive()
	{
		String trip = "@";
		int t=0;
		String myKeys = "@";
		System.out.println( "myPath " + trip + "    myKeys: " + myKeys );
		
		Scanner kb = new Scanner(System.in);
		
		System.out.println( "node to check: " );
		//char c = kb.nextLine().charAt(0);
		char c = '#';
		//while ( trip.length() <= 26 )
		//while (c != '#')
		boolean stop = false;
		while(!stop)
		{
			
			//show all that can be reached
			char minNode = '@';
			int minValue = 1000000;
			
			for (int i=0; i<nodes.length(); i++)
			{
				String temp = kdMap.get( trip.charAt(t)+","+nodes.charAt(i) );
				
				boolean OK = true;
				for (int j=1; j<getLetters(temp).length()-1; j++)
				{
					
					char curChar = getLetters(temp).charAt(j);
					
					if (Character.isUpperCase( curChar ))
					{
						if (myKeys.indexOf( Character.toLowerCase(curChar) ) == -1)
						{
							OK = false;
						}
					}
				}
				
				if ( trip.indexOf( (char)i ) != -1 )
					OK = false; //alread in trip
			
				if (OK)
				{
					System.out.println( " to " + (char)i + " path: " + getPath(temp).length() + " " + getLetters(temp) );		
					if ( getPath(temp).length() < minValue )
					{
						minValue = getPath(temp).length();
						minNode = (char)i;
					}
				}
			}
			
			//pick one to add	
			System.out.println( "node to add: " + minNode);
			//c = kb.nextLine().charAt(0);
			c = minNode;
			
			String tempA = kdMap.get( trip.charAt(t)+","+c );
			System.out.println( "path to add " + getLetters( tempA ) );
			
			for (int j=1; j<getLetters(tempA).length(); j++)
			{
				char curChar = getLetters(tempA).charAt(j);
				
				if (Character.isLowerCase( curChar ))
				{
					if (myKeys.indexOf( Character.toLowerCase(curChar) ) == -1)
					{
						System.out.println( "Adding ..");
						myKeys += curChar;
						trip += curChar;
						t++;
					}
				}
			}
			
			System.out.println( "myPath " + trip + "    myKeys: " + myKeys );
			System.out.println( getCost( trip ) );
			
			//next to check			
			//c = kb.nextLine().charAt(0);
			
		}//while
		
	}//interactive
	
	
	public void testPaths()
	{
	  int trial = 0;
	  int lowCost = 20000000;
	  
	  
	  //String trip = "vntfcqjzemsouhkrigdaywxblp"; ///5670 - too high
	  //  String trip = "vntfqjczemsuhkrioaygdwxblp"; //5610 - too high
	  
	  String trip = "vntfcqjzemsuhkrioaygdwxblp";
	  trip = "mfzvntksuaqyjdhoebrwixpglc";
	  
	  //String trip = "zevnt";
	  String lowTrip = trip;
	  
	  while (trial < 2000000000)
	  //while (lowCost == 2000000)
	  {

		  
		  
		  int curCost = getCost("@"+trip);
		  if (curCost != -1)
		  {			  
			   if (curCost < lowCost)
			   {
				   lowCost = curCost;
				   lowTrip = trip;
				   System.out.println( lowTrip + " " + lowCost );		
			   }
		  }
		  else
		  {
			  //go back to low cost
			  trip = lowTrip;
		  }
			   
		   int a = rn.nextInt(trip.length());
		   int b = rn.nextInt(trip.length());
		   String tempTrip = "";
		   for (int j=0; j<trip.length(); j++)
		   {
			   if (j == a)
				   tempTrip += trip.charAt(b);
			   else if (j == b)
				   tempTrip += trip.charAt(a);
			   else
				   tempTrip += trip.charAt(j);
		   }
		   trip = tempTrip;
		   
		   trial++;
		   
		  if (trial == 2000000000)
		  {
			  System.out.println( "2B" );
			  trial = 0;
		  }
	  }
	}//testPath
	
	public int getCost( String trip )
	{
		int totalCost = 0;
		boolean OK = true;
		String keysFound = "";
		
		int i=0;
		while (OK && i < trip.length()-1)
		{
			char nodeStart = trip.charAt(i);
			char nodeStop = trip.charAt(i+1);
			String temp = kdMap.get( nodeStart+","+nodeStop );
			String path = getPath( temp );
			String letters = getLetters( temp );
			
			//grab starting key
			keysFound += nodeStart;
			
			for (int j=1; j<letters.length()-1; j++)
			{
				char curChar = letters.charAt(j);
				
				//add key if not already found
				if (Character.isLowerCase( curChar ) || curChar == '@')
				{
					if (keysFound.indexOf( curChar ) == -1)
						keysFound = keysFound + curChar;
				}
				else if (Character.isUpperCase( curChar ))
				{
					if (keysFound.indexOf( Character.toLowerCase(curChar) ) == -1)
					{
						OK = false;
						totalCost = -1; //error
						break;
					}
				}
				else
					System.out.println("Error: letter in path not found");					
			}
			
			//System.out.println( nodeStart + " to " + nodeStop );
			//System.out.println( path.length() );
			//System.out.println( letters );
			//System.out.println();
			
			if (OK)
				totalCost += path.length();
			
			i++;
		}			
		//System.out.println( "keysFound " + keysFound );
		
		return totalCost;
	}
	
	
	
	
	public void bfs( char startNode ) 
	{
		
		vMap = new HashMap<String, Boolean>();
		
		Queue<String> q = new LinkedList<>(); 
		
        //System.out.println( "BFS part 1: " );
		//q.add( "40,40:;" );
		//q.add( "63,3:;" );

		q.add( getPosition(startNode) + ":;" );
		
		int count = 0; //looking for A-Z, a-Z, and @ total of 53 items
		
		while (count <= 53 && q.size() > 0) 
		{
			String temp = q.remove();
			//System.out.println( "next q value : " + temp );
			
			int row = getRow(temp);
			int col = getCol(temp);
			String path = getPath(temp);
			String letters = getLetters(temp);
			
			//mark as visited
			vMap.put(row+","+col, true);
			
			if ( data[row][col] != '.')
			{
				//found letter
				String s = Character.toString( data[row][col] );
				
				if (kdMap.get( startNode+","+s ) == null)
				{
					letters = letters + s;
					//System.out.println( "storing " + s + " path: " + path + "-" + path.length() + " " + letters);
					System.out.println( "storing " + s + " path: " + path.length() + " " + letters);
					kdMap.put( startNode+","+s, startNode+","+s+":"+path+";"+letters );
					
					count++;
				}
				else
				{
					letters = letters + s;
					//System.out.println( s + " found again");
					//System.out.println( "storxxx " + s + " path: " + path + "-" + path.length() + " " + letters);
					//System.out.println( "storxxx " + s + " path: " + path.length() + " " + letters);
				}
			}
			
			int nr = -1;
			int nc = -1;
			
			//up
			nr = row-1;
			nc = col;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"u"+";"+letters );
			
			//down
			nr = row+1;
			nc = col;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"d"+";"+letters );

			//left
			nr = row;
			nc = col-1;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"l"+";"+letters );		

			//right
			nr = row;
			nc = col+1;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"r"+";"+letters );	
				
		}//while
		
	}//bfs
	
	private int getRow( String temp )
	{
		return Integer.parseInt( temp.substring(0,temp.indexOf(',')) );
	}
	
	private int getCol( String temp )
	{
		return Integer.parseInt( temp.substring(temp.indexOf(',')+1, temp.indexOf(':') ) );

	}		
			
	private String getPath( String temp )
	{
		return temp.substring( temp.indexOf(':')+1,temp.indexOf(';') );
	}		
	
	private String getLetters( String temp )
	{
		return temp.substring( temp.indexOf(";")+1 );
	}	
	
}//class

