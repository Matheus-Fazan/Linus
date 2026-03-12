<%@ page import="com.linus.model.dao.Aluno" %>
<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Editar aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<% AlunoDto aluno = (AlunoDto) request.getAttribute("aluno");%>

<body>
<jsp:include page="headerAdmin.jsp"/>

<div class="largest-container">
    <div class="subtitle">
        <h2>Edite o perfil do aluno</h2>
        <p>Visualize suas informações e as edite.</p>
    </div>
    <input type="text" class="search-bar"
           placeholder="Pesquise para ver se já tem um aluno/professor cadastrado:">
    <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>
    <% if (encontrado != null && encontrado) { %>
    <div class="main-container">
        <form action="" class="form-perfil">
            <label>Matrícula:</label>
            <input type="text" name="matricula" value="<%= aluno.matricula %>">
            <label>Nome:</label>
            <input type="text" name="nome" value="<%= aluno.nome %>">
            <label>Email:</label>
            <input type="email" name="email" value="<%= aluno.email %>">
            <label>CPF:</label>
            <input type="text" name="cpf" value="<%= aluno.cpf %>">
            <label>Turma:</label>
            <input type="text" name="idTurma" value="<%= aluno.turmaDto %>">
            <button type="submit">Salvar Alterações</button>
        </form>
    </div>
    <% } else if (encontrado != null && !encontrado) { %>

    <div class="nao-encontrado">
        <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
        <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</div>


</body>

</html>