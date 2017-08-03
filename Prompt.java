import java.util.*;
import javax.swing.ImageIcon;

public class Prompt {
		public String Prompter(){
			Random rand = new Random();
			String prompt;
			String[] note = {"A","B","C","D","E","F","G"};
			
			int noteIndex = rand.nextInt(note.length);
			prompt = note[noteIndex];
			
			return prompt;	
			
		}
		
		public ImageIcon ImagePrompt(){
			ImageIcon promptImage = null;
			Random rand = new Random();
			ImageIcon aImage = new ImageIcon("A.gif");
			ImageIcon bImage = new ImageIcon("B.gif");
			ImageIcon cImage = new ImageIcon("C.gif");
			ImageIcon dImage = new ImageIcon("D.gif");
			ImageIcon eImage = new ImageIcon("E.gif");
			ImageIcon fImage = new ImageIcon("F.gif");
			ImageIcon gImage = new ImageIcon("G.gif");
			
			ImageIcon[] note ={aImage, bImage, cImage, dImage, eImage, fImage, gImage};
			
			int noteIndex = rand.nextInt(note.length);
			promptImage = note[noteIndex];
			
			return promptImage;
			
		}

	}

