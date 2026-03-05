<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Nova senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/recuperarSenha.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">

</head>
<body>
<main>
    <section class="main-content">
        <section class="login-container">
            <h1>Cadastro nova senha</h1>
            <p>Digite uma nova senha e insira duas vezes para confirmar.</p>

            <form class="login-form" method="post" action="${pageContext.request.contextPath}/redefinir-senha">
                <input type="password" placeholder="Digite uma senha" name="password" class="login-input" required>
                <input type="password" placeholder="Digite a senha novamente" name="passwordConfirm" class="login-input" required>

                <% if (request.getAttribute("error") != null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <%} %>
                <button type="submit" class="login-button">Confirmar</button>
            </form>
        </section>
    </section>
</main>
</body>
</html>