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
    int mixerChoice = 100;
    
   // TestSoundRecordingUtil output = new TestSoundRecordingUtil();
    JButton tuneButton = new JButton("Tune");
    JButton startButton = new JButton("Start");
    

    public static void main(String[] args) {
        AppInterface gui = new AppInterface();
        gui.go();
    }
 
    /**
     * Declare and initialize GUI components
     */
    public void go(){
        
    	frame = new JFrame("Beginning Guitarists App");
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
        	     label1.setText("This is Label1");
        label2 = new JLabel();
        		Help output = new Help();
        		String labelOutput = output.getHelp();
        		label2.setHorizontalAlignment(SwingConstants.CENTER);
        		label2.setVerticalAlignment(SwingConstants.CENTER);
        		label2.setText(labelOutput);
        		Font bigFont = new Font("sansserif",Font.PLAIN,16);
        		label2.setFont(bigFont);
        	
        ImageIcon testImage = new ImageIcon("musicNotes.png");
        imageLabel = new JLabel();
        	imageLabel.setIcon(testImage);
    	
        // add buttons and labels to panels 
        utilPanel.add(helpButton);
        utilPanel.add(startButton);
        utilPanel.add(resetButton);
        utilPanel.add(tuneButton);
        //utilPanel.add(promptButton);
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        outputPanel.add(label1);
        //outputPanel.add(Box.createVerticalGlue());
        outputPanel.add(label2);
        imagePanel.add(imageLabel);
        
        // add panels to frame
        frame.getContentPane().add(BorderLayout.SOUTH, utilPanel);
        frame.getContentPane().add(BorderLayout.CENTER, outputPanel);
        frame.getContentPane().add(BorderLayout.EAST, imagePanel);
        Container center = new Container();
        
        // create mixer buttons
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
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
        public void actionPerformed(ActionEvent event){
        	if (mixerChoice == 100) {
        		label1.setText("Select a mixer to begin.");
        	} else {
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
        		             		if (startButton.getText().equals("Start")) {
        		             			noteIDThread.start();
        		             			startButton.setText("Stop");
        		             		}
        		             		else if (startButton.getText().equals("Stop")) {
        		             			try {
        		 							noteIDUtil.stop();
        		 						} catch (IOException e) {
        		 							// TODO Auto-generated catch block
        		 							e.printStackTrace();
        		 						}
        		             			startButton.setText("Start");
        		             		}
        		   
                label1.setText("Play: ");
                label1.setHorizontalAlignment(SwingConstants.CENTER);
                label1.setVerticalAlignment(SwingConstants.CENTER);
                Prompt imageOutput = new Prompt();
                ImageIcon icon = imageOutput.ImagePrompt();
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                imageLabel.setVerticalAlignment(SwingConstants.CENTER);
                imageLabel.setIcon(icon);
            	imagePanel.add(imageLabel);
     
        		}
        }
    }
    ///////// WORK ON RESET METHOD /////////
    class ResetListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		//call reset method
    		label1.setText("Cleared");
    		label1.setHorizontalAlignment(SwingConstants.CENTER);
        	label1.setVerticalAlignment(SwingConstants.CENTER);
    		imageLabel.setVisible(false);
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
       	public void actionPerformed(ActionEvent event) {
       		if (mixerChoice == 100)
       			label1.setText("Select a mixer to begin.");
       		else {
	       		tuner.setMixerChoice(mixerChoice);
	       		Thread tuneThread = new Thread(new Runnable() {
	       			public void run() {
	       				tuner.runTuner();
	    	    		}
	    	    	});
	       		if (tuneButton.getText().equals("Tune")) {
	       			tuneThread.start();
	       			tuneButton.setText("Next String");
	       		}
	       		else {
	       			tuner.StringNo++;
	       		}
	       		if (tuner.StringNo > 5) {
	    				try {
	    					tuner.stop();
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
		mixerChoice = Integer.parseInt(buttonPressed.getText().substring(0,1));
		//output.recorder.setMixerChoice(mixerChoice);
		label1.setText(buttonPressed.getText() + " selected. Ready to record.");
		
		
	}
}

