
# Proyecto: Gestión de Batallón de Pilotos — Java (MVC) — IntelliJ + Oracle + Bootstrap :

<img width="203" height="193" alt="image" src="https://github.com/user-attachments/assets/acd0ce8a-579d-497b-a76e-797430d0bb13" />  

## 📌 Descripción
Aplicación web desarrollada en **Java (Maven, Servlets + JSP)** con el patrón **Modelo-Vista-Controlador (MVC)**, 
para gestionar la información de un **batallón de pilotos militares** .  
La información se almacena en una **base de datos Oracle** y la interfaz está diseñada con **Bootstrap** para mejorar la experiencia de usuario .

<img width="2559" height="1039" alt="image" src="https://github.com/user-attachments/assets/27ec9da1-e2f4-4e73-bb0c-53ea4f8b5b27" />

<img width="2552" height="1079" alt="image" src="https://github.com/user-attachments/assets/4101d6d6-af1c-41ca-9f52-ae7e1ec34838" />

---

## 🏗️ Arquitectura del Proyecto

- **Modelo (`Pilot.java`)** → Representa la entidad piloto.
- **DAO (`PilotDAO.java`)** → Acceso a datos mediante JDBC y Oracle.
- **Servicio (`PilotService.java`)** → Lógica de negocio sobre los datos de pilotos.
- **Controlador (`PilotServlet.java`)** → Gestiona las peticiones del usuario.
- **Vistas (JSP + Bootstrap)** → Interfaz amigable para CRUD.

---

## 📂 Estructura del Proyecto

```
Proyecto_Batallon_Pilotos/
│── src/main/java/com/example/batallon/model/Pilot.java
│── src/main/java/com/example/batallon/dao/PilotDAO.java
│── src/main/java/com/example/batallon/service/PilotService.java
│── src/main/java/com/example/batallon/web/PilotServlet.java
│── src/main/webapp/WEB-INF/views/list-pilots.jsp
│── src/main/webapp/WEB-INF/views/form-pilot.jsp
│── src/main/webapp/index.jsp
│── pom.xml
```

---

## 📑 Código Fuente

### `Pilot.java` (Modelo)
```java
package com.example.batallon.model;

public class Pilot {
    private int id;
    private String name;
    private String rank;
    private int age;

    public Pilot() {}

    public Pilot(int id, String name, String rank, int age) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.age = age;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRank() { return rank; }
    public void setRank(String rank) { this.rank = rank; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

---

### `PilotDAO.java` (DAO - JDBC + Oracle)
```java
package com.example.batallon.dao;

import com.example.batallon.model.Pilot;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PilotDAO {
    private String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
    private String jdbcUsername = "system";
    private String jdbcPassword = "oracle";

    private static final String INSERT_SQL = "INSERT INTO pilots (name, rank, age) VALUES (?, ?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM pilots";
    private static final String SELECT_BY_ID = "SELECT * FROM pilots WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE pilots SET name=?, rank=?, age=? WHERE id=?";
    private static final String DELETE_SQL = "DELETE FROM pilots WHERE id=?";

    protected Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
    }

    public void insertPilot(Pilot pilot) throws SQLException {
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

    public boolean deletePilot(int id) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}
```

---

### `PilotService.java` (Servicio)
```java
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

    public List<Pilot> getAllPilots() throws SQLException {
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
```

---

### `PilotServlet.java` (Controlador)
```java
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
            throws ServletException, IOException {
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
```

---

### `list-pilots.jsp` (Vista principal - listar pilotos)
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Pilotos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
    <h2>Lista de Pilotos</h2>
    <a href="pilots?action=new" class="btn btn-primary mb-3">➕ Agregar Piloto</a>
    <table class="table table-bordered">
        <thead>
            <tr><th>ID</th><th>Nombre</th><th>Rango</th><th>Edad</th><th>Acciones</th></tr>
        </thead>
        <tbody>
        <c:forEach var="pilot" items="${listPilots}">
            <tr>
                <td>${pilot.id}</td>
                <td>${pilot.name}</td>
                <td>${pilot.rank}</td>
                <td>${pilot.age}</td>
                <td>
                    <a href="pilots?action=edit&id=${pilot.id}" class="btn btn-warning btn-sm">✏️ Editar</a>
                    <a href="pilots?action=delete&id=${pilot.id}" class="btn btn-danger btn-sm">🗑 Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
```

---

### `form-pilot.jsp` (Vista formulario - agregar/editar)
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario Piloto</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
    <h2>${pilot != null ? "Editar Piloto" : "Agregar Piloto"}</h2>
    <form action="pilots" method="get">
        <input type="hidden" name="action" value="${pilot != null ? "update" : "insert"}"/>
        <c:if test="${pilot != null}">
            <input type="hidden" name="id" value="${pilot.id}"/>
        </c:if>
        <div class="mb-3">
            <label>Nombre</label>
            <input type="text" name="name" class="form-control" value="${pilot != null ? pilot.name : ""}" required/>
        </div>
        <div class="mb-3">
            <label>Rango</label>
            <input type="text" name="rank" class="form-control" value="${pilot != null ? pilot.rank : ""}" required/>
        </div>
        <div class="mb-3">
            <label>Edad</label>
            <input type="number" name="age" class="form-control" value="${pilot != null ? pilot.age : ""}" required/>
        </div>
        <button type="submit" class="btn btn-success">💾 Guardar</button>
        <a href="pilots" class="btn btn-secondary">↩ Volver</a>
    </form>
</body>
</html>
```

---

## 🛠️ Instalación y Despliegue :

### 1️⃣ Requisitos Previos
- **Java JDK 17+**
- **Apache Tomcat 10+**
- **Oracle Database 19c XE** (o superior)
- **IntelliJ IDEA Ultimate** (para soporte de JSP/Servlets)
- **Maven 3.9+**

### 2️⃣ Configuración de la Base de Datos Oracle
Ejecutar en Oracle SQL Developer:
```sql
CREATE TABLE pilots (
    id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    rank VARCHAR2(50) NOT NULL,
    age NUMBER NOT NULL
);
```

### 3️⃣ Clonar el proyecto e importar en IntelliJ
```bash
git clone https://github.com/usuario/proyecto_batallon_pilotos.git
cd proyecto_batallon_pilotos
```

Importar como **proyecto Maven** en IntelliJ.

### 4️⃣ Configuración de Tomcat en IntelliJ
- Ir a **Run → Edit Configurations → Add New Configuration → Tomcat Server**.
- Seleccionar el proyecto como **Deployment Artifact**.
- Configurar URL base: `http://localhost:8080/proyecto_batallon_pilotos`.

### 5️⃣ Compilar y Ejecutar
```bash
mvn clean install
```

Levantar Tomcat desde IntelliJ y acceder a :
```
http://localhost:8080/proyecto_batallon_pilotos/pilots
```

---

## ✅ Resultado Esperado :

Una aplicación web con las siguientes funcionalidades:
- Listar pilotos.
- Agregar un nuevo piloto.
- Editar piloto existente.
- Eliminar piloto.

---

📌 Autor: *Giovanny Alejandro Tapiero Cataño*  
🚀 Proyecto educativo — Arquitectura MVC con Java + Oracle + JSP + Servlets + Bootstrap .
