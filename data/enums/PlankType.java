package scripts.spxaioplanker.data.enums;

/**
 * Created by Sphiinx on 7/28/2016.
 */
public enum PlankType {

    PLANK(960, 1511, 100, 89),
    OAK_PLANK(8778, 1521, 250, 90),
    TEAK_PLANK(8782, 8333, 500, 91),
    MAHOGANY_PLANK(8780, 8332, 1500, 92);

    private final int PLANK_ID;
    private final int LOG_ID;
    private final int SAWMILL_COST;
    private final int BUY_PLANK_INTERFACE_CHILD;

    PlankType(int plank_id, int log_id, int sawmill_cost, int buy_plank_interface_child) {
        this.PLANK_ID = plank_id;
        this.LOG_ID = log_id;
        this.SAWMILL_COST = sawmill_cost;
        this.BUY_PLANK_INTERFACE_CHILD = buy_plank_interface_child;
    }

    public int getPlankID() {
        return PLANK_ID;
    }

    public int getLogID() {
        return LOG_ID;
    }

    public int getSawmillCost() {
        return SAWMILL_COST;
    }

    public int getBuyPlankInterfaceChild() {
        return BUY_PLANK_INTERFACE_CHILD;
    }

}

