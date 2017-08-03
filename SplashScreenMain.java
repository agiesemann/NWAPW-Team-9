import javax.swing.UIManager;

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
    ImageIcon myImage = new ImageIcon("portland-oregon-sign.jpg");
    screen = new SplashScreen(myImage);
    screen.setLocationRelativeTo(null);
    screen.setProgressMax(100);
    screen.setScreenVisible(true);
  }
 
 


 
}