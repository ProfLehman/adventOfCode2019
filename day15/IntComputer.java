import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;
import java.util.Random;

public class IntComputer
{
//IntComputer variables
HashMap<Long, Long> hm;
long ip;
long base;
public boolean halt;


//other variables
int count = 0;

HashMap<String, Integer> dm; //display map
public int curX;
public int curY;

int direction; //1N, 2S, 3W, 4E
public String status;

String path;
int p;

public IntComputer( long d[], HashMap<String, Integer> tdm )
{
        hm = new HashMap<Long, Long>();
		//dm = new HashMap<String, Integer>();
		dm = tdm; //link to display map from main
		
		//reset computer
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
		status = "unknown";
		setPath("1");//default path with North as only direction instruction
		curX = 500;
		curY = 500;
		dm.put(curX+","+curY, 0);//open
}

public void setPath( String tp )
{
	path = tp;
	p=0;
	direction = ((int) path.charAt(p)) - 48;
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
		                //System.out.println("Input: ");
						//Scanner scn = new Scanner(System.in);
		                //long temp = Long.parseLong( scn.nextLine() );
						//hm.put(a, temp);
						
						hm.put(a, (long) direction);
						
                        ip = ip + 2;
                        break;

                case 4:
						a = parm('r', 1, ABC);
                        //System.out.println("Output: " + a );
						//System.out.println("direction " + direction + "  cy: " + cy);
						
						if (a == 0)  //wall - 1N, 2S, 3W, 4E
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
							
							halt = true;
							status = "wall";
							
						}
						else if (a == 1 || a == 2) // **** remove a == 2 for showing sensor
						{
							if (a == 2)
							{
								System.out.println( "Sensor at " + curX + ", " + curY );	
							}
							switch (direction)
							{
								case 1: curY++;  break; //move to open position
								case 2: curY--;  break;
								case 4: curX++;  break;
								case 3: curX--;  break;
								default:  System.out.println("Error Output direction " + direction);
							}//switch
							dm.put(curX+","+curY, 0); //mark as open position
							
							p++;
							if (p < path.length())
							{
								direction = ((int) path.charAt(p)) - 48;	
							}
							else
							{
								halt = true;
								status = "open";
							}					
						}
						else if (a == 2) //oxygen sensor found
						{
							halt = true;
							status = "found";
							switch (direction)
							{
								case 1: curY++;  break; //move to sensor position
								case 2: curY--;  break;
								case 4: curX++;  break;
								case 3: curX--;  break;
								default:  System.out.println("Error Output direction " + direction);
							}//switch
							dm.put(curX+","+curY, 2); //mark as sensor position
							System.out.println( "2Sensor at " + curX + ", " + curY );							
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

		//System.out.println( "count: " + count );
}//process

//IntComputer helper method for instruction parameter permissions
private long parm( char rw, int pos, String ABC )
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

//IntComputer helper method for retrieving data and instructions
private long get(long p)
{
        if (hm.get(p) != null)
                return hm.get(p);
        else
                return 0;
}

}//class
