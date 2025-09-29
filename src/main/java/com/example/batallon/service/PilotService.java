package com.example.batallon.service;

import com.example.batallon.dao.PilotDAO;
import com.example.batallon.model.Pilot;
import java.sql.SQLException;
import java.util.List;

public class PilotService {
    private PilotDAO pilotDAO = new PilotDAO();

    public void addPilot(Pilot pilot) throws SQLException {
        pilotDAO.insertPilot(pilot);
    }

    public List<Pilot> getAllPilots() throws  SQLException {
        return pilotDAO.selectAllPilots();
    }

    public Pilot getPilotById(int id) throws SQLException {
        return pilotDAO.selectPilot(id);
    }

    public boolean updatePilot(Pilot pilot) throws SQLException {
        return pilotDAO.updatePilot(pilot);
    }

    public boolean deletePilot(int id) throws SQLException {
        return pilotDAO.deletePilot(id);
    }
}
