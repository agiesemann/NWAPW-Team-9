 /* Guitar App Interface
 * Carrie Nguyen
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
public class AppInterface {
	JFrame frame;
    JLabel label;
 
    public static void main(String[] args) {
        AppInterface gui = new AppInterface();
        gui.go();
    }
 
    public void go(){
        frame = new JFrame("Beginning Guitarists App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        JButton startButton = new JButton("Begin audio capture");
        startButton.addActionListener(new StartListener());
 
        JButton stopButton = new JButton("Stop audio capture");
        stopButton.addActionListener(new StopListener());
        
        JButton helpButton = new JButton("Help");
        helpButton.addActionListener(new HelpListener());
 
        label = new JLabel("Welcome",SwingConstants.CENTER);
 
 
        frame.getContentPane().add(BorderLayout.SOUTH, helpButton);
        frame.getContentPane().add(BorderLayout.EAST, startButton);
        frame.getContentPane().add(BorderLayout.WEST, stopButton);
        frame.getContentPane().add(BorderLayout.CENTER, label);
        
 
        frame.setSize(700, 500);
        frame.setVisible(true);
    }
 
    class StartListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
                label.setText("Beginning audio capture...");
        }
    }
    
    class StopListener implements ActionListener{
    	public void actionPerformed(ActionEvent event){
    		label.setText("Audio capture stopped.");
    	}
    }
 
    class HelpListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
        	// call Help method
        	label.setText("");
        	Help output = new Help();
        	String labelOutput = output.getHelp();
        	label.setText(labelOutput);
        }
    }
}
 
