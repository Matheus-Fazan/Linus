<%@ page import="com.linus.model.dao.Aluno" %>
<%@ page import="com.linus.dto.AlunoPerfilDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Editar aluno</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/style.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoesProfessor.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<% AlunoPerfilDto aluno=(AlunoPerfilDto) request.getAttribute("aluno");%>

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
  <div class="subtitle">
    <h2>Edite o perfil do aluno</h2>
    <p>Visualize suas informações e as edite.</p>
  </div>
  <input type="text" class="search-bar"
         placeholder="Pesquise para ver se já tem um aluno/professor cadastrado:">
  <% Boolean encontrado=(Boolean) request.getAttribute("encontrado"); %>
  <% if(encontrado !=null && encontrado){ %>
  <div class="main-container">
    <div class="info-aluno">
      <p><strong>Matrícula:</strong> <%= aluno.getMatricula() %></p>
      <p><strong>Nome:</strong> <%= aluno.getNome() %></p>
      <p><strong>CPF:</strong> <%= aluno.getCpf() %></p>
      <p><strong>Turma:</strong> <%= aluno.getTurma() %></p>
      <p><strong>Email Atual:</strong> <%= aluno.getEmail() %></p>
    </div>
    
    <form action="${pageContext.request.contextPath}/admin/alterar-email-aluno" method="POST" class="form-perfil">
      <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">
      <label>Novo Email:</label>
      <input type="email" name="email" placeholder="Digite o novo email" required>
      <button type="submit">Alterar Email</button>
    </form>
  </div>
  <% } else if(encontrado !=null && !encontrado){ %>

  <div class="nao-encontrado">
    <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
    <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado, <br>
      Pesquise novamente</h2>
  </div>

  <% } %>
</div>


</body>

</html>