<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Adicionar Notas</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/style.css">

</head>
<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto Linus </h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/professor/adicionarNotas">Adicionar
                    Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/professor/editarNotas">Editar Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/professor/perfil">Perfil</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </nav>
</header>
<div class="largest-container">
    <div class="subtitle" id="s1">
        <h2>Adicionar notas</h2>
        <p>adicione as notas do aluno.</p>
    </div>
    <input type="text" class="search-bar"
           placeholder="Pesquise o aluno que você queira adicionar a nota:">
    <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>
    <% if (encontrado != null && encontrado) { %>
    <%AlunoDto aluno = (AlunoDto) request.getAttribute("aluno"); %>
    <div class="main-container">
        <form action="" method="post">
            <div>
                <label for="nome">Nome</label>
                <input type="text" name="nome" value="<%= aluno.nome %>" disabled>
                <label for="n1">Nota 1</label>
                <input type="text" name="n1" placeholder="Digite a nota 1">
                <label for="n2">Nota 2</label>
                <input type="text" name="n2" placeholder="Digite a nota 2">
            </div>
            <button type="submit">Adicionar</button>
        </form>
    </div>
    <% } else if (encontrado != null && !encontrado) { %>

    <div class="nao-encontrado">
        <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png" width="200" alt="not found">
        <h2 style="color: #4FB2D9;">Nenhum professor foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</div>
</body>
</html>
