<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="java.util.List" %>
        <%@ page import="seu.pacote.NotaDTO" %>
            <html lang="pt-br">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Aluno | Tela Inicial</title>
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/crud_geral.css">
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
                                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/tabelaNotas.jsp">Página
                                    inicial</a>
                            </li>
                            <li>
                                <a
                                    href="${pageContext.request.contextPath}/WEB-INF/pages/professor/observacoesProfessor.jsp">Observação</a>
                            </li>
                            <li>
                                <a
                                    href="${pageContext.request.contextPath}/WEB-INF/pages/professor/perfil.jsp">Perfil</a>
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
                        <p>Acompanhe seus alunos, registre notas e faça observações</p>

                        <input type="text" class="search" placeholder="Pesquise o aluno pelo número de matrícula:">
                    </section>

                    <% Boolean encontrado=(Boolean) request.getAttribute("encontrado"); %>

                        <% if(encontrado !=null && encontrado){ %>

                            <% Aluno aluno=(Aluno) request.getAttribute("aluno"); 
                            List<BoletimDto> notas = (List<BoletimDto>)
                                    request.getAttribute("notas");
                                    %>

                                    <section class="card-tabela">

                                        <div class="info-aluno">
                                            <span><strong>Aluno:</strong>
                                                <%= aluno.getNome() %>
                                            </span>
                                            <span><strong>Turma:</strong>
                                                <%= aluno.getTurma() %>
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
                                                <% for (BoletimDto nota : nota) { %>
                                                    <tr>
                                                        <td>
                                                            <%= n.getMateria() %>
                                                        </td>
                                                        <td>
                                                            <%= n.getNota1() %>
                                                        </td>
                                                        <td>
                                                            <%= n.getNota2() %>
                                                        </td>
                                                        <td>
                                                            <%= n.getMedia() %>
                                                        </td>
                                                        <td>
                                                            <%= n.getObservacao() %>
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
                                                <%= aluno.getNome() %>
                                            </p>
                                            <p><strong>Matéria:</strong> Matemática</p>

                                            <form action="${pageContext.request.contextPath}/nota" method="post">
                                                <input type="hidden" name="alunoId" value="<%= aluno.getId() %>">

                                                <label>Nota 1:</label>
                                                <input type="number" step="0.1" name="nota1"
                                                    placeholder="Digite a nota">

                                                <label>Nota 2:</label>
                                                <input type="number" step="0.1" name="nota2"
                                                    placeholder="Digite a nota">

                                                <button type="submit" class="btn-roxo">Salvar nota</button>
                                            </form>
                                        </div>

                                        <div class="card-acoes">
                                            <h3>Ações rápidas</h3>
                                            <a href="${pageContext.request.contextPath}/boletim"><button>Gerar
                                                    boletim</button></a>
                                            <a
                                                href="${pageContext.request.contextPath}/pages/professor/observacoesProfessor.jsp"><button>Fazer
                                                    comentário</button></a>
                                            <a href="${pageContext.request.contextPath}/pages/professor/perfil.jsp"><button>Ir
                                                    para página de perfil</button></a>
                                        </div>

                                    </section>

                                    <% } else if(encontrado !=null && !encontrado){ %>

                                        <div class="nao-encontrado">
                                            <center>
                                                <img src="/src/main/webapp/assets/imgs/aluno-nao-achado 1.jpg"
                                                    width="200">
                                            </center>
                                            <h2 style="color: #4FB2D9;">Nenhum aluno foi encontrado, <br> 
                                            Pesquise novamente</h2>
                                        </div>

                                        <% } %>

                </main>
            </body>

            </html>