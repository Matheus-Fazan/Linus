
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/crud_geral.css">
    <title>Criar Acesso</title>
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
                <a href="${pageContext.request.contextPath}/admin/pagPrincipal.jsp">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/criarAcesso.jsp">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/admin/perfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="index.html">Logout</a>
            </li>
        </ul>
    </nav>
</header>

<div class="topo">
    <h1>Crie um novo acesso</h1>
    <p>Visualize suas informações e as edite.</p>
</div>

<div class="acess">
    <div class="form-perfil">
        <div class="main-container">
            <h2 class="acess_title">Crie um novo aluno</h2>
            <form action="">
                <label for="nome">Nome:</label>
                <input type="text" name="nome" placeholder="Digite o nome do aluno">
                <label for="turma">Turma:</label>
                <input type="text" id="turma" placeholder="Digite a turma">
            </form>
            <button type="submit">Salvar</button>
        </div>
    </div>

    <div class="form-perfil">
        <div class="main-container">
            <h2 class="acess_title">Crie um novo professor</h2>
            <form action="">
                <label for="nome">Nome:</label>
                <input type="text" id="nome" placeholder="Digite o nome professor">
                <label for="usuario">Usuário:</label>
                <input type="text" id="usuario" placeholder="Digite o nome do usuário">
                <label for="disciplina">Disciplina:</label>
                <input type="text" id="disciplina" placeholder="Digite a disciplina do professor">
                <label for="senha">Senha:</label>
                <input type="password" id="senha" placeholder="Digite a senha do professor">
            </form>
            <button type="submit">Salvar</button>
        </div>
    </div>
</div></body>
</html>
