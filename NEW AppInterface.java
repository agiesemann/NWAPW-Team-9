import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AppInterface implements ActionListener{
	JFrame frame;
    JLabel label1;
    JLabel label2;
    JLabel helpLabel;
    JLabel imageLabel;
    JPanel utilPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    JPanel imagePanel = new JPanel();
    JPanel eastPanel = new JPanel();
    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    JRadioButtonMenuItem rbMenuItem;
    Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
    int mixerChoice = 100;
    boolean busy = false;
    public boolean runningRecord = false;
    public boolean runningTimer = false;

    
    JButton tuneButton = new JButton("Tune");
    JButton startButton = new JButton("Start note recognition program");
	
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
    public void go(){
        
    	frame = new JFrame("Resonance");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    menuBar = new JMenuBar();
    menu = new JMenu("Mixers");
    		menu.setMnemonic(KeyEvent.VK_A);
    		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");

       
        startButton.addActionListener(new StartListener());
 
    JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetListener());
        
        tuneButton.addActionListener(new TunerListener());
 
        label1 = new JLabel();
        		label1.setHorizontalAlignment(SwingConstants.CENTER);
        		label1.setVerticalAlignment(SwingConstants.CENTER);
        		Font medFont = new Font("sansserif", Font.PLAIN,20);
        		//label1.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        		label1.setFont(medFont);
        	    label1.setText(""); // TEST
        label2 = new JLabel();
        	
        helpLabel = new JLabel();
        		Help output = new Help();
        		String labelOutput = output.getHelp();
        		helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        		helpLabel.setVerticalAlignment(SwingConstants.CENTER);
        		helpLabel.setText(labelOutput);
        		Font bigFont = new Font("sansserif",Font.PLAIN,12);
        		helpLabel.setFont(bigFont); 
        	
        ImageIcon testImage = new ImageIcon("welcomeScreen1.png"); 
        imageLabel = new JLabel(); 
        imageLabel.setIcon(testImage); 
    	
        // add buttons and labels to panels 
        utilPanel.add(startButton);
        utilPanel.add(resetButton);
        utilPanel.add(tuneButton);
       
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(label1);
        
        //Dimension minSize = new Dimension(5, 50);
        //Dimension prefSize = new Dimension(5, 50);
       // Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
        //outputPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        outputPanel.add(label2);
        //outputPanel.setVgap(10);
        imagePanel.add(imageLabel);
        
        eastPanel.add(helpLabel);
        
        // add panels to frame
        frame.getContentPane().add(BorderLayout.NORTH, utilPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, outputPanel);
        frame.getContentPane().add(BorderLayout.CENTER, imagePanel);
        frame.getContentPane().add(BorderLayout.EAST, eastPanel);
        
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
        
        frame.setJMenuBar(menuBar);
        frame.setSize(1500, 500);
        frame.setVisible(true);
    }
 
    /**
     * Action listeners respond to button events
     */
    public class StartListener implements ActionListener{
    	SoundRecordingUtil noteIDUtil = new SoundRecordingUtil();
    	boolean continueOutput = true;
        public void actionPerformed(ActionEvent event){
        	if (busy && startButton.getText().equals("Start note recognition program")) {
        		label1.setText("Busy");
        	}
        	else if (mixerChoice == 100) {
        		label1.setText("Select a mixer to begin.");
        		label2.setText(" ");
        	}
        	else if (noteIDUtil.testMixer(AudioSystem.getMixer(mixerInfo[mixerChoice]))) {
        		label1.setText("Mixer not supported. Select a different mixer and try again.");
        		label2.setText(" ");
        	}
        	
        	else {
        		//call record audio method
        		 
        		noteIDUtil.setMixerChoice(mixerChoice);
        		runningRecord = true;
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
        					label1.setText("Begin playing");
        					label2.setText("Note Detected: " + noteIDUtil.currentNote);
        					Font bigFont = new Font("sansserif",Font.PLAIN,26);
        	        			label2.setFont(bigFont);
        				}
        			}
        		});
        		
        		if (startButton.getText().equals("Start note recognition program")) {
        			noteIDThread.start();
        			ImageTimer timer = new ImageTimer();
        			busy = true;
        			//label1.setText("Play: ");
        			label1.setHorizontalAlignment(SwingConstants.CENTER);
        			label1.setVerticalAlignment(SwingConstants.CENTER);
        		
        			timer.start();
        			startButton.setText("Stop");
        			continueOutput = true;
        			noteIDOutput.start();
        		}
        		else if (startButton.getText().equals("Stop")) {
        			try {
        				noteIDUtil.stop();
        				busy = false;
        				continueOutput = false;
        			} catch (IOException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			startButton.setText("Start note recognition program");
        		}
     
        	}
        }
    }
    
    class ImageTimer extends Thread {
	    public void run() {
	       while (true) {
	    	   runningTimer = true;
	           try {
	               Thread.sleep(3000);
	           }
	           catch (InterruptedException e) {
	           }
	       	Prompt imageOutput = new Prompt();
			ImageIcon icon = imageOutput.ImagePrompt();
			imageLabel.setVisible(true);
			imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			imageLabel.setVerticalAlignment(SwingConstants.CENTER);
			imageLabel.setIcon(icon);
	       }
	    }
	}
    ///////// WORK ON RESET METHOD /////////
    class ResetListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		//call reset method
    		if (runningRecord == true & runningTimer == true) {
    			startButton.setText("Start note recognition program");
    			label1.setText("Cleared");
    			label1.setHorizontalAlignment(SwingConstants.CENTER);
    			label1.setVerticalAlignment(SwingConstants.CENTER);
    			imageLabel.setVisible(false);
    			label2.setVisible(false);
    		}
    		runningRecord = false;
    		runningTimer = false;
    	}
    }
	
    
    class TunerListener implements ActionListener {
       	SoundRecordingUtil tuner = new SoundRecordingUtil();
       	boolean continueTune = true;
       	String[] notes = {"Low E String","A String","D String","G String","B String","High E String"};
       	public void actionPerformed(ActionEvent event) {
       		if (busy && tuneButton.getText().equals("Tune")) {
       			label1.setText("Busy");
       		}
       		else if (mixerChoice == 100) {
       			label1.setText("Select a mixer");
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
	       			tuneOutput.start();
	       		}
	       		else {
	       			tuner.StringNo++;
	       		}
	       		if (tuner.StringNo > 5) {
	    			try {
	    				tuner.stop();
	    				busy = false;
	    				continueTune = false;
	    			} catch (IOException e) {
	    				e.printStackTrace();
	    			}
	    			tuneButton.setText("Tune");
	    			tuner.StringNo = 0;
	       		}  	
       		}
       	}
    }
    

	/**
     * Get mixer choice from user
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		JRadioButtonMenuItem buttonPressed = (JRadioButtonMenuItem)e.getSource(); 
		buttonPressed.setVisible(true);
		if (!busy) {
		mixerChoice = Integer.parseInt(buttonPressed.getText().substring(0,1));
		label1.setText(buttonPressed.getText() + " selected. Ready to record.");
		}
		else
			label1.setText("Busy");
	}
}