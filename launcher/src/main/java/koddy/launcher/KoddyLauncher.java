package koddy.launcher;

import koddy.input.Hello;
import koddy.ui.MainFrame;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class KoddyLauncher {

  public static void main(String[] args) {
    System.out.println(Hello.sayHello());

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
  }
}
