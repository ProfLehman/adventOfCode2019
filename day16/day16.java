/*
 * 12.30.2019
 * day 16 
 * lehman
 * Advent of Code 2019
 * 
 */
 
public class day16
{
	//String data = "12345678";
	//String data = "80871224585914546619083218645595";
	//String data = "19617804207202209144916044189917";
	//String data = "69317163492948606335995924319873
	String data = "59766977873078199970107568349014384917072096886862753001181795467415574411535593439580118271423936468093569795214812464528265609129756216554981001419093454383882560114421882354033176205096303121974045739484366182044891267778931831792562035297585485658843180220796069147506364472390622739583789825303426921751073753670825259141712329027078263584903642919122991531729298497467435911779410970734568708255590755424253797639255236759229935298472380039602200033415155467240682533288468148414065641667678718893872482168857631352275667414965503393341925955626006552556064728352731985387163635634298416016700583512112158756656289482437803808487304460165855189";
	
	//String data = "03036732577212944063491565474664";
	

	
	int list[];
	int base[] = {0,1,0,-1};
	
    public static void main( String args[] ) {
        day16 myApp = new day16();
        myApp.test();   
    }//main
     
    public void test() {    
		String nextData = "";
	
	//Part II
	//String ts = "";
	//for (int i=0; i<10000; i++)
	//	ts = ts + data;
	
	//data = ts; 
	System.out.println( data );
	
	for (int phase=1; phase <=100; phase++)
	{
		//setup list from String data
		list = new int[data.length()];
		for (int i=0; i<list.length; i++)
			list[i] = Integer.parseInt( data.substring(i, i+1) );


		//process list for x1 phase
		int list2[] = new int[list.length];
		nextData = "";
		
		for (int i=0; i<list.length; i++)
		{
			int element = i+1;
			
			int pattern[] = new int[list.length+element]; //one larger to skip first
			int pp = 0;
			int bp = 0;
			
			while (pp <= list.length)
			{
				for (int k=0; k<element; k++)
				{
					pattern[pp] = base[bp]; 				
					pp++;
				}
				bp++;
				if (bp == base.length)
					bp = 0;
			}//while
			
			//for (int x=0; x<=list.length; x++)
			//	System.out.println( pattern[x] );
			
			for (int x=0; x<list.length; x++)
			{
				list2[i] = list2[i] + ( list[x] * pattern[x+1] );
				//System.out.printf( "%d*%d +", list[x], pattern[x+1] );
			}
			//System.out.println( list2[i] );
			
			String temp = Integer.toString( list2[i] );
			temp = temp.substring( temp.length()-1 );
			//System.out.println( temp );
			nextData = nextData + temp;
			
		}//element
		System.out.println( "Phase " + phase);
		//System.out.println( nextData );
		System.out.println( nextData.substring(0,8) );
		data = nextData;
		
	}//phase
	
    }//test
                  
}//day16