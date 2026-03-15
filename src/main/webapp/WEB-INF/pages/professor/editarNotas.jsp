<%@ page import="com.linus.dto.NotaDto" %>
<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Editar Notas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoes.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>
<body>
<jsp:include page="headerProfessor.jsp"/>

<div class="largest-container">
    <div class="subtitle" id="s1">
        <h2>Edite as notas dos alunos</h2>
        <p>Visualize e edite as informações do aluno.</p>
    </div>
    <input type="text" class="search-bar"
           placeholder="Pesquise o aluno que você queira editar a nota:">

    <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>
    <% if(encontrado !=null && encontrado){ %>
    <%AlunoDto aluno = (AlunoDto) request.getAttribute("aluno"); %>
    <%NotaDto nota = (NotaDto) request.getAttribute("nota"); %>
    <div class="main-container" id="m1">

        <form action="" class="form-perfil">
            <label for="nome">Nome</label>
            <input type="text" id="nome" value="<%= aluno.nome %>" disabled>
            <label for="n1">Nota 1</label>
            <input type="text" id="n1" value="<%= nota.n1 %>">
            <label for="n2">Nota 2</label>
            <input type="text" id="n2" value="<%= nota.n2 %>">
            <button type="submit">Salvar Alterações</button>
        </form>

    </div>
    <% } else if(encontrado !=null && !encontrado){ %>

    <div class="nao-encontrado">
        <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
        <h2>Nenhum professor foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</div>
</body>
</html>
