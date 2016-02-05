package scripts.SPXAIOPlanker.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import scripts.SPXAIOPlanker.Variables;
import scripts.SPXAIOPlanker.API.Framework.Task;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class BuyPlanks extends Task {

    private RSInterfaceChild logs;

    public BuyPlanks(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        if (Interfaces.get(403) == null) {
            talkToOperator();
        } else {
            buyPlanks();
        }
    }

    public void buyPlanks() {
        logs = Interfaces.get(403, vars.interfaceChild);
        if (logs != null) {
            if (Clicking.click("Buy all", logs)) {
                Timing.waitCondition(new Condition() {
                    @Override
                    public boolean active() {
                        General.sleep(100);
                        return Inventory.getCount(vars.logType) <= 0;
                    }
                }, General.random(750, 1000));
                vars.planksMade = vars.planksMade + Inventory.getCount(vars.plankType);
            }
        }
    }

    public void talkToOperator() {
        if (vars.operator.length > 0) {
            if (vars.operator[0].isOnScreen()) {
                if (DynamicClicking.clickRSNPC(vars.operator[0], "Buy-plank")) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            General.sleep(100);
                            return Interfaces.get(403) != null;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                Walking.walkTo(vars.operator[0]);
            }
        }
    }

    @Override
    public String toString(){
        return "Buying Planks...";
    }

    @Override
    public boolean validate() {
        vars.operator = NPCs.find("Sawmill operator");
        return vars.operator.length > 0 && Inventory.getCount(vars.logType) > 0  && Inventory.getCount(vars.plankType) <= 0  && Inventory.getCount("Coins") >= 5000;
    }

}

