   /* Guitar App Interface
 *  Team 9
 */

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Font;

public class AppInterface implements ActionListener{
	JFrame frame;
    JLabel label1;
    JLabel label2;
    JLabel imageLabel;
    JPanel utilPanel = new JPanel();
    JPanel outputPanel = new JPanel();
    JPanel imagePanel = new JPanel();
    Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
    int mixerChoice = 100;
    boolean busy = false;
    
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
        
    	frame = new JFrame("Beginning Guitar App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        startButton.addActionListener(new StartListener());
 
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ResetListener());
        
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new HelpListener());
        
        tuneButton.addActionListener(new TunerListener());
 
        label1 = new JLabel();
        		label1.setHorizontalAlignment(SwingConstants.CENTER);
        		label1.setVerticalAlignment(SwingConstants.CENTER);
        		Font medFont = new Font("sansserif", Font.PLAIN,20);
        		label1.setFont(medFont);
        	    //label1.setText("This is Label1"); // TEST
        label2 = new JLabel();
        		Help output = new Help();
        		String labelOutput = output.getHelp();
        		label2.setHorizontalAlignment(SwingConstants.CENTER);
        		label2.setVerticalAlignment(SwingConstants.CENTER);
        		label2.setText(labelOutput);
        		Font bigFont = new Font("sansserif",Font.PLAIN,16);
        		label2.setFont(bigFont);
        	
        	
        //ImageIcon testImage = new ImageIcon("musicNotes.png"); // TEST
        imageLabel = new JLabel(); 
        //	imageLabel.setIcon(testImage); 
    	
        // add buttons and labels to panels 
        utilPanel.add(helpButton);
        utilPanel.add(startButton);
        utilPanel.add(resetButton);
        utilPanel.add(tuneButton);
       
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(label1);
        Dimension minSize = new Dimension(5, 50);
        Dimension prefSize = new Dimension(5, 50);
        Dimension maxSize = new Dimension(Short.MAX_VALUE, 50);
        outputPanel.add(new Box.Filler(minSize, prefSize, maxSize));
        outputPanel.add(label2);
        
        imagePanel.add(imageLabel);
        
        // add panels to frame
        frame.getContentPane().add(BorderLayout.SOUTH, utilPanel);
        frame.getContentPane().add(BorderLayout.CENTER, outputPanel);
        frame.getContentPane().add(BorderLayout.EAST, imagePanel);
        Container center = new Container();
        
        // create mixer buttons
        //Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		center.setLayout(new GridLayout(mixerInfo.length+1, 1));
		JButton[] buttons = new JButton[mixerInfo.length];
		for (int i = 0; i < mixerInfo.length; i++) {
			buttons[i] = new JButton(i + ". "+mixerInfo[i]);
			center.add(buttons[i]);
			buttons[i].addActionListener(this);
		}
		// add mixer buttons to frame
		frame.getContentPane().add(BorderLayout.WEST, center);
        
        
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
    			startButton.setText("Start note recognition program");
			
    		label1.setText("Cleared");
    		label1.setHorizontalAlignment(SwingConstants.CENTER);
        	label1.setVerticalAlignment(SwingConstants.CENTER);
    		imageLabel.setVisible(false);
    		label2.setVisible(false);
    	}
    }
	
    class HelpListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
        	// call Help method
        	label1.setText("");
        	imageLabel.setVisible(false);
        	Help output = new Help();
        	String labelOutput = output.getHelp();
        	label2.setHorizontalAlignment(SwingConstants.CENTER);
        	label2.setVerticalAlignment(SwingConstants.CENTER);
        	label2.setText(labelOutput);
    
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
		JButton buttonPressed = (JButton)e.getSource(); 
		buttonPressed.setVisible(true);
		if (!busy) {
		mixerChoice = Integer.parseInt(buttonPressed.getText().substring(0,1));
		label1.setText(buttonPressed.getText() + " selected. Ready to record.");
		}
		else
			label1.setText("Busy");
	}
}

