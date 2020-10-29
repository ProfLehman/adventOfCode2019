public class node {

	public String id;
	public int x;
	public int y;
	
	public node( int nx, int ny, String nid )
	{
		id = nid;
		x = nx;
		y = ny;
	}

	public String toString()
	{
		return "node: " + id + "  " + x + "," + y;
	}
	
}//class