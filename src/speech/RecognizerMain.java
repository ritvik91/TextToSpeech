package speech;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.speech.*;
import javax.speech.recognition.*;
import javax.speech.synthesis.*;
import javax.speech.synthesis.Voice;
import java.io.*;
import java.util.Locale;

public class RecognizerMain extends MenuAdd
{
	static Recognizer rec;
	static Synthesizer synth;
	static JTextPane area = new JTextPane();
	static JScrollPane scrollpane = new JScrollPane(area);
	static JInternalFrame internalframe;
	static JDesktopPane desktop = new JDesktopPane();
	static RecognizerMain frame;
	static String str;
	static MenuAdd menuadd;
	static int count=1;
	static boolean open_win,save_win,cal_win=false;
	static int return_value;
	static JDialog dialog;
	static TitledBorder titledborder;

	static JTabbedPane tb;
  	static JTextField txtFind1;
  	static JTextField txtFind2;
  	static Document docFind;
  	static Document docReplace;
  	static ButtonModel modelWord;
  	static ButtonModel modelCase;
  	static ButtonModel modelUp;
  	static ButtonModel modelDown;
	
	static int searchIndex = -1;
	static boolean searchUp = false;
	static String  searchData;
	static int m_xStart = -1;
   	static int m_xFinish = -1;
	static int start=0, end=0;
	static Sample sam;

	static DefaultStyledDocument doc;
	static boolean speaking = false;
    	static SimpleAttributeSet atts = new SimpleAttributeSet();
	static int fstart=0;

	static boolean open_dialog;
	static JFileChooser fc;
	static JOptionPane jp;
	
	// Receives RESULT_ACCEPTED event: print it, clean up, exit
	static ResultListener resultlistener = new ResultAdapter()
	{	
		public void resultAccepted(ResultEvent e) 
		{
			try
			{
			Result r = (Result)(e.getSource());
			System.out.println(r);
                        System.out.println("balle balle");
			ResultToken tokens[] = r.getBestTokens();
				for (int i = 0; i <tokens.length; i++)
				{
					try
					{
					FinalRuleResult result = (FinalRuleResult)e.getSource();
					String tags[] = result.getTags();
						if(tags[0].equals("new"))
						{
							try
							{
							menuadd.New();
							}
							catch(Exception ne)
							{
							ne.printStackTrace();
							}
						}
						if(tags[0].equals("open"))
						{
							if(open_win==false || save_win==true)
							{
								try
								{
								open_win=true;
								save_win=false;
								menuadd.Open();
								}
								catch(Exception e1)
								{}
							open_win=false;
							save_win=true;
							}
						}
						if(tags[0].equals("save"))
						{
							if(open_win==false || save_win==true)
							{
								try
								{
								open_win=true;
								save_win=false;
								menuadd.Save();
								}
								catch(Exception e2)
								{}
							open_win=false;
							save_win=true;		
							}
						}
						if(tags[0].equals("close"))
						{
							try
							{
							menuadd.Close();
							}
							catch(Exception e2)
							{
							e2.printStackTrace();
							}
						}
						if(tags[0].equals("exit"))
						{
						menuadd.Exit();
						}
						if(tags[0].equals("undo"))
						{
						menuadd.Undo();
						}
						if(tags[0].equals("redo"))
						{
						menuadd.Redo();
						}
						if(tags[0].equals("copy"))
						{
						area.copy();
						//String str=(JTextArea)area.getSelectedText();
						}
						if(tags[0].equals("cut"))
						{
							try
							{
								/*
								String str=(JTextArea)area.getSelectedText();
								int i=(JTextArea)area.getText().indexOf(str);
								(JTextArea)area.replaceRange(" ",i,i+str.length());
								*/
							area.cut();
							}
							catch(Exception e3)
							{
							e3.printStackTrace();
							}
						}
						if(tags[0].equals("paste"))
						{
						area.paste();
							//int pos1=(JTextArea)area.getCaretPosition();
							//(JTextArea)area.insert(str,pos1);
						}
						if(tags[0].equals("select all"))
						{
						area.selectAll();
						}
						if(tags[0].equals("find"))
						{
							try
							{
							FindMethod(0);
							Dimension d1 = dialog.getSize();
        							Dimension d2 = menuadd.getSize();
        							int x = Math.max((d2.width-d1.width)/2, 0);
        							int y = Math.max((d2.height-d1.height)/2, 0);
        							dialog.setBounds(x + menuadd.getX(),y + menuadd.getY(), d1.width, d1.height);
        							dialog.setVisible(true);
							}
							catch(Exception ex)
							{}
						}
						if(dialog.isVisible())
						{
							if(tags[0].equals("find next"))
							{
								try
								{
								findNext(false, true);
								}
								catch(Exception fe)
								{}	
							}
							if(tags[0].equals("replace next"))
							{
								try
								{
								int counter = 0;
        									while (true) 
									{
          									int result1 = findNext(true, false);
          										if (result1 < 0)    
            										return;
          										else if (result1 == 0)
            										break;
          									counter++;
									}
        								JOptionPane.showMessageDialog(null, counter+" replacement(s) have been done", "Info",JOptionPane.INFORMATION_MESSAGE);
								}
								catch(Exception fe)
								{}	
							}
						}
						if(tags[0].equals("replace"))
						{
							try
							{
							FindMethod(1);
							Dimension d1 = dialog.getSize();
        							Dimension d2 = menuadd.getSize();
        							int x = Math.max((d2.width-d1.width)/2, 0);
        							int y = Math.max((d2.height-d1.height)/2, 0);
        							dialog.setBounds(x + menuadd.getX(),y + menuadd.getY(), d1.width, d1.height);
        							dialog.setVisible(true);
							}
							catch(Exception re)
							{}
						}
						if(tags[0].equals("font arial"))
						{
						area.setFont(new Font("arial",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font arial black"))
						{
						area.setFont(new Font("arial black",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font arial narrow"))
						{
						area.setFont(new Font("arial narrow",menuadd.font_style,menuadd.font_size_i));
						}
 						if(tags[0].equals("font book antiqua"))
						{
						area.setFont(new Font("book antiqua",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font bookman old style"))
						{
						area.setFont(new Font("bookman old style",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font courier"))
						{
						area.setFont(new Font("courier",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font courier new"))
						{
						area.setFont(new Font("courier new",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font monotype corsiva"))
						{
						area.setFont(new Font("monotype corsiva",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font times new roman"))
						{
						area.setFont(new Font("times new roman",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("font verdana"))
						{
						area.setFont(new Font("verdana",menuadd.font_style,menuadd.font_size_i));
						}
						if(tags[0].equals("style bold"))
						{
						area.setFont(new Font(menuadd.font_name_i,1,menuadd.font_size_i));
						}
						if(tags[0].equals("style italic"))
						{
						area.setFont(new Font(menuadd.font_name_i,2,menuadd.font_size_i));
						}
						if(tags[0].equals("size 10"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,10));
						}
						if(tags[0].equals("size 12"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,12));
						}
						if(tags[0].equals("size 18"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,18));
						}
						if(tags[0].equals("size 20"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,20));
						}
						if(tags[0].equals("size 22"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,22));
						}
						if(tags[0].equals("size 32"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,32));
						}
						if(tags[0].equals("size 36"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,36));
						}
						if(tags[0].equals("size 46"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,46));
						}
						if(tags[0].equals("size 52"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,52));
						}
						if(tags[0].equals("size 60"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,60));
						}
						if(tags[0].equals("size 72"))
						{
						area.setFont(new Font(menuadd.font_name_i,menuadd.font_style,72));
						}
						if(tags[0].equals("black"))
						{
						area.setForeground(Color.black);
						}
						if(tags[0].equals("green"))
						{
						area.setForeground(Color.green);
						}
						if(tags[0].equals("blue"))
						{
						area.setForeground(Color.blue);
						}
						if(tags[0].equals("yellow"))
						{
						area.setForeground(Color.yellow);
						}
						if(tags[0].equals("red"))
						{
						area.setForeground(Color.red);
						}
						if(tags[0].equals("play"))
						{
							try
							{
							menuadd.SpeakMethod();
							} 
							catch(Exception pe)
							{
							pe.printStackTrace();
							}
						}
						if(tags[0].equals("stop"))
						{
							try
							{
							synth.cancel();
							}
							catch(ArrayIndexOutOfBoundsException ar)
							{
							System.out.println(ar);
							}
						}
						if(tags[0].equals("calculator"))
						{
							try
							{
								if(cal_win==false)
								{
								cal_win=true;
								Sample calframe = new Sample();
								calframe.setSize(200,200);
								calframe.setTitle("Calculator");
								calframe.setResizable(false);
								calframe.show();
								}
							}
							catch(Exception e4)
							{}
						}
						
						
						if(tags[0].equals("shutdown"))
						{ 
						System.out.println("shutdown");
							/*
							try
							{
							menuadd.ShutDown();
							}
							catch(Exception se)
							{}
							*/
						}
						if(tags[0].equals("restart"))
						{ 
						System.out.println("restart");
							/*
							try
							{
							menuadd.Restart();
							}
							catch(Exception ex)
							{}
							*/
						}
						if(tags[0].equals("logoff"))
						{
						System.out.println("logoff");
							/*
							try
							{
							menuadd.LogOff();
							}
							catch(Exception ex)
							{}
							*/
						 }
					}
					catch(Exception ef)
					{
					ef.printStackTrace();
					}
				}
			}
			catch(Exception e1)
			{
			e1.printStackTrace();
			}
		}
	};
	// It allocate recognizer and sythesizer engine.
	public RecognizerMain()
	{
		try
		{
			try
			{
			rec = Central.createRecognizer(new EngineModeDesc(Locale.ENGLISH));
			}
			catch(Exception rc)
			{
			rc.printStackTrace();
			}
			try
			{
			SynthesizerModeDesc desc = null;
	    		synth = Central.createSynthesizer(desc);
	    		((com.cloudgarden.speech.CGEngineProperties)
			synth.getSynthesizerProperties()).setEventsInNewThread(false);
	    		synth.allocate();
	    		synth.resume();
	   	 	synth.waitEngineState(Synthesizer.ALLOCATED);
			Voice v = null;
	    		if(v == null) new Voice(null,Voice.GENDER_FEMALE, Voice.AGE_DONT_CARE, null);
	    		System.out.println("Using voice "+v);
	    		SynthesizerProperties props = synth.getSynthesizerProperties();
	    		props.setVoice(v);
	    		props.setVolume(1.0f);
	    		props.setSpeakingRate(100.0f);
			} 
			catch(Exception e) 
			{
	    		e.printStackTrace(System.out);
			} 
			catch(Error e1) {
	    		e1.printStackTrace(System.out);
			}
	    		
			
		menuadd = new MenuAdd();
		menuadd.addWindowListener(new WindowAdapter()
			{
				public void windowActivated(WindowEvent e)
				{
					String lnfName = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";   
					try	
					{	
					UIManager.setLookAndFeel(lnfName);				
					SwingUtilities.updateComponentTreeUI(menuadd);
					}
					catch (Exception exc) 
					{}   	
				}

				// it deallocates the synthesizer and recognizer engine and exits. 
				public void windowClosing(WindowEvent e)
				{
					try	
					{	
					menuadd.Exit();
					rec.deallocate();
					synth.deallocate();
					}
					catch (Exception exc) 
					{}   	
				}
			});
		//it shows the menuadd frame.
		SetDisable_JTextPane();
		titledborder = new TitledBorder(BorderFactory.createLoweredBevelBorder(),"");
		scrollpane.setBorder(titledborder);
		menuadd.getContentPane().add(scrollpane,BorderLayout.CENTER);
		menuadd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuadd.show();
		menuadd.addUndoRedo(area);
		}
		catch(Exception ec)
		{
		ec.printStackTrace();
		}
	}
	// It gives the find and replace dialog box
	
	static void FindMethod(int index1)
	{
	dialog = new JDialog();
	dialog.setTitle("Find And Replace");
	
	tb = new JTabbedPane();
    	JPanel p1 = new JPanel(new BorderLayout());
    	JPanel pc1 = new JPanel(new BorderLayout());

    	JPanel pf = new JPanel();
    	//pf.setLayout(new DialogLayout(20, 5));
    	pf.setBorder(new EmptyBorder(8, 5, 8, 0));
    	pf.add(new JLabel("Find what:"));
        
    	txtFind1 = new JTextField();
    	docFind = txtFind1.getDocument();
    	pf.add(txtFind1);
    	pc1.add(pf, BorderLayout.CENTER);

    	JPanel po = new JPanel(new GridLayout(2, 2, 8, 2));
    	po.setBorder(new TitledBorder(new EtchedBorder(), "Options"));

    	JCheckBox chkWord = new JCheckBox("Whole words only");
    	chkWord.setMnemonic('w');
    	modelWord = chkWord.getModel();
    	po.add(chkWord);

    	ButtonGroup bg = new ButtonGroup();
    	JRadioButton rdUp = new JRadioButton("Search up");
    	rdUp.setMnemonic('u');
    	modelUp = rdUp.getModel();
    	bg.add(rdUp);
    	po.add(rdUp);

    	JCheckBox chkCase = new JCheckBox("Match case");
    	chkCase.setMnemonic('c');
    	modelCase = chkCase.getModel();
    	po.add(chkCase);

    	JRadioButton rdDown = new JRadioButton("Search down", true);
    	rdDown.setMnemonic('d');
    	modelDown = rdDown.getModel();
    	bg.add(rdDown);
    	po.add(rdDown);
    	pc1.add(po, BorderLayout.SOUTH);

    	p1.add(pc1, BorderLayout.CENTER);

    	JPanel p01 = new JPanel(new FlowLayout());
    	JPanel p = new JPanel(new GridLayout(2, 1, 2, 8));

    		ActionListener findAction = new ActionListener() 
		{ 
      			public void actionPerformed(ActionEvent e) 
			{
        			findNext(false, true);
      			}
    		};
    	JButton btFind = new JButton("Find Next");
    	btFind.addActionListener(findAction);
    	btFind.setMnemonic('f');
    	p.add(btFind);

    		ActionListener closeAction = new ActionListener() 
		{ 
      			public void actionPerformed(ActionEvent e) 
			{
        			dialog.setVisible(false);
      			}
    		};
    	JButton btClose = new JButton("Close");
    	btClose.addActionListener(closeAction);
    	btClose.setDefaultCapable(true);
    	p.add(btClose);
    	p01.add(p);
    	p1.add(p01, BorderLayout.EAST);

    	tb.addTab("Find", p1);

	// "Replace" panel
    	JPanel p2 = new JPanel(new BorderLayout());

    	JPanel pc2 = new JPanel(new BorderLayout());

    	JPanel pc = new JPanel();
    	//pc.setLayout(new DialogLayout(20, 5));
    	pc.setBorder(new EmptyBorder(8, 5, 8, 0));
        
    	pc.add(new JLabel("Find what:"));
    	txtFind2 = new JTextField();
    	txtFind2.setDocument(docFind);
    	pc.add(txtFind2);

    	pc.add(new JLabel("Replace:"));
    	JTextField txtReplace = new JTextField();
    	docReplace = txtReplace.getDocument();
    	pc.add(txtReplace);
    	pc2.add(pc, BorderLayout.CENTER);

    	po = new JPanel(new GridLayout(2, 2, 8, 2));
    	po.setBorder(new TitledBorder(new EtchedBorder(), "Options"));

    	chkWord = new JCheckBox("Whole words only");
    	chkWord.setMnemonic('w');
    	chkWord.setModel(modelWord);
    	po.add(chkWord);

    	bg = new ButtonGroup();
    	rdUp = new JRadioButton("Search up");
    	rdUp.setMnemonic('u');
    	rdUp.setModel(modelUp);
    	bg.add(rdUp);
    	po.add(rdUp);

    	chkCase = new JCheckBox("Match case");
    	chkCase.setMnemonic('c');
    	chkCase.setModel(modelCase);
    	po.add(chkCase);

    	rdDown = new JRadioButton("Search down", true);
    	rdDown.setMnemonic('d');
    	rdDown.setModel(modelDown);
    	bg.add(rdDown);
    	po.add(rdDown);
    	pc2.add(po, BorderLayout.SOUTH);

    	p2.add(pc2, BorderLayout.CENTER);

    	JPanel p02 = new JPanel(new FlowLayout());
    	p = new JPanel(new GridLayout(3, 1, 2, 8));

    		ActionListener replaceAction = new ActionListener() 
		{ 
      			public void actionPerformed(ActionEvent e) 
			{
        			findNext(true, true);
      			}
    		};
    	JButton btReplace = new JButton("Replace");
    	btReplace.addActionListener(replaceAction);
    	btReplace.setMnemonic('r');
    	p.add(btReplace);

    		ActionListener replaceAllAction = new ActionListener() 
		{ 
      			public void actionPerformed(ActionEvent e) 
			{
			
        			int counter = 0;
        				while (true) 
				{
          				int result = findNext(true, false);
          					if (result < 0)    // error
            						return;
          					else if (result == 0)    // no more
            				break;
          				counter++;
        				}
        		JOptionPane.showMessageDialog(null, counter+" replacement(s) have been done", "Info",JOptionPane.INFORMATION_MESSAGE);
			
      			}
    		};
    	JButton btReplaceAll = new JButton("Replace All");
    	btReplaceAll.addActionListener(replaceAllAction);
    	btReplaceAll.setMnemonic('a');
    	p.add(btReplaceAll);

    	btClose = new JButton("Close");
    	btClose.addActionListener(closeAction);
    	btClose.setDefaultCapable(true);
    	p.add(btClose);
    	p02.add(p);
    	p2.add(p02, BorderLayout.EAST);
        
    	// Make button columns the same size
    	p01.setPreferredSize(p02.getPreferredSize());

    	tb.addTab("Replace", p2);

    	tb.setSelectedIndex(index1);

	dialog.getContentPane().add(tb, BorderLayout.CENTER);

    		 WindowListener flst = new WindowAdapter() 
		{ 
      			public void windowActivated(WindowEvent e) 
			{
        			searchIndex = -1;
        				if (tb.getSelectedIndex()==0)
          					txtFind1.grabFocus();
        				else
          					txtFind2.grabFocus();
      			}

      			public void windowDeactivated(WindowEvent e) 
			{
        			searchData = null;
      			}
    		};
    	dialog.addWindowListener(flst);

	// Make button columns the same size
    	//p01.setPreferredSize(50);
	
   	dialog.pack();
     	dialog.setResizable(false);
  	}

	// Give find or replace dialog box, from index.
	public void setSelectedIndex(int index) 
	{
    	tb.setSelectedIndex(index);
    	dialog.setVisible(true);
    	searchIndex = -1;
	}

	// find or replace the word form the given file.
	static int findNext(boolean doReplace, boolean showWarnings) 
	{
		try
		{
	    	//JTextArea monitor = recogmain.area;
		int pos = area.getCaretPosition();
			if (modelUp.isSelected() != searchUp) 
			{
	      		searchUp = modelUp.isSelected();
	      		searchIndex = -1;
	    		}
			if (searchIndex == -1) 
			{
      				try 
				{
        				Document doc = area.getDocument();
					if (searchUp)
						searchData = doc.getText(0, pos);
					else
						searchData =doc.getText(pos, doc.getLength()-pos);
						
        				searchIndex = pos;
      				}
      				catch (BadLocationException ex) 
				{
	        			warning(ex.toString());
        				return -1;
      				}
    			}
		String key = "";
    			try { key = docFind.getText(0, docFind.getLength()); }
    			catch (BadLocationException ex) {}
    		
			if (key.length()==0) 
			{
	      		warning("Please enter the target to search");
      			return -1;
    			}
	    		if (!modelCase.isSelected()) 
			{
      			searchData = searchData.toLowerCase();
	      		key = key.toLowerCase();
    			}
    			if (modelWord.isSelected()) 
			{
      				for (int k=0; k<Utils.WORD_SEPARATORS.length; k++) 
				{
        					if (key.indexOf(Utils.WORD_SEPARATORS[k]) >= 0) 
					{
	          				warning("The text target contains an illegal "+"character \'"+Utils.WORD_SEPARATORS[k]+"\'");
          					return -1;
        					}
      				}
			}
		
    		String replacement = "";
	    		if (doReplace) 
			{
      				try 
				{
	        			replacement = docReplace.getText(0, docReplace.getLength());
      				} 
				catch (BadLocationException ex) {}
    			}
		int xStart = -1;
    		int xFinish = -1;
    			while (true) 
    			{
      				if (searchUp)
        				xStart = searchData.lastIndexOf(key, pos-1);
      				else
        				xStart = searchData.indexOf(key, pos-searchIndex);
      				if (xStart < 0) 
				{
        					if (showWarnings)
	          				warning("Text not found");
        				return 0;
      				}
		      		xFinish = xStart+key.length();
			
      				if (modelWord.isSelected()) 
				{
        				boolean s1 = xStart>0;
		        		boolean b1 = s1 && !Utils.isSeparator(searchData.charAt(xStart-1));
		        		boolean s2 = xFinish<searchData.length();
        				boolean b2 = s2 && !Utils.isSeparator(searchData.charAt(xFinish));
                    		
        					if (b1 || b2)    // Not a whole word
        					{
		          				if (searchUp && s1)    // Can continue up
          						{
            						pos = xStart;
		            				continue;
          						}
          						if (!searchUp && s2)    // Can continue down
          						{
		            				pos = xFinish;
            						continue;
          						}
			          		// Found, but not a whole word, and we cannot continue
          						if (showWarnings)
			            				warning("Text not found");
					return 0;
        					}
      				}
      			break;
    			}
			if (!searchUp) 
			{
      			xStart += searchIndex;
      			xFinish += searchIndex;
    			}
    			if (doReplace) 
			{
      			setSelection(xStart, xFinish, searchUp);
	      		area.replaceSelection(replacement);
      			setSelection(xStart, xStart+replacement.length(), searchUp);
      			searchIndex = -1;
    			}
    			else
	      		setSelection(xStart, xFinish, searchUp);
		}
		catch(Exception e1)
		{}
	return 1;
	}

	//it show sthe mesage box when search word.
	static void warning(String message) 
	{
    	JOptionPane.showMessageDialog(null,message, "Warning", JOptionPane.INFORMATION_MESSAGE);
  	}
	
	// It shows the find and replace word to be selected.
	static  void setSelection(int xStart, int xFinish, boolean moveUp) 
	{
    		if (moveUp) 
		{
      		area.setCaretPosition(xFinish);
      		area.moveCaretPosition(xStart);
    		}
    		else
      		area.select(xStart, xFinish);
		
    	m_xStart = area.getSelectionStart();
    	m_xFinish = area.getSelectionEnd();
  	}
	
	//The following code shows how to create a recognizer, load the grammar.
	static void MicroOn()
	{
		try
		{
		rec.allocate();
		FileReader reader = new FileReader("c:\\numbers.gram");
		RuleGrammar gram = rec.loadJSGF(reader);
		gram.setEnabled(true);
		rec.addResultListener(resultlistener);
		rec.commitChanges();
		rec.requestFocus();
		rec.resume();
		}
		catch(Exception mn)
		{
		System.out.println(mn);
		}
	}

	// it deallocates the engine.
	static void MicroOff()
	{
		try
		{
		rec.deallocate();
		}
		catch(Exception mo)
		{
		System.out.println(mo);
		}
	}
	
	// formate of textpane when enable.
	public void SetEnable_JTextPane()
	{
		//area.setFont(new java.awt.Font("Tahoma", 0, 60));
		area.setBackground(Color.white);
		area.setEnabled(true);
		area.requestFocus();
	}
	// formate of textpane when disable.
	public void SetDisable_JTextPane()
	{
		area.setBackground(Color.gray);
		area.setEnabled(false);
	}
	//Main function of application.
	public static void main(String arg[])
	{
	frame = new RecognizerMain();
	}	
}