package scripts.SPXAIOPlanker.tasks;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.data.Constants;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.SPXAIOPlanker.framework.Task;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class WalkToSawmill implements Task {

    public void execute() {
        WebWalking.walkTo(Constants.SAWMILL_AREA.getRandomTile());
    }

    public String toString(){
        return "Walking to Sawmill...";
    }

    public boolean validate() {
        Vars.get().operator = NPCs.find("Sawmill operator");
        return  Vars.get().operator.length <= 0 && Inventory.getCount(Vars.get().logType) > 0  && Inventory.getCount(Vars.get().plankType) <= 0 && Inventory.getCount("Coins") >= 5000;
    }

}

