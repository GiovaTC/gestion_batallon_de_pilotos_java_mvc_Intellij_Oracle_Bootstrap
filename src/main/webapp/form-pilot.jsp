<%--
  Created by IntelliJ IDEA.
  User: USUARIO
  Date: 29/09/2025
  Time: 4:52 p. m.
  To change this template use File | Settings | File Templates.
--%>
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
