<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/style.css">
    <title>Primeiro Acesso</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
    <a href="${pageContext.request.contextPath}/index.jsp">
        <div class="voltar">
            <img src="${pageContext.request.contextPath}/assets/imgs/seta.png" alt="seta">
            <p>Voltar para home</p>
        </div>
    </a>
    <div class="largest-container">
        <div class="title">
            <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
            <h1>Instituto Linus</h1>
        </div>
        <div class="main-container" id="mc2">
            <form action="${pageContext.request.contextPath}/aluno/primeiro-acesso" method="POST">
                <h2>Primeiro Acesso</h2>
                <label for="email">Email</label>
                <input type="email" name="email" placeholder="Digite seu email">
                <label for="senha">Senha</label>
                <input type="password" name="senha" placeholder="Digite sua senha">
                <label for="matricula">Matricula</label>
                <input type="text" name="matricula" placeholder="Digite sua matricula">
                <button>Login</button>
            </form>
        </div>
    </div>

    <% if (request.getAttribute("error") != null) {
        String messege = (String) request.getAttribute("error");
        request.setAttribute("error", null);
    %>
    <div id="toast-erro" style="position: fixed; bottom: 20px; left: -400px; width: 350px; background: #ff4d4f; color: white; padding: 16px 20px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.2); transition: left 0.4s ease; z-index: 9999;">
        <%= messege %>
        <script>setTimeout(() => document.getElementById('toast-erro').style.left = '20px', 100);</script>
    </div>
    <% } %>

    <% if (request.getAttribute("sucess") != null) {
        String messege = (String) request.getAttribute("sucess");
        request.setAttribute("sucess", null);
    %>
    <div id="toast-erro" style="position: fixed; bottom: 20px; left: -400px; width: 350px; background: darkgreen; color: white; padding: 16px 20px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.2); transition: left 0.4s ease; z-index: 9999;">
        <%= messege %>
        <script>setTimeout(() => document.getElementById('toast-erro').style.left = '20px', 100);</script>
    </div>
    <% } %>
</body>

</html>