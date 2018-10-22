package eight_puzzle;

import java.util.ArrayList;

public class Node implements Comparable<Node>
{
	private int id=0;
	private int values[];
	private int hv=0;
	private int level=9999999;
	private int zero;
	private int N=3;
	Node prev,next;
	
	public Node(int values[], int level)
	{
		this.values=values;
		this.level=level;
		for(int i : values)	id=id*10+i;
		setManhattanSum();
	}
	public int getManhattanSum()
	{
		return hv;
	}
	public void setManhattanSum() 
	{
		int t=0;
		for(int i=0; i<values.length; i++)	
		{
			if(values[i]!=0)
			{
				int c1,c2,r1,r2;
				c1=i%N;
				c2=(values[i]-1)%N;
				r1=(int)Math.ceil((double)(i+1)/N);
				r2=(int)Math.ceil((double)(values[i]-0)/N);
				
				int g = Math.abs(c1-c2)+Math.abs(r1-r2);
				t += g;
			}
				
			else	zero=i;
		}
		hv=t;
	}

	@Override
	public int compareTo(Node o) 
	{
		return this.hv+this.level-o.hv-o.level;
		//if(this.hv+this.level<o.hv+o.level)	return	this.level-o.level;
		//return this.hv-o.hv;
	}
	
	public ArrayList<Node> getChilds()
	{
		ArrayList<Node>nodes=new ArrayList<>();
		if(zero>2)
		{
			int [] tv = values.clone();
			tv[zero]=getItself(tv[zero-N], tv[zero-N]=tv[zero]);
			Node nd=new Node(tv, level+1);
			nd.prev=this;
			nodes.add(nd);
		}
		if(zero<6)
		{
			int [] tv = values.clone();
			tv[zero]=getItself(tv[zero+N], tv[zero+N]=tv[zero]);
			Node nd=new Node(tv, level+1);
			nd.prev=this;
			nodes.add(nd);
		}
		if(zero%N!=0)
		{
			int [] tv = values.clone();
			tv[zero]=getItself(tv[zero-1], tv[zero-1]=tv[zero]);
			Node nd=new Node(tv, level+1);
			nd.prev=this;
			nodes.add(nd);
		}
		if((zero+1)%N!=0)
		{
			int [] tv = values.clone();
			tv[zero]=getItself(tv[zero+1], tv[zero+1]=tv[zero]);
			Node nd=new Node(tv, level+1);
			nd.prev=this;
			nodes.add(nd);
		}
		return nodes;
	}
	public static int getItself(int itself, int dummy)
	{
	    return itself;
	}
	public void print()
	{
		for(int i=0; i<values.length; i++)	
		{
			System.out.print(values[i]+" ");
			if((i+1)%N==0)	System.out.println();
		}
	}
	public int getId()
	{
		return this.id;
	}
	public boolean relax(int level, Node prev)
	{
		if(this.level>level)
		{
			this.prev=prev;
			this.level=level;
			return true;
		}
		return false;
	}
	public int getLevel()
	{
		return level;
	}
	public int [] getValues()
	{
		return values;
	}
}