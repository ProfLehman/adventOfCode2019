/*
 * 12.1.2019
 * day 6 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
 
public class day6
{
	ArrayList al;
	HashMap<String, String> hm; 
	
    public day6() {
		al = adventLibrary.readData("day6.txt");
		hm = new HashMap<String, String>(); 
    }

    public static void main( String args[] ) {
        day6 myApp = new day6();
        myApp.test();   
    }//main
     
    public void test() {    

		//store orbits
		for (int x=0; x<al.size(); x++)
		{
			String s = al.get(x).toString();
			//System.out.println( s );
			
			int p = s.indexOf(")");
			String parent = s.substring(0,p);
			String child = s.substring(p+1);
			//System.out.println( child + " orbits " + parent);
			hm.put(child, parent);
			
		}//for x
		System.out.println("Created hashmap is" + hm); 
		
		
		//part1 - count parents
		/*
		Iterator hmIterator = hm.entrySet().iterator(); 
  
		int count1 = 0;
        while (hmIterator.hasNext()) { 
            Map.Entry mapElement = (Map.Entry)hmIterator.next(); 
            String p = (String) mapElement.getValue(); 
            System.out.println(mapElement.getKey() + " : " + p);
			
			//System.out.println( countParents( (String) mapElement.getKey() ) ); 
			
			count1 = count1 + countParents( (String) mapElement.getKey() );
        } 
		System.out.println("part1 count: " + count1);
		assert count1 == 42 : " sample data should be 42";
		*/
		
		//part2 - path from YOU to SANta
		String me = "YOU";
		int myCost = -1;
		
		boolean found = false;
		while (!me.equals("COM") && !found)
		{
			System.out.println( me + " cost: " + myCost );
			me = (String) hm.get(me);
			myCost++;
			
			//see if SANta can get to me?
			int santaCost = santaFindMe( me );
			
			if (santaCost != -1)
			{
				found = true;
				System.out.println( "me: " + me + "  santaCost: " + santaCost );
				System.out.println( myCost + santaCost );
			}
		}					
		
    }//test1

	public int santaFindMe( String searchKey )
	{
		return santaFindMeH( searchKey, "SAN", 0 );
	}//santaFindMe
	
	public int santaFindMeH( String searchKey, String santaKey, int cost )
	{
		santaKey = (String) hm.get(santaKey);
		
		if (santaKey == null)
			return -1;
		else if (santaKey.equals(searchKey))
			return cost;
		else
			return santaFindMeH( searchKey, santaKey, cost+1 );
	}//santaHelpFindMeH


	public int countParents( String key )
	{
		if (key.equals("COM"))
			return 0;
		else
			return 1 + countParents( (String) hm.get(key)  );
	}//countParents
	
}//day6


	
	
	
	