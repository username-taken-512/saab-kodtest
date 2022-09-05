
import javax.swing.*;
import java.awt.*;

/*
    Jonatan's code test
    2022-09-01
 */

public class Main {
    private static MainFrame mainFrame;
    public static void main(String[] args) {
        // Run GUI in new thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Set "modern" look
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        try {
                            UIManager.setLookAndFeel(info.getClassName());
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                                | UnsupportedLookAndFeelException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        break;
                    }
                }

                // Run app GUI
                mainFrame = new MainFrame();
                mainFrame.show();

                // Run network thread when GUI is up
                Thread networkThread = new Thread(new NetworkClient(mainFrame));
                networkThread.start();
            }
        });
    }
}
