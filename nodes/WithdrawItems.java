package scripts.SPXAIOPlanker.nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.Variables;
import scripts.SPXAIOPlanker.api.Node;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class WithdrawItems extends Node {

    public WithdrawItems(Variables v) {
        super(v);
    }

    @Override
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
        if (Inventory.getCount("Coins") <= 5000) {
            if (Banking.find("Coins").length > 0) {
                if (Banking.withdraw(vars.coinsAmount, "Coins")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Inventory.getCount("Coins") == vars.coinsAmount;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                General.println("We could not find any coins.");
                General.println("Stopping Script...");
                vars.stopScript = true;
            }
        }
        if (Banking.find(vars.logType).length > 0) {
            if (Banking.withdraw(27, vars.logType)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Inventory.getCount(vars.logType) > 0;
                    }
                }, General.random(750, 1000));
            }
        } else {
            General.println("We could not find any " + vars.logType);
            General.println("Stopping Script...");
            vars.stopScript = true;
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

    @Override
    public String toString(){
        return "Withdrawing items...";
    }

    @Override
    public boolean validate() {
        return !Inventory.isFull() && Inventory.getCount(vars.logType) <= 0 && Inventory.getCount(vars.plankType) <= 0;
    }

}

