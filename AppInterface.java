/* Guitarist App Interface
 * Carrie Nguyen
 * 7/24/17
 */

import javax.swing.*;
import java.awt.event.*;      

public class AppInterface implements ActionListener {
	JFrame frame;
	JPanel contentPane;
	JLabel label;
	JButton button;

    public AppInterface(){
        /* Create and set up the frame */
        frame = new JFrame("Beginning Guitarists App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* Create a content pane */
        contentPane = new JPanel();

		/* Create and add label */
	    label = new JLabel("Welcome.");
        contentPane.add(label);

		/* Create and add button */
        button = new JButton("Begin audio capture");
    	button.setActionCommand("Begin audio capture");
    	button.addActionListener(this);
    	contentPane.add(button);
    	
    	button = new JButton("Help");
    	button.setActionCommand("Help");
    	button.addActionListener(this);
    	contentPane.add(button);

		/* Add content pane to frame */
        frame.setContentPane(contentPane);

        /* Size and then display the frame. */
        frame.pack();
	frame.setSize(512,512);
        frame.setVisible(true);
    }
    
    
    /**
     * Handle button click action event
     * pre: none
     * post: Clicked button has different text and label
     * displays message depending on when the button was clicked.
     */
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
       
        	if (eventName.equals("Begin audio capture")) {
        		// call audio capture method
        	label.setText("Capturing audio...");
        	button.setText("Stop audio capture");
        	button.setActionCommand("Stop audio capture");
        	
        } else if (eventName.equals("Help")) {
        	// call Help method
		Help output = new Help();
        	String labelOutput = output.getHelp();
        	label.setText(labelOutput);
        } else {
        	label.setText("Audio capture stopped.");
        	// call stop audio method
        }
    }


    /**
     * Create and show the GUI.
     */
    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);

        AppInterface greeting = new AppInterface();
    }
     

    public static void main(String[] args) {
        /* Methods that create and show a GUI should be 
           run from an event-dispatching thread */
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });
    }
}
