package com.codecool.dungeoncrawl.logic;

public enum CellType {
    EMPTY("empty", false),
    FLOOR("floor", true),
    WALL("wall", false),
    SKULL("skull", true),
    SCORPIO("scorpio", true),
    TEQUILA("tequila", true),
    RED_HOUSE1("redHouse1", false),
    RED_HOUSE2("redHouse2", false),
    RED_HOUSE3("redHouse3", false),
    RED_HOUSE4("redHouse4", false),
    CHURCH_TOP("churchTop1", false),
    CHURCH_TOP2("churchTop2", false),
    CHURCH_HOUSE("churchHouse", false),
    TOMB_STONE("tombStone", false),
    SKULL1("skull1", false),
    SKULL2("skull2", false),
    DOOR("door", false),
    CLOSED_DOOR("closedDoor", false);


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
