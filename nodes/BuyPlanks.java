package scripts.SPXAIOPlanker.nodes;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSNPC;
import scripts.SPXAIOPlanker.Constants;
import scripts.SPXAIOPlanker.Variables;
import scripts.SPXAIOPlanker.api.Node;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class BuyPlanks extends Node {

    private RSNPC[] operator;
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
                        return Inventory.getCount(vars.logType) <= 0;
                    }
                }, General.random(750, 1000));
                vars.planksMade = vars.planksMade + Inventory.getCount(vars.plankType);
            }
        }
    }

    public void talkToOperator() {
        operator = NPCs.find("Sawmill operator");
        if (operator.length > 0) {
            if (operator[0].isOnScreen()) {
                if (Clicking.click("Buy-plank", operator)) {
                    Timing.waitCondition(new Condition() {
                        @Override
                        public boolean active() {
                            return Interfaces.get(403) != null;
                        }
                    }, General.random(750, 1000));
                }
            } else {
                Walking.walkTo(operator[0]);
            }
        }
    }

    @Override
    public String toString(){
        return "Buying Planks...";
    }

    @Override
    public boolean validate() {

        return Constants.SAWMILL_AREA.contains(Player.getPosition()) && Inventory.getCount(vars.logType) > 0  && Inventory.getCount(vars.plankType) <= 0  && Inventory.getCount("Coins") >= 5000;
    }

}

