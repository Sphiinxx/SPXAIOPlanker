package scripts.spxaioplanker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSNPC;
import scripts.spxaioplanker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.tribotapi.game.npcs.NPCs07;
import scripts.tribotapi.game.timing.Timing07;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class BuyPlanks implements Task {

    private final int BUY_PLANK_INTERFACE = 403;

    @Override
    public boolean validate() {
        final RSNPC sawmill_operator = NPCs07.getNPC("Sawmill operator");
        return sawmill_operator != null && sawmill_operator.isOnScreen() && Inventory.getCount(Vars.get().plank_type.getLogID()) > 0 && Inventory.getCount("Coins") >= 2700;
    }

    @Override
    public void execute() {
        final RSNPC sawmill_operator = NPCs07.getNPC("Sawmill operator");
        if (sawmill_operator == null)
            return;

        final RSInterface buy_planks_interface = Interfaces.get(BUY_PLANK_INTERFACE, Vars.get().plank_type.getBuyPlankInterfaceChild());
        if (buy_planks_interface != null) {
            if (Clicking.click("Buy All", buy_planks_interface))
                if (Timing07.waitCondition(() -> Interfaces.get(BUY_PLANK_INTERFACE, Vars.get().plank_type.getBuyPlankInterfaceChild()) == null, General.random(2000, 2500))) {
                    Vars.get().planks_made += Inventory.getCount(Vars.get().plank_type.getPlankID());
                }
        } else {
            if (Clicking.click("Buy-plank", sawmill_operator))
                Timing07.waitCondition(() -> Interfaces.get(BUY_PLANK_INTERFACE, Vars.get().plank_type.getBuyPlankInterfaceChild()) != null, General.random(1500, 2000));
        }
    }

    @Override
    public String toString() {
        return "Buying planks";
    }
}

