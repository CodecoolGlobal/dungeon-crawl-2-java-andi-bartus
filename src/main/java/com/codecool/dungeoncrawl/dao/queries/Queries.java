package com.codecool.dungeoncrawl.dao.queries;

public class Queries {
    public static String getSaveFileName(){
        return "SELECT json FROM saves WHERE id = ?";
    }

    public static String getAllSaveNames(){
        return "SELECT name FROM saves";
    }

}
