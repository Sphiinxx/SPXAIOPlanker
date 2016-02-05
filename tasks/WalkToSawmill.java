package scripts.SPXAIOPlanker.tasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.data.Constants;
import scripts.SPXAIOPlanker.data.Variables;
import scripts.SPXAIOPlanker.API.Framework.Task;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class WalkToSawmill extends Task {

    public WalkToSawmill(Variables v) {
        super(v);
    }

    @Override
    public void execute() {
        WebWalking.walkTo(Constants.SAWMILL_AREA.getRandomTile());
    }

    @Override
    public String toString(){
        return "Walking to Sawmill...";
    }

    @Override
    public boolean validate() {
        vars.operator = NPCs.find("Sawmill operator");
        return  vars.operator.length <= 0 && Inventory.getCount(vars.logType) > 0  && Inventory.getCount(vars.plankType) <= 0 && Inventory.getCount("Coins") >= 5000;
    }

}

