import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class AppInterface implements ActionListener{
	JFrame frame;
    JLabel label1;
    JLabel helpLabel;
    JLabel imageLabel;
    JLabel waveformLabel;
    JPanel utilPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    JPanel imagePanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem;
    Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
    SoundRecordingUtil fretPractice = new SoundRecordingUtil();
    SoundRecordingUtil noteIDUtil = new SoundRecordingUtil();
    SoundRecordingUtil mixerTester = new SoundRecordingUtil();
    WavRun wavRun = new WavRun(); 
    byte[] wavBytes = new byte[4410];
    boolean busy = false;
    boolean wavThreadStatus = true;
    public boolean runningRecord = false;
    public boolean runningTimer = false;
    int mixerChoice = 100;

    FlowLayout experimentLayout = new FlowLayout();
    JButton fretButton = new JButton("Start fretboard practice");
    JButton tuneButton = new JButton("Tune");
    JButton startButton = new JButton("Start note recognition");
	
    public static void main(String[] args) {
    	try {
  	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  	    }
  	    catch (Exception e) {
  	      e.printStackTrace();
  	    }
  	    new SplashScreenMain();
  	    
        AppInterface gui = new AppInterface();
        gui.go();
    }
 
    /**
     * Declare and initialize GUI components
     */
    @SuppressWarnings("serial")
	public void go(){
        
    	frame = new JFrame("Resonance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        menuBar = new JMenuBar();
        menu = new JMenu("Mixers");
    	menu.setMnemonic(KeyEvent.VK_A);
    	menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
       
        startButton.addActionListener(new StartListener());
        tuneButton.addActionListener(new TunerListener());
        fretButton.addActionListener(new FretListener());
        
        label1 = new JLabel();
        		label1.setHorizontalAlignment(SwingConstants.CENTER);;
        		label1.setVerticalAlignment(SwingConstants.CENTER);
        		Font bigFont = new Font("sansserif", Font.PLAIN,22);
        		label1.setFont(bigFont);
        	    label1.setText("");
        	
        helpLabel = new JLabel();
        		Help output = new Help();
        		String labelOutput = output.getHelp();
        		helpLabel.setHorizontalAlignment(SwingConstants.LEFT);
        		helpLabel.setText(labelOutput);
        		Font medFont = new Font("sansserif",Font.PLAIN,12);
        		helpLabel.setFont(medFont); 
        		
        		JScrollPane helpScrollPane = new JScrollPane(helpLabel);
                helpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                helpScrollPane.setPreferredSize(new Dimension(250, 250));
                helpScrollPane.setPreferredSize(new Dimension(250, 250));
                helpScrollPane.setBorder(
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                        BorderFactory.createTitledBorder("Welcome to Resonance"),
                                        BorderFactory.createEmptyBorder(5,5,5,5)),
                        helpScrollPane.getBorder()));
                
        ImageIcon testImage = new ImageIcon("welcomeScreen1.png"); 
        imageLabel = new JLabel(); 
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setIcon(testImage); 
        
    	ImageIcon waveformImage = new ImageIcon("hbvFL.png");
    	waveformLabel = new JLabel() {
              public void paint(Graphics g) {
                g.setColor(Color.black);
                /*g.fillRect(0, 0, waveformLabel.getWidth(), waveformLabel.getHeight());
                g.setColor(Color.red);*/
                for (int x = 0; x < waveformLabel.getWidth(); x++) {
                	g.fillOval(x, waveformLabel.getHeight()/2 + wavBytes[x], 5, 5);
                	//System.out.print(wav.wavBuffer[x] + " ");
                }
              }
    	};
    	waveformLabel.setIcon(waveformImage);
    		
        // add buttons and labels to panels 
        utilPanel.add(startButton);
        utilPanel.add(tuneButton);
        utilPanel.add(fretButton);
       
        //outputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        //outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        //outputPanel.add(label1);
        
        imagePanel.setLayout(new GridLayout(2,1));
        imagePanel.add(imageLabel);
        imagePanel.add(label1);
        
        eastPanel.setLayout(new GridLayout(2,1));
        //eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        eastPanel.add(waveformLabel);
        eastPanel.add(helpScrollPane);
        
        // add panels to frame
        frame.getContentPane().add(BorderLayout.NORTH, utilPanel);
        //frame.getContentPane().add(BorderLayout.SOUTH, outputPanel);
        frame.getContentPane().add(BorderLayout.CENTER, imagePanel);
        frame.getContentPane().add(BorderLayout.EAST, eastPanel);
        frame.add(new JSeparator(), BorderLayout.SOUTH);
        
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem []buttons = new JRadioButtonMenuItem[mixerInfo.length];
        for (int i = 0; i < mixerInfo.length; i++) {
        	buttons[i] = new JRadioButtonMenuItem(i + ". " + mixerInfo[i]);
        	group.add(buttons[i]);
        	buttons[i].setSelected(true);
        	buttons[i].setMnemonic(KeyEvent.VK_M); //mixer
        		
        	buttons[i].addActionListener(this);
        	menu.add(buttons[i]);
        }
        menuBar.add(menu);
        
        outputPanel.add(new JSeparator(), BorderLayout.SOUTH);
        frame.setJMenuBar(menuBar);
        frame.setSize(1500, 1000);
        frame.setVisible(true);
    }
 
    /**
     * Action listeners respond to button events
     */
    public class StartListener implements ActionListener {
    	ImageTimer timer;
		boolean continueOutput = true;
	    public void actionPerformed(ActionEvent event){
	    	if (busy && startButton.getText().equals("Start note recognition")) {
	    		label1.setText("Busy");
	    	}
	    	else if (mixerChoice == 100) {
	    		label1.setText("Select a mixer to begin.");
	    	}
	    	else if (noteIDUtil.testMixer(AudioSystem.getMixer(mixerInfo[mixerChoice]))) {
	    		label1.setText("Mixer not supported. Select a different mixer and try again.");
	    	}
	    	
	    	else {
	    		//call record audio method
	    		 
	    		noteIDUtil.setMixerChoice(mixerChoice);
	    		Thread noteIDThread = new Thread(new Runnable() {
	    			public void run() {
	    				try {
	    					noteIDUtil.start();
	    					
	    				} catch (LineUnavailableException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    				}
	    			}
	    		});
	    		Thread noteIDOutput = new Thread(new Runnable() {
	    			public void run() {
	    				//System.out.println(continueOutput);
	    				while (continueOutput) {
	    					try {
	    						if (noteIDUtil.currentNote.equalsIgnoreCase(timer.icon.toString().substring(0, 1))) {
	    							label1.setText("Note Detected: " + noteIDUtil.currentNote + " CORRECT");
	    						}
	    						else {
	    							label1.setText("Note Detected: " + noteIDUtil.currentNote + " INCORRECT");
	    						}
	    					} catch (NullPointerException ex) {
	    						ex.printStackTrace();
	    					}
	    				}
	    			}
	    		});
	    		if (startButton.getText().equals("Start note recognition")) {
	    			noteIDThread.start();
	    			imageLabel.setVisible(true);
	    			timer = new ImageTimer();
	    			busy = true;
	    			label1.setHorizontalAlignment(SwingConstants.CENTER);
	    			label1.setVerticalAlignment(SwingConstants.CENTER);
	    		                         
	    			timer.keepGoing = true;
	    			timer.start();
	    			startButton.setText("Stop");
	    			continueOutput = true;
	    			wavBytes = noteIDUtil.output;
	    			
	    			Thread wavThread = new Thread(wavRun);
					wavThreadStatus = true;
	    			wavThread.start();
	    			noteIDOutput.start();
	    		}
	    		else if (startButton.getText().equals("Stop")) {
	    			try {
	    				noteIDUtil.stop();
	    				busy = false;
	    				continueOutput = false;
	    				timer.keepGoing = false;
	    				wavThreadStatus = false;
	    				imageLabel.setVisible(false);
	    				label1.setText("");
	    				//System.out.println(timer.isAlive());
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    			startButton.setText("Start note recognition");
	    		}
	    	}
	    }
    }
    
    class ImageTimer extends Thread {
    	boolean keepGoing = true;
    	ImageIcon icon;
	    public void run() {
	    	while (keepGoing) {
	           
	    		Prompt imageOutput = new Prompt();
	    		icon = imageOutput.ImagePrompt();
	    		imageLabel.setVisible(true);
	    		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    		imageLabel.setVerticalAlignment(SwingConstants.CENTER);
	    		imageLabel.setIcon(icon);
	    		try {
	    			Thread.sleep(3000);
	    		}
	    		catch (InterruptedException e) {
	    		}
	    	}
	    }
	}
    
    class FretImageTimer extends Thread {
    	boolean keepGoing = true;
    	ImageIcon icon;
	    public void run() {
	    	while (keepGoing) {
	           
	    		Prompt imageOutput = new Prompt();
	    		icon = imageOutput.FretImagePrompt();
	    		imageLabel.setVisible(true);
	    		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    		imageLabel.setVerticalAlignment(SwingConstants.CENTER);
	    		imageLabel.setIcon(icon);
	    		try {
	    			Thread.sleep(3000);
	    		}
	    		catch (InterruptedException e) {
	    		}
	    	}
	    }
	}
    
    class TunerListener implements ActionListener {
    	FretImageTimer timer = new FretImageTimer();
       	SoundRecordingUtil tuner = new SoundRecordingUtil();
       	boolean continueTune = true;
       	String[] notes = {"Low E String","A String","D String","G String","B String","High E String"};
       	String[] strings = {"EString.png","AString.png","DString.png","GString.png","BString.png","HighEString.png"};
       	
       	public void actionPerformed(ActionEvent event) {
       		if (busy && tuneButton.getText().equals("Tune")) {
       			label1.setText("Busy");
       		}
       		else if (mixerChoice == 100) {
       			label1.setText("Select a mixer to begin.");
       		}
       		else if (tuner.testMixer(AudioSystem.getMixer(mixerInfo[mixerChoice]))) {
        		label1.setText("Mixer not supported. Select a different mixer and try again.");
       		}
       		
       		else {
	       		tuner.setMixerChoice(mixerChoice);
	       		Thread tuneThread = new Thread(new Runnable() {
	       			public void run() {
	       				tuner.runTuner();
	    	    	}
	    	    });
	       		Thread tuneOutput = new Thread(new Runnable() {
	       			public void run() {
	       				while (continueTune) {
	       					if (tuner.StringNo <= 5)
	       						label1.setText(notes[tuner.StringNo] + " " + tuner.currentNote);
	       				}
	       			}
	       		});
	       		if (tuneButton.getText().equals("Tune")) {
	       			tuneThread.start();
	       			busy = true;
	       			tuneButton.setText("Next String");
	       			continueTune = true;
	       			imageLabel.setVisible(true);
	       			ImageIcon icon = new ImageIcon(strings[tuner.StringNo]);
	       			imageLabel.setIcon(icon);
	       			wavBytes = tuner.output;
       				wavThreadStatus = true;
       				Thread wavThread = new Thread(wavRun);
	       			wavThread.start();
	       			tuneOutput.start();
	       		}
	       		else {
	       			tuner.StringNo++;
	       			if (tuner.StringNo <= 5) {
	       				ImageIcon icon = new ImageIcon(strings[tuner.StringNo]);
	       				imageLabel.setIcon(icon);
	       			}
	       		}
	       		if (tuner.StringNo > 5) {
	    			try {
	    				tuner.stop();
	    				busy = false;
	    				continueTune = false;
	    				wavThreadStatus = false;
	    				label1.setText("");
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    			tuneButton.setText("Tune");
	    			tuner.StringNo = 0;
	    			imageLabel.setVisible(false);
	       		}  	
       		}
       	}
    }
    class FretListener implements ActionListener {
    	FretImageTimer timer;
    	boolean continueOutput = true;
        public void actionPerformed(ActionEvent event){
        	if (busy && fretButton.getText().equals("Start fretboard practice")) {
        		label1.setText("Busy");
        	}
        	else if (mixerChoice == 100) {
        		label1.setText("Select a mixer to begin.");
        	}
        	else if (noteIDUtil.testMixer(AudioSystem.getMixer(mixerInfo[mixerChoice]))) {
        		label1.setText("Mixer not supported. Select a different mixer and try again.");
        	}
        	
        	else {
        		//call record audio method
        		 
        		noteIDUtil.setMixerChoice(mixerChoice);
        		Thread noteIDThread = new Thread(new Runnable() {
        			public void run() {
        				try {
        					noteIDUtil.start();
        					
        				} catch (LineUnavailableException e) {
        					// TODO Auto-generated catch block
        					e.printStackTrace();
        				}
        			}
        		});
        		Thread noteIDOutput = new Thread(new Runnable() {
        			public void run() {
        				//System.out.println(continueOutput);
        				while (continueOutput) {
        					try {
        						if (noteIDUtil.currentNote.equalsIgnoreCase(timer.icon.toString().substring(timer.icon.toString().length()-5, timer.icon.toString().length()-4)) || noteIDUtil.currentNote.equalsIgnoreCase(timer.icon.toString().substring(timer.icon.toString().length()-6, timer.icon.toString().length()-4)))
        							label1.setText("Note Detected: " + noteIDUtil.currentNote + "CORRECT");
        						else
        							label1.setText("Note Detected: " + noteIDUtil.currentNote + " INCORRECT");
        					} catch (NullPointerException ex) {
        						ex.printStackTrace();
        					}
        				}
        			}
        		});
        		if (fretButton.getText().equals("Start fretboard practice")) {
        			noteIDThread.start();
        			imageLabel.setVisible(true);
        			timer = new FretImageTimer();
        			busy = true;
        			//label1.setText("Play: ");
        			label1.setHorizontalAlignment(SwingConstants.CENTER);
        			label1.setVerticalAlignment(SwingConstants.CENTER);
        		                         
        			timer.keepGoing = true;
        			timer.start();
        			fretButton.setText("Stop");
        			continueOutput = true;
        			wavBytes = noteIDUtil.output;
					wavThreadStatus = true;
					Thread wavThread = new Thread(wavRun);
        			wavThread.start();
        			noteIDOutput.start();
        		}
        		else if (fretButton.getText().equals("Stop")) {
        			try {
        				noteIDUtil.stop();
        				busy = false;
        				continueOutput = false;
        				timer.keepGoing = false;
        				wavThreadStatus = false;
        				imageLabel.setVisible(false);
        				label1.setText("");
        				//System.out.println(timer.isAlive());
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			fretButton.setText("Start fretboard practice");
        		}
        	}
        }
    }
    

	/**
     * Get mixer choice from user
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		//JButton buttonPressed = (JButton)e.getSource(); 
		JRadioButtonMenuItem buttonPressed = (JRadioButtonMenuItem)e.getSource();
		buttonPressed.setVisible(true);
		if (!busy) {
			mixerChoice = Integer.parseInt(buttonPressed.getText().substring(0,1));
				if (mixerTester.testMixer(AudioSystem.getMixer(mixerInfo[mixerChoice])))
					label1.setText("Mixer not supported. Select a different mixer");
				else
					label1.setText(buttonPressed.getText() + " selected. Ready to record.");
		}
		else
			label1.setText("Busy");
	}
	public class WavRun implements Runnable {
    	public void run() {
    		while (wavThreadStatus) {
    			waveformLabel.repaint();
    		}
    	}
    }
}