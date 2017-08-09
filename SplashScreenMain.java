/* Splash Screen Main
 * Resonance
 * NWAPW 2017 - Team 9
 */

import javax.swing.ImageIcon;

public class SplashScreenMain {

  SplashScreen screen;

  public SplashScreenMain() {
    // initialize the splash screen
    splashScreenInit();
    // do something here to simulate the program doing something that
    // is time consuming
    for (int i = 0; i <= 100; i++)
    {
      for (long j=0; j<50000; ++j)
      {
        @SuppressWarnings("unused")
		String load = " " + (j + i);
      }
      // run either of these two -- not both
      screen.setProgress("Hello " + i, i);  // progress bar with a message
      //screen.setProgress(i);           // progress bar with no message
    }
    splashScreenDestruct();
    //System.exit(0);
  }

  private void splashScreenDestruct() {
    screen.setScreenVisible(false);
  }

  private void splashScreenInit() {
    ImageIcon myImage = new ImageIcon("SplashScreen.jpg"); // choose splash screen image file
    screen = new SplashScreen(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgressMax(100);
    screen.setScreenVisible(true);
  }
 
 


 
}
