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
			ImageIcon aImage = new ImageIcon("aFinal.png");
			ImageIcon bImage = new ImageIcon("bFinal.png");
			ImageIcon cImage = new ImageIcon("cFinal.png");
			ImageIcon dImage = new ImageIcon("dFinal.png");
			ImageIcon eImage = new ImageIcon("eFinal.png");
			ImageIcon fImage = new ImageIcon("fFinal.png");
			ImageIcon gImage = new ImageIcon("gFinal.png");
			ImageIcon e1Image = new ImageIcon("eFinal1.png");
			ImageIcon f1Image = new ImageIcon("fFinal1.png");
			
			ImageIcon[] note ={aImage, bImage, cImage, dImage, eImage, fImage, gImage, e1Image, f1Image};
			
			int noteIndex = rand.nextInt(note.length);
			promptImage = note[noteIndex];
			
			return promptImage;
			
		}

	}
