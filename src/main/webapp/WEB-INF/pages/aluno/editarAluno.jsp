<%@ page import="com.linus.model.dao.Aluno" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
  <title>Editar aluno</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoesProfessor.css">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
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
<% Aluno aluno = (Aluno) request.getAttribute("aluno");%>
<div class="largest-container">
  <div class="subtitle">
    <h2>Edite o perfil do aluno</h2>
    <p>Visualize suas informações e as edite.</p>
  </div>
  <input type="text" class="search-bar" placeholder="Pesquise para ver se já tem um aluno/professor cadastrado:">
  <div class="main-container">
    <form action="" class="form-perfil">
      <label>Matrícula:</label>
      <input type="text" name="matricula" value="<%= aluno.getMatricula() %>">
      <label>Nome:</label>
      <input type="text" name="nome" value="<%= aluno.getNome() %>">
      <label>Email:</label>
      <input type="email" name="email" value="<%= aluno.getEmail() %>">
      <label>CPF:</label>
      <input type="text" name="cpf" value="<%= aluno.getCpf() %>">
      <label>Turma:</label>
      <input type="text" name="idTurma" value="<%= aluno.getIdTurma() %>">
      <button type="submit">Salvar Alterações</button>
    </form>
  </div>
</div>


</body>

</html>