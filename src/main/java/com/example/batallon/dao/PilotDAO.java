package com.example.batallon.dao;

import com.example.batallon.model.Pilot;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotDAO {

    // 🔹 Ajusta la URL según tu base de datos
    // Si usas SERVICE NAME:
    private String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    // Si usas SID (menos común en 19c+):
    // private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:orcl";

    private String jdbcUsername = "system";     // usuario Oracle
    private String jdbcPassword = "Tapiero123"; // contraseña Oracle

    // Consultas SQL
    private static final String INSERT_SQL = "INSERT INTO pilots (name, rank, age) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM pilots";
    private static final String SELECT_BY_ID = "SELECT * FROM pilots WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE pilots SET name=?, rank=?, age=? WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM pilots WHERE id=?";

    /**
     * Establece la conexión con Oracle DB
     */
    protected Connection getConnection() throws SQLException {
        try {
            // 🔹 Driver actualizado (para ojdbc8.jar u ojdbc11.jar)
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC de Oracle. Verifica ojdbc11.jar en /lib de Tomcat.", e);
        } catch (SQLException e) {
            throw new SQLException("Error al conectar con la BD en " + jdbcURL, e);
        }
    }

    /**
     * Insertar un nuevo piloto
     */
    public void insertPilot(Pilot pilot) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(INSERT_SQL)) {

            ps.setString(1, pilot.getName());
            ps.setString(2, pilot.getRank());
            ps.setInt(3, pilot.getAge());
            ps.executeUpdate();
        }
    }

    /**
     * Consultar todos los pilotos
     */
    public List<Pilot> selectAllPilots() throws SQLException {
        List<Pilot> pilots = new ArrayList<>();

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                pilots.add(new Pilot(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("rank"),
                        rs.getInt("age")
                ));
            }
        }

        return pilots;
    }

    /**
     * Consultar un piloto por ID
     */
    public Pilot selectPilot(int id) throws SQLException {
        Pilot pilot = null;

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(SELECT_BY_ID)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pilot = new Pilot(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("rank"),
                            rs.getInt("age")
                    );
                }
            }
        }

        return pilot;
    }

    /**
     * Actualizar un piloto
     */
    public boolean updatePilot(Pilot pilot) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_SQL)) {

            ps.setString(1, pilot.getName());
            ps.setString(2, pilot.getRank());
            ps.setInt(3, pilot.getAge());
            ps.setInt(4, pilot.getId());

            return ps.executeUpdate() > 0;
        }
    }

    /**
     * Eliminar un piloto
     */
    public boolean deletePilot(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
