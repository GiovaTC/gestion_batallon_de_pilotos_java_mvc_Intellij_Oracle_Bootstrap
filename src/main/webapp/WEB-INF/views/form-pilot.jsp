<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulario Piloto</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body class="container mt-4">
<h2>
    <c:choose>
        <c:when test="${pilot != null}">Editar Piloto</c:when>
        <c:otherwise>Agregar Piloto</c:otherwise>
    </c:choose>
</h2>

<form action="pilots" method="post">
    <input type="hidden" name="action"
           value="<c:out value='${pilot != null ? "update" : "insert"}'/>"/>

    <c:if test="${pilot != null}">
        <input type="hidden" name="id" value="${pilot.id}"/>
    </c:if>

    <div class="mb-3">
        <label>Nombre</label>
        <input type="text" name="name" class="form-control"
               value="<c:out value='${pilot.name}'/>" required/>
    </div>
    <div class="mb-3">
        <label>Rango</label>
        <input type="text" name="rank" class="form-control"
               value="<c:out value='${pilot.rank}'/>" required/>
    </div>
    <div class="mb-3">
        <label>Edad</label>
        <input type="number" name="age" class="form-control"
               value="<c:out value='${pilot.age}'/>" required/>
    </div>

    <button type="submit" class="btn btn-success">💾 Guardar</button>
    <a href="pilots?action=list" class="btn btn-secondary">↩ Volver</a>
</form>
</body>
</html>
