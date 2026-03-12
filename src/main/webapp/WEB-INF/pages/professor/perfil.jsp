<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.linus.dto.ProfessorPerfilDto" %>

<% ProfessorPerfilDto perfil=(ProfessorPerfilDto) request.getAttribute("perfil"); %>


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

<jsp:include page="headerProfessor.jsp"/>

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
                                        <span class="perfil-label">Nome</span>
                                        <span class="perfil-valor">
                                            <%= perfil.nome %>
                                        </span>
                                    </div>

                                    <div class="perfil-linha">
                                        <span class="perfil-label">Usuario</span>
                                        <span class="perfil-valor">
                                            <%= perfil.usuario %>
                                        </span>
                                    </div>

                                    <div class="perfil-linha">
                                        <span class="perfil-label">Email</span>
                                        <span class="perfil-valor">
                                            <%= perfil.email %>
                                        </span>
                                    </div>

            <div class="perfil-linha">
                <span class="perfil-label">Disciplina</span>
                <span class="perfil-valor">
                                            <%= perfil.getDisciplina() %>
                                        </span>
            </div>

        </section>
    </center>

    <% } %>

                </div>

            </body>

            </html>