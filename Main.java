package scripts.SPXAIOPlanker;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Login;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.SPXAIOPlanker.api.Node;
import scripts.SPXAIOPlanker.nodes.BuyPlanks;
import scripts.SPXAIOPlanker.nodes.DepositItems;
import scripts.SPXAIOPlanker.nodes.WalkToSawmill;
import scripts.SPXAIOPlanker.nodes.WithdrawItems;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Sphiinx on 12/30/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Money making", name = "[SPX] AIO Planker", version = 0.1)
public class Main extends Script implements Painting{

    private Variables variables = new Variables();
    private ArrayList<Node> nodes = new ArrayList<>();
    public GUI gui = new GUI(variables);

    @Override
    public void run() {
        initializeGui();
        Collections.addAll(nodes, new DepositItems(variables), new WithdrawItems(variables), new WalkToSawmill(variables), new BuyPlanks(variables));
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

    public void onPaint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHints(Constants.ANTIALIASING);
        if (Login.getLoginState() == Login.STATE.INGAME) {

            long timeRan = System.currentTimeMillis() - scripts.SPXCowKiller.Constants.startTime;
            long planksHr = (long) (variables.planksMade * 3600000D / (System.currentTimeMillis() - Constants.START_TIME));

            g.drawImage(Constants.IMG1, 2, 200, null);
            g.setFont(Constants.FONT1);
            g.setColor(Constants.COLOR1);
            g.setFont(Constants.FONT2);
            g.setColor(Constants.COLOR2);
            g.drawString("Runtime: " + Timing.msToString(timeRan), 11, 252);
            g.drawString("Planks Made: " + variables.planksMade, 11, 272);
            g.drawString("Planks/Hr: " + planksHr, 11, 292);
            g.drawString("Status: " + variables.status, 11, 312);
            g.drawString("v" + variables.version, 205, 330);
        }
    }

}

