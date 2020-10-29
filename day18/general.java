/*
 * 1.5.2020
 * day 18 
 * lehman
 * Advent of Code 2019
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Stack;
import java.util.Random;
import java.util.Scanner;

public class general
{
	char data[][];
	graph myGraph;
	Random rn;
	//String extras = "adeghijklmnpqrstuvwxyz"; //top left cbfo - 712
	//String extras = "abcdefgmnostvwxyz"; //top right hijklpqru - 408
	//String extras = "abcfhijklnopqrstuvxy"; //bottomRight degmwz - 812
	String extras = "bcdefghijklmnopqruwz"; //astvxy - 402



    
	public general() {
		
		System.out.println( 712+408+812+402 );
		
		//read maze from file into character array
		ArrayList al = adventLibrary.readData("bottomLeft.txt");
        data = adventLibrary.charArray2( al );
		//adventLibrary.show( data );
		/*
		for (int r=0; r<data.length; r++)
		{
			for (int c=40; c<=80; c++)
				System.out.print( data[r][c] );
			bott
			System.out.println();
		}
		
		*/
		
		rn = new Random();
		myGraph = new graph();	
		
		addNodes();
		
		System.out.println( myGraph.nodes.size() );
		myGraph.showNodes();
		
		buildGraph();
		myGraph.showEdges();
		
		//System.out.println( getTripCost("@vsmnftqujihyadgzewxcbokrlp") ); //4954
		//System.out.println( getTripCost("@vfzmntskujaygdqobcewixrplh") ); //6018
		//System.out.println( getTripCost("@hfzmntskujaygqdobcewixrplh") ); //-1
		//System.out.println( getTripCost("@mfzvntksuaqyjdhoebrwixpglc") ); //9580
		
		
		//dfs();
		//improvePath("test");
		
		
    }//main

    public static void main( String args[] ) 
	{
        general myApp = new general();
    }//main
	
//******************************************************	
	public void addNodes()
	{
		for (int r=0; r<data.length; r++)
			for (int c=0; c<data[r].length; c++)
				if (data[r][c] != '.' && data[r][c] != '#' )
				{
					if (!Character.isUpperCase( data[r][c] ))
						myGraph.addNode( new node(r,c, ""+data[r][c] ) );					
				}		
	}//addNodes
	
	public void buildGraph()
	{	
		for (int i=0; i<myGraph.nodes.size(); i++)
		{
			build_bfs( myGraph.nodes.get(i) );
			//System.out.println();
		}
	}
	
	public void build_bfs( node startNode ) 
	{
		
		HashMap<String, Boolean> vMap = new HashMap<String, Boolean>();
		
		Queue<String> q = new LinkedList<>(); 
		
		q.add( startNode.x+","+startNode.y + ":;" ); //returns r,c	
		
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
			
				if (curChar != startNode.id.charAt(0) && !Character.isUpperCase(curChar))
				{			
					String key = startNode.id+","+curChar;
					if (myGraph.getEdge(key) == null)
					{
						node st = myGraph.getNode( startNode.id );
						node sp = myGraph.getNode( ""+curChar );
						edge e = new edge( st, sp, path, letters );
						myGraph.addEdge(key, e);
						//System.out.println( "Storing edge " + e );
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
//**************************************************
	public int getTripCost( String trip )
	{
		int totalCost = 0;
		boolean OK = true;
		
		//add starting point to keysFound
		String keysFound = ""+trip.charAt(0);
		
		int i=0;
		while (OK && i < trip.length()-1)
		{
			//System.out.println( "keysFound " + keysFound );
			node startNode = myGraph.getNode( ""+trip.charAt(i) );
			node stopNode = myGraph.getNode( ""+trip.charAt(i+1) );
			
			String key = startNode.id+","+stopNode.id;
			//System.out.println ("key " + key);
			
			edge e = myGraph.getEdge( key );
			//System.out.println ("edge " + e);
			
			if ( e.isReachable(keysFound+extras) )
			{
				//add stopNoode to trip
				//trip += stopNode.id;
				
				//add keys from edge
				String edgeLetters = e.getLetters();
				for (int g=0; g<edgeLetters.length(); g++)
				{
					char c = edgeLetters.charAt(g);
					if (Character.isLowerCase(c))
						if (keysFound.indexOf(""+c) == -1)
							keysFound += c;
				}
				
				//keysFound += stopNode.id;
				//add cost to totalCost
				totalCost += e.getCost();
			}
			else
			{
				OK = false;
				totalCost = -1; //flag as invalid
			}
			
			
			i++;
		}			
		
		return totalCost;
	}//tripCost	
	
	public void dfs()
	{
		System.out.println("dfs");
		
		
		int count = 0;
		
		Stack<String> stack = new Stack<String>(); 
		//Queue<String> queue = new LinkedList<String>(); 
		
		String trip = "@";	
		stack.push( trip );
		//queue.add( trip );
		
		String minTrip = trip;
		int minCost = 1000000;
		
		while(stack.size() > 0)			
		//while(queue.size() > 0)			
		{		
			trip = stack.pop();
			//trip = queue.remove();
	
			if ( trip.length() == myGraph.nodes.size() )
			{
				//System.out.println( trip + " - " + getTripCost(trip) );
				
				int tempCost = getTripCost(trip);
				if (tempCost != -1)
				{
					
					count++;
					
					///if (count == 1)
					// improvePath( trip );
					
					
					//System.out.println( "trip: " + trip + " : " + tempCost );
					if ( tempCost < minCost)
					{
						System.out.println( trip + " " + getTripCost( trip ) );
						minCost = tempCost;
						minTrip = trip;
						
						
					}
					
					
				}
			}
			else
			{
				//add all that can be reached from current trip
				char last = trip.charAt( trip.length()-1 );
				node startNode = myGraph.getNode( ""+last );
				
				for (int i=0; i<myGraph.nodes.size(); i++)
				{
					node stopNode = myGraph.nodes.get(i);
					
					if (trip.indexOf( stopNode.id ) == -1)
					{
						String key = startNode.id+","+stopNode.id;
						
						edge e = myGraph.getEdge(key);
						
						if (e != null)		
							if ( e.isReachable(trip+extras) )
							{	
								//add to stack
								stack.push( trip+stopNode.id );
								//queue.add( trip+stopNode.id );
								//System.out.println( "pushing " + trip + stopNode.id );
							}
					}
				}//for
				
			}//if add all that can be reached
			
		}//while
		
	}//dfs
	
	public void improvePath(String trip)
	{
		//trip = "@mfzvntksuaqyjdhoebrwixpglc";
		System.out.println( "improvePath " + trip + " " + getTripCost(trip) );
		
		
		String lowTrip = trip;
		 
		int trial = 0;
		int lowCost = 20000000;
		rn = new Random();
		
		while (trial < 2000000000)
		{
  
		  int curCost = getTripCost(trip);
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
	}//improvePaths
	
}//class



