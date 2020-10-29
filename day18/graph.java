import java.util.ArrayList;
import java.util.HashMap;

public class graph {

	public ArrayList<node> nodes;
	public HashMap<String, edge> edgeMap;
	
	public graph()
	{
		nodes = new ArrayList<node>();	
		edgeMap = new HashMap<String, edge>();
	}
	
	public void addNode( node n )
	{
		nodes.add( n );
	}
	
	public void addEdge( String key, edge e )
	{
		if (edgeMap.get(key) == null)
			edgeMap.put(key, e);
		else
			System.out.println("Error: " + key + " already exists");
	}
	
	public void showNodes()
	{
		for( int i=0;i < nodes.size(); i++)
		{
			System.out.println( nodes.get(i) );
		}
	}
	
	public node getNode( String s )
	{
		node n = null;
		
		for( int i=0;i < nodes.size(); i++)
		{
			if (nodes.get(i).id.equals(s))
			{
				n = nodes.get(i);
				i = nodes.size(); //break :-)
			}
		}
		
		return n;
	}

	public edge getEdge( String key )
	{
		edge e = null;
		if (edgeMap.get(key) != null)
			e = edgeMap.get(key); 
		return e;
	}
	
	public void showEdges()
	{
		for( int i=0;i < nodes.size(); i++)
		{
			node start = nodes.get(i);
			for( int j=0;j < nodes.size(); j++)
			{	
				node stop = nodes.get(j);
				String key = start.id+","+stop.id;
				if (this.getEdge(key) != null)
					System.out.println( this.getEdge(key) );
			}
			System.out.println();
		}
	}
	
}//class graph