import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.*;
import java.util.Random;

public class IntComputer
{

HashMap<Long, Long> hm;
HashMap<String, Integer> dm;

long ip;
long base;

public boolean halt;

//other variables
int count = 0;
int minY, maxY, minX, maxX;
int curX, curY;
int direction; //1N, 2S, 3W, 4E

String cycle;
int cy;

int curLR;
int curUD;
int leftW;
int rightW;
int cycleCount;

public IntComputer( long d[] )
{
        hm = new HashMap<Long, Long>();
		dm = new HashMap<String, Integer>();

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
		
		//other variables
		maxY = 505;
		minY = 495;
		
		minX = 495;
		maxX = 505;
		
		
		curX = 500;
		curY = 500;
		
		dm.put(curX+","+curY, 0);//open
		direction = 3; // West
		curUD = 1;
		curLR = 3;
		leftW = 0;
		rightW = 0;
		cycleCount = 0;
		
		cycle = "12432134";
		cy = 0;
		direction = ((int) cycle.charAt(cy)) - 48;
}

public void process()
{
		Random rn = new Random();
		
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
						display();
                        System.out.println("Input: ");
						System.out.println("direction " + direction + "  cy: " + cy);
                        //long temp = Long.parseLong( scn.nextLine() );
						//direction = (int) temp;
						//hm.put(a, temp);
						
						/*
						char temp = scn.next().charAt(0);
						if (temp == 'i')
							direction = 1;
						else if (temp == 'l')
							direction = 4;
						else if (temp == 'j')
							direction = 3;
						else if (temp == 'm')
							direction = 2;
						*/
						
						hm.put(a, (long) direction);
						
                        ip = ip + 2;
                        break;

                case 4:
						a = parm('r', 1, ABC);
                        System.out.println("Output: " + a );
						System.out.println("direction " + direction + "  cy: " + cy);
						//wall - 1N, 2S, 3W, 4E
						if (a == 0)
						{
							int tX = curX;
							int tY = curY;
							
							switch (direction)
							{
								case 1: tY++; break; //hit wall
								case 2: tY--; break;
								case 4: tX++; break;
								case 3: tX--; break;

								default:  System.out.println("Error Output direction " + direction);
							}//switch
							dm.put(tX+","+tY, 1); //mark as wall position
							
							//cy+=2;
							//if (cy >= cycle.length())
							//	halt=true;
							//else
							//	direction = ((int) cycle.charAt(cy)) - 48;	
														
							//if (rn.nextInt(5) == 0)
							direction = rn.nextInt(4) + 1; //random positions
							
							
						}
						else if (a == 1) //open
						{
							switch (direction)
							{
								case 1: curY++;  break; //move to open position
								case 2: curY--;  break;
								case 4: curX++;  break;
								case 3: curX--;  break;
								default:  System.out.println("Error Output direction " + direction);
							}//switch
							dm.put(curX+","+curY, 0); //mark as open position
							
							//cy++;
							//if (cy == cycle.length())
							//	halt=true;
							//else
							//	direction = ((int) cycle.charAt(cy)) - 48;	
							direction = rn.nextInt(4) + 1; //random positions
							
							//update values for display to scale
							if (curX < minX)
								minX = curX - 5;
							
							if (curY < minY)
								minY = curY - 5;
							
							if (curX > maxX)
								maxX = curX +5;
							
							if (curY > maxY)
								maxY = curY +5;						
						}
						else if (a == 2) //oxygen sensor found
						{
							halt = true;
							switch (direction)
							{
								case 1: curY++;  break; //move to sensor position
								case 2: curY--;  break;
								case 4: curX++;  break;
								case 3: curX--;  break;
								default:  System.out.println("Error Output direction " + direction);
							}//switch
							dm.put(curX+","+curY, 2); //mark as sensor position
							System.out.println( "Sensor at " + curX + ", " + curY );
						}
						else
							System.out.println("output error a is " + a);
							
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

public void display()
{
	for (int k=maxY; k>=minY; k--)
	{
		for (int j=minX; j<=maxX; j++)
		{
			if ( curX == j && curY == k)
				System.out.print("D");
			else if ( dm.get(j+","+k) == null )
				System.out.print(" ");
			else if (dm.get(j+","+k) == 1)
				System.out.print("#");
			else if (dm.get(j+","+k) == 0)
				System.out.print(".");
			else if (dm.get(j+","+k) == 2)
				System.out.print("S");
			else
				System.out.print( "E" );			
		}
		System.out.println();
	}
}

public static void main(String args[])
{
        //long temp = 1125899906842624L;

        ArrayList al = adventLibrary.readData("day15.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

        System.out.print( "part 1: " );
        IntComputer amp1 = new IntComputer( data1 );
        amp1.process();

}//main



}//class
