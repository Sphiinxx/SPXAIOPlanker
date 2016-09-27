package scripts.spxaioplanker.tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import scripts.spxaioplanker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.task_framework.framework.TaskManager;
import scripts.tribotapi.game.banking.Banking07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.util.Logging;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class WithdrawItems implements Task {

    private int item_id;
    private int amount_to_withdraw;

    @Override
    public boolean validate() {
        if (Inventory.isFull())
            return false;

        if (Inventory.getCount("Coins") < 2700) {
            item_id = 995;
            amount_to_withdraw = (int) Vars.get().coins_to_take;
            return true;
        }

        if (Inventory.getCount(Vars.get().plank_type.getLogID()) <= 0) {
            item_id = Vars.get().plank_type.getLogID();
            amount_to_withdraw = 27;
            return true;
        }

        return false;
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            final RSItem item_to_withdraw = Banking07.findItem(item_id);
            if (item_to_withdraw != null) {
                if (Banking07.withdrawItem(amount_to_withdraw, item_to_withdraw.getID()))
                    Timing07.waitCondition(() -> Inventory.getCount(item_id) >= amount_to_withdraw, General.random(2500, 3000));
            } else {
                Logging.warning("We're out of resources.");
                TaskManager.stopProgram(true);
            }
        } else {
            if (!Banking07.isInBank())
                WebWalking.walkToBank();

            if (Banking.openBank())
                Timing07.waitCondition(Banking07::isBankItemsLoaded, General.random(1500, 2000));
        }
    }

    @Override
    public String toString() {
        return "Withdrawing items";
    }

}

