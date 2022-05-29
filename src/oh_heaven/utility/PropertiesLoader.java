/** This class is adapted from PropertiesLoader class provided in SWEN30006 SnakeAndLaddersOnAPlane project **/
package oh_heaven.utility;

import oh_heaven.game.Player;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesLoader {
    public static final String DEFAULT_DIRECTORY_PATH = "properties/";
    public static Properties loadPropertiesFile(String propertiesFile) {
        if (propertiesFile == null) {
            try (InputStream input = new FileInputStream( DEFAULT_DIRECTORY_PATH + "runmode.properties")) {

                Properties prop = new Properties();

                prop.load(input);

                propertiesFile = DEFAULT_DIRECTORY_PATH + prop.getProperty("current_mode");
                System.out.println(propertiesFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (InputStream input = new FileInputStream(propertiesFile)) {

            Properties prop = new Properties();

            prop.load(input);

            return prop;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /** Reads the types of players from property file and uses a Factory to create them **/
    public static List<Player> loadPlayers(Properties properties, int playerCount) {

        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            String playerType = properties.getProperty("players." + i);
            players.add(PlayerFactory.getInstance().createNewPlayer(i,playerType));
        }

        return players;
    }
}
