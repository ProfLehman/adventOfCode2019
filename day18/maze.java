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
import java.util.Stack;
import java.util.Random;
import java.util.Scanner;


public class maze
{
	char data[][];
	HashMap<String, String> nMap;
	HashMap<String, Boolean> vMap;
	String nodes;
	Random rn;
	
    public maze() {
		//read maze from file into character array
		ArrayList al = adventLibrary.readData("day18.txt");
        data = adventLibrary.charArray2( al );
		//adventLibrary.show( data );
		
		//remove uppercase letters
		//for (int r=0; r<data.length; r++)
		//	for (int c=0; c<data[r].length; c++)
		//		if (Character.isUpperCase( data[r][c] ))
		//			data[r][c] = '.';
		addNodes();
		//nodes = "@abcdefghijklmnopqrstuvwxyz";
		
		showNodes();	
		buildGraph();
		//showGraph();
		//improvePath();
		//greedyPath();
		trip_dfs();
		//System.out.println( getCost( "@mfzvntksuaqyjdhoebrwixpglc"));
		//improvePath( "@mfzvntksuaqyjdhoebrwixpglc");
		
		//improvePath("@javmfzvntksuaqyjdhoebrwixpglc");
		//System.out.println( getCost( "@svmnfthijuqaydgzewxobckrlp" ) ); //4954
		//System.out.println( getCost( "@svnmztfquihjaywegdxcbokrlp" ) ); //5062
    }

    public static void main( String args[] ) 
	{
        maze myApp = new maze();
 		
    }//main
    
	public void addNodes()
	{
		nodes = "";
		for (int r=0; r<data.length; r++)
			for (int c=0; c<data[r].length; c++)
				if (data[r][c] != '.' && data[r][c] != '#' )
				{
					if (!Character.isUpperCase( data[r][c] ) ) //***************
						nodes = nodes + data[r][c];					
				}		
	}//addNodes
	
	public void showNodes()
	{
		for (int r=0; r<nodes.length(); r++)
			System.out.print( nodes.charAt(r) );
		System.out.println();
				
	}//addNodes
	
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
	
	public void buildGraph()
	{	
		nMap = new HashMap<String, String>(); //store paths		
		for (int i=0; i<nodes.length(); i++)
		{
			build_bfs( nodes.charAt(i) );
			//System.out.println();
		}
	}
	
	public void build_bfs( char startNode ) 
	{
		
		vMap = new HashMap<String, Boolean>();
		
		Queue<String> q = new LinkedList<>(); 
		
		q.add( getPosition(startNode) + ":;" ); //returns r,c
		
		
		while (q.size() > 0) 
		{
			String temp = q.remove();
			//System.out.println( "next q value : " + temp );
			
			int row = getRow(temp);
			int col = getCol(temp);
			String path = getPath(temp);
			String letters = getLetters(temp);
			
			//mark as visited
			vMap.put(row+","+col, true);

			//stop at letter to store, add to letters, or both
			char curChar = data[row][col];
			
			//if key or door, add to letters, store if not startNode or already found
			if (Character.isLetter(curChar) || curChar == '@')
			{
				letters = letters + curChar;
			
				if (curChar != startNode)
				{			
					String key = startNode+","+curChar;
					if (nMap.get(key) == null)
					{
						//System.out.printf("Store %c to %c, %d, %s\n", startNode, curChar, path.length(), letters);
						nMap.put(key, path+":"+letters);
					}
				}
			}
			
			int nr = -1;
			int nc = -1;
				
			//up
			nr = row-1;
			nc = col;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"u"+";"+letters);
			
			//down
			nr = row+1;
			nc = col;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"d"+";"+letters);

			//left
			nr = row;
			nc = col-1;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"l"+";"+letters);		

			//right
			nr = row;
			nc = col+1;
			if ( data[nr][nc] != '#' )
				if ( vMap.get( nr+","+nc ) == null )
					q.add( nr+","+nc+":"+path+"r"+";"+letters);	
			
			
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
		return temp.substring( temp.indexOf(':')+1, temp.indexOf(';') );
	}	
	
	private String getLetters( String temp )
	{
		return temp.substring( temp.indexOf(';')+1 );
	}

	public void showGraph()
	{
		for(int h=0; h<nodes.length(); h++)
			System.out.printf( "%4c", nodes.charAt(h) );
				
		System.out.println();
		
		for(int a=0; a<nodes.length(); a++)
		{
			System.out.printf( "%c ", nodes.charAt(a) );
			
			for(int b=0; b<nodes.length(); b++)
			{
				String key = nodes.charAt(a)+","+nodes.charAt(b);
				int cost = -1;
				if ( nMap.get(key) != null )
				{
					String temp = nMap.get(key);
					cost = temp.substring( 0, temp.indexOf(":")).length();
				}	
				System.out.printf( "%4d", cost );
			}	
			System.out.println();
		}
	}//showGraph	
	
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
			String temp = nMap.get( nodeStart+","+nodeStop );
			String path = temp.substring(0, temp.indexOf(":"));
			String letters = temp.substring(temp.indexOf(":")+1);
			
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
					System.out.println("Error: letter in path not found " + curChar);					
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
	}//getCost

	public void improvePath(String trip)
	{
		//String trip = "@zyxwvutsrqponmlikhjgdeafcb";
		String lowTrip = trip;
		 
		int trial = 0;
		int lowCost = 20000000;
		rn = new Random();
		
		while (trial < 2000000000)
		{
  
		  int curCost = getCost(trip);
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
			   
		   int a = rn.nextInt(trip.length()-1) + 1;
		   int b = rn.nextInt(trip.length()-1) + 1;
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
	}//testPaths

	public void greedyPath()
	{
		String trip = "@";
		int t=0;

		char c = '#';
		
		while( trip.length() != nodes.length() )
		{		
			char minChar = '#';
			int minCost =  1000000;
			String minLetters = "";
			Scanner kb = new Scanner(System.in);
			
			//show all that can be reached
			for (int i=0; i<nodes.length(); i++)
			{
				char startNode = trip.charAt(t);
				char stopNode = nodes.charAt(i);
				
				boolean reachIt = true;
				
				//starting point?
				if (startNode == stopNode)
					reachIt = false;
							
				//already found?
				if (trip.indexOf( stopNode+"" ) != -1)
					reachIt = false;
				
				//edge exist?
				String key = startNode+","+stopNode;
				if (nMap.get(key) == null)
					reachIt = false;
				
				if (reachIt)
				{	
					//check to make sure you have all needed keys
					String temp = nMap.get(key);
					String letters = temp.substring( temp.indexOf(":")+1 );
					String path = temp.substring(0, temp.indexOf(":"));
					
					String tripTemp = trip;
					boolean haveKeys = true;
					for (int j=1; j<letters.length()-1; j++)
					{
						char tempChar = letters.charAt(j);
						if (Character.isLowerCase( tempChar ))
							tripTemp += tempChar;
						else if (Character.isUpperCase( tempChar ))
						{
							if ( tripTemp.indexOf( Character.toLowerCase(tempChar)+"" ) == -1)
								haveKeys = false;
						}
					}
					
					//at this point stopNode is a valid option
					if (haveKeys)
					{
						System.out.printf("%c to %c cost %d  path %s\n", startNode, stopNode, path.length(), letters);	
						if (path.length() < minCost)
						{
							minCost = path.length();
							minChar = stopNode;
							minLetters = letters;
						}
					}
				}//reachIt
			}//for
			
			//pick node to add
			System.out.println( "pick next node: ");			
			//c = kb.nextLine().charAt(0);

//**** try adding all nodes along way???? from letters
			//trip += minChar;
			
			String tempA = nMap.get( trip.charAt(t)+","+minChar );
			
			for (int j=1; j<minLetters.length(); j++)
			{
				char nextChar = minLetters.charAt(j);
				
				if (Character.isLowerCase( nextChar ))
				{
					if (trip.indexOf( Character.toLowerCase(nextChar) ) == -1)
					{
						System.out.println( "Adding ..");
						trip += nextChar;
						t++;
					}
				}
			}
			
			System.out.println( "trip: " + trip + " : " + getCost( trip ) );
			
			//next to check			
			//char junk = kb.nextLine().charAt(0);
			
		}//while
		
		improvePath( trip );
		
	}//interactive
	
	public void trip_dfs()
	{
		Stack<String> stack = new Stack<String>(); 
		
		String trip = "@";	
		stack.push( trip );
		
		String minTrip = trip;
		int minCost = 1000000;
		
		while(stack.size() > 0)			
		{		

			trip = stack.pop();
			
			
			if ( trip.length() == 27 )
			{
				System.out.println( trip + " - " + getCost(trip) );
				int tempCost = getCost(trip);
				if (tempCost != -1)
				{
					System.out.println( "trip: " + trip + " : " + tempCost );
					if ( tempCost < minCost)
					{
						System.out.println( "trip: " + trip + " : " + tempCost );	
						minCost = tempCost;
						minTrip = trip;
					}
				}
			}
			else
			{
				//add all that can be reached from current trip
				char last = trip.charAt( trip.length()-1 );
				
				for (int i=0; i<nodes.length(); i++)
				{
					boolean reachIt = true;
					
					//node to check ie. last to tc
					char toNode = nodes.charAt(i);
					
					//already in trip
					if (trip.indexOf( ""+toNode ) != -1)
						reachIt = false;
					
					//skip doors
					
					//door without key?
					if ( Character.isUpperCase( toNode ) 
						&& trip.indexOf( ""+Character.toLowerCase(toNode))  == -1)
							reachIt = false;
						
					//edge exist?
					String key = last+","+toNode;
					if (nMap.get(key) == null)
						reachIt = false;
					
					if (reachIt)
					{	
						//add to stack
						stack.push( trip+toNode );
						System.out.println( "pushing " + trip + toNode );
					}
				}//for
			}//if add all that can be reached
			
		}//while
		
	}//dfs
	
}//class



	