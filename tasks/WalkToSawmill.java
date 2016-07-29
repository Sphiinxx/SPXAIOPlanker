package scripts.SPXAIOPlanker.tasks;

import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;
import scripts.SPXAIOPlanker.data.Vars;
import scripts.TaskFramework.framework.Task;
import scripts.TribotAPI.game.npcs.NPCs07;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public class WalkToSawmill implements Task {

    private final RSTile SAWMILL_OPERATOR = new RSTile(3302, 3491, 0);

    @Override
    public boolean validate() {
        final RSNPC sawmill_operator = NPCs07.getNPC("Sawmill operator");
        return (sawmill_operator != null && !sawmill_operator.isOnScreen() || sawmill_operator == null) && Inventory.getCount(Vars.get().plank_type.getLogID()) > 0 && Inventory.getCount(Vars.get().plank_type.getPlankID()) <= 0 && Inventory.getCount("Coins") >= 2700;

    }

    @Override
    public void execute() {
        WebWalking.walkTo(SAWMILL_OPERATOR, new Condition() {
            @Override
            public boolean active() {
                final RSNPC sawmill_operator = NPCs07.getNPC("Sawmill operator");
                return sawmill_operator != null && sawmill_operator.isOnScreen();
            }
        }, 250);
    }

    @Override
    public String toString() {
        return "Walking to Sawmill";
    }

}

