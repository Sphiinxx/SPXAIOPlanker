package scripts.spxaioplanker.tasks;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.spxaioplanker.data.Vars;
import scripts.task_framework.framework.Task;
import scripts.task_framework.framework.TaskManager;
import scripts.tribotapi.game.banking.Banking07;
import scripts.tribotapi.game.timing.Timing07;
import scripts.tribotapi.util.Logging;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class DepositItems implements Task {


    @Override
    public boolean validate() {
        return Inventory.isFull() && Inventory.getCount(Vars.get().plank_type.getPlankID()) > 0;
    }

    @Override
    public void execute() {
        if (Banking07.isBankItemsLoaded()) {
            if (Banking.depositAllExcept("Coins") > 0) {
                Timing07.waitCondition(() -> Inventory.getCount(Vars.get().plank_type.getPlankID()) <= 0, General.random(1500, 2000));
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
        return "Depositing items";
    }

}

