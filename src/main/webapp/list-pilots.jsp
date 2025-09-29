<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 29/09/2025
  Time: 4:52 p. m.
  To change this template use File | Settings | File Templates.
--%>
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
