package eight_puzzle;

import java.util.ArrayList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class Solver
{
	Node root;
	Node last;
	Map <Integer,Node> openSet = new TreeMap<>();
	Map <Integer,Node> closedSet = new TreeMap<>();
	
	public Solver(Node root)
	{
		this.root=root;
		visit();
		construct(last);
		//print();
	}
	
	private void print() 
	{
		Node n = root;
		while(n!=null)
		{
			n.print();
			System.out.println();
			n=n.next;
		}
	}

	private void construct(Node n)
	{
		if(n!=null)
		if(n.prev!=null)			
		{
			n.prev.next=n;
			construct(n.prev);
		}
	}

	private void visit()
	{
		PriorityQueue<Node>queue = new PriorityQueue<>();
		queue.add(root);
		openSet.put(root.getId(), root);
		
		while(!queue.isEmpty())
		{
			Node current = queue.remove();	
			
			if(current.getManhattanSum()==0)
			{
				last=current;
				System.out.println("paisi "+current.getLevel());
				break;
			}
			
			closedSet.put(current.getId(), openSet.remove(current.getId()));
			
			ArrayList<Node>nodes=current.getChilds();
			
			for(Node n : nodes)
			{
				if(!closedSet.containsKey(n.getId()))
				{
					if(!openSet.containsKey(n.getId()))
					{
						queue.add(n);
						n.relax(current.getLevel()+1, current);
					}
					else
						n.relax(current.getLevel()+1, current);
				}
			}
		}
		System.out.println("terminated");
	}
}