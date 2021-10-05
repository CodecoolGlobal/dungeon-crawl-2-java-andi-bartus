package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.model.GameState;

import java.util.List;

public interface GameStateDao {
    void add(String name, String json);
    void update(GameState state);
    GameState get(int id);
    List<String> getAll();
}
