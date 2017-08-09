
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.*;

 
/**
 * A utility class provides general functions for recording sound.
 * @author www.codejava.net
 *
 */
public class SoundRecordingUtil {
    private static final int BUFFER_SIZE = 4410;
    private ByteArrayOutputStream recordBytes;
    private TargetDataLine audioLine;
    private AudioFormat format;
    private int mixNum;
    int StringNo;
    String currentNote = "";
    byte[] output = new byte[BUFFER_SIZE];
 
    public boolean isRunning;
 
    /**
     * Defines a default audio format used to record
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
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
		
		Mixer mixer = AudioSystem.getMixer(mixerInfo[mixNum]);

        audioLine = (TargetDataLine)mixer.getLine(info);
        
		//}
        try {
        	audioLine.open(format);
        } catch (LineUnavailableException ex) {
        	ex.printStackTrace();
        }
        audioLine.start();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
 
        recordBytes = new ByteArrayOutputStream();
        isRunning = true;

        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
			NoteID note = new NoteID();
			int max = 0;
			for (int i = 0; i < buffer.length; i++) {
				max = Math.max(buffer[i], max);
				output[i] = buffer[i];
			}
			if (max > 5) {
				//System.out.println(note.noteID(buffer) + " ");
				currentNote = note.noteID(buffer);
			}
        }
    }
    
    public void setMixerChoice(int mixerChoice){
		mixNum = mixerChoice;
	}
	
 
    /**
     * Stop recording sound.
     * @throws IOException if any I/O error occurs.
     */
    public void stop() throws IOException {
        isRunning = false;
         
        if (audioLine != null) {
            audioLine.flush();
            audioLine.close();
        }
		
    }
    
    public void runTuner() {
    	format = getAudioFormat();
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		Mixer mixer = AudioSystem.getMixer(mixerInfo[mixNum]);

		try {
			audioLine = (TargetDataLine)mixer.getLine(info);
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
     
        try {
        	audioLine.open(format);
        } catch (LineUnavailableException ex) {
        	ex.printStackTrace();
        }
        audioLine.start();
 
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead = 0;
 
        recordBytes = new ByteArrayOutputStream();
        isRunning = true;

        while (isRunning) {
            bytesRead = audioLine.read(buffer, 0, buffer.length);
            recordBytes.write(buffer, 0, bytesRead);
            int max = buffer[0];
            boolean loud = false;
            for (int i = 0; i < buffer.length; i++) {
            	max = Math.max(max, buffer[i]);
            	output[i] = buffer[i];
            }
            if (max > 5)
            	loud = true;
			//NoteID note = new NoteID();
			if (StringNo == 0 && loud) {
					//System.out.println("E " + tune(buffer, 82.41, 87.31, 77.78));
					currentNote = tune(buffer, 82.41, 87.31, 77.78);
			}
			else if (StringNo == 1 && loud) {
					//System.out.println("a " + tune(buffer, 110.00, 116.54, 103.83));
					currentNote = tune(buffer, 110.00, 116.54, 103.83);
			}
			else if (StringNo == 2 && loud) {
					//System.out.println("d " + tune(buffer, 146.83, 155.56, 138.59));
					currentNote = tune(buffer, 146.83, 155.56, 138.59);
			}
			else if (StringNo == 3 && loud) {
					//System.out.println("g " + tune(buffer, 196.00, 207.65, 185.00));
					currentNote = tune(buffer, 196.00, 207.65, 185.00);
			}
			else if (StringNo == 4 && loud) {
					//System.out.println("b " + tune(buffer, 246.94, 261.63, 233.08));
					currentNote = tune(buffer, 246.94, 261.63, 233.08);
			}
			else if (StringNo == 5 && loud) {
					//System.out.println("e " + tune(buffer, 329.63, 349.23, 311.13));
					currentNote = tune(buffer, 329.63, 349.23, 311.13);
			}
        }
    }
    
	public String tune(byte[] b, double f, double fSharp, double fFlat) {
		byte[] tuned = new byte[(int)(44100/f)];
		byte[] flat = new byte[(int)(44100/fFlat)];
		byte[] sharp = new byte[(int)(44100/fSharp)];
		
		int tunedDiff = fillAndMaxMinDiff(tuned, b);
		int flatDiff = fillAndMaxMinDiff(flat, b);
		int sharpDiff = fillAndMaxMinDiff(sharp, b);
		
		if (tunedDiff > flatDiff && tunedDiff > sharpDiff && tunedDiff > 250)
			return "in tune";
		else if (flatDiff > sharpDiff)
			return "too low";
		else if (sharpDiff > flatDiff)
			return "too high";
		return "";
	}
	public int fillAndMaxMinDiff(byte[] a, byte[] b) {
		int max = a[0];
		int min = a[0];
		int x = 0;
		for(int i = 0; i < b.length; i++) {
			if (x >= a.length)
				x = 0;
			a[x] += b[i];
			x++;
		}
		
		for (int i = 0; i < a.length; i++) {
			max = Math.max(a[i], max);
			min = Math.min(a[i], min);
		}
		return max-min;
	}	
	public boolean testMixer(Mixer mixer) {
		AudioFormat format = getAudioFormat();
		DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
		try {
			mixer.getLine(info);
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			return true;
		} catch (IllegalArgumentException ex) {
			return true;
		}
		return false;
	}
}