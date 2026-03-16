<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.linus.dto.NotaDto" %>
<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page import="com.linus.dto.BoletimDto" %>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aluno | Tela Inicial</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png"
          type="image/x-icon">

</head>

<% AlunoDto aluno = (AlunoDto) request.getAttribute("AlunoDto"); %>
<% List<BoletimDto> notas = (List<BoletimDto>) request.getAttribute("notas"); %>

<body>

<jsp:include page="headerProfessor.jsp"/>

<main>

    <section class="topo">
        <h1>Visualização dos alunos</h1>
        <p>Acompanhe seus alunos, registre notas e faça observações</p>

        <input type="text" class="search" placeholder="Pesquise o aluno pelo número de matrícula:">
    </section>

    <% Boolean encontrado = (Boolean) request.getAttribute("encontrado"); %>

    <% if (encontrado != null && encontrado) { %>

    <section class="card-tabela">

        <div class="info-aluno">
                                            <span><strong>Aluno:</strong>
                                                <%= aluno.nome %>
                                            </span>
            <span><strong>Turma:</strong>
                                                <%= aluno.turmaDto %>
                                            </span>
        </div>

        <table>
            <thead>
            <tr>
                <th>Matéria</th>
                <th>Nota1</th>
                <th>Nota2</th>
                <th>Média</th>
                <th>Observações</th>
            </tr>
            </thead>

            <tbody>
            <% for (BoletimDto n : notas) { %>
            <tr>
                <td>
                    <%= n.materia %>
                </td>
                <td>
                    <%= n.n1 %>
                </td>
                <td>
                    <%= n.n2 %>
                </td>
                <td>
                    <%= n.media %>
                </td>
                <td>
                    <%= n.observacao %>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>

    </section>


    <section class="grid-inferior">

        <div class="card-form">
            <h2>Definir nota do aluno:</h2>
            <p><strong>Nome do aluno:</strong>
                <%= aluno.nome %>
            </p>
            <p><strong>Matéria:</strong> Matemática</p>

            <form action="${pageContext.request.contextPath}/nota" method="post">
                <input type="hidden" name="alunoId" value="<%= aluno.id %>">

                <label>Nota 1:</label>
                <input type="number" step="0.1" name="nota1"
                       placeholder="Digite a nota">

                <label>Nota 2:</label>
                <input type="number" step="0.1" name="nota2"
                       placeholder="Digite a nota">

                <button type="submit" class="btn-roxo">Salvar nota</button>
            </form>
        </div>

        <div class="card-form">
            <h2>Editar nota do aluno:</h2>
            <p><strong>Nome do aluno:</strong>
                <%= aluno.nome %>
            </p>
            <p><strong>Matéria:</strong> Matemática</p>

            <form action="${pageContext.request.contextPath}/nota" method="post">
                <input type="hidden" name="alunoId" value="<%= aluno.id %>">

                <label>Nota 1:</label>
                <input type="number" step="0.1" name="nota1"
                       placeholder="Edite a nota do aluno" value="nota.getN1()">

                <label>Nota 2:</label>
                <input type="number" step="0.1" name="nota2"
                       placeholder="Digite a nota" value="nota.getN2()">

                <button type="submit" class="btn-roxo">Editar nota</button>
            </form>
        </div>
    </section>

    <div class="card-acoes">
        <h3>Ações rápidas</h3>
        <a href="${pageContext.request.contextPath}/boletim">
            <button>Gerar
                boletim
            </button>
        </a>
        <a
                href="${pageContext.request.contextPath}/professor/observacoesProfessor">
            <button>Fazer
                comentário
            </button>
        </a>
        <a href="${pageContext.request.contextPath}/professor/perfil">
            <button>Ir
                para página de perfil
            </button>
        </a>
    </div>


    <% } else if (encontrado != null && !encontrado) { %>

    <div class="nao-encontrado">
        <center>
            <img src="${pageContext.request.contextPath}/assets/imgs/aluno-nao-achado%201.png"
                 width="200">
        </center>
        <h2>Nenhum aluno foi encontrado, <br>
            Pesquise novamente</h2>
    </div>

    <% } %>

</main>
</body>

</html>