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

//robot stuff
HashMap<String, Integer> rm;
int rx;
int ry;
char dir;
boolean paint;
int count;

int minX, maxX;
int minY, maxY;

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

        //robot
        rm = new HashMap<String, Integer>();
        rx = 0;
        ry = 0;
        dir = '^';
        paint = true;
        count = 0;

		//part 2
		rput( 1L );
		minX = 1000;
		maxX = -1;
		minY = 1000;
		maxY = -1;
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
                        //Scanner scn = new Scanner(System.in);
                        //System.out.println("Input: ");
                        //long temp = Long.parseLong( scn.nextLine() );
                        //hm.put(c, 2L);
                        int cv = rget(); //get robot color as input
                        hm.put(a, Long.valueOf(cv) );
                        ip = ip + 2;
                        break;

                case 4:
						a = parm('r', 1, ABC);
                        //System.out.println("Ouput: " + a );
                        if (paint)
                        {
                                rput(a); //paint value
                                paint = false;
                        }
                        else
                        {        //turn
                                paint = true;
                                if (a == 0)
                                {
                                        //turn left 90
                                        switch (dir)
                                        {
                                        case '^': dir='<'; rx--; break;
                                        case 'v': dir='>'; rx++; break;
                                        case '>': dir='^'; ry++; break;
                                        case '<': dir='v'; ry--; break;
                                        default: System.out.println("Error: turn left");
                                        } //switch
                                }
                                else
                                {
                                        //trun right 90
                                        switch (dir)
                                        {
                                        case '^': dir='>'; rx++; break;
                                        case 'v': dir='<'; rx--; break;
                                        case '>': dir='v'; ry--; break;
                                        case '<': dir='^'; ry++; break;
                                        default: System.out.println("Error: turn right");
                                        }        //switch
                                }
								
								if (rx < minX)
									minX = rx;
								if (rx > maxX)
									maxX = rx;
								if (ry < minY)
									minY = ry;
								if (ry > maxY)
									maxY = ry;

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
		
		System.out.println( "Count: " + count );

		for (int r=minX; r<=maxX; r++)
		{
			for (int c=minY; c<=maxY; c++)
			{
				if (rm.get(r+","+c) != null)
				{
					int t = rm.get(r+","+c);
					if (t == 1)
						System.out.print("X ");
					else
						System.out.print("  ");
				}
				else
					System.out.print( "  " );		
			}
			System.out.println();
		}
		
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

public int rget()
{
        String k = rx + "," + ry;
        if (rm.get(k) != null)
                return rm.get(k);
        else
                return 0;
}

public void rput( long a)
{
        String k = rx + "," + ry;
        int i = (int) a;

        //check to see if first-time painted
        if (rm.get(k) == null)
                count++;

        rm.put(k, i );
}


public static void main(String args[])
{
        ArrayList al = adventLibrary.readData("day11.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

        System.out.print( "part 1: " );
        IntComputer amp1 = new IntComputer( data1 );
        amp1.process();
}//main

}//class
