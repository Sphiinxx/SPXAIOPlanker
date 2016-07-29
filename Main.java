package scripts.SPXAIOPlanker;

import com.allatori.annotations.DoNotRename;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.*;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.tasks.BuyPlanks;
import scripts.SPXAIOPlanker.tasks.DepositItems;
import scripts.SPXAIOPlanker.tasks.WalkToSawmill;
import scripts.SPXAIOPlanker.tasks.WithdrawItems;
import scripts.TaskFramework.framework.Task;
import scripts.TribotAPI.AbstractScript;
import scripts.TribotAPI.game.pricechecking.PriceChecking07;
import scripts.TribotAPI.gui.GUI;
import scripts.TribotAPI.painting.paint.Calculations;
import scripts.TribotAPI.painting.paint.enums.DataPosition;

import java.awt.*;

/**
 * Created by Sphiinx on 12/30/2015.
 */
@ScriptManifest(authors = "Sphiinx", category = "Money making", name = "[SPX] AIO Planker", version = 1.2)
@DoNotRename
public class Main extends AbstractScript implements Painting, EventBlockingOverride, Ending {

    @Override
    protected GUI getGUI() {
        return new GUI(getClass().getResource("GUI.fxml"));
    }

    @Override
    public void run() {
        Vars.reset();
        super.run();
        getItemPrices();
    }

    @Override
    public void addTasks(Task... tasks) {
        super.addTasks(new DepositItems(), new WithdrawItems(), new WalkToSawmill(), new BuyPlanks());
    }

    public void getItemPrices() {
        Vars.get().plank_price = PriceChecking07.getOSBuddyPrice(Vars.get().plank_type.getPlankID());
        Vars.get().log_price = PriceChecking07.getOSBuddyPrice(Vars.get().plank_type.getLogID());
    }

    @Override
    public void onPaint(Graphics g) {
        super.onPaint(g);
        Vars.get().profit = Vars.get().planks_made * Vars.get().plank_price - (((Vars.get().plank_price * Vars.get().planks_made) + (Vars.get().log_price * Vars.get().planks_made)) - Vars.get().plank_type.getSawmillCost() * Vars.get().planks_made);
        paint_manager.drawGeneralData("Planks bought: ", Integer.toString(Vars.get().planks_made) + Calculations.getPerHour(Vars.get().planks_made, this.getRunningTime()), DataPosition.TWO, g);
        paint_manager.drawGeneralData("Profit: ", Integer.toString(Vars.get().profit) + Calculations.getGPPerHour(Vars.get().profit, this.getRunningTime()), DataPosition.THREE, g);
        paint_manager.drawGeneralData("Status: ", task_manager.getStatus(), DataPosition.FOUR, g);
    }

    @Override
    public void onEnd() {
        SignatureData.sendSignatureData(this.getRunningTime() / 1000, Vars.get().planks_made, Vars.get().profit);
        super.onEnd();
    }
}

