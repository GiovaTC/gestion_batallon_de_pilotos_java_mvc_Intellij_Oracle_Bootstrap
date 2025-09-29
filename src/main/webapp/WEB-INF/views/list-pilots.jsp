<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Pilotos</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2>👨‍✈️ Lista de Pilotos</h2>
<a href="pilots?action=new" class="btn btn-success mb-3">➕ Agregar Piloto</a>

<c:if test="${empty listPilots}">
    <div class="alert alert-warning">No hay pilotos registrados.</div>
</c:if>

<c:if test="${not empty listPilots}">
    <table class="table table-bordered table-striped">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Rango</th>
            <th>Edad</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="pilot" items="${listPilots}">
            <tr>
                <td><c:out value="${pilot.id}"/></td>
                <td><c:out value="${pilot.name}"/></td>
                <td><c:out value="${pilot.rank}"/></td>
                <td><c:out value="${pilot.age}"/></td>
                <td>
                    <a href="pilots?action=edit&id=${pilot.id}" class="btn btn-warning btn-sm">✏ Editar</a>
                    <a href="pilots?action=delete&id=${pilot.id}" class="btn btn-danger btn-sm"
                       onclick="return confirm('¿Seguro que deseas eliminar este piloto?')">🗑 Eliminar</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<a href="index.jsp" class="btn btn-secondary">🏠 Inicio</a>
</body>
</html>
