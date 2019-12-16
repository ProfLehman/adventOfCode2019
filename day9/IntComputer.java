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
                //System.out.println("\nbase: " + base);
                //System.out.println("processing: " + ip + " : " + instruction );
                String ABC = "0000" + instruction; //convert to string with 0 padding
                ABC = ABC.substring(ABC.length()-5); //get right x5
                //System.out.println( ABC );
                int opcode = Integer.parseInt( ABC.substring(ABC.length()-2 ) );

                long a = -1;
                long b = -1;
                long c = -1;

                if (opcode >= 1 && opcode <= 9)
               {
                        long ap = get(ip+1);
                        if ( ABC.charAt(2) == '0' )
                                a = get(ap);
                        else if ( ABC.charAt(2) == '1' )
                                a = ap;
                        else
                                a = get(base + ap); //2
               }

              if (opcode == 1 || opcode == 2 || opcode == 5 || opcode == 6 || opcode == 7 || opcode == 8)
               {
                        long bp = get(ip+2);
                        if ( ABC.charAt(1) == '0' )
                                b = get(bp);
                        else if ( ABC.charAt(1) == '1' )
                                b = bp;
                        else
                                b = get(base + bp); //2
              }

              if (opcode == 1 || opcode == 2 || opcode == 3 || opcode == 7 || opcode == 8)
              {
                        long cp = get(ip+3);
                        if ( ABC.charAt(0) == '0' )
						{
                                c = get(ip+3); //???? not sure if this is correct
								//System.out.println("c with mode 0");
						}
                        else if ( ABC.charAt(0) == '1' )
						{
                                c = cp;
								System.out.println("c with mode 1");
						}
                        else
						{
                                c = base + cp; //2
								//System.out.println("c with mode 2");
						}
			  
                //System.out.printf("A %d,  B %d, C %d\n", a, b, c);
			  }

                switch (opcode)
                {
                case 99:
                        halt=true;
                        break;

                case 1:
                        //data[data[ip+3]] = a + b;
                        long ar = a + b;
                        hm.put(c, ar);
                      //  System.out.println( "add: " + ar + " placing in " + c);
                        ip = ip + 4;
                        break;

                case 2:
                        long am = a * b;
                        hm.put(c, am);
                        //System.out.println( "mult: " + am + " placing in " + c);
                        ip = ip + 4;
                        break;


                case 3: Scanner scn = new Scanner(System.in);
                        System.out.println("Input: ");
                        //data[ data[ip+1] ] = Long.parseLong( scn.nextLine() );
                        long temp = Long.parseLong( scn.nextLine() );
                        //hm.put(c, 2L);
						hm.put(c, temp);
                        ip = ip + 2;
                        break;

                case 4:
                        System.out.println("Ouput: " + a );
                        ip = ip + 2;
                        break;

                case 5:
                      //  if (a != 0)
                      //          System.out.println( "a != 0, ip = b");
                      //  else
                      //          System.out.println( "a != 0, ip = ip + 3");

                        if (a != 0)
                                ip = b;
                        else
                                ip = ip + 3;
                        break;

                case 6:
                      //  if (a == 0)
                      //          System.out.println( "a == 0, ip = b");
                      //  else
                      //          System.out.println( "a == 0, ip = ip + 3");

                        if (a == 0)
                                ip = b;
                        else
                                ip = ip + 3;
                        break;

                case 7:
                      //  if (a < b)
                      //          System.out.println( "a < b, 1 in c");
                      //  else
                        //        System.out.println( "a < b, 0 in c");

                        if ( a < b)
                                hm.put( c, 1L);
                        else
                                hm.put( c, 0L);
                        ip = ip + 4;
                        break;

                case 8:
                      //  if (a == b)
                      //          System.out.println( "a == b, 1 in c");
                      //  else
                      //          System.out.println( "a != b, 0 in c");
                        if ( a == b)
                                hm.put( c, 1L);
                        else
                                hm.put( c, 0L);
                        ip = ip + 4;
                        break;

                case 9:
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

}//process


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


        ArrayList al = adventLibrary.readData("day9.txt");
        long data1[] = adventLibrary.longArray1CSV( al );

        System.out.print( "part 1: " );
        IntComputer amp1 = new IntComputer( data1 );
        amp1.process();
        //amp1.dataDump();

}//main

public void dataDump()
{
        //for (long x=0; x<data.length; x++)
        //        System.out.println( x + " : " + data[x]);

}

}//class
