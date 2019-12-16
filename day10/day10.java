/*
 * 12.1.2019
 * day 10
 * lehman
 * Advent of Code 2019 (and 2018 catch up)
 */

import java.util.ArrayList;

public class day10
{
//int data[];
char data[][];
char copy[][];

int maxR;
int maxC;
int max;

public day10() {

}

public void copyData()
{
        for (int r=0; r<data.length; r++)
                for (int c=0; c<data[r].length; c++)
                        copy[r][c] = data[r][c];
}//copy data

public int countData()
{
        int count = 0;
        for (int r=0; r<data.length; r++)
                for (int c=0; c<data[r].length; c++)
                        if ( copy[r][c] == '#')
                                count++;
        return count-1;
}//copy data

public static void main( String args[] ) {
        day10 myApp = new day10();
        //myApp.testAll();
        myApp.test2();
}    //main

public void test2()
{
        ArrayList al = adventLibrary.readData( "day10.txt" );
        data = adventLibrary.charArray2( al );
      //  adventLibrary.show( data );
        //System.out.println();

        //X at 3,8
        int ar = 23; //23
        int ac = 17; //17

        for (int r=0; r<data.length; r++)
        {
                for (int c=0; c<data[r].length; c++)
                {
                  if (data[r][c] != '9')
                  {
                        //  System.out.println( "X at " + r + ", " + c);
                        double m = (double)(r-ar) / (double)(c-ac);
                        //System.out.printf("%.3f ", m);

                        double a =  Math.abs( Math.toDegrees( Math.atan(m) ) );
                        int md = Math.abs(r-ar) + Math.abs(c-ac);


                        int q = -1;
                        if (c == ac && r < ar)
                                q = 0;
                        else if (c > ac && r < ar)
                        {
                                q = 1;
                                a = 90.0-a;
                        }
                        else if (r == ar && c > ac)
                                q = 2;
                        else if (r > ar && c > ac)
                                q = 3;
                        else if (c == ac && r > ar)
                                q = 4;
                        else if (c < ac && r > ar)
                        {
                                q = 5;
                                a = 90.0-a;
                        }
                        else if (c < ac && r == ar)
                                q = 6;
                        else if (c < ac && r < ar)
                                q = 7;
                        else
                                q = 8;

                        //System.out.printf("%d, %5.2f, %d, %d, %d\n", q, a, md, r, c);
                        //System.out.printf("%5.2f,", a);
                        System.out.printf("%2d,", md);
                   }//if
                }//for c
                System.out.println();
        }//for r
}









public void testAll()
{
        String tests[] = {"day10j1.txt", "day10t1.txt", "day10t2.txt", "day10t3.txt", "day10t4.txt", "day10t5.txt", "day10.txt"};

        for (int t=0; t<tests.length; t++ )
        {
                System.out.println("Auto test #" + t + " - " + tests[t] );
                ArrayList al = adventLibrary.readData( tests[t] );
                data = adventLibrary.charArray2( al );
                copy = adventLibrary.charArray2( al );


                adventLibrary.show( data );
                System.out.println();

                test();
        }//for t

}//testAll

public void test()
{
        maxR = -1;
        maxC = -1;
        max = -1;


        for (int r=0; r<copy.length; r++)
        {
                for (int c=0; c<copy[r].length; c++)
                {
                        if (copy[r][c] == '#')
                        {
                                this.copyData();
                                checkAsteroid( r, c );

                                int tempCount = this.countData();
                                if (tempCount > max)
                                {
                                        max = tempCount;
                                        maxR = r;
                                        maxC = c;
                                }
                                //System.out.printf("%d, %d  Count #: %d\n", r, c, tempCount );
                                //adventLibrary.show( copy );
                                //System.out.println();
                        }//if #
                }//for c
        }//for r

        //System.out.println("Count #: " + this.countData() );
        System.out.printf("Max at %d, %d  Count #: %d\n\n\n", maxC, maxR, max );

}    //test

public void checkAsteroid(int ar, int ac)
{
        for (int r=0; r<copy.length; r++)
        {
                for (int c=0; c<copy[r].length; c++)
                {
                        if (copy[r][c] == '#' && !(r == ar && c == ac))
                        {
                                clearPath( ar, ac, r, c );
                        }//if #
                }//for c
        }//for r
}//checkAsteroid

//if any points found between a and b, then remove b
public void clearPath( int ar, int ac, int br, int bc)
{
        boolean blocked = false;

        //determine left and right points
        int lr = ar; //assume a is left
        int lc = ac;
        int rr = br;
        int rc = bc;

        if (bc < ac) //change if b is less
        {
                lr = br;
                lc = bc;
                rr = ar;
                rc = ac;
        }

//System.out.printf("Clearing left %d, %d to %d, %d\n", lr, lc, rr, rc);
        //check same row
        if ( lr == rr )
        {
                for (int i=lc+1; i<rc; i++)
                        if (copy[lr][i] == '#')
                                blocked = true;
        }

        //check same col
        else if ( lc == rc && !blocked )
        {
                int r1 = Math.min( lr, rr);
                int r2 = Math.max( lr, rr);

                for (int i=r1+1; i<r2; i++)
                        if (copy[i][lc] == '#')
                                blocked = true;
        }

        //check same line
        else if (!blocked)
        {
                int cr = rr - lr;
                int cc = rc - lc;

                //System.out.println( "ch  " + cr + ", " + cc);
                int d = __gcd( Math.abs(cr), Math.abs(cc) );

                cr = cr / d;
                cc = cc / d;

                int tr = lr+cr;
                int tc = lc+cc;

                //        System.out.printf("simp ch %d, %d .. first %d, %d\n", cr, cc, tr, tc);
                while ( !(tr==rr && tc==rc) && !blocked)
                {
                        if ( copy[tr][tc] == '#')
                        {
                                blocked = true;
                        }

                        tr = tr+cr;
                        tc = tc+cc;
                }
        }

        //last step if blocked clear point b
        if (blocked)
        {
                //System.out.println( "deleting " + r + ", " + c);
                copy[br][bc] = '.';
        }
}//clearPath

//thanks to https://www.geeksforgeeks.org/reduce-the-fraction-to-its-lowest-form/
// Function to reduce a fraction to its lowest form
static void reduceFraction(int x, int y)
{
        int d;
        d = __gcd( Math.abs(x), Math.abs(y) );

        x = x / d;
        y = y / d;

        System.out.println("gcd x = " + x + ", y = " + y);
}

static int __gcd(int a, int b)
{
        if (b == 0)
                return a;
        return __gcd(b, a % b);
}

}//day10


/*
   //check same line - worked for test cases 1 to 4, failed on 5
   //possibly due to percision?
   else if (!blocked)
   {
   double m = (double)(rr-lr)/(double)(rc-lc);
   double b = (double)lr - (m * (double)lc);
   //System.out.println( "m = " + m + ", b = " + b + " to " + rr + ", " + rc);
   //check all points between left and right
   for( int c=lc+1; c<rc; c++)
   {
      //int r = (int) (b + m * c);
      double r =  (b + m * c);

      //https://stackoverflow.com/questions/9898512/how-to-test-if-a-double-is-an-integer
      //System.out.println( "** " + r + ", " + c);
      if ( r % 1 == 0)
        if ( copy[(int)r][c] == '#')
        {
          blocked = true;
          c = rc; //stop loop - yes a break :-)
        }
   }
   }//line check
 */


/*
   public void checkPath( int curR, int curC, int endR, int endC)
   {
    System.out.printf( "checking line from %d, %d to %d, %d\n", curR, curC, endR, endC);
    knockOutH( curR, curC, endR, endC);
    adventLibrary.show( copy );
    System.out.println();

   }



   //thanks to https://www.geeksforgeeks.org/reduce-the-fraction-to-its-lowest-form/
   // Function to reduce a fraction to its lowest form
   static void reduceFraction(int x, int y)
   {
        int d;
        d = __gcd( Math.abs(x), Math.abs(y) );

        x = x / d;
        y = y / d;

        System.out.println("gcd x = " + x + ", y = " + y);
   }

   static int __gcd(int a, int b)
   {
        if (b == 0)
                return a;
        return __gcd(b, a % b);
   }

 */



/*
   //public boolean onLine(int x1, int y1, int x2, int y2, int x3, int y3)
   public boolean onLine(double m, double b, int r, int c, int curR, int curC)
   {
        //double m = getSlope(x1, y1, x2, y2);
        //double b = getB( m, x1, y1);
        if (r == curR || c == curC)
          return true;
        else
        return b == ((double)r-m*(double)c);
   }

   public double getSlope(int r1, int c1, int r2, int c2)
   {
        if (c1 == c2)
          return 0;
        else return (r2-r1)/(c2-c1);
   }

   public double getB(double m, int r, int c)
   {
        return (double)r - m * (double)c;
   }
 */



/*  double m = getSlope(curR, curC, endR, endC);
   double b = getB( m, curR, curC);

   for (int r=0; r<copy.length; r++)
   {
          for (int c=0; c<copy[r].length; c++)
          {
                  //if find # that is not start point
                  if ( copy[r][c] == '#' )
                    if ( !(r == curR && c == curC) && !(r==endR && c==endC) )
                      if (onLine( m, b, r, c, curR, curC) )
                      {
                        System.out.printf( "online: %d,%d\n", r, c);
                        if ( between(r,c,curR,curC,endR,endC) )
                           copy[curR][curC] = '.'; //knockOut
                      }
          }//for c
   }//for r
 */

/*
   //double m = getSlope(1, 0, 3, 4);
   //System.out.println( "m = " + m );

   //double b = getB( m, 1, 0);
   //System.out.println( "b = " + b );

   //System.out.println( onLine(1,0,3,4,2,2) );
   //System.out.println( onLine(1,0,3,4,2,3) );
   //System.out.println( onLine(m, b, 2,2) );
   //System.out.println( onLine(m, b, 2,3) );

   double m = getSlope(1, 0, 3, 4 );
   double b = getB( m, 1, 0);

   for (int r=0; r<data.length; r++)
   {
   for (int c=0; c<data[r].length; c++)
   {
    if ( onLine(m, b, c, r) )
      System.out.print( "T" );
    else
    System.out.print( " " );
   }//for c
   System.out.println();
   }//for r
 */
