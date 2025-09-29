package com.example.batallon.web;

import com.example.batallon.model.Pilot;
import com.example.batallon.service.PilotService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/pilots")
public class PilotServlet extends HttpServlet {
    private PilotService pilotService;

    @Override
    public void init() {
        pilotService = new PilotService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  ServletException, IOException {
        String action = request.getParameter("action");
        try {
            switch (action == null ? "list" : action) {
                case "new": showNewForm(request, response); break;
                case "insert": insertPilot(request, response); break;
                case "delete": deletePilot(request, response); break;
                case "edit": showEditForm(request, response); break;
                case "update": updatePilot(request, response); break;
                default: listPilots(request, response); break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
    private void listPilots(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        List<Pilot> listPilots = pilotService.getAllPilots();
        request.setAttribute("listPilots", listPilots);
        request.getRequestDispatcher("WEB-INF/views/list-pilots.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("WEB-INF/views/form-pilot.jsp").forward(request, response);
    }

    private void insertPilot(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String name = request.getParameter("name");
        String rank = request.getParameter("rank");
        int age = Integer.parseInt(request.getParameter("age"));
        pilotService.addPilot(new Pilot(0, name, rank, age));
        response.sendRedirect("pilots");
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Pilot existingPilot = pilotService.getPilotById(id);
        request.setAttribute("pilot", existingPilot);
        request.getRequestDispatcher("WEB-INF/views/form-pilot.jsp").forward(request, response);
    }

    private void updatePilot(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String rank = request.getParameter("rank");
        int age = Integer.parseInt(request.getParameter("age"));
        pilotService.updatePilot(new Pilot(id, name, rank, age));
        response.sendRedirect("pilots");
    }

    private void deletePilot(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        pilotService.deletePilot(id);
        response.sendRedirect("pilots");
    }
}
