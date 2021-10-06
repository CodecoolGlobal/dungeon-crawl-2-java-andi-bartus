package com.codecool.dungeoncrawl.dao;

import com.codecool.dungeoncrawl.dao.queries.Queries;
import com.codecool.dungeoncrawl.model.GameState;
import com.codecool.dungeoncrawl.model.PlayerModel;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameStateDaoJdbc implements GameStateDao {
    private DataSource dataSource;

    public GameStateDaoJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void add(String name, String json) {
        try (Connection conn = dataSource.getConnection()) {
            String sql = "INSERT INTO saves (name, json) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setString(2, json);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(String name, String json) {
        try (Connection connection = dataSource.getConnection()){
            String sql = Queries.updateExistingSave();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, json);
            statement.setString(2, name);
            statement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public GameState get(int id) {
        return null;
    }

    @Override
    public List<String> getAll() {
        try (Connection connection = dataSource.getConnection()){
            String sql = Queries.getAllSaveNames();

            ResultSet resultSet = connection.createStatement().executeQuery(sql);
            List<String> result = new ArrayList<>();

            while (resultSet.next()){
                String name = resultSet.getString(1);
                result.add(name);
            }

            return result;
        }
        catch (SQLException e){
            throw new RuntimeException("Error while reading all names", e);
        }
    }

}
