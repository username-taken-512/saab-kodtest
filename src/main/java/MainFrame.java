
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MainFrame {
    private static final Dimension BUTTON_DIM = new Dimension(100, 36);
    private static final String LOGO_PATH = System.getProperty("java.class.path") + "/images/saablogo.png";
    private static final String WINDOW_TITLE = "Jonatan SAAB Super JAS Radar 5000";

    private JFrame mainFrame;
    private JPanel leftPanel, rightPanel, buttonPanel;
    private MapPanel mapPanel;

    public MainFrame() {
        init();
    }

    private void init() {
        // Prepare frame
        FlowLayout mainFlow = new FlowLayout(FlowLayout.CENTER, 20, 20);
        mainFrame = new JFrame();
        mainFrame.setLayout(mainFlow);
        mainFrame.setTitle(WINDOW_TITLE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);

        // Left side outer panel
        leftPanel = new JPanel();
        leftPanel.setBackground(Color.BLUE);
        leftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        leftPanel.setBounds(0, 0, 300, 300);
        leftPanel.setPreferredSize(new Dimension(300, 300));

        // Left side map panel
        mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(300, 300));

        // Right side outer panel
        rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

        // Top-right logo
        JLabel logoLabel = new JLabel();
        URL iconURL = getClass().getClassLoader().getResource("images/saablogo.png");
        // Workaround: URL object works when packaged, string path works from IntelliJ
        ImageIcon labelIcon = (iconURL != null ?
                new ImageIcon(iconURL) :
                new ImageIcon(LOGO_PATH)
        );
        logoLabel.setIcon(labelIcon);

        // Bottom-right buttons
        GridLayout buttonGrid = new GridLayout(4, 1);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(buttonGrid);

        JButton[] buttons = new JButton[3];
        buttons[0] = new JButton("<html>Category 1 (<font color=red>Type1</font><font color=blue>/Type2)</font>");
        buttons[1] = new JButton("<html>Category 2 (<font color=#FF00FF>Type3</font>)</html>");
        buttons[2] = new JButton("Show All");
        for (int i = 0; i < buttons.length; i++) {
            JButton button = buttons[i];
            button.setBackground(i < 2 ? Color.GRAY : Color.GREEN);
            button.setSize(BUTTON_DIM);
            button.setPreferredSize(BUTTON_DIM);
            button.putClientProperty("category", i < 2 ? String.valueOf(i + 1) : "all");     // Store category prop

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JButton jButton : buttons) {
                        jButton.setBackground(Color.GRAY);
                    }
                    JButton eventButton = (JButton) e.getSource();
                    eventButton.setBackground(Color.GREEN);
                    mapPanel.showCategory(String.valueOf(eventButton.getClientProperty("category")));
                }
            });

            buttonPanel.add(buttons[i]);
        }

        leftPanel.add(mapPanel, BorderLayout.CENTER);
        rightPanel.add(logoLabel, BorderLayout.NORTH);
        rightPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainFrame.add(leftPanel);
        mainFrame.add(rightPanel);

        mainFrame.pack();
    }

    public void show() {
        mainFrame.setVisible(true);
    }

    // Sends MapObject to MapPanel to update object position on map
    public void updateMap(MapObject mapObject) {
        mapPanel.updateMapObject(mapObject);
    }
}
