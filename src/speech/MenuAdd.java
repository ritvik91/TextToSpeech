package speech;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.undo.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;
import javax.speech.*;
import javax.speech.synthesis.*;

class MenuAdd extends JFrame implements ActionListener
{
	RecognizerMain recogmain;
	String str;
	JMenuBar menubar;
	static JMenu filemenu,editmenu,logoffmenu,formatmenu,fontmenu,fontstylemenu,colormenu,fontsizemenu,alignmenu;
	JMenuItem newitem,openitem,saveitem,closeitem,exititem,saveasitem;
	JMenuItem cutitem,copyitem,pasteitem,undoitem,selectitem,redoitem,finditem,replaceitem;
	JMenuItem shutdownitem,restartitem,logitem;
	JMenuItem bolditem,italicitem,underlineitem;
	JMenuItem leftitem,centeritem,rightitem;
	//JMenuItem fontdialogitem;
	JButton openbutton,startbutton,stopbutton,calbutton,microOnbutton,microOffbutton;
	JDesktopPane desktop;
	boolean b=true;
	JToolBar toolbar,toolbar1;
	JFileChooser filechoose=new JFileChooser(System.getProperty("user.dir"));
	public boolean opened=false;
	public boolean new_file=false;
	public boolean saved_once=false;
	public boolean find_replace;
	final UndoManager undo_redo = new UndoManager();
	String font_name_i="Times New Roman";
	int font_style=0;
	int font_size_i=20;
	ButtonGroup font_group,color_group,size_group;
	JRadioButtonMenuItem font_item[],color_item[],size_item[];
	JCheckBoxMenuItem bold,italic,underline;
	int count=1;
	String color_name[]={"Black","Green","Blue","Red","Yellow"};
	Color color_value[]={Color.black, Color.green, Color.blue,Color.red, Color.yellow};
	String font_names[]={"Arial","Arial Black","Arial Narrow","Book Antiqua","Boolman Old Style","Courier","Courier New","Monotype Corsiva","Times New Roman","Verdana"};
	String font_sizes[]={"10","12","18","20","22","32","36","46","52","60","72"};
 	boolean flage_bold=false;
	boolean flage_italic=false;
	boolean flage_under_line=false;
	SimpleAttributeSet atts = new SimpleAttributeSet();		
	int fstart=0;
	DefaultStyledDocument doc;

	//When start reading word it exists.
	SpeakableListener speaklistener=new SpeakableAdapter()
	{
	 	public void wordStarted(final SpeakableEvent ev) 
		{
		int start=0, end=0;
		start = ev.getWordStart()+fstart;
		end = ev.getWordEnd()+fstart;
		StyleConstants.setUnderline(atts,true);
		doc.setCharacterAttributes(start,end-start,atts,true);
		recogmain.area.setCaretPosition(start);
		recogmain.area.setSelectionStart(start);
		recogmain.area.setSelectionEnd(end);
		recogmain.area.setSelectedTextColor(Color.red);
		recogmain.area.repaint();
			
		}
	};
	
	//It add Menubar and its MenuItem, also toolbar with its button
	public MenuAdd()
	{
		try
		{
		setTitle("Recognizer Window");
		setSize(500,500);
						
		menubar = new JMenuBar();	
		setJMenuBar(menubar);
		
		filemenu = new JMenu("File");
		filemenu.setMnemonic('F');
		menubar.add(filemenu);
		
		newitem = new JMenuItem("New");
		newitem.setMnemonic('N');
		newitem.addActionListener(this);
		KeyStroke newKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N,Event.CTRL_MASK);
		newitem.setAccelerator(newKeyStroke);
		filemenu.add(newitem);
		
		openitem = new JMenuItem("Open");
		openitem.setMnemonic('O');
		openitem.addActionListener(this);
		KeyStroke openKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O,Event.CTRL_MASK);
		openitem.setAccelerator(openKeyStroke);
		filemenu.add(openitem);
	
		closeitem = new JMenuItem("Close");
		closeitem.setMnemonic('C');
		closeitem.addActionListener(this);
		filemenu.add(closeitem);
		filemenu.addSeparator();
	
		saveitem = new JMenuItem("Save");
		saveitem.setMnemonic('S');
		saveitem.addActionListener(this);
		KeyStroke saveKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S,Event.CTRL_MASK);
		saveitem.setAccelerator(saveKeyStroke);
		filemenu.add(saveitem);
		
		saveasitem = new JMenuItem("Save As");
		saveasitem.setMnemonic('A');
		saveasitem.addActionListener(this);
		filemenu.add(saveasitem);
		filemenu.addSeparator();
		
		exititem = new JMenuItem("Exit");
		exititem.setMnemonic('x');
		exititem.addActionListener(this);
		filemenu.add(exititem);
			
		editmenu = new JMenu("Edit");
		editmenu.setMnemonic('E');
		menubar.add(editmenu);
		
		undoitem = new JMenuItem("Undo");
		undoitem.setMnemonic('U');
		undoitem.addActionListener(this);
		KeyStroke undoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z,Event.CTRL_MASK);
		undoitem.setAccelerator(undoKeyStroke);
		editmenu.add(undoitem);
		
		redoitem = new JMenuItem("Redo");
		redoitem.setMnemonic('R');
		redoitem.addActionListener(this);
		KeyStroke redoKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y,Event.CTRL_MASK);
		redoitem.setAccelerator(redoKeyStroke);
		editmenu.add(redoitem);
	
		editmenu.addSeparator();
		cutitem = new JMenuItem("Cut");
		cutitem.setMnemonic('C');
		cutitem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
					recogmain.area.cut();
					}
					catch(Exception e3)
					{
					e3.printStackTrace();
					}
				}
			});
		KeyStroke cutKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_X,Event.CTRL_MASK);
		cutitem.setAccelerator(cutKeyStroke);
		editmenu.add(cutitem);
		
		copyitem = new JMenuItem("Copy");
		copyitem.setMnemonic('o');
		copyitem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				recogmain.area.copy();
				}
			});
		KeyStroke copyKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_C,Event.CTRL_MASK);
		copyitem.setAccelerator(copyKeyStroke);
		editmenu.add(copyitem);
	
		pasteitem = new JMenuItem("Paste");
		pasteitem.setMnemonic('P');
		pasteitem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				recogmain.area.paste();
				}
			});
		KeyStroke pasteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_V,Event.CTRL_MASK);
		pasteitem.setAccelerator(pasteKeyStroke);
		editmenu.add(pasteitem);

		editmenu.addSeparator();
		selectitem = new JMenuItem("Select All");
		selectitem.setMnemonic('A');
		selectitem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
				/*
				String strText=area.getText();
				int strLen = strText.length();
				area.select(0,strLen);
				*/
				recogmain.area.selectAll();
				}
			});
		KeyStroke selectKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_A,Event.CTRL_MASK);
		selectitem.setAccelerator(selectKeyStroke);
		editmenu.add(selectitem);
		
		editmenu.addSeparator();
		finditem = new JMenuItem("Find");
		finditem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent fe)
				{
					try
					{
					recogmain.FindMethod(0);
					Dimension d1 = recogmain.dialog.getSize();
        					Dimension d2 = MenuAdd.this.getSize();
        					int x = Math.max((d2.width-d1.width)/2, 0);
        					int y = Math.max((d2.height-d1.height)/2, 0);
        					recogmain.dialog.setBounds(x + MenuAdd.this.getX(),y + MenuAdd.this.getY(), d1.width, d1.height);
        					recogmain.dialog.setVisible(true);
					}
					catch(Exception ex)
					{}
				}
			});
		finditem.setMnemonic('F');
		KeyStroke findKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F,Event.CTRL_MASK);
		finditem.setAccelerator(findKeyStroke);
		editmenu.add(finditem);

		replaceitem = new JMenuItem("Replace");
		replaceitem.setMnemonic('R');
		replaceitem.addActionListener(this);
		KeyStroke replaceKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_H,Event.CTRL_MASK);
		replaceitem.setAccelerator(replaceKeyStroke);
		editmenu.add(replaceitem);
		
		formatmenu = new JMenu("Format");
		formatmenu.setMnemonic('o');
		menubar.add(formatmenu);	

		fontmenu= new JMenu("Fonts");
		font_group = new ButtonGroup();
		font_item = new JRadioButtonMenuItem[font_names.length];
			for(int k=0;k<font_names.length;k++)
			{
			font_item[k] = new JRadioButtonMenuItem(font_names[k]);
			font_group.add(font_item[k]);
			fontmenu.add(font_item[k]);
			font_item[k].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent fe)
					{
						for(int k=0;k<font_names.length;k++)
						if(font_item[k].isSelected())
						{
						recogmain.area.setFont(new Font(font_names[k],font_style,font_size_i));
						break;
						}
					}
				});
			}
		formatmenu.add(fontmenu);
		
		fontstylemenu = new JMenu("Font Style");
		
		bold = new JCheckBoxMenuItem(new StyledEditorKit.BoldAction());
		bold.setMnemonic('B');
		bold.setText("Bold");
		bold.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_MASK));
		fontstylemenu.add(bold);
		
		italic = new JCheckBoxMenuItem(new StyledEditorKit.ItalicAction());
		italic.setMnemonic('I');
		italic.setText("Italic");
		italic.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));
		fontstylemenu.add(italic);
		
		underline = new JCheckBoxMenuItem(new StyledEditorKit.UnderlineAction());
		underline.setMnemonic('U');
		underline.setText("Underline");
		underline.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_MASK));
		fontstylemenu.add(underline);
		
		formatmenu.add(fontstylemenu);
		
		alignmenu = new JMenu("Alignment");
		leftitem = alignmenu.add(new StyledEditorKit.AlignmentAction("Left",0));
		centeritem = alignmenu.add(new StyledEditorKit.AlignmentAction("Center",1));
		rightitem = alignmenu.add(new StyledEditorKit.AlignmentAction("Right",2));
		formatmenu.add(alignmenu);

		
		colormenu = new JMenu("Color");
		color_group = new ButtonGroup();
		color_item = new JRadioButtonMenuItem[color_name.length];
			for(int j=0;j<color_name.length;j++)
			{
			color_item[j] = new JRadioButtonMenuItem(color_name[j]);
			color_group.add(color_item[j]);
			colormenu.add(color_item[j]);
			color_item[j].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent ce)
					{
						for(int i=0;i<color_name.length;i++)
						if(color_item[i].isSelected())
						{
						recogmain.area.setForeground(color_value[i]);
						break;
						}
					recogmain.area.requestFocus();
					repaint();
					}
				});
			}
		color_item[0].setSelected(true);
		formatmenu.add(colormenu);
		
		fontsizemenu = new JMenu("Font Size");
		size_group = new ButtonGroup();
		size_item = new JRadioButtonMenuItem[font_sizes.length];
			for(int m=0;m<font_sizes.length;m++)
			{
			size_item[m] = new JRadioButtonMenuItem(font_sizes[m]);
			size_group.add(size_item[m]);
			fontsizemenu.add(size_item[m]);
			size_item[m].addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent se)
					{
					FontSizeMethod();	
					}
				});
			}
		formatmenu.add(fontsizemenu);
	/*
		fontdialogitem = new JMenuItem("Font Dialog");
		fontdialogitem.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent fe)
				{
				new FontDialog();
				}
			});
		formatmenu.add(fontdilaogitem);
	*/
		logoffmenu = new JMenu("Turn Off Computer");
		menubar.add(logoffmenu);
		
		shutdownitem = new JMenuItem("Shut Down");
		shutdownitem.addActionListener(this);
		logoffmenu.add(shutdownitem);		
		
		restartitem = new JMenuItem("Restart");
		restartitem.addActionListener(this);
		logoffmenu.add(restartitem);

		logitem = new JMenuItem("Log Off");
		logitem.addActionListener(this);
		logoffmenu.add(logitem);
		
		openbutton = new JButton("Open");
		openbutton.addActionListener(this);
				
		startbutton = new JButton("Speak");
		startbutton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
					SpeakMethod();
					} 
					catch(Exception s1)
					{
					s1.printStackTrace();
					}
				}
			});
		
		stopbutton = new JButton("Stop");
		stopbutton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
					recogmain.synth.cancel();
					}
					catch(ArrayIndexOutOfBoundsException ar)
					{
					System.out.println(ar);
					}
				}
			});
		
		calbutton = new JButton("Calculator");
		calbutton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						if(recogmain.cal_win==false)
						{
						recogmain.cal_win=true;
						Sample calframe = new Sample();
						calframe.setSize(200,200);
						calframe.setTitle("Calculator");
						calframe.setResizable(false);
						calframe.addWindowListener(new WindowAdapter()
							{
								public void windowClosing(WindowEvent we)
								{
									try
									{
									recogmain.rec.deallocate();
									}
									catch(Exception wc)
									{}
								}
							});
						calframe.show();
						}
					}
					catch(Exception e4)
					{}
				}
			});
		toolbar = new JToolBar();
		toolbar.add(openbutton);
		toolbar.add(startbutton);
		toolbar.add(stopbutton);
		//toolbar.add(calbutton);
		getContentPane().add(toolbar,BorderLayout.NORTH);
		this.itemsEnabled(false);

		microOnbutton = new JButton("Microphone On");
		microOnbutton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent me)
				{
				recogmain.MicroOn();
				microOnbutton.setEnabled(false);
				microOffbutton.setSelected(true);
				}
			});
		microOffbutton = new JButton("Microphone Off");
		microOffbutton.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent me)
				{
				recogmain.MicroOff();
				microOffbutton.setSelected(false);
				microOnbutton.setEnabled(true);
				}
			});
		toolbar1 = new JToolBar();
		toolbar1.add(microOnbutton);
		toolbar1.add(microOffbutton);
		getContentPane().add(toolbar1,BorderLayout.SOUTH);
		//setFont(new java.awt.Font("Tahoma", 0, 60));
		}
		catch(Exception e)
		{
		e.printStackTrace();
		}
	}

	// It undo and redo listener edit the text .
	void addUndoRedo(JTextPane ta)
	{
		Document doc = ta.getDocument();
			doc.addUndoableEditListener(new UndoableEditListener()
			{
				public void undoableEditHappened(UndoableEditEvent e)
				{
				undo_redo.addEdit(e.getEdit());
				}
			});
	}

	// It undo the text.
	void Undo()
	{
		try
		{
			if(undo_redo.canUndo())
				undo_redo.undo();
		}
		catch(CannotUndoException ex)
		{}
	}

	// It redo the text
	void Redo()
	{
		try
		{
			if(undo_redo.canRedo())
				undo_redo.redo();
		}
		catch(CannotRedoException ex)
		{}
	}

	//New Action
	void New()
	{
		if(opened==true || new_file==true)
		{
		int returned_value = JOptionPane.showConfirmDialog(null,"Would you like to save changes?","Save...",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(returned_value==JOptionPane.YES_OPTION)
				Save();
			else if(returned_value==JOptionPane.CANCEL_OPTION)
				return;		
		}
		recogmain.area.setBackground(Color.white);
		recogmain.area.setEnabled(true);
		recogmain.area.requestFocus();
		//recogmain.SetEnable_JTextPane();
		recogmain.area.selectAll();
		int start=recogmain.area.getSelectionStart();
		int end=recogmain.area.getSelectionEnd();
		try 
		{
			recogmain.area.getStyledDocument().remove(start,end);
		}
		catch(Exception ex) {}
						
		recogmain.titledborder.setTitle("UNTITLED "+count);
		opened=false;		
		saved_once=false;
		new_file=true;
		itemsEnabled(true);
		count++;
	}

	//Open Action
	void Open()
	{
		
		if(opened==true || new_file==true)
		{
			int return_value=JOptionPane.showConfirmDialog(null,"Would you like to save changes?",	"Save...",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
			if(return_value==JOptionPane.YES_OPTION)
				Save();
			else if(return_value==JOptionPane.CANCEL_OPTION)
				return;
		}
		recogmain.area.selectAll();
		
		try
		{
			recogmain.area.getStyledDocument().remove(recogmain.area.getSelectionStart(),recogmain.area.getSelectionEnd());
		}
		catch(Exception ex) {}
		
		try
		{
			if(filechoose.showOpenDialog(null)== JFileChooser.APPROVE_OPTION)
			{
			File readfile = filechoose.getSelectedFile();
				try	
				{
				FileReader reader = new FileReader(readfile);
				recogmain.area.read(reader,readfile.getName());
				String title =filechoose.getSelectedFile().toString();
				recogmain.titledborder.setTitle(title);
				reader.close();
				
				}
				catch(IOException io)
				{
				io.printStackTrace();
				}
			itemsEnabled(true);
			opened =true;
			new_file = false;
			SwingUtilities.updateComponentTreeUI(MenuAdd.this);
			}	
		}
		catch(Exception e1)
		{}
	}
	
	//Close Action
	void Close()
	{
		if(new_file || opened)
      		{
			try
			{
	        		int confirm = JOptionPane.showConfirmDialog(null,"Would you like to save?","Close Document",JOptionPane.YES_NO_CANCEL_OPTION);
				if(confirm == JOptionPane.OK_OPTION)
 				{
 					Save();
					recogmain.area.selectAll();
					try
					{
					recogmain.area.getDocument().remove(recogmain.area.getSelectionStart(),recogmain.area.getSelectionEnd());
					}
					catch(Exception ex)
					{}
				recogmain.area.setBackground(Color.gray);
				recogmain.area.setEnabled(false);
 				}
				
				else if(confirm == JOptionPane.CANCEL_OPTION)
				{
					return;
				}
				else
	 			{
 					recogmain.area.selectAll();
					try
					{
					recogmain.area.getDocument().remove(recogmain.area.getSelectionStart(),recogmain.area.getSelectionEnd());
					}
					catch(Exception ex)
					{}
				recogmain.area.setBackground(Color.gray);
				recogmain.area.setEnabled(false);
				}
				
			}
			catch(Exception e)
			{
			System.out.println(e);
			}
		}
		recogmain.titledborder.setTitle("");
		opened = false;
		new_file = false;
		itemsEnabled(true);
	}

	//Save Action
	void Save()
	{				
		if(opened==true)
		{
			try
			{
			File readfile = filechoose.getSelectedFile();
				try	
				{
				FileWriter writer = new FileWriter(readfile);
				recogmain.area.write(writer);
				//String title =filechoose.getSelectedFile().getName();
				String title =filechoose.getSelectedFile().toString();
				recogmain.titledborder.setTitle(title);
				writer.close();
				}
				catch(IOException io)
				{
				io.printStackTrace();
				}
			}
			catch(Exception e1)
			{}
		}
		else if(saved_once==true)
		{
			try
			{
			File readfile = filechoose.getSelectedFile();
				try	
				{
				FileWriter writer = new FileWriter(readfile);
				recogmain.area.write(writer);
				//String title =filechoose.getSelectedFile().getName();
				String title =filechoose.getSelectedFile().toString();
				recogmain.titledborder.setTitle(title);
				writer.close();
				}
				catch(IOException io)
				{
				io.printStackTrace();
				}

			}
			catch(Exception e1)
			{}
		}
		else
			SaveAs();
	}

	//SaveAs Action
	private void SaveAs()
	{
		if(filechoose.showSaveDialog(null)==0)
		{
			try
			{	
			File readfile = filechoose.getSelectedFile();
					try	
					{
					FileWriter writer = new FileWriter(readfile);
					recogmain.area.write(writer);
					File Save_Ones = filechoose.getSelectedFile();
					recogmain.titledborder.setTitle(filechoose.getSelectedFile().toString());
					saved_once=true;
					writer.close();
					}
					catch(IOException io)
					{
					io.printStackTrace();
					}
            			}
			catch (Exception ex) 
         			{
           	 		ex.printStackTrace();
         			}	
         		filechoose.rescanCurrentDirectory();
		}
	}
	
	//Exit Action
	void Exit()
	{
		if(new_file || opened)
      		{
	      	int confirm = JOptionPane.showConfirmDialog(null,"Would you like to save?","Exit Application",JOptionPane.YES_NO_CANCEL_OPTION);
			if(confirm == JOptionPane.YES_OPTION)
 			{
 				Save();
 				dispose();
 				System.exit(0);
 			}
			else if(confirm == JOptionPane.CANCEL_OPTION)
 				return;	
			else
 			{
 				dispose();
 				System.exit(0);
 			}
 		}
 		else
 			System.exit(0);
	}
	
	//Shutdownt Action
	public void ShutDown() throws IOException
	{
	Process p = Runtime.getRuntime().exec("shutdown -s -t 03");
	}
	
	//Restart Action
	public void Restart() throws IOException
	{
	Process p = Runtime.getRuntime().exec("shutdown -r");
	}
	
	//LogOff Action
	public void LogOff() throws IOException
	{
	Process p = Runtime.getRuntime().exec("shutdown -l");
	}

	// Items Enabled or Disabled
	void itemsEnabled(boolean b)
	{
		closeitem.setEnabled(b);
		saveitem.setEnabled(b);
		saveasitem.setEnabled(b);

		undoitem.setEnabled(b);
		redoitem.setEnabled(b);
		cutitem.setEnabled(b);
		copyitem.setEnabled(b);
		pasteitem.setEnabled(b);
		selectitem.setEnabled(b);
		finditem.setEnabled(b);
		replaceitem.setEnabled(b);

		startbutton.setEnabled(b);
		stopbutton.setEnabled(b);
				
		fontmenu.setEnabled(b);
		colormenu.setEnabled(b);
		fontstylemenu.setEnabled(b);
		alignmenu.setEnabled(b);
		fontsizemenu.setEnabled(b);
	}

	//Get Font Size
	public void FontSizeMethod()
	{
		for(int k=0;k<font_sizes.length;k++)
		if(size_item[k].isSelected())
		{
		font_size_i=Integer.parseInt(font_sizes[k]);
		recogmain.area.setFont(new Font(font_name_i,font_style,font_size_i));
		break;
		}
	}

	//Speak 
	public void SpeakMethod()
	{
		try
		{
		System.out.println("please");
		String textToplay="";
			if(recogmain.area.getSelectedText() != null)
				textToplay = recogmain.area.getSelectedText();
			else
			{
			doc = (DefaultStyledDocument)recogmain.area.getDocument();
			textToplay= recogmain.area.getText(0,doc.getLength());
			}
		recogmain.synth.speakPlainText(textToplay,speaklistener);
		//recogmain.synth.waitEngineState(Synthesizer.QUEUE_EMPTY);
		recogmain.synth.addSpeakableListener(speaklistener);
		/*
		int start = recogmain.area.getSelectionStart();
		int end = recogmain.area.getSelectionEnd();
		String text = recogmain.area.getText(start,end);
		recogmain.synth.speakPlainText(text,null);
		*/
		}
		catch(Exception se)
		{
		se.printStackTrace();
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			if(e.getSource()==newitem)
				New();
			if(e.getSource()==openitem)
				Open();
			if(e.getSource()==closeitem)
				Close();
			if(e.getSource()==saveitem)
				Save();
			if(e.getSource()==saveasitem)
				SaveAs();
			if(e.getSource()==exititem)
				Exit();
			if(e.getSource()==undoitem)
				Undo();
			if(e.getSource()==redoitem)
				Redo();
			if(e.getSource()==replaceitem)
			{	
				try
				{
				recogmain.FindMethod(1);
				Dimension d1 = recogmain.dialog.getSize();
        				Dimension d2 = MenuAdd.this.getSize();
        				int x = Math.max((d2.width-d1.width)/2, 0);
        				int y = Math.max((d2.height-d1.height)/2, 0);
        				recogmain.dialog.setBounds(x + MenuAdd.this.getX(),y + MenuAdd.this.getY(), d1.width, d1.height);
        				recogmain.dialog.setVisible(true);
				}
				catch(Exception ex)
				{}
			}
			if(e.getSource()==openbutton)
				Open();
			
			if(e.getSource()==shutdownitem)
			{
				try
				{
				ShutDown();
				}
				catch(Exception ex)
				{}
			}
			if(e.getSource()==restartitem)
			{
				try
				{
				Restart();
				}
				catch(Exception ex)
				{}
			}
			if(e.getSource()==logitem)
			{
				try
				{
				LogOff();
				}
				catch(Exception ex)
				{}
			}
		}
		catch(Exception ae)
		{}
	}
}