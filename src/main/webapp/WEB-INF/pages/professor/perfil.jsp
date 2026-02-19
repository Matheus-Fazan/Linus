<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/src/main/webapp/WEB_INF/assets/style/perfil.css">
    <link rel="stylesheet" href="/src/main/webapp/WEB-INF/assets/style/style.css">
    <link rel="stylesheet" href="/src/main/webapp/WEB-INF/assets/style/crud_geral.css">
    <title>Perfil Professor</title>
</head>

<header>
    <div class="imagem">
        <img id="logo_colegio" src="/src/main/webapp/WEB-INF/assets/imgs/logoColegio.png" alt="Logo colegio">
        <h3>Instituto de <br> Tecnologia</h3>
    </div>

    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexPagPrincipal.jsp">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexObservacao.jsp">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/area-restrita/indexPerfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="index.html">Logout</a>
            </li>
        </ul>
    </nav>

</header>

<body>
    <div iclass="topo">
        <div id="local">
            <h1>Perfil!</h1>
            <p>Visualize suas informações e as edite.</p>
        </div>
    </div>
    <div class="largest-container">
        <div class="main-container">
            <form action="">
                <div>
                    <label for="user">Nome:</label>
                    <input type="text" name="Nome" placeholder="Digite seu nome">
                    <label for="senha">Email:</label>
                    <input type="email" name="Email" placeholder="Digite seu email">
                    <label for="user">Disciplina:</label>
                    <input type="text" name="disciplina" placeholder="Digite sua disciplina">
                </div>
            </form>
        </div>
    </div>
    </div>
</body>

</html>