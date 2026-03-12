<%@ page import="com.linus.model.dao.Professor" %>
<%@ page import="com.linus.dto.ProfessorPerfilDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <title>Editar Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoes.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<% ProfessorPerfilDto professor = (ProfessorPerfilDto) request.getAttribute("professor");%>

<body>
<header>
  <div class="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
    <h3>Instituto linus</h3>
  </div>

  <nav class="nav-header">
    <ul>
      <li>
        <a href="${pageContext.request.contextPath}/admin/pagPrincipal">Página inicial</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Visualização</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/adicionar-aluno">Criar Acesso</a>
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
  <div class="subtitle" id="s1">
    <h2>Edite o perfil do professor</h2>
    <p>Visualize suas informações e as edite.</p>
  </div>
  <input type="text" class="search-bar"
         placeholder="Pesquise para ver se já tem um aluno/professor cadastrado:">

  <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>
  <% if(encontrado !=null && encontrado){ %>
  <div class="main-container" id="m1">
    <div class="info-professor">
      <p><strong>Nome:</strong> <%= professor.nome %></p>
      <p><strong>Usuário:</strong> <%= professor.usuario %></p>
      <p><strong>Disciplina:</strong> <%= professor.getDisciplina() %></p>
      <p><strong>Email Atual:</strong> <%= professor.email %></p>
    </div>

    <form action="${pageContext.request.contextPath}/admin/alterar-email-professor" method="POST" class="form-perfil">
      <input type="hidden" name="id" value="<%= professor.getId() %>">
      <label for="email">Novo Email:</label>
      <input type="email" id="email" name="email" placeholder="Digite o novo email" required>
      <button type="submit">Alterar Email</button>
    </form>

  </div>
  <% } else if(encontrado !=null && !encontrado){ %>

  <div class="nao-encontrado">
    <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
    <h2 style="color: #4FB2D9;">Nenhum professor foi encontrado, <br>
      Pesquise novamente</h2>
  </div>

  <% } %>
</div>


</body>

</html>