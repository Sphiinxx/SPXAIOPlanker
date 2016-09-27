package scripts.spxaioplanker;

import com.allatori.annotations.DoNotRename;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.spxaioplanker.data.Vars;
import scripts.spxaioplanker.tasks.BuyPlanks;
import scripts.spxaioplanker.tasks.DepositItems;
import scripts.spxaioplanker.tasks.WalkToSawmill;
import scripts.spxaioplanker.tasks.WithdrawItems;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.AbstractScript;
import scripts.tribotapi.game.pricechecking.PriceChecking07;
import scripts.tribotapi.game.utiity.Utility07;
import scripts.tribotapi.gui.GUI;
import scripts.tribotapi.painting.paint.Calculations;
import scripts.tribotapi.painting.paint.enums.DataPosition;

import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sphiinx on 12/30/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Money making", name = "[SPX] AIO Planker", version = 1.3)
@DoNotRename
public class Main extends AbstractScript implements Painting, EventBlockingOverride, Ending {

    @Override
    protected GUI getGUI() {
        try {
            return new GUI(new URL("http://spxscripts.com/spxaioplanker/GUI.fxml"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void run() {
        Vars.reset();
        super.run();
    }

    @Override
    public void addTasks(Task... tasks) {
        super.addTasks(new DepositItems(), new WithdrawItems(), new WalkToSawmill(), new BuyPlanks());
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        if (Vars.get().plank_type != null) {
            if (Vars.get().plank_price <= 0 || Vars.get().log_price <= 0) {
                Vars.get().plank_price = PriceChecking07.getOSBuddyPrice(Vars.get().plank_type.getPlankID());
                Vars.get().log_price = PriceChecking07.getOSBuddyPrice(Vars.get().plank_type.getLogID());
            }

            Vars.get().profit = Vars.get().planks_made * Vars.get().plank_price - (((Vars.get().plank_price * Vars.get().planks_made) + (Vars.get().log_price * Vars.get().planks_made)) - Vars.get().plank_type.getSawmillCost() * Vars.get().planks_made);
        }
        paint_manager.drawGeneralData("Planks bought: ", Integer.toString(Vars.get().planks_made) + Calculations.getPerHour(Vars.get().planks_made, this.getRunningTime()), DataPosition.TWO, g);
        paint_manager.drawGeneralData("Profit: ", Integer.toString(Vars.get().profit) + Calculations.getGPPerHour(Vars.get().profit, this.getRunningTime()), DataPosition.THREE, g);
        paint_manager.drawGeneralData("Status: ", task_manager.getStatus() + Utility07.getLoadingPeriods(), DataPosition.FOUR, g);
    }

    @Override
    public void onEnd() {
        SignatureData.sendSignatureData(this.getRunningTime() / 1000, Vars.get().planks_made, Vars.get().profit);
        super.onEnd();
    }
}

