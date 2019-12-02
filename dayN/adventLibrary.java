import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
import java.util.Comparator;

public class adventLibrary
{
	//read data from file into ArrayList
    public static ArrayList<String> readData(String fn)
    {
        ArrayList<String> dataArrayList = new ArrayList<String>();
 
        try {      
			
            BufferedReader reader = new BufferedReader( new FileReader ( new File(fn)));
            String s = null;
 
            int lineCount = 0;
 
            s = reader.readLine();
            while (s != null)
            {
                //System.out.println( s );
                dataArrayList.add( s );
 
                lineCount++;
                s = reader.readLine();
            }//s
 
            System.out.println("ArrayList line count: " + lineCount);
 
        }
        catch (Exception ex) 
        {
            System.out.println( ex ); 
        }
 
        return dataArrayList;
    }//readData
 
	//return ArrayList as one dimensional array of int values
    public static int[] intArray1(ArrayList al) {
        
		int temp[] = new int[ al.size() ];
		
        int x = 0;
        while (x < al.size())
        {
			temp[x] = Integer.parseInt( al.get(x).toString() );
			x++;            
        }//while
             
        return temp;    
    }//array1
	
	//.csv ints one line
	//return ArrayList as one dimensional array of int values
    public static int[] intArray1CSV(ArrayList al) {
        
		String s = al.get(0).toString();
		
		String sv[] = s.split(",");
		
		int temp[] = new int[ sv.length ];
		
		for (int x=0; x<temp.length; x++)
		{
			temp[x] = Integer.parseInt( sv[x] );
		}
		 System.out.println("num items " + temp.length);
		     
        return temp;    
    }//array1
	
	//convert ArrayList to two dimensional array of char values
    public static char[][] charArray2(ArrayList al) {
		
		int maxColumn = 0;
        int x = 0;
        while (x < al.size())
        {
            String temp = String.valueOf( al.get(x) );
            if (temp.length() > maxColumn)
                maxColumn = temp.length();
            x++;            
        }//while
             
         
        //create grid char array
        char grid[][] = new char[al.size()][maxColumn];
         
        x = 0;
        while (x < al.size())
        {
            String temp = String.valueOf( al.get(x) );
             
            for (int y=0; y<temp.length(); y++)
                grid[x][y] = temp.charAt(y);            
            x++;            
        }//while
		
        System.out.println("char loaded");
        System.out.println("max rows: " + al.size());
        System.out.println("max columns: " + maxColumn);

		return grid;
		
	}//charArray2
	
    public static void show(int grid[])
    {
        //test print grid
        for (int r=0; r<grid.length; r++)
        {
            
            System.out.print( grid[r] + " ");
            
            
        }
		System.out.println();
    }
 
    public static void show(char grid[][])
    {
        //test print grid
        for (int r=0; r<grid.length; r++)
        {
            for (int c=0; c<grid[r].length; c++)
            {
                System.out.print( grid[r][c] );
            }
            System.out.println();
        }
    }
		
}//library

