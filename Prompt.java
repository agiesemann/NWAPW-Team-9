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
		public ImageIcon FretImagePrompt() {
			ImageIcon promptImage = null;
			Random rand = new Random();
			ImageIcon EfImage = new ImageIcon("E1f.jpg");
			ImageIcon EfsImage = new ImageIcon("E2f#.jpg");
			ImageIcon EgImage = new ImageIcon("E3g.jpg");
			ImageIcon EgsImage = new ImageIcon("E4g#.jpg");
			ImageIcon EaImage = new ImageIcon("E5a.jpg");
			ImageIcon EasImage = new ImageIcon("E6a#.jpg");
			ImageIcon EbImage = new ImageIcon("E7b.jpg");
			ImageIcon EcImage = new ImageIcon("E8c.jpg");
			ImageIcon EcsImage = new ImageIcon("E9c#.jpg");
			ImageIcon EdImage = new ImageIcon("E10d.jpg");
			ImageIcon EdsImage = new ImageIcon("E11d#.jpg");
			ImageIcon EeImage = new ImageIcon("E12e.jpg");
			
			ImageIcon AasImage = new ImageIcon("A1a#.jpg");
			ImageIcon AbImage = new ImageIcon("A2b.jpg");
			ImageIcon AcImage = new ImageIcon("A3c.jpg");
			ImageIcon AcsImage = new ImageIcon("A4c#.jpg");
			ImageIcon AdImage = new ImageIcon("A5d.jpg");
			ImageIcon AdsImage = new ImageIcon("A6d#.jpg");
			ImageIcon AeImage = new ImageIcon("A7e.jpg");
			ImageIcon AfImage = new ImageIcon("A8f.jpg");
			ImageIcon AfsImage = new ImageIcon("A9f#.jpg");
			ImageIcon AgImage = new ImageIcon("A10g.jpg");
			ImageIcon AgsImage = new ImageIcon("A11g#.jpg");
			ImageIcon AaImage = new ImageIcon("A12a#.jpg");
			
			ImageIcon DdsImage = new ImageIcon("D1d#.jpg");
			ImageIcon DeImage = new ImageIcon("D2e.jpg");
			ImageIcon DfImage = new ImageIcon("D3f.jpg");
			ImageIcon DfsImage = new ImageIcon("D4f#.jpg");
			ImageIcon DgImage = new ImageIcon("D5g.jpg");
			ImageIcon DgsImage = new ImageIcon("D6g#.jpg");
			ImageIcon DaImage = new ImageIcon("D7a.jpg");
			ImageIcon DasImage = new ImageIcon("D8a#.jpg");
			ImageIcon DbImage = new ImageIcon("D9b.jpg");
			ImageIcon DcImage = new ImageIcon("D10c.jpg");
			ImageIcon DcsImage = new ImageIcon("D11c#.jpg");
			ImageIcon DdImage = new ImageIcon("D12d.jpg");
			
			ImageIcon GgsImage = new ImageIcon("G1g#.jpg");
			ImageIcon GaImage = new ImageIcon("G2a.jpg");
			ImageIcon GasImage = new ImageIcon("G3a#.jpg");
			ImageIcon GbImage = new ImageIcon("G4b.jpg");
			ImageIcon GcImage = new ImageIcon("G5c.jpg");
			ImageIcon GcsImage = new ImageIcon("G6c#.jpg");
			ImageIcon GdImage = new ImageIcon("G7d.jpg");
			ImageIcon GdsImage = new ImageIcon("G8d#.jpg");
			ImageIcon GeImage = new ImageIcon("G9e.jpg");
			ImageIcon GfImage = new ImageIcon("G10f.jpg");
			ImageIcon GfsImage = new ImageIcon("G11f#.jpg");
			ImageIcon GgImage = new ImageIcon("G12g.jpg");
			
			ImageIcon BcImage = new ImageIcon("B1c.jpg");
			ImageIcon BcsImage = new ImageIcon("B2c#.jpg");
			ImageIcon BdImage = new ImageIcon("B3d.jpg");
			ImageIcon BdsImage = new ImageIcon("B4d#.jpg");
			ImageIcon BeImage = new ImageIcon("B5e.jpg");
			ImageIcon BfImage = new ImageIcon("B6f.jpg");
			ImageIcon BfsImage = new ImageIcon("B7f#.jpg");
			ImageIcon BgImage = new ImageIcon("B8g.jpg");
			ImageIcon BgsImage = new ImageIcon("B9g#.jpg");
			ImageIcon BaImage = new ImageIcon("B10a.jpg");
			ImageIcon BasImage = new ImageIcon("B11a#.jpg");
			ImageIcon BbImage = new ImageIcon("B12b.jpg");
			
			//I is used to mean High E (as in High E string)
			ImageIcon IfImage = new ImageIcon("HighE1f.jpg");
			ImageIcon IfsImage = new ImageIcon("HighE2f#.jpg");
			ImageIcon IgImage = new ImageIcon("HighE3g.jpg");
			ImageIcon IgsImage = new ImageIcon("HighE4g#.jpg");
			ImageIcon IaImage = new ImageIcon("HighE5a.jpg");
			ImageIcon IasImage = new ImageIcon("HighE6a#.jpg");
			ImageIcon IbImage = new ImageIcon("HighE7b.jpg");
			ImageIcon IcImage = new ImageIcon("HighE8c.jpg");
			ImageIcon IcsImage = new ImageIcon("HighE9c#.jpg");
			ImageIcon IdImage = new ImageIcon("HighE10d.jpg");
			ImageIcon IdsImage = new ImageIcon("HighE11d#.jpg");
			ImageIcon IeImage = new ImageIcon("HighE12e.jpg");
			
			ImageIcon[] note = {EfImage, EfsImage, EgImage, EgsImage, EaImage, EasImage, EbImage, EcImage, EcsImage, EdImage, EdsImage, EeImage,
							    AasImage, AbImage, AcImage, AcsImage, AdImage, AdsImage, AeImage, AfImage, AfsImage, AgImage, AgsImage, AaImage,
							    DdsImage, DeImage, DfImage, DfsImage, DgImage, DgsImage, DaImage, DasImage, DbImage, DcImage, DcsImage, DdImage,
							    GgsImage, GaImage, GasImage, GbImage, GcImage, GcsImage, GdImage, GdsImage, GeImage, GfImage, GfsImage, GgImage,
							    BcImage, BcsImage, BdImage, BdsImage, BeImage, BfImage, BfsImage, BgImage, BgsImage, BaImage, BasImage, BbImage,
							    IfImage, IfsImage, IgImage, IgsImage, IaImage, IasImage, IbImage, IcImage, IcsImage, IdImage, IdsImage, IeImage};
			
			int noteIndex = rand.nextInt(note.length);
			promptImage = note[noteIndex];
			
			return promptImage;
			
		}

	}