<%@ page import="java.util.List" %>
<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page import="com.linus.dto.ProfessorPerfilDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ProfessorPerfilDto > professores       = (List<ProfessorPerfilDto >) request.getAttribute("professor");
    List<AlunoDto> alunos = (List<AlunoDto>)     request.getAttribute("observacoes");
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

            <% if (encontrado != null && encontrado) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>usuario</th>
                        <th>Disciplina</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ProfessorPerfilDto p : professores) { %>
                    <tr>
                        <td><%= p.nome %></td>
                        <td><%= p.email %></td>
                        <td><%= p.usuario %></td>
                        <td><%= p.getDisciplina() %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/editar-professor/usuario=<%= p.usuario %>"
                               class="btn-action btn-edit" title="Ir para tela de editar aluno">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

            <% } else if (encontrado != null && !encontrado) { %>
            <div class="nao-encontrado">
                <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado,<br>pesquise novamente.</h2>
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
                    <% for (AlunoDto a : alunos) { %>
                    <tr>
                        <td><%= a.matricula %></td>
                        <td><%= a.nome %></td>
                        <td><%= a.email %></td>
                        <td><%= a.cpf %></td>

                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/admin/editar-aluno/matricula=<%= a.matricula %>"
                               class="btn-action btn-edit" title="Ir para tela de editar aluno">Editar</a>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
            <% } else { %>
            <div class="nao-encontrado">
                <h2 style="color: #4FB2D9;">Nenhuma observação registrada ainda.</h2>
            </div>
            <% } %>
        </section>

    </main>
</div>
</body>
</html>