<%@ page import="com.linus.model.dao.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Editar Professor</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoesProfessor.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
<header>
  <div class="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
    <h3>Instituto linus</h3>
  </div>

  <nav class="nav-header">
    <ul>
      <li>
        <a href="">Página inicial</a>
      </li>
      <li>
        <a href="">Criar Acesso</a>
      </li>
      <li>
        <a href="">Perfil</a>
      </li>
      <li>
        <a href="">Logout</a>
      </li>
    </ul>
  </nav>

</header>
<% Professor professor = (Professor) request.getAttribute("professor");%>
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
    <form action="" class="form-perfil">
      <label for="nome">Nome:</label>
      <input type="text" name="nome" value="<%= professor.getNome() %>">
      <label for="user">Usuário:</label>
      <input type="text" name="user" value="<%= professor.getUsuario() %>">
      <label for="email">Email:</label>
      <input type="email" name="email" value="<%= professor.getEmail() %>">
      <label for="idMateria">Matéria:</label>
      <input type="text" name="idMateria" value="<%= professor.getIdMateria() %>">
      <button type="submit">Salvar Alterações</button>
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