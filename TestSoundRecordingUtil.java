import java.io.File;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;

import javax.sound.sampled.LineUnavailableException;
 
/**
 * A sample program that tests the SoundRecordingUtil utility class.
 * @author www.codejava.net
 *
 */
public class TestSoundRecordingUtil {
	public final SoundRecordingUtil recorder = new SoundRecordingUtil();

	public String getTestSoundRecordingUtil() {
    final int RECORD_TIME = 20000;   
     
   // public static void main(String[] args) {
        //File wavFile = new File("/Users/cuongnguyen/Desktop/TestSound", "Test.wav");
         
        //final SoundRecordingUtil recorder = new SoundRecordingUtil();
         
        // create a separate thread for recording
        Thread recordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Start recording...");
                    recorder.start();
                } catch (LineUnavailableException ex) {
                    ex.printStackTrace();
                    System.exit(-1);
                }              
            }
        });
         
        recordThread.start();
         
        try {
            Thread.sleep(RECORD_TIME);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
         
        try {
            recorder.stop();
            //recorder.save(wavFile);
            System.out.println("STOPPED");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         
        System.out.println("DONE");
        return "recorded";
    }
 
}
	