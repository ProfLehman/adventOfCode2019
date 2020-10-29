


public class mod1 {

	public static void main( String args[] ) 
	{
	   
	   int p = 4;
	   int d = 32;
	   int count = 0;
	   
	   for (int i=1; i<=100; i++)
	   {
		
		boolean found = false;
		for (int n=1; n<=10000; n++)
		{
			if ((n * d) % p == 2)
			{
				System.out.println( i + " : " + p + "  " + n );
				count += (n*10000);
				found = true;
				break;
			}
		}
		if (!found)
		{
			System.out.println( i + " not found" );
			count += (650*10000);
		}
		p += 4;
	   }
	   
	   System.out.println( count );
	   System.out.println( 650*10000*650*10000 );
	   
	}//main
	
}