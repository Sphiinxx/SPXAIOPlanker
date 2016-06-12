package scripts.SPXAIOPlanker.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.framework.Task;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class DepositItems implements Task {

    public void execute() {
        if (Banking.isInBank()) {
            openBank();
        } else {
            walkToBank();
        }
    }

    private void openBank() {
        if (Banking.isBankScreenOpen()) {
            if (Banking.deposit(0, Vars.get().plankType)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Inventory.getCount(Vars.get().plankType) <= 0;
                    }
                }, General.random(750, 1000));
            }
        } else {
            if (Banking.openBank()) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Banking.isBankScreenOpen();
                    }
                }, General.random(750, 1000));
            }
        }
    }

    private void walkToBank() {
        if (WebWalking.walkToBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);
                    return Banking.isInBank();
                }
            }, General.random(750, 1000));
        }
    }

    public String toString(){
        return "Depositing Items...";
    }

    public boolean validate() {
        return Inventory.getCount(Vars.get().plankType) > 0;
    }

}

