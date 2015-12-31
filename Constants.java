package scripts.SPXAIOPlanker;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class Constants {

    public static final Color MOUSE_COLOR = new Color(222, 0, 0);
    public static final Color COLOR1 = new Color(0, 169, 194);
    public static final Color COLOR2 = new Color(255, 255, 255);
    public static final Font FONT1 = new Font("Segoe Script", 0, 20);
    public static final long START_TIME = System.currentTimeMillis();
    public static final Font FONT2 = new Font("Arial", 0, 15);
    public static final Image IMG1 = getImage("http://i.imgur.com/fRrLAWr.png");
    public static final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public static Image getImage(String url) {
        try {
            return ImageIO.read(new URL(url));
        } catch (IOException e) {
            return null;
        }
    }

    public static final RSArea SAWMILL_AREA = new RSArea(new RSTile[]{
            new RSTile(3305, 3492, 0),
            new RSTile(3300, 3492, 0),
            new RSTile(3300, 3491, 0),
            new RSTile(3305, 3491, 0)
    });

}

