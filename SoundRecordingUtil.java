
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
			if (note.noteID(110, buffer, 44100))
				System.out.print("a");
			else if (note.noteID(116.54, buffer, 44100))
				System.out.print("a#");
			else if (note.noteID(164.81, buffer, 44100))
				System.out.print("e");
			else if (note.noteID(123.47, buffer, 44100))
				System.out.print("b");
			else if (note.noteID(130.81, buffer, 44100))
				System.out.print("c");
			else if (note.noteID(138.59, buffer, 44100))
				System.out.print("c#");
			else if (note.noteID(146.83, buffer, 44100))
				System.out.print("d");
			else if (note.noteID(155.86, buffer, 44100))
				System.out.print("d#");
			else if (note.noteID(174.61, buffer, 44100))
				System.out.print("f");
			else if (note.noteID(185, buffer, 44100))
				System.out.print("f#");
			else if (note.noteID(196, buffer, 44100))
				System.out.print("g");
			else if (note.noteID(207.65, buffer, 44100))
				System.out.print("g#");

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
}