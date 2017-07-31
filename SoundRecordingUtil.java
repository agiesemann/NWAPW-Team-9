
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
 
    private boolean isRunning;
 
    /**
     * Defines a default audio format used to record
     */
    AudioFormat getAudioFormat() {
        float sampleRate = 44100;
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
 
        // checks if system supports the data line
        
		
		Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
		
		//boolean audioCheck = true;
		//while (audioCheck) {
		
		Mixer mixer = AudioSystem.getMixer(mixerInfo[mixNum]);
		/*if (!AudioSystem.isLineSupported(mixer.getLineInfo())) {
            throw new LineUnavailableException(
                    "The system does not support the specified format.");
        }*/

        audioLine = (TargetDataLine)mixer.getLine(info);
        
		//}
        audioLine.open(format);
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
			}
			if (max > 10)
			System.out.println(note.noteID(buffer) + " ");
			/*if (note.noteID(82.41, buffer, 44100))
				System.out.print("e");
			else if (note.noteID(110, buffer, 44100))
				System.out.print("a");
			else if (note.noteID(146.83, buffer, 44100))
				System.out.print("d");
			else if (note.noteID(196, buffer, 44100))
				System.out.print("g");
			else if (note.noteID(246.94, buffer, 44100))
				System.out.print("b");
			else if (note.noteID(329.63, buffer, 44100))
				System.out.print("e");*/
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
	public String tune(byte[] b, double f, double fSharp, double fFlat) {
		byte[] tuned = new byte[(int)(44100/f)];
		byte[] flat = new byte[(int)(44100/fFlat)];
		byte[] sharp = new byte[(int)(44100/fSharp)];
		
		int tunedDiff = fillAndMaxMinDiff(tuned, b);
		int flatDiff = fillAndMaxMinDiff(flat, b);
		int sharpDiff = fillAndMaxMinDiff(sharp, b);
		
		if (flatDiff > tunedDiff && flatDiff > sharpDiff)
			return "too low";
		else if (tunedDiff > flatDiff && tunedDiff > sharpDiff)
			return "in tune";
		else if (sharpDiff > tunedDiff && sharpDiff > flatDiff)
			return "too high";
		return "in tune";
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
}