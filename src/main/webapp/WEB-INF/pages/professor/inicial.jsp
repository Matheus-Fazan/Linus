<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page import="com.linus.dto.BoletimDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/crud_geral.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/edicao.css">
    <title>Professor | Tela Inicial</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>
<% AlunoDto aluno = (AlunoDto) request.getAttribute("AlunoDto"); %>
<% List<BoletimDto> notas = (List<BoletimDto>) request.getAttribute("notas"); %>
<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto Linus </h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/adicionarNotas.jsp">Adicionar Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/editarNotas.jsp">Editar Notas</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/perfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="">Logout</a>
            </li>
        </ul>
    </nav>
</header>
<main>
    <section class="topo">
        <h1>Visualização dos alunos</h1>
        <p>Acompanhe seus alunosx</p>
        <input type="text" class="search" placeholder="Pesquise o aluno pelo número de matrícula:">
    </section>
    <input type="text" class="search-bar"
           placeholder="Pesquise para ver se já tem um aluno/professor cadastrado:">
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
        <% for (AlunoProfessorDto n : notas) { %>
        <tr>
            <td>
                <%= n.matricula %>
            </td>
            <td>
                <%= n.nome %>
            </td>
            <td>
                <%= n.turma %>
            </td>
            <td>
                <%= n.n1 %>
            </td>
            <td>
                <%= n.n2 %>
            </td>

        </tr>
        <% } %>
        </tbody>
    </table>
    <% } else if(encontrado !=null && !encontrado){ %>

    <div class="nao-encontrado">
        <center>
            <img src="${pageContext.request.contextPath}/assets/imgs/"
                 width="200">
        </center>
        <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>
</main>
</body>
</html>
