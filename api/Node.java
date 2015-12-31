package scripts.SPXAIOPlanker.api;


import scripts.SPXAIOPlanker.Variables;

/**
 * Created by Sphiinx on 12/30/2015.
 */
public abstract class Node {

    protected Variables vars;

    public Node(Variables v) {
        vars = v;
    }

    public abstract void execute();

    public abstract boolean validate();

}

