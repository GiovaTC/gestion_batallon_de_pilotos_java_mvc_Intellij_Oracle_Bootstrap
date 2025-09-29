package com.example.batallon.dao;

import com.example.batallon.model.Pilot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PilotDAO {
    private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private String jdbcUsername = "system";
    private String jdbcPassword = "Tapiero123";

    private static final String INSERT_SQL = "INSERT INTO pilots (name, rank, age) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM pilots";
    private static final String SELECT_BY_ID = "SELECT * FROM pilots WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE pilots SET name=?, rank=?, age=? WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM pilots WHERE id=?";

    protected  Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername,jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw  new SQLException();
        }
    }
    protected void insertPilot(Pilot pilot) throws SQLException {
        try (Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {
           ps.setString(1, pilot.getName());
           ps.setString(2, pilot.getRank());
           ps.setInt(3, pilot.getAge());
           ps.executeUpdate();
        }
    }

    public List<Pilot> selectAllPilots() throws SQLException {
        List<Pilot> pilots = new ArrayList<>();
        try (Connection connection = getConnection();
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL)) {
           ResultSet rs = ps.executeQuery();
           while (rs.next()) {
               pilots.add(new Pilot(rs.getInt("id"), rs.getString("name"),
                       rs.getString("rank"), rs.getInt("age")));
           }
        }
        return pilots;
    }

    public Pilot selectPilot(int id) throws SQLException {
        Pilot pilot = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pilot = new Pilot(rs.getInt("id"), rs.getString("name"),
                        rs.getString("rank"), rs.getInt("age"));
            }
        }
        return pilot;
    }

}
