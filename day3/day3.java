/*
 * 12.3.2019
 * day 3 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
import java.util.*;

public class day3
{
	//int data[];
    byte data[][];
	ArrayList al;
	int cr, cc;
	
	int cost;
	int minCost;
	HashMap<String, Integer> hmap;
	
	
    public day3() {
		 al = adventLibrary.readData("day3s2.txt");
		 
		//setup array
		data = new byte[100000][100000];
		for (int r=0; r<data.length; r++)
			for (int c=0; c<data[r].length; c++)
				data[r][c] = 0;
			
		//set origin
		data[50000][50000] = -1;
		cr = -1;
		cc = -1;
		
		hmap = new HashMap<String, Integer>();
		cost = 0;
    }

    public static void main( String args[] ) {
        day3 myApp = new day3();
        myApp.test();  
		myApp.test2();
    }//main
     
    public void test() {    
		
		byte v = 1;
		int i = 0;
		while (i < al.size())
		{
			//get next line
			String s = al.get(i).toString();
			//System.out.println( s );
			
			//split into array for processing
			String line[] = s.split(",");
			System.out.println( line.length );
			
			//start at origin for each wire
			cr = 50000;
			cc = 50000;
			
			int ml = 0;
			int mr = 0;
			int mu = 0;
			int md = 0;
				
			//process next line
			for (int x=0; x<line.length; x++)
			{
				//extract direction and number
				char direction = line[x].charAt(0);
				int number = Integer.parseInt( line[x].substring(1) );
				//System.out.printf("%c %d\n", direction, number );
				
				//update data
				switch (direction)
				{
					case 'U': mu += number; break;
					case 'D': md += number; break;
					case 'L': ml += number; break;
					case 'R': mr += number; break;
					default: System.out.println("process line switch error");
				}
				
				//process instruction
				go( direction, number, v);
				
			}//for x
			
			System.out.println( "finished line i = " + i );
			//System.out.printf( "U %d  D %d   L %d  R %d\n", mu, md, ml, mr );
		
			v++;
			i++;
		}
		System.out.println( "**** finished test 1 ****" );
    }//test
                

	public void go(char d, int n, byte v)
	{
		//System.out.printf("%c %d %d %d %d\n", d, n, v, cr, cc);
		if (n > 0)
		{
			//update next r, c position
			switch (d)
			{
				case 'U': cr--; break;
				case 'D': cr++; break;
				case 'L': cc--; break;
				case 'R': cc++; break;
				default: System.out.println("go switch error");
			}
			
			//update data
			if (data[cr][cc] == 0)
				data[cr][cc] = v; //blank
				//self, origin, previous cross
			else if (data[cr][cc] != v &&	data[cr][cc] != -1 && data[cr][cc] != -2)
				{
				  data[cr][cc] = -2; //cross
				  System.out.printf("Cross at %d, %d\n", cr, cc);
				  hamming();
				}
			
			go( d, n-1, v );
				
		}//if
	}//go
	
	public void hamming()
	{
		int rd = Math.abs(50000 - cr);
		int rc = Math.abs(50000 - cc);
		int t = rd + rc;
		System.out.printf("Cross Hamming Distance %d,%d => %d\n", rd, rc, t);
	}

    public void test2() {    
		minCost = 2000000;
		byte v = 1;
		int i = 0;
		while (i < al.size())
		{
			//get next line
			String s = al.get(i).toString();
			//System.out.println( s );
			
			//split into array for processing
			String line[] = s.split(",");
			System.out.println( line.length );
			
			//start at origin for each wire
			cr = 50000;
			cc = 50000;
			cost = 0;
			
			//process next line
			for (int x=0; x<line.length; x++)
			{
				//extract direction and number
				char direction = line[x].charAt(0);
				int number = Integer.parseInt( line[x].substring(1) );
				//System.out.printf("%c %d\n", direction, number );
								
				//process instruction
				go2( direction, number, v, cost);
				
			}//for x
			
			System.out.println( "finished line i = " + i );
			//System.out.printf( "U %d  D %d   L %d  R %d\n", mu, md, ml, mr );
		
			v++;
			i++;
		}
		
		System.out.println("minCost = " + minCost);
    }//test2
	
	public void go2(char d, int n, byte v, int c)
	{
		//System.out.printf("%c %d %d %d %d\n", d, n, v, cr, cc);
		if (n > 0)
		{
			//update next r, c position
			switch (d)
			{
				case 'U': cr--; break;
				case 'D': cr++; break;
				case 'L': cc--; break;
				case 'R': cc++; break;
				default: System.out.println("go switch error");
			}
			
			cost++;
			
			//check data
			if (data[cr][cc] == -2)
			{
			System.out.printf("%d Cross at %d, %d  cost %d\n", v, cr, cc, cost);
			if (v == 1)
				hmap.put( cr + "," + cc, cost);
			else
				{
					int cost1 = hmap.get( cr + "," + cc);
					int total = cost1 + cost;
					System.out.println("cost = " + total);
					if (total < minCost)
						minCost = total;
				}
			}
			go2( d, n-1, v, c );
				
		}//if
	}//go2


				
}//day3