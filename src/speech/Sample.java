package speech;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.*;
import java.util.Locale;

// Create the simple calculator frame.
class Sample extends JFrame
{
	static Recognizer rec;
	static JTextField tf;
	static boolean start=true;
	static String op="=";
	static double arg=0;
	static JButton b;
	static String button="789/456*123-0.=+";
	static JPanel p =  new JPanel();
	static RecognizerMain recogmain;

	//Accept the command form user.
	static ResultListener resultlis = new ResultAdapter()
		{
			public void resultAccepted(ResultEvent e)
			{
			Result r = (Result)(e.getSource());
			System.out.println(r);
			ResultToken tokens[] = r.getBestTokens();
				for(int k=0;k<tokens.length;k++)
				{
				FinalRuleResult result = (FinalRuleResult)e.getSource();
				String tag[]=result.getTags();
					if(tag[0].equals("0"))
					{	
					String str ="0";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("1"))
					{	
					String str ="1";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("2"))
					{	
					String str ="2";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("3"))
					{	
					String str ="3";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("4"))
					{	
					String str ="4";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("5"))
					{	
					String str ="5";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("6"))
					{	
					String str ="6";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("7"))
					{	
					String str ="7";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("8"))
					{	
					String str ="8";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("9"))
					{	
					String str ="9";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("plus"))
					{	
					String str ="+";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("minus"))
					{	
					String str ="-";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("into"))
					{	
					String str ="*";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("divide"))
					{	
					String str ="/";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("dot"))
					{	
					String str =".";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
					if(tag[0].equals("equal"))
					{	
					String str ="=";
						if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
						{
							if(start)
							tf.setText(str);
							else
							tf.setText(tf.getText()+str);
						start=false;
						}
						else
						{
							if(start)
							{
								if(str.equals("-"))
								{
								tf.setText(str);
								start=false;
								}
								else
								op=str;
							}	
							else
							{
							calculate(Double.parseDouble(tf.getText()));
							op=str;
							start=true;
							}
						}
					}
				}
			}
		};
	
	//Allocate recognizer engine.	
	public Sample()
	{
		try
		{	
			try
			{	
			rec = Central.createRecognizer(new EngineModeDesc(Locale.ENGLISH));
			rec.allocate();
		 	FileReader reader = new FileReader("numbers.gram");
			RuleGrammar gram = rec.loadJSGF(reader);
			gram.setEnabled(true);
			rec.addResultListener(resultlis);
			rec.commitChanges();
			rec.requestFocus();
			rec.resume();
			}
			catch(Exception rc)
			{
			rc.printStackTrace();
			}
						
			tf = new JTextField();
			tf.setEditable(false);
			getContentPane().add(tf,BorderLayout.NORTH);		
				for(int i=0;i<button.length();i++)
				{	
				p.setLayout(new GridLayout(4,4));
				b = new JButton(button.substring(i,i+1));
				b.addActionListener(new ActionListener()
					{
					public void actionPerformed(ActionEvent e)
						{
						String str = e.getActionCommand();
						System.out.println(str);
							if('0'<=str.charAt(0) && str.charAt(0)<='9' || str.equals("."))
							{
								if(start)
								tf.setText(str);
								else
								tf.setText(tf.getText()+str);
							start=false;
							}
							else
							{
								if(start)
								{
									if(str.equals("-"))
									{
									tf.setText(str);
									start=false;
									}
									else
									op=str;
								}
								else
								{
								calculate(Double.parseDouble(tf.getText()));
								op=str;
								start=true;
								}
							}
						}
					});
				p.add(b);
				}
			getContentPane().add(p,BorderLayout.CENTER);
		}
		catch(Exception e1)
		{
		e1.printStackTrace();
		}
	}
	
	//Calculate the numbers
	static void calculate(double n)
	{
		if(op.equals("+"))
			arg+=n;
		else if(op.equals("-"))
			arg-=n;
		else if(op.equals("*"))
			arg*=n;
		else if(op.equals("/"))
			arg/=n;
		else if(op.equals("="))
			arg=n;
		tf.setText(""+arg);
	}	
}