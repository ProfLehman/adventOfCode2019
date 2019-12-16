/*
 * 12.1.2019
 * day 12 
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 * 
 */
 
import java.util.ArrayList;
import java.util.HashMap;
 
public class day12
{
	//HashMap<String, Boolean> hm;
	moon moons[];
	
	public day12()
	{
		//hm = new HashMap<String, Boolean>();
	
	}
	
	
    public static void main( String args[] ) {
        day12 myApp = new day12();

        myApp.test();   
    }//main
     
    public void test() {  

		//String data[] = {"<x=-1, y=0, z=2>", "<x=2, y=-10, z=-7>","<x=4, y=-8, z=8>", "<x=3, y=5,z=-1>"};
								
		//String data[] = {"<x=-8, y=-10, z=0>","<x=5, y=5, z=10>","<x=2, y=-7, z=3>","<x=9, y=-8, z=-3>"};


String data[] = {"<x=7, y=10, z=17>","<x=-2, y=7, z=0>","<x=12, y=5, z=12>","<x=5, y=-8, z=6>"};


		//init moons
		moons = new moon[ data.length ];
		for (int i=0; i<moons.length; i++)
		{		
			moons[i] = new moon( data[i] );
			System.out.println( moons[i] );
		}
		System.out.println();

		

		long steps = 0;
		long count = 0;
		long count2 = 0;
		
		boolean stop = false;
		
		while(!stop)
		//for (int steps=1; steps<=1000; steps++)
		{

			//adjust gravity for moons
			for (int a=0; a<moons.length; a++)
				for (int b=0; b<moons.length; b++)
					if (a != b)
						moons[a].addMoon( moons[b] );


			//apply gravity and print
			steps++;
			count++;

			//System.out.printf("After %d steps:\n", steps);
			for (int a=0; a<moons.length; a++)
			{
				moons[a].appplyGravityChanges();
			}

			//if (moons[0].x == 7)
			if (moons[0].z == 17)
			 if (moons[1].z == 0)
			  if (moons[2].z == 12)
				if (moons[3].z == 6)	
					//if (moons[0].z == 17)
						//if (moons[0].vx == 0)
							if (moons[0].vz == 0)
								//if (moons[0].vz == 0)
								{
									System.out.println( "steps: " + steps );
									stop = true;
								}
									
				
			
			if (count == 100000000)
			{
				System.out.println( steps );
				count = 0;
			}


		}//steps
		
		//int totalEnergy = 0;
		for (int a=0; a<moons.length; a++)
		{
			//	totalEnergy += moons[a].te();
			System.out.println( moons[a] );	
		}
		//System.out.println("Total Energy: " + totalEnergy);

		
		
    }//test
                  
}//day12