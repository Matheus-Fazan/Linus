<%@ page import="com.linus.dto.ProfessorPerfilDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Editar Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<% ProfessorPerfilDto professor = (ProfessorPerfilDto) request.getAttribute("professor");%>

<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3>Instituto Linus</h3>
    </div>

    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/admin/dashboard">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/visualizar">Visualização</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/criar-acesso">Criar Acesso</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/perfil">Perfil</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </nav>

</header>

<div class="largest-container">
    <div class="barra-inicial" id="s1">
        <h2>Edite o perfil do professor</h2>
        <p>Visualize suas informações e as edite.</p>
    </div>

    <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>
    <% if (encontrado != null && encontrado) { %>
    <div class="main-container">
        <div class="info-aluno">
            <p class="linha"><strong>ID:</strong> <%= professor.getId() %></p>
            <p class="linha"><strong>Nome:</strong> <%= professor.nome %></p>
            <p class="linha"><strong>Usuário:</strong> <%= professor.usuario %></p>
            <p class="linha"><strong>Disciplina:</strong> <%= professor.getDisciplina() %></p>
            <p class="linha"><strong>Email Atual:</strong> <%= professor.email %></p>


            <form action="${pageContext.request.contextPath}/admin/alterar-email-professor" method="POST" class="form-perfil">
                <input type="hidden" name="id" value="<%= professor.getId() %>">
                <label>Novo Email:</label>
                <input type="email" name="email" placeholder="Digite o novo email" required>
                <button type="submit">Alterar Email</button>
            </form>
        </div>
    </div>
    <% } else if (encontrado != null && !encontrado) { %>

    <div class="nao-encontrado">
        <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
        <h2>Nenhum professor foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</div>


</body>

</html>