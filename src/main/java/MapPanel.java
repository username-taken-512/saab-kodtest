
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;

public class MapPanel extends JPanel {
    private static final String MAP_IMAGE_PATH = System.getProperty("java.class.path") + "/images/map.gif";

    boolean[] showMapObjects = new boolean[3];  // True/false to show MapObject index corresponding to mapObjects array
    MapObject[] mapObjects = new MapObject[3];  // Last received MapObject of each type

    Graphics2D graphic2d;

    public MapPanel() {
        Arrays.fill(showMapObjects, true);  // Default: Show all categories on startup
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        graphic2d = (Graphics2D) g;

        URL mapURL = getClass().getClassLoader().getResource("images/map.gif");
        // Workaround: URL object works when packaged, string path works from IntelliJ
        ImageIcon bg = (mapURL != null ?
                new ImageIcon(mapURL) :
                new ImageIcon(MAP_IMAGE_PATH)
        );
        graphic2d.drawImage(bg.getImage(), 0, 0, 300, 300, Color.GRAY, null);

        for (MapObject mapObject : mapObjects) {
            if (mapObject == null || !showMapObjects[mapObject.getType() - 1]) {
                continue;
            }
            addOval(mapObject.getX(), mapObject.getY(), mapObject.getType());
        }
    }

    // Updates map object and repaints graphic
    public void updateMapObject(MapObject mapObject) {
        mapObjects[mapObject.getType() - 1] = mapObject;    // Index + 1 = Type
        this.repaint();
    }

    // Puts a dot on the graphic
    public void addOval(int x, int y, int typeColor) {
        switch (typeColor) {
            case 1 -> graphic2d.setColor(Color.RED);
            case 2 -> graphic2d.setColor(Color.BLUE);
            case 3 -> graphic2d.setColor(Color.MAGENTA);
        }
        // Subtract radius of oval to place oval at middle of x & y
        graphic2d.fillOval(x - 5, y - 5, 10, 10);
    }

    // Toggle categories shown
    public void showCategory(String category) {
        switch (category) {
            case "1" -> {
                showMapObjects[0] = true;
                showMapObjects[1] = true;
                showMapObjects[2] = false;
            }
            case "2" -> {
                showMapObjects[0] = false;
                showMapObjects[1] = false;
                showMapObjects[2] = true;
            }
            case "all" -> {
                Arrays.fill(showMapObjects, true);
            }
        }
        this.repaint();
    }
}
