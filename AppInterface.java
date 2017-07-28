 /* Guitar App Interface
 *  Team 9
 */
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Mixer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.util.Timer;
//import java.util.TimerTask;

 
public class AppInterface implements ActionListener{
	JFrame frame;
    JLabel label;
    int mixerChoice = 0;
    TestSoundRecordingUtil output = new TestSoundRecordingUtil();
    
 
    public static void main(String[] args) {
        AppInterface gui = new AppInterface();
        gui.go();
    }
 
    public void go(){
        
    	frame = new JFrame("Beginning Guitarists App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel helpPanel = new JPanel();
        JPanel panel = new JPanel();
        JPanel panel2 = new JPanel();
 
        JButton startButton = new JButton("Begin audio capture");
        startButton.setSize(100,200);
        startButton.addActionListener(new StartListener());
 
        JButton resetButton = new JButton("Reset");
        resetButton.setSize(700,200);
        resetButton.addActionListener(new ResetListener());
        
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new HelpListener());
 
        label = new JLabel("Welcome", SwingConstants.CENTER);
 
        panel.add(startButton);
        panel.add(resetButton);
        helpPanel.add(helpButton);
        panel2.add(label);
        frame.getContentPane().add(BorderLayout.NORTH, helpPanel);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        frame.getContentPane().add(BorderLayout.CENTER, panel2);
        Container center = new Container();
        
        Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		center.setLayout(new GridLayout(mixerInfo.length+1, 1));
		//center.add(label);
		JButton[] buttons = new JButton[mixerInfo.length];
		for (int i = 0; i < mixerInfo.length; i++) {
			buttons[i] = new JButton(i + ". "+mixerInfo[i]);
			center.add(buttons[i]);
			buttons[i].addActionListener(this);
			
			System.out.println(i + ". " + mixerInfo[i]);
		
		}
		
		frame.getContentPane().add(BorderLayout.WEST, center);
        
        
        frame.setSize(900, 500);
        frame.setVisible(true);
    }
 
    public class StartListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
        	//call record audio method
                label.setText("Beginning audio capture...");
                //TestSoundRecordingUtil output = new TestSoundRecordingUtil();
                String labelOutput = output.getTestSoundRecordingUtil();
                label.setText(labelOutput);
        }
    }
    
    class ResetListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		//call reset method
    		label.setText("Cleared");
    	}
    }
 
    class HelpListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
        	// call Help method
        	label.setText("");
        	Help output = new Help();
        	String labelOutput = output.getHelp();
        	label.setHorizontalAlignment(SwingConstants.CENTER);
        	label.setVerticalAlignment(SwingConstants.CENTER);
        	label.setText(labelOutput);
    
        }
        
    class MixerListener implements ActionListener {
        	public void actionPerformed(ActionEvent event) {
        		label.setText("Working");
        	
    		}
    	}
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton buttonPressed = (JButton)e.getSource();
		//buttons.setVisible(false); FIX
		buttonPressed.setVisible(true);
		mixerChoice = Integer.parseInt(buttonPressed.getText().substring(0,1));
		output.recorder.setMixerChoice(mixerChoice);
		
	}
}
