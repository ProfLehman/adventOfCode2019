/*
 * 12.4.2019
 * day 4
 * prof. lehman
 * Advent of Code 2019
 */
import java.util.Arrays; 

public class day4
{
    public static void main( String args[] ) {
		int count1 = 0;
		int count2 = 0;
		
		for (int n = 234208; n<= 765869; n++)
		{	
			String s = Integer.toString(n);
			
			//part 1 and part 2 -  check for ascending order
			boolean ascending = true;
			for (int i=0; i<s.length()-1; i++)
				if (s.charAt(i) > s.charAt(i+1))
					ascending = false;

			//alt part 1 and part 2 - sort and compare
			//char charArray[] = s.toCharArray(); 
            //Arrays.sort(charArray);
            //if (!s.equals( new String(charArray)) )
			//	ascending = false;
			
			
			//part 1 - check for group of two
			boolean part1Double = false;					
			for (int i=0; i<s.length()-1; i++)
				if (s.charAt(i) == s.charAt(i+1))
					part1Double = true;
						
			//part 2 - check for group of two not part of larger group			
			boolean part2Double = false;
			int v[] = {0,0,0,0,0,0,0,0,0,0}; //group of two for digits 0, 1, 2, ... 9
					
			for (int i=0; i<s.length()-1; i++)
				if (s.charAt(i) == s.charAt(i+1))
				{
					char t = s.charAt(i);
					int vp = (int) t-48;
					v[ vp ] = 1;
					if (i > 0)
						if (s.charAt(i-1) == t) //same digit before group
							v[ vp ] = 0;
					if ((i+2) < s.length())
						if (s.charAt(i+2) == t) //same digit after group
							v[ vp ] = 0;
				}
				
			for (int i=0; i<v.length; i++) //any digit have a group of only two
				if (v[i] == 1)
					part2Double = true;
			
			//add to acounts if ascending and meets double requirement
			if (ascending)
			{
				if (part1Double)
				{
					System.out.println( "Part 1: " +  n );
					count1++;
				}
				if (part2Double)
				{
					System.out.println( "Part 2: " +  n );
					count2++;
				}
			}

		}//for
		
		System.out.println( "Valid Part 1: " + count1 );
		System.out.println( "Valid Part 2: " + count2 );
		
    }//main
     
  
}//day4