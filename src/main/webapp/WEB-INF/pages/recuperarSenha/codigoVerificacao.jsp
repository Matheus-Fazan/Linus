<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Código de verificação</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/recuperarSenha.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">

</head>
<body>
<main>
    <section class="main-content">
        <section class="login-container">
            <h1>Código de verificação</h1>
            <p>Digite o código que enviamos em seu e-mail para redefinir a senha.</p>


            <form class="login-form" method="post" action="${pageContext.request.contextPath}/verificar-codigo">
                <input type="text" inputmode="numeric" maxlength="6" placeholder="------" name="codigo" class="login-input" required>
                <% if (request.getAttribute("error") != null) { %>
                <div class="error">
                    <%= request.getAttribute("error") %>
                </div>
                <%} %>
                <button type="submit" class="login-button">Continuar</button>
            </form>
        </section>
    </section>
</main>

</body>
</html>