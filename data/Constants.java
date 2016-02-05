package scripts.SPXAIOPlanker.data;

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

    public static final Color RED_COLOR = new Color(214, 39, 39, 240);
    public static final Color BLACK_COLOR = new Color(0, 0, 0, 100);
    public static final Font TITLE_FONT = new Font("Arial Bold", 0, 15);
    public static final Font TEXT_FONT = new Font("Arial", 0, 12);
    public static final RenderingHints ANTIALIASING = new RenderingHints(
            RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    public static final long START_TIME = System.currentTimeMillis();
    public static final RSArea SAWMILL_AREA = new RSArea(new RSTile[]{
            new RSTile(3303, 3491, 0),
            new RSTile(3301, 3491, 0),
            new RSTile(3301, 3490, 0),
            new RSTile(3303, 3490, 0)
    });

}

