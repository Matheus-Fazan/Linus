<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/primeiro-acesso.css">
    <title>Primeiro Acesso</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
    <div class="voltar">
        <img src="${pageContext.request.contextPath}/assets/imgs/seta.png" alt="seta">
        <p>Voltar para home</p>
    </div>
    <div class="largest-container">
        <div class="title">
            <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
            <h1>Instituto Linus</h1>
        </div>
        <div class="main-container">
            <form action="">
                <h2>Primeiro Acesso</h2>
                <label for="nome">Nome</label>
                <input type="text" name="nome" placeholder="Digite seu nome">
                <label for="email">Email</label>
                <input type="email" name="email" placeholder="Digite seu email">
                <label for="senha">Senha</label>
                <input type="password" name="senha" placeholder="Digite sua senha">
                <label for="cpf">CPF</label>
                <input type="text" name="cpf" placeholder="Digite seu CPF">
                <label for="matricula">Matricula</label>
                <input type="text" name="matricula" placeholder="Digite sua matricula">
                <button>Login</button>
            </form>
        </div>
    </div>
</body>

</html>