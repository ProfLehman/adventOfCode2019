public class moon {

	public int x, y, z;
	public int vx, vy, vz;
	
	public moon(String s)
	{
		//get x,y,z
		int c1 = s.indexOf(",");
		int c2 = s.indexOf(",", c1+1);
		int c3 = s.indexOf(">");
		
		x = Integer.parseInt( s.substring(3, c1) );
		y = Integer.parseInt( s.substring(c1+4, c2).trim() );
		z = Integer.parseInt( s.substring( c2+4, c3 ).trim() );

		//init velocities
		vx = 0;
		vy = 0;
		vz = 0;
	}
	
	
	public void appplyGravityChanges()
	{	
		//update positions
		//x = x + vx;
		//y = y + vy;
		z = z + vz;
	}
	
	public void addMoon( moon m)
	{
			//if (x > m.x)
			//	vx--;
			//else if (x < m.x)
			//	vx++;

			//if (y > m.y)
			//	vy--;
			//else if (y < m.y)
			//	vy++;
			
			if (z > m.z)
				vz--;
			else if (z < m.z)
				vz++;
	}
	
	public int te()
	{
		int pe = Math.abs(x) + Math.abs(y) + Math.abs(z);
		int ke = Math.abs(vx) + Math.abs(vy) + Math.abs(vz);
		return pe * ke;
	}
		
	public String toString()
	{
		return String.format("pos=<x=%3d, y=%3d, z=%3d>, vel=<x=%3d, y=%3d, z=%3d>",x,y,z,vx,vy,vz);
	}
	
	public String getState()
	{
		return String.format("m%d,%d,%d,%d,%d,%d",x,y,z,vx,vy,vz);
	}

}//class