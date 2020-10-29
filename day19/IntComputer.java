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
	//***

	int commands[];
	int ci = 0;
	int beamCount = 0;
	long data[];
	
	long lastOutput;
	
public IntComputer( long d[] )
{
	//IntComputer setup
	hm = new HashMap<Long, Long>();
	
	//load instructions
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
	//***
	
	data = d; //store commands so program can be reset
	
}

//added for day19
public void reset()
{
	//IntComputer setup
	hm = new HashMap<Long, Long>();
	
	//load instructions
	for (int x=0; x<data.length; x++)
	{
			long p = x;
			long v = data[x];
			hm.put( p, v );
			//System.out.println( x + " : " + v);
	}
		
	ip = 0L;
	base = 0L;
	halt = false;
}

public void part1()
{
  int count = 0;
  for (int i=0; i<50*50; i++)
  {
	this.process();
	count++;
	if (count == 50)
	{
		System.out.println();
		count = 0;
	}
	this.reset();
  }	  
  System.out.println( "beamCount: " + beamCount );
}

public void part2(int N)
{
	commands = new int[N*N*2];
	for (int x=0; x<N; x++)
		for (int y=0; y<N; y++)
		{
			commands[ci] = y;
			ci++;
			commands[ci] = x;
			ci++;
		}
		
	ci=0;
	
	beamCount = 0;

	int bCount = 0;
	int bMin = -1;
  
	for (int y=0; y<N; y++)
	{   
		bCount = 0;
		for (int x=0; x<N; x++)
		{
			this.process();
	
			if (lastOutput == 1L)
				bCount++;
	
			this.reset();
		
		    if (bCount >= 100 && bMin == -1)
			{
				bMin = 1;
				System.out.println( x + " " + y );
			}
		}//for x
		//System.out.println();	
	}//for y	  
  
  System.out.println( "beamCount: " + beamCount );
  System.out.println( "first 100 beams at : " + bMin );

}
public void find(int x, int y)
{
	//A
	commands = new int[2];
	commands[0] = x;
	commands[1] = y;
	ci = 0;
	this.reset();
	this.process();
	long A = lastOutput;
	
	//B
	commands = new int[2];
	commands[0] = x+99;
	commands[1] = y;
	ci = 0;
	this.reset();
	this.process();
	long B = lastOutput;

	//C
	commands = new int[2];
	commands[0] = x;
	commands[1] = y+99;
	ci = 0;
	this.reset();
	this.process();
	long C = lastOutput;

	//D
	commands = new int[2];
	commands[0] = x+99;
	commands[1] = y+99;
	ci = 0;
	this.reset();
	this.process();
	long D = lastOutput;

	
	if (A != 1L)
		find( x+1, y);
	else if (B != 1L)
		find( x, y+1);
	else if (C != 1L)
		find( x+1, y);
	else if (D != 1L)
		find( x, y+1);
	else 
	{

    System.out.println( x + "," + y);
	System.out.println( A + "   " + B);
	System.out.println( C + "   " + D);
	System.out.println();
	
		System.out.println( "*** found ***");
		int answer = x * 10000 + y;
		System.out.println( answer );
	}
}

//IntComputer Process ie. Run
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

				//input
                case 3: 
						a = parm('w', 1, ABC);						
		                //System.out.println("Input: ");
						//Scanner scn = new Scanner(System.in);
		                //long temp = Long.parseLong( scn.nextLine() );
						//hm.put(a, temp); //keep for IntComputer, store input
						
						
						if (ci < commands.length)
						{
							int temp = commands[ci];
							ci++;
							hm.put(a, (long) temp); //store input
						}
						else
							System.out.println( "else out of commands");
						
						ip = ip + 2;
                        break;
				
				//output
                case 4:
						a = parm('r', 1, ABC);
                        //System.out.println("Output: " + a );
						display( a );
						
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

		//System.out.println( "lastOutput: " + lastOutput );
		
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

public void display( long a )
{
	//System.out.println( "d: " + (int) a );
	//if (a == 1L)
	//	System.out.print("#");
	//else
	//	System.out.print(".");
	
	lastOutput = a;
	
	if (a == 1L)
		beamCount++;
}

}//class
