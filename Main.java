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
import scripts.SPXAIOPlanker.API.Framework.Task;
import scripts.SPXAIOPlanker.nodes.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sphiinx on 12/30/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Money making", name = "[SPX] AIO Planker", version = 0.1)
public class Main extends Script implements Painting{

    private Variables variables = new Variables();
    private ArrayList<Task> tasks = new ArrayList<>();
    public GUI gui = new GUI(variables);
    public static final ABCUtil AntiBan = new ABCUtil();

    @Override
    public void run() {
        General.useAntiBanCompliance(true);
        initializeGui();
        getPrices();
        Collections.addAll(tasks, new DepositItems(variables), new WithdrawItems(variables), new WalkToSawmill(variables), new BuyPlanks(variables), new AntiBan(variables));
        variables.version = getClass().getAnnotation(ScriptManifest.class).version();
        loop(20, 40);
    }

    private void loop(int min, int max) {
        while (!variables.stopScript) {
            for (final Task task : tasks) {
                if (task.validate()) {
                    variables.status = task.toString();
                    task.execute();
                    General.sleep(min, max);
                }
            }
        }
    }

    public void initializeGui() {
        EventQueue.invokeLater(() -> {
            try {
                sleep(50);
                variables.status = "Initializing...";
                gui.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
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

            int subtractLogPrice = variables.currentLogPrice * variables.planksMade;
            int subtractPlankPrice = variables.plankPrice * variables.planksMade;
            int addSubtraction = subtractLogPrice + subtractPlankPrice;
            int profit = variables.planksMade * variables.currentPlankPrice - addSubtraction;
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


}

