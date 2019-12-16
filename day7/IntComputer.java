import java.util.ArrayList;

public class IntComputer
{

int data[];
int save[];

int ip;
int phase;
public int signal;
int output;

boolean phaseSet;
public boolean halt;
boolean outputAvailable;

public IntComputer( int d[] )
{
        data = d;
        phase = -1;
        signal = -1;

        //save data
        save = new int[ data.length ];
        for (int i=0; i<data.length; i++)
                save[i] = data[i];

        resetAll(phase, signal);
}

public IntComputer( int d[], int p, int s)
{
        data = d;
        phase = p;
        signal = s;

        //save data
        save = new int[ data.length ];
        for (int i=0; i<data.length; i++)
                save[i] = data[i];

        resetAll(phase, signal);
}

public int process()
{
        while (!halt && !outputAvailable)
        {
                //System.out.println("\nprocessing: " + data[i] );
                String ABC = "0000" + data[ip]; //convert to string with 0 padding
                ABC = ABC.substring(ABC.length()-5); //get right x5
                //System.out.println( "ABC = " + ABC );

                int opcode = Integer.parseInt( ABC.substring(ABC.length()-2 ) );
                //System.out.printf( "opcode = %d  %c%c \n", opcode, ABC.charAt(0), ABC.charAt(1) );

                int a = -1;
                int b = -1;

                if (opcode == 1 || opcode == 2 || opcode == 5 || opcode == 6 || opcode == 7 || opcode == 8)
                {
                        if ( ABC.charAt(2) == '0' )
                                a = data[ data[ip+1] ];
                        else
                                a = data[ ip+1 ];

                        if ( ABC.charAt(1) == '0' )
                                b = data[ data[ip+2] ];
                        else
                                b = data[ ip+2 ];
                }


                switch (opcode)
                {
                case 99:
                        //System.out.println("HALT");
                        halt=true; break;

                case 1:
                        data[data[ip+3]] = a + b;
                        //return process(i+4, phase, signal, output);
                        ip = ip + 4;
                        break;

                case 2:
                        data[data[ip+3]] = a * b;
                        //return process(i+4, phase, signal, output);
                        ip = ip + 4;
                        break;

                case 3: //Scanner scn = new Scanner(System.in);
                        //System.out.print("Input: ");
                        //data[ data[i+1] ] = Integer.parseInt( scn.nextLine() );

                        //use phase for first input, signal for second input
                        if (phase != -1)
                        {
                                data[ data[ip+1] ] = phase;
                                phase = -1;
                        } else if (signal != -1)
                        {
                                data[ data[ip+1] ] = signal;
                                signal = -1;
                        } else
                                System.out.println( "Error: phase and signal both used");

                        //return process(i+2, phase, signal, output);
                        ip = ip + 2;
                        break;

                case 4:
                        //System.out.println("Ouput: " + data[ data[i+1] ]);
                        output = data[ data[ip+1] ];
                        outputAvailable = true;
                        //return process(i+2, phase, signal, output);
                        ip = ip + 2;
                        break;

                case 5:
                        if ( a != 0)
                                //return process(b, phase, signal, output);
                                ip = b;
                        else
                                //return process(i+3, phase, signal, output);
                                ip = ip + 3;
                        break;

                case 6:
                        if ( a == 0)
                                //return process(b, phase, signal, output);
                                ip = b;
                        else
                                //return process(i+3, phase, signal, output);
                                ip = ip + 3;
                        break;

                case 7:
                        if ( a < b)
                                data[ data[ip+3] ] = 1;
                        else
                                data[ data[ip+3] ] = 0;

                        //return process(i+4, phase, signal, output);
                        ip = ip + 4;
                        break;

                case 8:
                        if ( a ==  b)
                                data[ data[ip+3] ] = 1;
                        else
                                data[ data[ip+3] ] = 0;

                        //return process(i+4, phase, signal, output);
                        ip = ip + 4;
                        break;

                default:
                        System.out.println("Error: process switch: ");
                        System.out.println( ip + ": " + data[ip]);
                        halt = true;
                }//switch

        }//while

        return output;
}//process

public void resetAll(int p, int s)
{
        //restore data
        for (int i=0; i<data.length; i++)
                data[i] = save[i];

        ip = 0;
        output = -1;

        phase = p;
        signal = s;
        halt = false;
        phaseSet = false;
        outputAvailable = false;
}


public static void main(String args[])
{
        ArrayList al = adventLibrary.readData("day7.txt");
        int data1[] = adventLibrary.intArray1CSV( al );

        System.out.println( "part 2: " );
        IntComputer amp1 = new IntComputer( data1 );
        IntComputer amp2 = new IntComputer( data1 );
        IntComputer amp3 = new IntComputer( data1 );
        IntComputer amp4 = new IntComputer( data1 );
        IntComputer amp5 = new IntComputer( data1 );

        int max = -1;
        NumberCombo nc = new NumberCombo(5);
        while(!nc.done() )
        {
                int p1 = nc.getValue(4)+5;
                int p2 = nc.getValue(3)+5;
                int p3 = nc.getValue(2)+5;
                int p4 = nc.getValue(1)+5;
                int p5 = nc.getValue(0)+5;

                int s1 = -1;
                int s2 = -1;
                int s3 = -1;
                int s4 = -1;
                int s5 = 0;

                amp1.resetAll( p1, -1);
                amp2.resetAll( p2, -1);
                amp3.resetAll( p3, -1);
                amp4.resetAll( p4, -1);
                amp5.resetAll( p5, -1);

                while (!amp1.halt)
                {
                        amp1.signal = s5;
                        s1 = amp1.process();
                        amp1.outputAvailable = false;

                        amp2.signal = s1;
                        s2 = amp2.process();
                        amp2.outputAvailable = false;

                        amp3.signal = s2;
                        s3 = amp3.process();
                        amp3.outputAvailable = false;

                        amp4.signal = s3;
                        s4 = amp4.process();
                        amp4.outputAvailable = false;

                        amp5.signal = s4;
                        s5 = amp5.process();
                        amp5.outputAvailable = false;

                        //System.out.printf( "%d %d %d %d %d\n", p1, p2, p3, p4, p5);
                        //System.out.printf( "%d %d %d %d %d\n", s1, s2, s3, s4, s5);

                }//while

                if (s5 > max)
                {
                        System.out.print( nc.toString() + " - ");
                        System.out.println( s5 );
                        max = s5;
                }

                nc.next();
        }//nc loop

}//main

}//class

/*
   ArrayList al = adventLibrary.readData("day7t5.txt");
   int data[] = adventLibrary.intArray1CSV( al );

   System.out.println( "part 1: " );

   int max = -1;
   NumberCombo nc = new NumberCombo(5);
   while(!nc.done() )
   {
        int p1 = nc.getValue(4);
        int p2 = nc.getValue(3);
        int p3 = nc.getValue(2);
        int p4 = nc.getValue(1);
        int p5 = nc.getValue(0);

        IntComputer amp1 = new IntComputer( data, p1, 0);
        int s1 = amp1.process();

        IntComputer amp2 = new IntComputer( data, p2, s1);
        int s2 = amp2.process();

        IntComputer amp3 = new IntComputer( data, p3, s2);
        int s3 = amp3.process();

        IntComputer amp4 = new IntComputer( data, p4, s3);
        int s4 = amp4.process();

        IntComputer amp5 = new IntComputer( data, p5, s4);
        int s5 = amp5.process();

        if (s5 > max)
        {
                System.out.print( nc.toString() + " - ");
                System.out.println( s5 );
                max = s5;
        }

        nc.next();
   }


 */
