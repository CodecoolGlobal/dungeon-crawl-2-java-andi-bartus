package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(String name, String json);
    void update(String name, String json);
    GameState get(int id);
    List<String> getAll();
}
