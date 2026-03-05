<%@ page import="com.linus.dto.AlunoDto" %>
<%@ page import="com.linus.dto.ProfessorDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String tipo = request.getParameter("tipo");
    if (tipo == null) {
        tipo = "";
    }
%>
<%
    AlunoDto aluno = (AlunoDto) request.getAttribute("aluno");
%>
<%
    ProfessorDto professor = (ProfessorDto) request.getAttribute("professor");
%>

    <html>
    <head>
        <title>Visualizar Professores e Alunos</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/visualizarAdm.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png"
              type="image/x-icon">
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
                        <a href="${pageContext.request.contextPath}/WEB-INF/pages/admin/dash">Página
                            inicial</a>
                    </li>
                    <li>
                        <a
                                href="${pageContext.request.contextPath}/WEB-INF/pages/admin/visualizar.jsp">Visualizar</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/WEB-INF/pages/admin/perfil.jsp">Perfil</a>
                    </li>
                    <li>
                        <a href="">Logout</a>
                    </li>
                </ul>
            </nav>
        </header>
        <main>
            <div class="container">

                <h1>Visualize professores e alunos</h1>
                <div class="sub">Veja os dados dos alunos e edite se necessário</div>

                <form method="get">
                    <input class="search-input" type="text" name="pesquisa"
                           placeholder="Pesquise o aluno/professor pelo número de matrícula ou email">

                    <div class="btn-group">
                        <button class="btn" type="submit" name="tipo" value="aluno">
                            Ver informações dos alunos
                        </button>

                        <button class="btn" type="submit" name="tipo" value="professor">
                            Ver informações dos professores
                        </button>
                    </div>
                </form>

                <% if ("aluno".equals(tipo)) { %>

                <div class="card">
                    <h3>Alunos</h3>

                    <table>
                        <tr>
                            <th>Nome</th>
                            <th>Usuário</th>
                            <th>Email</th>
                            <th>CPF</th>
                            <th>Matrícula</th>
                            <th>Turma</th>
                        </tr>
                        <tr>
                            <td><%= aluno.nome %></td>
                            <td><%= aluno.email %></td>
                            <td><%= aluno.cpf %></td>
                            <td><%= aluno.matricula %></td>
                            <td><%= aluno.turmaDto %></td>
                        </tr>
                    </table>
                </div>

                <% } else if ("professor".equals(tipo)) { %>

                <div class="card">
                    <h3>Professores</h3>

                    <table>
                        <tr>
                            <th>Nome</th>
                            <th>Usuário</th>
                            <th>Email</th>
                            <th>Disciplina</th>
                        </tr>
                        <tr>
                            <td><%= professor.nome %></td>
                            <td><%= professor.usuario %></td>
                            <td><%= professor.email %></td>
                            <td><%= professor.disciplina %></td>
                        </tr>
                    </table>
                </div>

                <% } %>

            </div>
        </main>
    </body>
</html>
