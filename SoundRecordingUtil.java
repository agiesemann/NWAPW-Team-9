 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
/**
 * A utility class provides general functions for recording sound.
 * @author www.codejava.net
 *
 */
public class SoundRecordingUtil {
    private static final int BUFFER_SIZE = 4096;
    private ByteArrayOutputStream recordBytes;
    private TargetDataLine audioLine;
    private AudioFormat format;
 
    private boolean isRunning;
 
    /**
     * Defines a default audio format used to record
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
                bigEndian);
    }
 
    /**
     * Start recording sound.
     * @throws LineUnavailableException if the system does not support the specified
     * audio format nor open the audio data line.
     */
    public void start() throws LineUnavailableException {
        format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);
 
        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            throw new LineUnavailableException(
                    "The system does not support the specified format.");
        }
 
        audioLine = (TargetDataLine) mixer.getLine(info);
 
        audioLine.open(format);
        audioLine.start();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
 
        recordBytes = new ByteArrayOutputStream();
        isRunning = true;
 
        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
			for (int i = 0; i < buffer.length; i++) {
				System.out.print(buffer[i] + " ");
			}
        }
		
		
    }
 
    /**
     * Stop recording sound.
     * @throws IOException if any I/O error occurs.
     */
    public void stop() throws IOException {
        isRunning = false;
         
        if (audioLine != null) {
            audioLine.drain();
            audioLine.close();
        }
    }
 
    /**
     * Save recorded sound data into a .wav file format.
     * @param wavFile The file to be saved.
     * @throws IOException if any I/O error occurs.
     */
}