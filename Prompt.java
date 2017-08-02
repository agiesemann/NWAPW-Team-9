import java.util.*;
import javax.swing.ImageIcon;

public class Prompt {
		public String Prompter(){
			Random rand = new Random();
			String prompt;
			String[] note = {"E","A","D","G","B7"};
			
			int noteIndex = rand.nextInt(note.length);
			prompt = note[noteIndex];
			
			return prompt;	
			
		}
		
		public ImageIcon ImagePrompt(){
			ImageIcon promptImage = null;
			Random rand = new Random();
			ImageIcon eImage = new ImageIcon("EPos.png");
			ImageIcon aImage = new ImageIcon("APos.png");
			ImageIcon dImage = new ImageIcon("DPos.png");
			ImageIcon gImage = new ImageIcon("GPos.png");
			ImageIcon bImage = new ImageIcon("B7Pos.png");
			
			ImageIcon[] note ={eImage, aImage, dImage, gImage, bImage};
			
			int noteIndex = rand.nextInt(note.length);
			promptImage = note[noteIndex];
			
			return promptImage;
			
		}

	}

