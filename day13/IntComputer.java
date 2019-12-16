import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.*;

public class IntComputer
{

HashMap<Long, Long> hm;

long ip;
long base;

public boolean halt;

//other variables
int count = 0;
int outputCount = 0;

public IntComputer( long d[] )
{
        hm = new HashMap<Long, Long>();

        for (int x=0; x<d.length; x++)
        {
                long p = x;
                long v = d[x];
                hm.put( p, v );
                //System.out.println( x + " : " + v);
        }

        ip = 0L;
        base = 0L;
        halt = false;
}

public void process()
{
        while (!halt)
        {

                long instruction = get(ip);
                String ABC = "0000" + instruction; //convert to string with 0 padding
                ABC = ABC.substring(ABC.length()-5); //get right x5
                int opcode = Integer.parseInt( ABC.substring(ABC.length()-2 ) );

                long a = -1;
                long b = -1;
                long c = -1;

                switch (opcode)
                {
                case 99:
                        halt=true;
                        break;

                case 1:
						a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						c = parm('w', 3, ABC);
                        //data[data[ip+3]] = a + b;
                        long ar = a + b;
                        hm.put(c, ar);
                      //  System.out.println( "add: " + ar + " placing in " + c);
                        ip = ip + 4;
                        break;

                case 2:
						a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						c = parm('w', 3, ABC);
                        long am = a * b;
                        hm.put(c, am);
                        //System.out.println( "mult: " + am + " placing in " + c);
                        ip = ip + 4;
                        break;


                case 3: 
						a = parm('w', 1, ABC);
										
						Scanner scn = new Scanner(System.in);
                        System.out.println("Input: ");
                        long temp = Long.parseLong( scn.nextLine() );
                        hm.put(a, temp);
                        ip = ip + 2;
                        break;

                case 4:
						a = parm('r', 1, ABC);
                        System.out.println("Ouput: " + a );
						
						outputCount++;
						if (outputCount == 3)
						{
							if (a == 2)
								count++;
							
							outputCount = 0;							
						}
                        ip = ip + 2;
                        break;

                case 5:
                      	a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						
                        if (a != 0)
                                ip = b;
                        else
                                ip = ip + 3;
                        break;

                case 6:
                      	a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						
                        if (a == 0)
                                ip = b;
                        else
                                ip = ip + 3;
                        break;

                case 7:
                      	a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						c = parm('w', 3, ABC);

                        if ( a < b)
                                hm.put( c, 1L);
                        else
                                hm.put( c, 0L);
                        ip = ip + 4;
                        break;

                case 8:
                      	a = parm('r', 1, ABC);
						b = parm('r', 2, ABC);
						c = parm('w', 3, ABC);

                        if ( a == b)
                                hm.put( c, 1L);
                        else
                                hm.put( c, 0L);
                        ip = ip + 4;
                        break;

                case 9:
				        a = parm('r', 1, ABC);
                      //  System.out.println( "adding a to base");
                        base = base + a;
                        ip = ip + 2;
                        break;

                default:
                        System.out.print("Error: process switch: ");
                        System.out.print( ip + ": " + instruction);
                        halt = true;
                }//switch



        }//while

		System.out.println( "count: " + count );
}//process


public long parm( char rw, int pos, String ABC )
{
	long result = -1;
	long p = get(ip+pos);

	//permission for variable
	char r = 'e';
	if (pos == 1)
		r = ABC.charAt(2); //first parameter A
	else if (pos == 2)
		r = ABC.charAt(1); //second parameter B
	else if (pos == 3)
		r = ABC.charAt(0); //third parameter C
	else
		System.out.println("invalid pos " + pos);
	
	switch (rw)
	{
			case 'r':				
					if (r == '0')
						result = get(p);
					else if (r == '1')
						result = p;
					else if (r == '2')
						result = get(base + p);
					else
						System.out.println("invalid r opcode persmission " + r);
					break;

			case 'w':
					if (r == '0')
						result = p;
					else if (r == '1')
					{
						result = p;
						System.out.print( "Error: write immediate mode" );
					}
					else if (r == '2')
						result = base + p;
					else
						System.out.println("invalid w opcode permission " + r);
					break;					
						
			default: System.out.println("invalid param " + rw);
		
	}
	
	return result;
	
}

public long get(long p)
{
        if (hm.get(p) != null)
                return hm.get(p);
        else
                return 0;
}


public static void main(String args[])
{
        //long temp = 1125899906842624L;

        ArrayList al = adventLibrary.readData("day13.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

        System.out.print( "part 1: " );
        IntComputer amp1 = new IntComputer( data1 );
        amp1.process();

}//main



}//class
