<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <link rel="stylesheet" href="./assets/style/style.css">
  <link rel="shortcut icon" href="./assets/imgs/logo.png" type="image/x-icon">
  <title>Login</title>
</head>

<body>
<div class="voltar">
  <a href="https://lucasramosdecarvalho.github.io/Linus_landing_page/" target="_blank"><p>Voltar para home</p></a>
</div>
<div class="largest-container">
  <div class="title">
    <img src="./assets/imgs/logo.png">
    <h1>Instituto <br> Linus</h1>
  </div>
  <div class="main-container">
    <form action="${pageContext.request.contextPath}/login" method="post">
      <div>
        <label for="email">Email</label>
        <input type="text" name="email" placeholder="Digite seu email">
        <label for="senha">Senha</label>
        <input type="password" name="senha" placeholder="Digite sua senha">
      </div>
      <button>Login</button>
      <hr>
      <p>É aluno? Faça seu primeiro acesso <a href="${pageContext.request.contextPath}/aluno/primeiro-acesso">aqui</a></p>
      <br>
      <a class="login-forget" href="${pageContext.request.contextPath}/recuperar-senha">Esqueci minha senha</a>

      <% if (request.getAttribute("error") != null) { %>
      <div class="error">
        <%= request.getAttribute("error") %>
      </div>
      <%} %>
    </form>
  </div>
</div>
</body>

</html>