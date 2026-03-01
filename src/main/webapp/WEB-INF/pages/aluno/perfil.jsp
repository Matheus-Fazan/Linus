<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/pagPrincipal.jsp">Página
                    inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/observacaoAluno.jsp">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/perfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="">Logout</a>
            </li>
        </ul>
    </nav>

</header>
<div class="largest-container">

    <div class="topo">
        <h1>Perfil</h1>
        <p>Visualize suas informações e as edite.</p>
    </div>
    <div class="main-container">
        <form action="">
            <label for="user">Matrícula:</label>
            <input type="text" name="matricula">
            <label for="senha">Nome:</label>
            <input type="password" name="nome" placeholder="Digite seu nome">
            <label for="user">Email:</label>
            <input type="email" name="email" placeholder="Digite seu email">
            <label for="cpf">CPF:</label>
            <input type="text" name="cpf" placeholder="Digite seu cpf">
            <label for="user">Situação:</label>
            <input type="text" name="situacao">
            <label for="senha">Turma:</label>
            <input type="text" name="turma">
        </form>
    </div>
</div>
</body>

</html>