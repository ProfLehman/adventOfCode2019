/*
 * 1.13.2020
 * day 15 
 * lehman
 * Advent of Code 2019
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList; 
import java.util.Queue;
import java.util.Scanner;

public class day20
{
	//int curX = 500;
	//int curY = 500;
	HashMap<String, String> pMap;
	char data[][];
	int max;
	
	//part2
	int innerTop = -1;
	int innerBottom = -1;
	int innerLeft = -1;
	int innerRight = -1;
	
    public day20() {
		max = 0;
    }

    public static void main( String args[] ) {
        day20 myApp = new day20();
        myApp.loadData();
		myApp.findPortals();
		//myApp.checkPortals();
		//myApp.bfs();
		myApp.bfs2();
    }//main
     
	public void loadData() {    
		ArrayList al = adventLibrary.readData("test3.txt");
        data = adventLibrary.charArray2( al );
		adventLibrary.show( data );
    }//loadData
	
	public void findPortals()
	{
		int portalCount = 0;
		pMap = new HashMap<String,String>();
		
		for (int r=0; r<data.length; r++)
		{
			for (int c=0; c<data[r].length; c++)
			{
				String tag = "";
				
				//if space check for portals
				if (data[r][c] == '.')
				{
					//above
					if (Character.isLetter(data[r-1][c]))
					{
					  tag = ""+data[r-2][c]+data[r-1][c];
					}
					//below
					if (Character.isLetter(data[r+1][c]))
					{
					  tag = ""+data[r+1][c]+data[r+2][c];
					}
					//right
					if (Character.isLetter(data[r][c+1]))
					{
					  tag = ""+data[r][c+1]+data[r][c+2];
					}
					//left
					if (Character.isLetter(data[r][c-1]))
					{
					  tag = ""+data[r][c-2]+data[r][c-1];
					}
				}
				
				if (!tag.equals(""))
				{
					//System.out.println( "tag " + tag + " at " + r + "," + c);
					portalCount++;
					
					if (pMap.get(tag) == null)
						pMap.put(tag, r+","+c);
					else
					{
						String one = pMap.get(tag);
						pMap.put(one,r+","+c);
						pMap.put(r+","+c,one);
					}
				}
				
				//part2
				if (data[r][c] == '-')
				{
					innerLeft = c;
					innerTop = r;
				}
				if (data[r][c] == '+')
				{
					innerRight = c;
					innerBottom = r;
				}
				
				
				
			}//for c			
		}//for r
		
		System.out.println("Portals found: " + portalCount);
	}
	
	public void checkPortals()
	{
		int portalCount = 0;
		
		for (int r=0; r<data.length; r++)
		{
			for (int c=0; c<data[r].length; c++)
			{
				if (pMap.get(r+","+c) != null)
				{
					String temp = pMap.get(r+","+c);
					System.out.println( "portal " + r + "," + c + "  to " + temp);
					portalCount++;
				}				
			}//for c			
		}//for r
		
		System.out.println("Portals found: " + portalCount);
		System.out.println("Staring point: " + pMap.get("AA") );
		System.out.println(" Ending point: " + pMap.get("ZZ") );
	}
	
    public void bfs() {    

        System.out.println( "BFS part 1: " );

		String startPoint = pMap.get("AA");
		int curR = Integer.parseInt( startPoint.substring(0,startPoint.indexOf(",")) );
		int curC = Integer.parseInt( startPoint.substring(startPoint.indexOf(",")+1) );
		System.out.println( "Starting at " + curR + ", " + curC );
		
		String stopPoint =  pMap.get("ZZ");
		int stopR = Integer.parseInt( stopPoint.substring(0,stopPoint.indexOf(",")) );
		int stopC = Integer.parseInt( stopPoint.substring(stopPoint.indexOf(",")+1) );
		System.out.println( "Stopping at " + stopR + ", " + stopC );		

		//keep track of visited positions
		HashMap<String, Boolean> vm = new HashMap<String, Boolean>();
		//add start as visited
		vm.put(curR+","+curC, true);
		
		//add start to queue
		Queue<String> q = new LinkedList<>(); 
		q.add( curR+","+curC+":" );

		boolean found = false;
int stopCount = 0;

		while (!found && q.size() > 0 )
		{
			//stopCount++;
			
			String temp = q.remove();
			//System.out.println( q.size() );
			System.out.println( temp );
			curR = Integer.parseInt( temp.substring(0,temp.indexOf(",")) );
			curC = Integer.parseInt( temp.substring(temp.indexOf(",")+1,temp.indexOf(":")) );
			String path = temp.substring( temp.indexOf(":")+1 );
			
			//System.out.println( "next from q   " + curR + "," + curC + " data is " + data[curR][curY] + " " + path );
			
			
			//see if exit found
			if (curR == stopR && curC == stopC)
			{
				found = true;
				System.out.println( " Found Path: " + path );
				System.out.println( "Path Length: " + path.length() );
				System.out.println( "   Location: " + curR + "," + curC );
			}
			//see if portal and one that you have not gone through
			else if ( pMap.get(curR+","+curC) != null && path.charAt(path.length()-1) != 'p') //&& vm.get(curR+","+curC) != null )
			{
				String t2 = pMap.get(curR+","+curC);
				int r2 = Integer.parseInt( t2.substring(0,t2.indexOf(",")) );
				int c2 = Integer.parseInt( t2.substring(t2.indexOf(",")+1) );
				System.out.println( "portal jump to " + t2 );
				
				//pMap.remove(r2+","+c2); //remove so that we do not backtrack
				//vm.put(curR+","+curC, true);
				
				q.add( r2+","+c2+":"+path+"p" );
				vm.put(r2+","+c2, true);
			}
			else				
				//open space
				{
					int tr = -1;
					int tc = -1;
									
					//down
					tr = curR+1;
					tc = curC;
					if ( vm.get(tr+","+tc) == null)
						if (data[tr][tc] == '.')
						{
							q.add( tr+","+tc+":"+path+"d"); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "d");
							vm.put(tr+","+tc, true);
						}

					//up
					tr = curR-1;
					tc = curC;
					if ( vm.get(tr+","+tc) == null)
						if (data[tr][tc] == '.')
						{
							q.add( tr+","+tc+":"+path+"u"); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "u");
							vm.put(tr+","+tc, true);
						}

					//right
					tr = curR;
					tc = curC+1;
					if ( vm.get(tr+","+tc) == null)
						if (data[tr][tc] == '.')
						{
							String key = tr+","+tc;
							q.add( key+":"+path+"r"); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "r");
							vm.put(key, true);
						}

					//left
					tr = curR;
					tc = curC-1;
					if ( vm.get(tr+","+tc) == null)
						if (data[tr][tc] == '.')
						{
							String key = tr+","+tc;
							q.add( key+":"+path+"l"); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "l");
							vm.put(key, true);
						}
						
				}//if open space
				

			
		}//while
		
		
		System.out.println( "Max: " + max );
		
    }//bfs
		
	public void bfs2() {    

		Scanner kb = new Scanner(System.in);
		
        System.out.println( "BFS part 1: " );

		String startPoint = pMap.get("AA");
		int curR = Integer.parseInt( startPoint.substring(0,startPoint.indexOf(",")) );
		int curC = Integer.parseInt( startPoint.substring(startPoint.indexOf(",")+1) );
		System.out.println( "Starting at " + curR + ", " + curC );
		
		String stopPoint =  pMap.get("ZZ");
		int stopR = Integer.parseInt( stopPoint.substring(0,stopPoint.indexOf(",")) );
		int stopC = Integer.parseInt( stopPoint.substring(stopPoint.indexOf(",")+1) );
		System.out.println( "Stopping at " + stopR + ", " + stopC );	

		System.out.printf( "%d,%d --- %d,%d\n", innerLeft, innerTop, innerRight, innerBottom );

		//keep track of level
		int level = 0;

		//keep track of visited positions
		HashMap<String, Boolean> vm = new HashMap<String, Boolean>();
		//add start as visited
		vm.put(curR+","+curC+":"+level, true);
		
		//add start to queue
		Queue<String> q = new LinkedList<>(); 
		q.add( curR+","+curC+":"+";"+level );

		boolean found = false;

		while (!found && q.size() > 0 )
		{
			//stopCount++;
			
			String temp = q.remove();
			//System.out.println( q.size() );
			
			//System.out.println( temp );
			curR = Integer.parseInt( temp.substring(0,temp.indexOf(",")) );
			curC = Integer.parseInt( temp.substring(temp.indexOf(",")+1,temp.indexOf(":")) );
			String path = temp.substring( temp.indexOf(":")+1, temp.indexOf(";") );
			level = Integer.parseInt( temp.substring( temp.indexOf(";")+1 ) );
			
			//System.out.printf("%d,%d %s %d\n", curR, curC, path, level);
			
			
			//see if exit found
			if (curR == stopR && curC == stopC && level == 0)
			{
				found = true;
				System.out.println( " Found Path: " + path );
				System.out.println( "Path Length: " + path.length() );
				System.out.println( "   Location: " + curR + "," + curC );
			}
			//see if portal 
			else if ( pMap.get(curR+","+curC) != null && path.charAt(path.length()-1) != 'p') //2nd part is "and if you just didn't go through portal"
			{
				String t2 = pMap.get(curR+","+curC);
				int r2 = Integer.parseInt( t2.substring(0,t2.indexOf(",")) );
				int c2 = Integer.parseInt( t2.substring(t2.indexOf(",")+1) );
				
				//see if inner or outer label
				boolean innerLabel = false;
				boolean outerLabel = false;
				if (curC >= innerLeft && curC <= innerRight && curR >= innerTop && curR <= innerBottom)
					innerLabel = true;
				else
					outerLabel = true;
					
				if ( !(outerLabel && level==0) )
				{
					if (innerLabel)
						level++;
					else
						level--;
				
					System.out.println( "portal jump to " + t2 + " at level " + level);
					
				    //String junk = kb.nextLine();
					//System.out.printf("%d,%d %s %d\n", curR, curC, path, level);
				
					//pMap.remove(r2+","+c2); //remove landing spot so that we do not backtrack
				
					q.add( r2+","+c2+":"+path+"p"+";"+level );
					vm.put(r2+","+c2+":"+level, true);
				}
				else
					System.out.println( t2 + " portal is wall at level 0" );
			}
			else				
				//open space
				{
					int tr = -1;
					int tc = -1;
									
					//down
					tr = curR+1;
					tc = curC;
					if ( vm.get(tr+","+tc+":"+level) == null)
						if (data[tr][tc] == '.')
						{
							q.add( tr+","+tc+":"+path+"d"+";"+level); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "d");
							vm.put(tr+","+tc+":"+level, true);
						}

					//up
					tr = curR-1;
					tc = curC;
					if ( vm.get(tr+","+tc+":"+level) == null)
						if (data[tr][tc] == '.')
						{
							q.add( tr+","+tc+":"+path+"u"+";"+level); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "u");
							vm.put(tr+","+tc+":"+level, true);
						}

					//right
					tr = curR;
					tc = curC+1;
					if ( vm.get(tr+","+tc+":"+level) == null)
						if (data[tr][tc] == '.')
						{
							String key = tr+","+tc;
							q.add( key+":"+path+"r"+";"+level); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "r");
							vm.put(key+":"+level, true);
						}

					//left
					tr = curR;
					tc = curC-1;
					if ( vm.get(tr+","+tc+":"+level) == null)
						if (data[tr][tc] == '.')
						{
							String key = tr+","+tc;
							q.add( key+":"+path+"l"+";"+level); 
							//System.out.println("Adding " + tr + "," + tc + " " + path + "l");
							vm.put(key+":"+level, true);
						}
						
				}//if open space
				
                
			
		}//while
		
		
		System.out.println( "Max: " + max );
		
		
    }//bfs2
		
	
}//main


