<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Esqueci minha senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/recuperarSenha.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">

</head>
<body>
<main>
    <section class="main-content">
        <section class="login-container">
            <h1>Esqueceu a sua senha?</h1>
            <p>Insira seu endereço de e-mail e enviaremos um código para redefinir a senha.</p>

            <form class="login-form" method="post" action="${pageContext.request.contextPath}/recuperar-senha">
                <input type="email" placeholder="Endereço de email" name="email" class="login-input" required>
                <% if (request.getAttribute("error") != null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <%} %>
                <button type="submit" class="login-button">Enviar código</button>
            </form>
        </section>
    </section>
</main>

</body>
</html>