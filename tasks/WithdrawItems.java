package scripts.SPXAIOPlanker.tasks;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.framework.Task;
import TribotAPI.game.banking.Banking07;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class WithdrawItems implements Task {

    public void execute() {
        if (Banking.isInBank()) {
            if (Banking.isBankScreenOpen()) {
                withdrawItems();
            } else {
                openBank();
            }
        } else {
            walkToBank();
        }
    }

    public void withdrawItems() {
        if (Banking07.isBankItemsLoaded()) {
            if (Inventory.getCount("Coins") <= 5000) {
                if (Banking.find("Coins").length > 0) {
                    if (Banking.withdraw(Vars.get().coinsAmount, "Coins")) {
                        Timing.waitCondition(new Condition() {
                            @Override
                            public boolean active() {
                                General.sleep(100);
                                return Inventory.getCount("Coins") >= Vars.get().coinsAmount;
                            }
                        }, General.random(750, 1000));
                    }
                } else {
                    General.println("We could not find any coins.");
                    General.println("Stopping Script...");
                    Vars.get().stopScript = true;
                }
            }
            if (Banking.find(Vars.get().logType).length > 0) {
                if (Banking.withdraw(27, Vars.get().logType)) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Inventory.getCount(Vars.get().logType) > 0;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                General.println("We could not find any " + Vars.get().logType);
                General.println("Stopping Script...");
                Vars.get().stopScript = true;
            }
        }
    }

    public void openBank() {
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

    private void walkToBank() {
        if (WebWalking.walkToBank()) {
            Timing.waitCondition(new Condition() {
                @Override
                public boolean active() {
                    General.sleep(100);return Banking.isInBank();
                }
            }, General.random(750, 1000));
        }
    }

    public String toString(){
        return "Withdrawing items...";
    }

    public boolean validate() {
        return !Inventory.isFull() && Inventory.getCount(Vars.get().logType) <= 0 && Inventory.getCount(Vars.get().plankType) <= 0;
    }

}

