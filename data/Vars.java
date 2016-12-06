package scripts.spxaioplanker.data;

import scripts.spxaioplanker.data.enums.PlankType;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public class Vars {

    public static Vars vars;

    public static Vars get() {
        return vars == null ? vars = new Vars() : vars;
    }

    public static void reset() {
        vars = null;
    }

    public int coins_to_take;

    public int planks_made;
    public int profit;
    public int plank_price;
    public int log_price;

    public PlankType plank_type;

}

