/*
 * NumberCombo.java
 * prof. lehman
 * fall 2017
 * generates string of all unique number combinations
 *
 * NumberCombo myApp = new NumberCombo(3);
 * 012
 * 021
 * 102
 * 120
 * 201
 * 210
 * count: 6
 *
 */
public class NumberCombo
{
    private int data[];
    private int max; //0 to 9, max is 10
    private boolean done;

    public NumberCombo()
    {
        data = new int[8];
        max = 8;
        done = false;
		this.next();
    }

    public NumberCombo(int n)
    {
        data = new int[n];
        max = n;
		done = false;
		this.next();
    }

    public NumberCombo(String s)
    {
        data = new int[s.length()];
        max = s.length();
		done = false;
        setNumberCombo(s);
    }

    public void setNumberCombo(String s)
    {
        for (int x=0; x<data.length; x++)
            data[x] = (int) s.charAt(x) - 48; // '0' => 0

		done = false;
    }

	public boolean done() { return done; };

	public int getValue(int i)
	{
		if (i >=0 && i < data.length)
			return data[i];
		else
			return -1;
	}

    public void update()
    {
        updateHelper(data.length-1);
        return;
    }

    public void updateHelper(int p)
    {
        if (p >= 0)
        {
            data[p]++;
            if (data[p] == max)
            {
                if (p == 0)
                {
                    data[p]--;
                    done = true;
                }
                else
                {
                    data[p] = 0;
                    updateHelper(p-1);
                }
            }
        }

        return;
    }

    public String toString()
    {
        String result = "";

        for (int x=0; x<data.length; x++)
            result += (char) (data[x] + 48);
        return result;
    }

    public boolean valid()
    {
        int counts[] = new int[ max ];

        for (int x=0; x<data.length; x++)
            counts[ data[x] ]++;

        for (int x=0; x<counts.length; x++)
            if ( counts[x] != 1)
                return false; //break's out of loop

        return true;
    }

    public void next()
    {
        this.update();
        while (!this.valid() && !done)
            this.update();
    }

    public static void main(String args[])
    {
		NumberCombo myApp = new NumberCombo(9);
		System.out.println( "Starting: " + myApp );

        //myApp.setNumberCombo("01234567");
		//myApp.setNumberCombo("201");
		int count = 0;
        while(true && !myApp.done() )
        {
            System.out.println( myApp );
			count++;
			myApp.next();
        }
		System.out.println( "count: " + count );

    }//test
}
