import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.applet.*;
import java.math.*;
import java.io.*;
import java.lang.*;
public class MyCalc extends Applet implements ActionListener,ItemListener,KeyListener
{

	private MyFile f=new MyFile();//class MyFile is an inner class contains the methods we used to act with the files
	private CheckboxGroup deg;
	private TextArea ta;
	private Label l,memoryTag;
	private MyButton[] button;
	private TextField t;
	private String s="0",s1="",s2="",op="",s3="";
	private double m=0,n=0,mem=0;
	private Panel p1,p2,p3,p4,p5,p6,p7,p8;
	private String[] Sbutton={"Power","Advanced","Shift","C","CE","Back Space","7","8","9","4",
                                  "5","6","1","2","3","0",".","PI","=","Ans"
                                  ,"Sin","Cos","Sqrt","Sqr","X!","Pow","%","1/X","+/-","S_Store",
                                  "E_Store","/","Del","*","Me","-","M+","+","M-","MR",
                                  "R","Previow","Close"};
	private boolean cond=false,cond1=false,cond2=false,cond3=false,cond4=false,cond5=true,pressed=false;

/*
	the conditions :
	1:cond  :for advanced button
	2:cond1 :for Power
	3:cond3 :for checkbox radian or degrees
	4:cond4 :for start saving and end saving into the file
	5:cond5 :for the simecolon
	5:pressed for making the textfield = "" after any calculation


*/
	public void init()
	{
		setLayout(new BorderLayout(0,7));//the layout for the applet
		setBackground(new Color(58,110,165));
		l=new Label("                 Bad Info Guys");
		memoryTag=new Label("  ");
		memoryTag.setEnabled(true);
		l.setFont(new Font("Times Roman",Font.BOLD,17));
		memoryTag.setFont(new Font("Times Roman",Font.BOLD,17));
		memoryTag.setForeground(Color.black);
		l.setForeground(Color.green);
		l.setForeground(Color.green);
		ta=new TextArea("No text\n-----------------------\n",8,17,TextArea.SCROLLBARS_VERTICAL_ONLY);
		ta.setFont(new Font("Times Roman",Font.BOLD,12));
		ta.setEditable(false);
		deg = new CheckboxGroup();//the container of the checkboxs
		Checkbox degrees = new Checkbox("Deg",deg,true);
		Checkbox radians = new Checkbox("Rad",deg,false);
		button =new MyButton[43];
		for(int i=0;i<20;i++)//creating the Advanced buttons like sin & cos & arcsin etc..
			button[i]=new MyButton(Sbutton[i],new Color(174,168,217));
		for(int i=20;i<43;i++)//creating the numbers
			button[i]=new MyButton(Sbutton[i],new Color(58,222,160));

		t=new TextField(30);
		t.setFont(new Font("Times Roman",Font.BOLD,12));
		t.addKeyListener(this);
		t.setText("0");

		//constructin the panels in the applet
		p1=new Panel();
		p2=new Panel();
		p3=new Panel();
		p4=new Panel();
		p5=new Panel();
		p6=new Panel();
		p7=new Panel();
		p8=new Panel();
//adding the components  to matching panel
		p5.setBounds(20,1,275,30);
		p5.add(button[0]);
		p5.add(l);
		add(p5);

		p1.setLayout(new GridLayout(3,3,2,2));
		p1.setBounds(20,104,225,78);
		for(int i=20;i<29;i++)
			p1.add(button[i]);
                p1.setVisible(false);
                add(p1);


		p2.setBounds(20,32,280,70);
		p2.add(t);
		p2.add(memoryTag);
		p2.add(p4);
		p4.add(button[1]);
		p4.add(button[2]);
		p4.add(degrees);
		p4.add(radians);
		t.setBackground(Color.black);
		add(p2);


		p3.setLayout(new GridLayout(6,3,2,3));
		p3.setBounds(20,190,225,156);
		for (int i=3;i<20;i++)
			p3.add(button[i]);
                add(p3);


		p7.setBounds(300,1,150,182);
		p7.add(ta);
		p7.add(button[41]);
		p7.add(button[42]);
		add(p7);


		p6.setLayout(new GridLayout(6,2,2,2));
		p6.setBounds(300,190,150,156);
		p8.setBounds(0,0,400,40);
		for(int i=29;i<41;i++)
			p6.add(button[i]);

		add(p6);

		add(p8);
//befor you can use the Calculator you have to press the power
		for(int i=1;i<43;i++)
			button[i].setEnabled(false);
		t.setEditable(false);
//adding the action listener
		for(int i=0;i<43;i++)
			button[i].addActionListener(this);
		degrees.addItemListener(this);
		radians.addItemListener(this);

	}
//this procedure compute the calculation needed
	public void calculate()
	{
				double l=0;
				int o=0;
				String opp="";
				s2=s;
				s="0";
				m=Double.valueOf(s1).doubleValue();
				n=Double.valueOf(s2).doubleValue();
				opp=op;

				if (op.compareTo("+")==0)
					{
						l=m+n;
						op="";
					}
				else if (op.compareTo("-")==0)
					{
						l=m-n;
						op="";
					}
				else if (op.compareTo("*")==0)
					{
						l=m*n;
						op="";
					}
				else if (op.compareTo("/")==0)
					{
						l=m/n;
						op="";
					}
				else if (op.compareTo("")==0)
					{
						l=Double.valueOf(s2).doubleValue();
					}

				else if (op.compareTo("Pow")==0)
					{
						o=Integer.valueOf(s2).intValue();
						l=MyMath.mypow(m,o);
						op="";
					}
				cond5=true;
				pressed=true;
				s=""+l;
				t.setText(s);
				f.addTofile(s1+opp+s2+"="+l);

			}

	public void keyPressed(KeyEvent e)
	{

		double ac=e.getKeyCode();

		if((ac==13)||(ac==10))
			calculate();
		else if(ac==8)
			{
				if(s.compareTo("")==0)
					t.setText("Error");
				else
					{
						s=s.substring(0,s.length()-1);
						t.setText(s);
					}
			}
	}
	public void keyTyped(KeyEvent e)
	{
		char ch;
		ch=e.getKeyChar();
		if ((ch=='1')||(ch=='2')||(ch=='3')||(ch=='4')||(ch=='5')||(ch=='6')||(ch=='7')||(ch=='8')||(ch=='9')||(ch=='0'))
		{   if(pressed)
			{
				s="0";
				pressed=false;
			}
			if(s=="0")
				s=""+ch;
			else s=s+ch;
		}
		else if(ch=='.'&&cond5)
		{
			if(pressed)
			{
				s="0";
				pressed=false;
			}
			s=s+ch;
			cond5=false;
		}
		else if((ch=='+')||(ch=='-')||(ch=='*')||(ch=='/'))
			{
				op=""+ch;
				s1=s;
				s="0";
			}
			else if(ch=='=')
				calculate();

		t.setText(s);
	}
	public void keyReleased(KeyEvent e)
	{
	}

	public void itemStateChanged(ItemEvent e)
	{
	}
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource()==button[29])//for begin store in the file
		{
			cond4=true;
			button[30].setEnabled(true);
			button[29].setEnabled(false);
			f.CreateFile();
		}
		else if (e.getSource()==button[30])//end of the storing
		{
			cond4=false;
			button[29].setEnabled(true);
			button[30].setEnabled(false);
			f.endStore();

		}
		else if (e.getSource()==button[41])//preview
		{
			button[41].setEnabled(false);
			button[42].setEnabled(true);
			f.WriteFileOnTextArea();
		}
		else if (e.getSource()==button[42])//close for textarea
		{
			button[41].setEnabled(true);
			button[42].setEnabled(false);
			ta.setText("Sankalp Mittal Presents!\n-----------------------\n");
		}
		else if (e.getSource()==button[32])//Del button
		{
			f.CreateFile();
		}
		else if (e.getSource()==button[1])//for advanced buttons
		{
			if(!cond)
			{
				cond=true;
				p1.setVisible(true);
				button[1].setLabel("Simple");
				for(int i=20;i<29;i++)
						button[i].setEnabled(true);
			}
			else
			{
				button[1].setLabel("Advanced");
				p1.setVisible(false);
				cond=false;
			}
		}
		else if (e.getSource()==button[0])//power
		{
			if(!cond1)
			{
				cond5=true;
				t.setBackground(Color.white);
				cond1=true;
				button[0].setLabel("OFF");
				for(int i=1;i<42;i++)
					button[i].setEnabled(true);
				button[30].setEnabled(false);
			}
			else
			{
				cond1=false;
				cond5=true;
				pressed=false;
				t.setBackground(Color.black);
				ta=new TextArea("Sankalp Mittal is here\n-----------------------\n");
				f.delFile();
				s="0";s1="";s2="";
				memoryTag.setText(" ");
				t.setText(s);
				mem=0;
				button[0].setLabel("ON");
				for(int i=1;i<43;i++)
					button[i].setEnabled(false);
			}
		}
		else if(e.getSource()==button[16])//semicolon
			{
				if(pressed)
				{
					s="0";
					pressed=false;
				}
				if (cond5)
				{
					s=s+".";
					cond5=false;
					t.setText(s);
				}
			}
//The implemetation of the Number Buttons
		else if ((e.getSource()==button[6]) || (e.getSource()==button[7]) || (e.getSource()==button[8]) || (e.getSource()==button[9]) || (e.getSource()==button[10]) || (e.getSource()==button[11]) || (e.getSource()==button[12]) || (e.getSource()==button[13]) || (e.getSource()==button[14]) ||(e.getSource()==button[15]) )
		{	if(pressed)
			{
				s="0";
				pressed=false;
			}
			if(s.compareTo("0")==0)
				s=e.getActionCommand();
			else s=s+e.getActionCommand();
			s3=s;
			t.setText(s);
			showStatus(e.getActionCommand());

		}
//the opertor section (simple opertaion)
		else if ((e.getSource()==button[37])||(e.getSource()==button[35])||(e.getSource()==button[33])||(e.getSource()==button[31])||(e.getSource()==button[25]))
		{
			cond5=true;
			op=e.getActionCommand();
			s1=s;
			s3=s;
			s="0";
			t.setText(s);
		}
		else if ((e.getSource()==button[18]))
			calculate();
		else if (e.getSource()==button[5])
		{
			s=s.substring(0,s.length()-1);
			t.setText(s);
		}
		else if (e.getSource()==button[3])
		{
			op="";
			s=s3;
			t.setText(s);
		}
		else if (e.getSource()==button[4])
		{
			t.setText("0");
			cond5=true;
			s="0";
			s1="";
			s2="";
		}
		else if (e.getSource()==button[27])
		{
			pressed=true;
			float g=0,p=0;
			p=Float.valueOf(s).floatValue();
			if(p==0)
			{
				s="0";
				t.setText("Invinite");
			}
			else{
			g=(1/p);
			s=""+g;
			s3=s;
			t.setText(s);
			f.addTofile("1/"+p+"="+s);
		}
		}
		else if (e.getSource()==button[28])
		{
            if (!(s.compareTo("0")==0))
            {
                if(s.charAt(0)=='-')
				s=s.substring(1,s.length());
            else
                s="-"+s;
                t.setText(s);
            }
        }
		else if(e.getSource()==button[34])
		{
			pressed=true;
			memoryTag.setText("M");
			mem=Double.valueOf(s).doubleValue();
			if(mem==0)
				memoryTag.setText(" ");
			showStatus("Memory="+mem);
		}
		else if(e.getSource()==button[36])
		{
			pressed=true;
			memoryTag.setText("M");
			mem=mem+(Double.valueOf(s).doubleValue());
			if(mem==0)
				memoryTag.setText(" ");
			showStatus("Memory="+mem);
		}
		else if(e.getSource()==button[38])
		{
			pressed=true;
			memoryTag.setText("M");
			mem=mem-(Double.valueOf(s).doubleValue());
			if(mem==0)
				memoryTag.setText(" ");
			showStatus("Memory="+mem);

		}
		else if(e.getSource()==button[39])
		{
			s=""+mem;
			s3=s;
			t.setText(s3);
		}
		else if(e.getSource()==button[20])
		{
			pressed=true;
			double x=Double.valueOf(s).doubleValue();
			String s4 = deg.getSelectedCheckbox().getLabel();
			if (!cond3)
			{
			if(s4.equals("Deg"))
			{
				while(x>=360)
				 		x=x-360;
				s=""+MyMath.msin(MyMath.invert(x));
				t.setText(""+s);
			}
			else if(s4.equals("Rad"))
			{
				while(x>=(2*Math.PI))
				 		x=x-Math.PI;

				s=""+MyMath.msin(x);
				t.setText(s);
			}
			f.addTofile("Sin("+x+")="+s);
			}
			else
			{
			if(s4.equals("Deg"))
			{
				x=Double.valueOf(s).doubleValue();
				s=""+MyMath.invertt(Math.asin(x));
				t.setText(""+s);
			}
			else if(s4.equals("Rad"))
			{
				x=Double.valueOf(s).doubleValue();
				s=""+MyMath.invertt(Math.acos(x));
				t.setText(s);
			}
				s3=s;
				f.addTofile("aSin("+x+")="+s);
			}
		}
		else if(e.getSource()==button[21])
		{
			pressed=true;
			double x=Double.valueOf(s).doubleValue();
			String s4 = deg.getSelectedCheckbox().getLabel();
			if (!cond3)
			{
				 if(s4.equals("Deg"))
				 {
				 		while (x>=360)
				 			x=x-360;
					 	s=""+MyMath.mcos(MyMath.invert(x));
				 	 	t.setText(s);
						s3=s;
				}
				 else if(s4.equals("Rad"))
				 {
				 	while(x>=(2*Math.PI))
				 		x=x-Math.PI;
					 s=""+MyMath.mcos(x);
				 	t.setText(s);
					s3=s;
				}
			f.addTofile("Cos("+x+")="+s);
			}
				else
				{
					if(s4.equals("Deg"))

					{
						x=Double.valueOf(s).doubleValue();
						s=""+MyMath.invertt(Math.acos(x));
						t.setText(s);
						s3=s;
					}
					else if(s4.equals("Rad"))
					{
						x=Double.valueOf(s).doubleValue();
						s=""+MyMath.invertt(Math.acos(x));
						t.setText(s);
						s3=s;
					}
			f.addTofile("aCos("+x+")="+s);
			}
	    	}
	    else if(e.getSource()==button[23])
	    {
	    		 pressed=true;
	    		 double y=0;
				 double  x=Double.valueOf(s).doubleValue();
				 y=MyMath.mysqr(x);
				 t.setText(""+y);
				 s=""+y;
				 s3=s;
				 f.addTofile("sqr("+x+")="+s);

		}
		else if(e.getSource()==button[24])
	    {
		   	 pressed=true;
			 int x=Integer.valueOf(s).intValue();
			 if(x>39)
			 	t.setText("Invinit");
			 else
			 {
			 	s=""+MyMath.myX(x);
			 	t.setText(""+s);
			 	s3=s;
			 	f.addTofile(""+x+"!="+s);
			 }

		}
		else if(e.getSource()==button[2])
		{
			if(!cond3)
				{
					button[20].setLabel("arcSin");
					button[21].setLabel("arcCos");
					cond3=true;
				}
			else
				{
					cond3=false;
					button[20].setLabel("Sin");
					button[21].setLabel("Cos");
				}
		}
		else if(e.getSource()==button[40])
		{
			t.setText("0");
			mem=0;
			memoryTag.setText("");
		}
		else if(e.getSource()==button[26])
		{
			pressed=true;
			float p,r;
			p=Float.valueOf(s).floatValue();
			r=p/100;
			s=""+r;
			s3=s;
			t.setText(s);
			f.addTofile(""+p+"%="+s);

		}
		else if(e.getSource()==button[22])
		{
			pressed=true;
			 double l=0;
			 m=Double.valueOf(s).doubleValue();
			 l=Math.sqrt(m);
			 s=""+l;
			 s3=s;
			 t.setText(s);
			 f.addTofile("sqrt("+m+")="+s);

		}
		else if (e.getSource()==button[19])
		{
			pressed=true;
		  	t.setText(s3);
			s=s3;
			showStatus(s3);

		}
		else if (e.getSource()==button[17])
		{
			pressed=true;
			t.setText(""+3.1415926);
			s=""+3.1415926;

		}
    }

  public static void main(String args[])
		{
			Frame app=new Frame("Calculator");
			app.setSize(470,400);
			app.addWindowListener(new CloseWindowAndExit());
			MyCalc m=new MyCalc();
			m.init();
			m.start();
			app.add(m,BorderLayout.CENTER);
			app.setVisible(true);
	}

    class MyButton extends Button
    {
	public MyButton(String name,Color xyz)
	{
//here we override the constructor of the button
//first we call the Main constructor and then
//we add the color proprty and response for mouse motion
		super(name);
		setBackground(xyz);
		addMouseListener(new MouseCalcButtonAdapter());
	}
	class MouseCalcButtonAdapter extends MouseAdapter
	{
		Color color;
		public void mouseEntered(MouseEvent me)
		{
			color=me.getComponent().getBackground();
			setForeground(Color.red);
			setBackground(Color.black);
			setFont(new Font("Courier New",Font.BOLD,12));
		}
		public void mouseExited(MouseEvent me)
		{
			setFont(new Font("Courier New",Font.BOLD,10));
			setForeground(Color.black);
			me.getComponent().setBackground(color);

		}
	}
   }


   class MyFile   //MyFile is inner class
   {
   	private DataOutputStream Of;
   	public void WriteFileOnTextArea()
 	{
          String str;
          char st;
          str="";
      try
      {
        DataInputStream If=new DataInputStream(new FileInputStream("FileOutput.txt"));
        while(If.readLine()!=null)
        {
        	if ((st=If.readChar())!='E')
        	{
          		str=st+str;
          	}
          	if (st=='E')
          	{
          		ta.append(str);
          		ta.append("\n");
          		str="";
          	}
      	}
      	If.close();

      }
    catch(FileNotFoundException e) {}
    catch(IOException e) {}
	}
	public void CreateFile()
	{
 		try
 		{
    	Of=new DataOutputStream(new FileOutputStream("FileOutput.txt"));
    	Of.writeChars("Begin\n");
    	}
    	catch(IOException e) {
    		System.err.println("File not opened properly\n"+e.toString());
            System.exit(1);
    	}
	}
	public void addTofile(String str)
	{
		try
		{
			for(int i=str.length()-1;i>=0;i--)
			{
				Of.writeChar(str.charAt(i));
				Of.writeChars("\n");
			}
			Of.writeChar('E');
			Of.writeChars("\n");
		}
		catch (IOException io){}
	}
	public void delFile()
	{
		 	CreateFile();
			try
			{
				Of.close();
			}
			catch (IOException io){}
	}
	public void endStore()
	{
			try
			{
				Of.close();
			}
			catch (IOException io){}
	}

}

}

class MyMath

{
		public static double msin(double x)
		{
		if (x<=0.0005)
		 x=x+((x*x*x)/6);
		else
		 x=2*msin(x/2)*mcos(x/2);
		return x;
		}
		public static double mcos(double x)
		{
		if (x<=0.0005)
		 x=1-((x*x*x)/2);
		else
		 x=(mcos(x/2)*mcos(x/2))-(msin(x/2)*msin(x/2));
		return x;
	}
		public static double invertt(double x)
		{
//converting from radian to degree
			double y;
			y=(180*x)/Math.PI;
			return y;
		}

	public static double invert(double x)
	{
//converting from degree to radian
		double y;
		y=(Math.PI*x)/180;
		return y;
	}
	public static double mypow(double x,int y)
	{
		double s;
		s=1;
		for(int i=1;i<=y;i++)
			s=s*x;
		return s;
	}

	public static double mysqr(double x)
	{
		double s=x*x;
		return s;
	}

	public static long myX(int x)
//factorial function
	{
		long t=1;
		for(int i=x;i>0;i--)
			t=i*t;
		return t;
	}

}

 class CloseWindowAndExit  implements WindowListener
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
	public void windowActivated(WindowEvent e){}
	public void windowClosed(WindowEvent e){}
	public void windowDeactivated(WindowEvent e){}
	public void windowDeiconified(WindowEvent e){}
	public void windowIconified(WindowEvent e){}
	public void windowOpened(WindowEvent e){}
}

//Created by SankalpMittal
