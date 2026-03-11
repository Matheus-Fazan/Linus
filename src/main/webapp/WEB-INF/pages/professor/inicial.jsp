<%@ page import="java.util.List" %>
<%@ page import="com.linus.dto.ListarAlunosDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <title>Professor | Tela Inicial</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>
<% List<ListarAlunosDto> alunos = (List<ListarAlunosDto>) request.getAttribute("idProfessor"); %>
<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto <br> Linus </h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/professor/adicionarNotas">Adicionar Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/professor/editarNotas">Editar Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/professor/perfil">Perfil</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </nav>
</header>
<main>
    <section class="topo">
        <h1>Visualização dos alunos</h1>
        <p>Acompanhe seus alunos</p>
        <input type="text" class="search" placeholder="Pesquise o aluno pelo número de matrícula:">
    </section>
    <% Boolean encontrado=(Boolean) request.getAttribute("encontrado"); %>
    <% if (encontrado != null && encontrado) { %>
    <table>
        <thead>
        <tr>
            <th>Matrícula</th>
            <th>Aluno</th>
            <th>Nome</th>
            <th>Nota1</th>
            <th>Nota2</th>
        </tr>
        </thead>

        <tbody>
        <% for (ListarAlunosDto listarAlunosDto : alunos) { %>
        <tr>
            <td>
                <%= listarAlunosDto.matricula %>
            </td>
            <td>
                <%= listarAlunosDto.nome %>
            </td>
            <td>
                <%= listarAlunosDto.turma %>
            </td>
            <td>
                <%= listarAlunosDto.n1 %>
            </td>
            <td>
                <%= listarAlunosDto.n2 %>
            </td>

        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else if(encontrado !=null && !encontrado){ %>

    <div class="nao-encontrado">
        <center>
            <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png"
                 width="200">
        </center>
        <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</main>
</body>
</html>
