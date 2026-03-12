<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.linus.dto.AdminPerfilDto" %>

        <% AdminPerfilDto perfil=(AdminPerfilDto) request.getAttribute("perfil"); %>


            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
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
                                <a href="${pageContext.request.contextPath}/admin/pagPrincipal">Página inicial</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/dashboard">Visualização</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/adicionar-aluno">Criar Acesso</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/admin/perfil">Perfil</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/logout">Logout</a>
                            </li>
                        </ul>
                    </nav>
                </header>

                <div class="container">

                    <div class="topo">
                        <h1>Veja seu Perfil</h1>
                        <p>Visualize suas informações.</p>
                    </div>

                    <% if (request.getAttribute("error") !=null) { %>

                        <div class="error-box">
                            <p>
                                <%= request.getAttribute("error") %>
                            </p>
                        </div>

                        <% } else if (perfil !=null) { %>

                            <center>
                                <form action="atualizarPerfil" method="post" class="perfil-card">

                                    <div class="perfil-linha">
                                        <label class="perfil-label" for="email">Email</label>

                                        <input
                                                type="email"
                                                id="email"
                                                name="email"
                                                class="perfil-input"
                                                value="<%= perfil.getEmail() %>"
                                                required
                                        >
                                    </div>

                                    <button type="submit" class="btn-salvar">
                                        Salvar alterações
                                    </button>

                                </form>
                            </center>

                            <% } %>

                </div>

            </body>

            </html>