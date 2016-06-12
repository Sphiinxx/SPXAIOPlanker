package scripts.SPXAIOPlanker.tasks;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.framework.Task;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class BuyPlanks implements Task {

    private RSInterfaceChild logs;

    public void execute() {
        if (Interfaces.get(403) == null) {
            talkToOperator();
        } else {
            buyPlanks();
        }
    }

    public void buyPlanks() {
        logs = Interfaces.get(403, Vars.get().interfaceChild);
        if (logs != null) {
            if (Clicking.click("Buy all", logs)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Inventory.getCount(Vars.get().logType) <= 0;
                    }
                }, General.random(750, 1000));
                Vars.get().planksMade = Vars.get().planksMade + Inventory.getCount(Vars.get().plankType);
            }
        }
    }

    public void talkToOperator() {
        if (Vars.get().operator.length > 0) {
            if (Vars.get().operator[0].isOnScreen()) {
                if (DynamicClicking.clickRSNPC(Vars.get().operator[0], "Buy-plank")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Interfaces.get(403) != null;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                Walking.walkTo(Vars.get().operator[0]);
            }
        }
    }

    public String toString(){
        return "Buying Planks...";
    }

    public boolean validate() {
        Vars.get().operator = NPCs.find("Sawmill operator");
        return Vars.get().operator.length > 0 && Inventory.getCount(Vars.get().logType) > 0  && Inventory.getCount(Vars.get().plankType) <= 0  && Inventory.getCount("Coins") >= 5000;
    }

}

