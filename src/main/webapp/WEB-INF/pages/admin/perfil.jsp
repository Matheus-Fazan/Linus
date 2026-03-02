<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="com.linus.dto.AdminDto" %>

        <% AdminDto perfil=(AdminDto) request.getAttribute("perfil"); %>


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
                        <img src="/src/main/webapp/assets/imgs/logo.png" alt="logo colegio">
                        <h3>Instituto Linus</h3>
                    </div>

                    <nav class="nav-header">
                        <ul>
                            <li>
                                <a href="${pageContext.request.contextPath}/WEB-INF/pages/admin/pagPrincipal.jsp">Página
                                    inicial</a>
                            </li>
                            <li>
                                <a
                                    href="${pageContext.request.contextPath}/WEB-INF/pages/admin/criarAcesso.jsp">Observação</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/WEB-INF/pages/admin/perfil.jsp">Perfil</a>
                            </li>
                            <li>
                                <a href="index.html">Logout</a>
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
                                <section class="perfil-card">
                                    <h2>Perfil</h2>

                                    <div class="perfil-linha">
                                        <span class="perfil-label">Email</span>
                                        <span class="perfil-valor">
                                            <%= perfil.getEmail() %>
                                        </span>
                                    </div>

                                </section>
                            </center>

                            <% } %>

                </div>

            </body>

            </html>