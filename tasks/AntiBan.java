package scripts.SPXAIOPlanker.tasks;

import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Options;
import org.tribot.api2007.Player;
import scripts.SPXAIOPlanker.API.Framework.Task;
import scripts.SPXAIOPlanker.Main;
import scripts.SPXAIOPlanker.data.Variables;

/**
 * Created by Sphiinx on 12/31/2015.
 */
public class AntiBan extends Task {

    public AntiBan(Variables v) {
        super(v);
    }

    @Override
    public void execute() {

        Main.AntiBan.performExamineObject();

        Main.AntiBan.performRotateCamera();

        Main.AntiBan.performLeaveGame();

        Main.AntiBan.performPickupMouse();

        Main.AntiBan.performRandomMouseMovement();

        Main.AntiBan.performRandomRightClick();

        Main.AntiBan.performCombatCheck();

        Main.AntiBan.performEquipmentCheck();

        Main.AntiBan.performFriendsCheck();

        Main.AntiBan.performMusicCheck();

        Main.AntiBan.performQuestsCheck();

        if (Interfaces.getAll() == null && Player.isMoving() && Game.getRunEnergy() >= Main.AntiBan.INT_TRACKER.NEXT_RUN_AT.next()) {
            Options.setRunOn(true);
            Main.AntiBan.INT_TRACKER.NEXT_RUN_AT.reset();
        }

    }

    @Override
    public String toString(){
        return "Performing Antiban...";
    }

    public boolean validate() {
        return Player.getAnimation() != -1 || Player.isMoving();
    }

}

