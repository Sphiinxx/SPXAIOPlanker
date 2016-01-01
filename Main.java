package scripts.SPXAIOPlanker;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.MousePainting;
import org.tribot.script.interfaces.MouseSplinePainting;
import org.tribot.script.interfaces.Painting;
import scripts.SPXAIOPlanker.api.Node;
import scripts.SPXAIOPlanker.nodes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sphiinx on 12/30/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Money making", name = "[SPX] AIO Planker", version = 0.1)
public class Main extends Script implements Painting, MousePainting, MouseSplinePainting{

    private Variables variables = new Variables();
    private ArrayList<Node> nodes = new ArrayList<>();
    public GUI gui = new GUI(variables);
    public static final ABCUtil AntiBan = new ABCUtil();

    @Override
    public void run() {
        General.useAntiBanCompliance(true);
        initializeGui();
        getPrices();
        Collections.addAll(nodes, new DepositItems(variables), new WithdrawItems(variables), new WalkToSawmill(variables), new BuyPlanks(variables), new AntiBan(variables));
        variables.version = getClass().getAnnotation(ScriptManifest.class).version();
        loop(20, 40);
    }

    private void loop(int min, int max) {
        while (!variables.stopScript) {
            for (final Node node : nodes) {
                if (node.validate()) {
                    variables.status = node.toString();
                    node.execute();
                    General.sleep(min, max);
                }
            }
        }
    }

    public void initializeGui() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(50);
                    variables.status = "Initializing...";
                    gui.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        do
            sleep(10);
        while (!variables.guiComplete);
    }

    public void getPrices() {
        variables.currentPlankPrice = PriceChecker.getOSbuddyPrice(variables.plankTypeId);
        variables.currentLogPrice = PriceChecker.getOSbuddyPrice(variables.logTypeId);
    }

    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(Constants.ANTIALIASING);
        if (Login.getLoginState() == Login.STATE.INGAME) {

            int subtractLogs = variables.currentLogPrice * variables.planksMade;
            int profit = variables.planksMade * variables.currentPlankPrice - subtractLogs;
            long profitHr = (long) (profit * 3600000D / (System.currentTimeMillis() - Constants.START_TIME));
            long timeRan = System.currentTimeMillis() - Constants.START_TIME;
            long planksHr = (long) (variables.planksMade * 3600000D / (System.currentTimeMillis() - Constants.START_TIME));

            g.setColor(Constants.BLACK_COLOR);
            g.fillRoundRect(11, 220, 200, 110, 8, 8); // Paint background
            g.setColor(Constants.RED_COLOR);
            g.drawRoundRect(9, 218, 202, 112, 8, 8); // Red outline
            g.fillRoundRect(13, 223, 194, 22, 8, 8); // Title background
            g.setFont(Constants.TITLE_FONT);
            g.setColor(Color.WHITE);
            g.drawString("[SPX] AIO Planker", 18, 239);
            g.setFont(Constants.TEXT_FONT);
            g.drawString("Runtime: " + Timing.msToString(timeRan), 14, 260);
            g.drawString("Planks Made: " + variables.planksMade, 14, 276);
            g.drawString("Planks/Hr: " + planksHr, 14, 293);
            g.drawString("Profit/Hr: " + profitHr, 14, 310);
            g.drawString("Status: " + variables.status, 14, 326);
            g.drawString("v" + variables.version, 185, 326);
        }
    }

    @Override
    public void paintMouse(Graphics g, Point point, Point point1) {
        g.setColor(Constants.BLACK_COLOR);
        g.drawRect(Mouse.getPos().x - 13, Mouse.getPos().y - 13, 27, 27); // Square rectangle Stroke
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 1, 500); // Top y axis Line Stroke
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 1, 500); // Bottom y axis Line Stroke
        g.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 1); // Right x axis line Stroke
        g.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 1); // left x axis line Stroke
        g.fillOval(Mouse.getPos().x - 3, Mouse.getPos().y - 3, 7, 7); // Center dot stroke
        g.setColor(Constants.RED_COLOR);
        g.drawRect(Mouse.getPos().x - 12, Mouse.getPos().y - 12, 25, 25); // Square rectangle
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y - 512, 0, 500); // Top y axis Line
        g.drawRect(Mouse.getPos().x, Mouse.getPos().y + 13, 0, 500); // Bottom y axis Line
        g.drawRect(Mouse.getPos().x + 13, Mouse.getPos().y, 800, 0); // Right x axis line
        g.drawRect(Mouse.getPos().x - 812, Mouse.getPos().y, 800, 0); // left x axis line
        g.fillOval(Mouse.getPos().x - 2, Mouse.getPos().y - 2, 5, 5); // Center dot
    }

    @Override
    public void paintMouseSpline(Graphics graphics, ArrayList<Point> arrayList) {
    }
}

