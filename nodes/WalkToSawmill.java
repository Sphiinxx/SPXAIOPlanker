package scripts.SPXAIOPlanker.nodes;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import scripts.SPXAIOPlanker.Constants;
import scripts.SPXAIOPlanker.Variables;
import scripts.SPXAIOPlanker.api.Node;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class WalkToSawmill extends Node {

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
        return !Constants.SAWMILL_AREA.contains(Player.getPosition()) && Inventory.getCount(vars.logType) > 0  && Inventory.getCount(vars.plankType) <= 0 && Inventory.getCount("Coins") >= 5000;
    }

}

