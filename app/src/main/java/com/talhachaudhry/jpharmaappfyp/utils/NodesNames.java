package com.talhachaudhry.jpharmaappfyp.utils;

public enum NodesNames {
    MAIN_NODE_NAME("Orders"),
    PENDING_NODE_NAME("Pending"),
    DISPATCH_NODE_NAME("Dispatched"),
    CANCEL_NODE_NAME("Cancelled"),
    COMPLETE_NODE_NAME("Complete"),
    PROCEEDING_NODE_NAME("Proceeding");

    String name;

    NodesNames(String orders) {
        this.name = orders;
    }

    public String getName() {
        return name;
    }
}
