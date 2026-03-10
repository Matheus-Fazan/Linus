<%@ page import="com.linus.model.dao.Professor" %>
<%@ page import="com.linus.dto.ProfessorDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Editar Professor</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoesProfessor.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

  <% ProfessorDto professor = (ProfessorDto) request.getAttribute("professor");%>

<body>
<header>
  <div class="logo">
    <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
    <h3>Instituto linus</h3>
  </div>

  <nav class="nav-header">
    <ul>
      <li>
        <a href="${pageContext.request.contextPath}/admin/pagPrincipal.jsp">Página inicial</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/visualizar.jsp">Visualização</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/criarAcesso.jsp">Criar Acesso</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/perfil.jsp">Perfil</a>
      </li>
      <li>
        <a href="${pageContext.request.contextPath}/admin/index.jsp">Logout</a>
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

      <form action="" class="form-perfil">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" value="<%= professor.nome %>">
        <label for="user">Usuário:</label>
        <input type="text" id="user" value="<%= professor.usuario %>">
        <label for="email">Email:</label>
        <input type="email" id="email" value="<%= professor.email %>">
        <label for="idMateria">Matéria:</label>
        <input type="text" id="idMateria" value="<%= professor.idMateria %>">
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