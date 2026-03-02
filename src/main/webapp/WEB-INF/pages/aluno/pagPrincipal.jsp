<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <html lang="pt-br">

    <%  List<BoletimDTO> BoletimDTO = (List<Boletim>) request.getAttribute("boletimDTO");
        EstatisticaBoletimDTO estatistica=(EstatisticaBoletim) request.getAttribute("estatistica");
    %>

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
                                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/pagPrincipal.jsp">Página
                                    inicial</a>
                            </li>
                            <li>
                                <a
                                    href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/observacaoAluno.jsp">Observação</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/perfil.jsp">Perfil</a>
                            </li>
                            <li>
                                <a href="">Logout</a>
                            </li>
                        </ul>
                    </nav>
                </header>
                <main>
                    <div class="fundo_tela">

                        <div class="topo">
                            <h1>Bem Vindo, Aluno!</h1>
                            <p>Visualize suas notas e desempenho escolar.</p>
                        </div>

                            <div id="informacoes_gerais">

                                <div class="informacao">
                                    <h3><a href="">Gerar Boletim</a></h3>
                                </div>

                                <div class="informacao">
                                    <h3>Notas baixas:</h3>
                                    <% for (EstatisticaBoletimDTO estatistica : estatistica) { %>
                                        <p>
                                            <%= estatistica.getNotasBaixas()%>
                                        </p>
                                        <% } %>
                                </div>

                                <div class="informacao">
                                    <h3>Melhor matéria:</h3>
                                    <p>
                                        <%= estatistica.getMelhorMateria() %>
                                    </p>
                                </div>

                            </div>

                    </div>


                    <div class="tela_principal">

                        <div class="tabela_usuarios">
                            <h3>Disciplina</h3>
                            <hr>
                            <table border="0">
                                <tr id="titulo_tabela">
                                    <th>Professor</th>
                                    <th>Matéria</th>
                                    <th>Nota 1</th>
                                    <th>Nota 2</th>
                                    <th>Média</th>
                                    <th>Observações</th>
                                </tr>
                                <% for (BoletimDto nota : nota) { %>
                                    <tr>
                                        <td>
                                            <%= nota.getProfessor() %>
                                        </td>
                                        <td>
                                            <%= nota.getMateria() %>
                                        </td>
                                        <td>
                                            <%= nota.getN1() %>
                                        </td>
                                        <td>
                                            <%= nota.getN2() %>
                                        </td>
                                        <td>
                                            <%= nota.getMedia() %>
                                        </td>
                                        <td>
                                            <%= nota.getObservacao() %>
                                        </td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/area-restrita/fabricas"
                                                method="get">
                                                <input type="hidden" name="id" value="<%= f.getId() %>">
                                                <input type="hidden" name="action" value="update">
                                                <button id="editar" type="submit">Editar</button>
                                            </form>
                                        </td>
                                    </tr>
                                    <% } %>
                            </table>
                        </div>

                    </div>

                </main>
                <script src=""></script>
            </body>

    </html>