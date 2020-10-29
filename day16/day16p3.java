/*
 * 12.30.2019
 * day 16 
 * lehman
 * Advent of Code 2019
 * 
 */
 
public class day16p3
{
	//String data = "12345678";
	//String data = "80871224585914546619083218645595";
	//String data = "19617804207202209144916044189917";
	//String data = "69317163492948606335995924319873";
//String data = "59766977873078199970107568349014384917072096886862753001181795467415574411535593439580118271423936468093569795214812464528265609129756216554981001419093454383882560114421882354033176205096303121974045739484366182044891267778931831792562035297585485658843180220796069147506364472390622739583789825303426921751073753670825259141712329027078263584903642919122991531729298497467435911779410970734568708255590755424253797639255236759229935298472380039602200033415155467240682533288468148414065641667678718893872482168857631352275667414965503393341925955626006552556064728352731985387163635634298416016700583512112158756656289482437803808487304460165855189";
	
	String data = "03036732577212944063491565474664";
	
    public static void main( String args[] ) {
        day16p3 myApp = new day16p3();
        myApp.test();   
    }//main
     
    public void test() {    

	int part = 10000; //part = 1 for part1, 10000 for part2 - forces pattern to repeat
	int list[] = new int[data.length()*part]; 
	int list2[];
	int base[] = {0,1,0,-1};
	
	//load initial list as int from String data
	int di = 0;
	for (int i=0; i<list.length; i++)
	{
		list[i] = Integer.parseInt( data.substring(di, di+1) );
		di++;
		if (di == data.length())
			di = 0;
	}
	System.out.println( "list length: " + list.length );	

	for (int phase=1; phase <=100; phase++)
	{

		list2 = new int[list.length];

		//process list for x1 phase  0303673
		//for (int i=5976698; i<list.length; i++)
		for (int i=0303674; i<list.length; i++)	
		{
			
			int gap = i+1;
			int m = 1; //multiplier
			int a = i; //starting point for 1's
			int p = a;		
			
			while (p < list.length)
			{
				int b = a + gap -1;				
				
				while (p <= b)
				{
						list2[i] = list2[i] + ( list[p] * m );
						//System.out.printf( "%d*%d + ", list[p], m );										
						p++;
						if (p == list.length)
							p = b+1; //kick out of loop and stop
				}//while
		
				a = b + gap + 1;
				p = a;
				m = m * -1;
			

			}//while
			
			//System.out.println( " = " + list2[i] );
			String temp = Integer.toString( list2[i] );
			list2[i] = Integer.parseInt( temp.substring( temp.length()-1 ) );
						
		}//for i
		
		System.out.printf( "Phase %3d : ", phase);
		//for (int a=0; a<part*data.length(); a++)
		//	System.out.print( list2[a] );
			
		System.out.println();
		
		//place new values in list
		list = list2;
		
	}//phase
	
	
	for (int a=0; a<8; a++)
		System.out.print( a + ":" + list[a] + ", " );
		
		
    }//test
                  
}//day16p3
