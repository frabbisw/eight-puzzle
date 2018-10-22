package eight_puzzle;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JFrame
{
	Node current;
	JPanel controls;
	JPanel board;
	int xx=100;
	int yy=100;
	JButton [] buttons;
	
	public GUI()
	{
		current=generatePuzzle();
		new Solver(current);
		this.setSize(5*xx, 3*yy);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Eight Puzzle");
		//this.setLayout(null);
		
		JPanel parent = new JPanel();
		this.add(parent);
		parent.setLayout(null);
		parent.setBounds(0, 0, 5*xx, 3*yy);
		
		controls = new JPanel();
		controls.setBounds(0, 0, 2*xx, 3*yy);
		controls.setBackground(new Color(150, 250, 200));
		controls.setLayout(null);
				
		board = new JPanel();
		board.setBounds(2*xx, 0, 3*xx, 3*yy);
		board.setBackground(new Color(150, 200, 250));
		board.setLayout(null);
		
		buttons = new JButton[9];
		for(int i=0; i<buttons.length; i++)	buttons[i] = new JButton("Butt");
		
		buttons[0].setBounds(0, 0, xx, yy);
		buttons[1].setBounds(xx, 0, xx, yy);
		buttons[2].setBounds(2*xx, 0, xx, yy);
		buttons[3].setBounds(0, yy, xx, yy);
		buttons[4].setBounds(xx, yy, xx, yy);
		buttons[5].setBounds(2*xx, yy, xx, yy);
		buttons[6].setBounds(0, 2*yy, xx, yy);
		buttons[7].setBounds(xx, 2*yy, xx, yy);
		buttons[8].setBounds(2*xx, 2*yy, xx, yy);
		
		for(int i=0; i<buttons.length; i++)
		{
			buttons[i].setBackground(new Color(200, 250, 150));
			board.add(buttons[i]);
			buttons[i].setFont(new Font("Verdana", Font.BOLD, 50));
		}
		set(current);
		
		JButton start = new JButton("solve");
		start.setBounds(xx/2, yy/2, xx, yy/4);
		controls.add(start);
		start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				new Thread()
				{
					public void run() 
					{
						super.run();
						
						while(current!=null)
						{
							try {
								sleep(500);
								set(current=current.next);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				}.start();
			}
		});
		
		JButton next = new JButton("next");
		next.setBounds(xx/2, yy, xx, yy/4);
		controls.add(next);
		next.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(current!=null)	set(current=current.next);
			}
		});
		
		JButton prev = new JButton("previous");
		prev.setBounds(xx/2, yy+yy/2, xx, yy/4);
		controls.add(prev);
		prev.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(current!=null)	set(current=current.prev);
			}
		});
		
		JButton reset = new JButton("reset");
		reset.setBounds(xx/2, yy+yy, xx, yy/4);
		controls.add(reset);
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				current=generatePuzzle();
				new Solver(current);
				set(current);
			}
		});
		
		parent.add(controls);
		parent.add(board);
	}
	protected void set(Node n) 
	{
		if(n==null)	return;
		int [] values = n.getValues();
		for(int i=0; i<buttons.length; i++)
		{
			if(values[i]==0)	buttons[i].setVisible(false);
			buttons[i].setText(values[i]+"");
		}
		for(int i=0; i<buttons.length; i++)
		{
			if(values[i]!=0)	buttons[i].setVisible(true);
		}
	}
	
	public static Node generatePuzzle()
	{
		int N=3;
		Random random = new Random();
		int [] values = new int [9];
		for(int i=0; i<values.length; i++)	values[i]=i+1;
		values[values.length-1]=0;
	
		int zero = values.length-1;
		for(int i=0; i<1000; i++)
		{
			int r = random.nextInt(4);
			
			if(zero>2 & r==0)
			{
				values[zero]=getItself(values[zero-N], values[zero-N]=values[zero]);
				zero=zero-N;
			}
			else if(zero<6 & r==1)
			{
				values[zero]=getItself(values[zero+N], values[zero+N]=values[zero]);
				zero=zero+N;
			}
			if(zero%N!=0 & r==2)
			{
				values[zero]=getItself(values[zero-1], values[zero-1]=values[zero]);
				zero=zero-1;
			}
			if((zero+1)%N!=0 & r==3)
			{
				values[zero]=getItself(values[zero+1], values[zero+1]=values[zero]);
				zero=zero+1;
			}
		}
		//int tmp[]  = {1,2,3,4,5,6,8,7,0};
		//values=tmp;
		return new Node(values, 0);
	}
	public static int getItself(int itself, int dummy)
	{
	    return itself;
	}
}
