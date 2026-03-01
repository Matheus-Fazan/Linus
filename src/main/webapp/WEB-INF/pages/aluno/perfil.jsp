<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.linus.dto.AlunoPerfilDto" %>
<%
    AlunoPerfilDto perfil = (AlunoPerfilDto) request.getAttribute("perfil");
%>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/crud_geral.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png"
          type="image/x-icon">
    <title>Perfil Aluno</title>
</head>

<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3>Instituto Linus</h3>
    </div>

    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexPagPrincipal.jsp">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexObservacao.jsp">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexPerfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="index.html">Logout</a>
            </li>
        </ul>
    </nav>
</header>

<div class="container">

    <div class="topo">
        <h1>Veja seu Perfil</h1>
        <p>Visualize suas informações.</p>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error-message"><%= request.getAttribute("error") %></p>
    <% } else if (perfil != null) { %>
    <div class="main-container">
        <div class="perfil-info">
            <div class="perfil-campo">
                <span class="perfil-label">Matrícula:</span>
                <span class="perfil-valor"><%= perfil.getMatricula() %></span>
            </div>
            <div class="perfil-campo">
                <span class="perfil-label">Nome:</span>
                <span class="perfil-valor"><%= perfil.getNome() %></span>
            </div>
            <div class="perfil-campo">
                <span class="perfil-label">Email:</span>
                <span class="perfil-valor"><%= perfil.getEmail() %></span>
            </div>
            <div class="perfil-campo">
                <span class="perfil-label">CPF:</span>
                <span class="perfil-valor"><%= perfil.getCpf() %></span>
            </div>
            <div class="perfil-campo">
                <span class="perfil-label">Situação:</span>
                <span class="perfil-valor"><%= perfil.getSituacao() %></span>
            </div>
            <div class="perfil-campo">
                <span class="perfil-label">Turma:</span>
                <span class="perfil-valor"><%= perfil.getTurma() %></span>
            </div>
        </div>
    </div>
    <% } %>

</div>
</body>

</html>
