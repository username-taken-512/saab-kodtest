
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;

/*
Data format example:
ID=905026056006800;X=110;Y=216;TYPE=2
ID=905026056008100;X=111;Y=206;TYPE=1
ID=905026056010100;X=49;Y=146;TYPE=1
 */

// Implement runnable to start as new thread
public class NetworkClient implements Runnable {
    private static final String SERVERNAME = "localhost";
    private static final int SERVERPORT = 5463;
    private static final int CONNECTION_RETRY_MILLIS = 5000;    // ms between re-connection attempts
    MainFrame mainFrame;

    NetworkClient(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    @Override
    public void run() {
        connectToServer();
    }

    // Sets up server connection and data-receive-loop
    private void connectToServer() {
        BufferedReader in = null;
        Socket s = null;

        // Loop reconnects on lost connection
        while (true) {
            try {
                // Setup connection
                s = new Socket(SERVERNAME, SERVERPORT);
                System.out.println((new Date()) + "-> Connected");
                in = new BufferedReader(new InputStreamReader(s.getInputStream()));

                // Continously receive data from server
                String inData;
                while ((inData = in.readLine()) != null) {
                    MapObject mapObject = inDataToObject(inData);
                    if (mapObject != null) {
                        mainFrame.updateMap(mapObject);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(new Date() + "-> Connection failed... retrying... ");
            }

            // Delay between connection retries
            try {
                Thread.sleep(CONNECTION_RETRY_MILLIS);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Converts inData string to MapObject. Returns null if invalid string format.
    private MapObject inDataToObject(String inData) {
        long[] splitDataLong = new long[4];
        String[] splitDataString = inData.split(";");
        MapObject mapObject = null;

        try {
            for (int i = 0; i < splitDataString.length; i++) {
                splitDataLong[i] = Long.parseLong(splitDataString[i].split("=")[1]);
            }
            // Parameters: id, x, y, type, category (type 1 or 2 = 1 & type 3 = 2)
            mapObject = new MapObject(splitDataLong[0], (int) splitDataLong[1],
                    (int) splitDataLong[2], (int) splitDataLong[3],
                    ( (int) splitDataLong[3] < 3 ? 1 : 2));
        } catch (Exception e) {
            System.out.println(new Date() + "-> Data format error: [" + inData + "]");
        }
        return mapObject;
    }
}
