package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", true),
    WALL("wall", false),
    SKULL("skull", true);

    private final String tileName;
    private final boolean canStepOn;

    CellType(String tileName, boolean canStepOn) {
        this.tileName = tileName;
        this.canStepOn = canStepOn;
    }

    public boolean getCanStepOn() {
        return canStepOn;
    }

    public String getTileName() {
        return tileName;
    }
}
