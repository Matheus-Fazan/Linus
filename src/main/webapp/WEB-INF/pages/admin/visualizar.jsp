<%@ page import="java.util.List" %>
<%@ page import="com.linus.dto.AlunoVisualizarDto" %>
<%@ page import="com.linus.dto.ProfessorVisualizarDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ProfessorVisualizarDto> professores = (List<ProfessorVisualizarDto>) request.getAttribute("professores");
    List<AlunoVisualizarDto> alunos = (List<AlunoVisualizarDto>) request.getAttribute("alunos");
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

<jsp:include page="headerAdmin.jsp"/>

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
                        <th>Disciplina</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ProfessorVisualizarDto p : professores) { %>
                    <tr>
                        <td><%= p.getNome() %></td>
                        <td><%= p.getEmail() %></td>
                        <td><%= p.getDisciplina() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/alterar-email-professor?id=<%= p.getId() %>"
                               class="btn-action btn-edit" title="Ir para tela de editar professor">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

            <% } else if (professores != null && professores.isEmpty()) { %>
            <div class="nao-encontrado">
                <h2>Nenhum professor foi encontrado.</h2>
            </div>
            <% } %>
        </section>

        <section class="table-section">

            <% if (alunos != null && !alunos.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (AlunoVisualizarDto a : alunos) { %>
                    <tr>
                        <td><%= a.getMatricula() %></td>
                        <td><%= a.getNome() %></td>
                        <td><%= a.getEmail() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/alterar-email-aluno?matricula=<%= a.getMatricula() %>"
                               class="btn-action btn-edit" title="Ir para tela de editar professor">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <% } else { %>
            <div class="nao-encontrado">
                <h2>Nenhum aluno encontrado.</h2>
            </div>
            <% } %>
        </section>

    </main>
</div>
</body>
</html>
