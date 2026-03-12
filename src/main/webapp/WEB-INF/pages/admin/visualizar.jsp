<%@ page import="java.util.List" %>
<%@ page import="com.linus.model.dao.Aluno" %>
<%@ page import="com.linus.model.dao.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Professor> professores = (List<Professor>) request.getAttribute("professores");
    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
    Boolean encontrado                  = (Boolean) request.getAttribute("encontrado");
    String  error                       = (String)  request.getAttribute("error");
    String  success                     = (String)  request.getAttribute("success");
%>

<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/tables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/popup.css">
    <title>Professor | Tela Inicial</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>
<body>

<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3>Instituto <br>Linus</h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li><a href="${pageContext.request.contextPath}/admin/pagPrincipal">Página inicial</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/visualizar">Visualização</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/adicionar-aluno">Criar Acesso</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/perfil">Perfil</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </nav>
</header>

<div class="page-container">
    <main class="main-content">

        <div class="page-header">
            <h1>Visualize professores e alunos</h1>
            <p>Veja os dados dos alunos e edite se necessário</p>
        </div>

        <% if (success != null) { %>
        <p class="success-message"><%= success %></p>
        <% } %>
        <% if (error != null) { %>
        <p class="error-message"><%= error %></p>
        <% } %>

        <section class="table-section">

            <% if (professores != null && !professores.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CPF</th>
                        <th>Disciplina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Professor p : professores) { %>
                    <tr>
                        <td><%= p.getNome() %></td>
                        <td><%= p.getEmail() %></td>
                        <td><%= p.getCpf() %></td>
                        <td><%= p.getIdMateria() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/editarProfessor?id=<%= p.getId() %>"
                               class="btn-action btn-edit" title="Ir para tela de editar professor">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

            <% } else if (professores != null && professores.isEmpty()) { %>
            <div class="nao-encontrado">
                <h2 style="color: #4FB2D9;">Nenhum professor foi encontrado.</h2>
            </div>
            <% } %>
        </section>

        <section class="table-section" style="margin-top: 32px;">

            <% if (alunos != null && !alunos.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CPF</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Aluno a : alunos) { %>
                    <tr>
                        <td><%= a.getMatricula() %></td>
                        <td><%= a.getNome() %></td>
                        <td><%= a.getEmail() %></td>
                        <td><%= a.getCpf() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/editarAluno?matricula=<%= a.getMatricula() %>"
                               class="btn-action btn-edit" title="Ir para tela de editar professor">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <% } else { %>
            <div class="nao-encontrado">
                <h2 style="color: #4FB2D9;">Nenhum aluno encontrado.</h2>
            </div>
            <% } %>
        </section>

    </main>
</div>
</body>
</html>
