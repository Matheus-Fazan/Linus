<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aluno | Tela Inicial</title>
    <link rel="stylesheet" href="/src/main/webapp/style/style.css">
    <link rel="stylesheet" href="/src/main/webapp/style/crud_geral.css">

</head>

<body>
    <main>
        <header>
            <div id="imagem">
                <img id="logo_colegio" src="/src/main/webapp/assets/logoColegio.png" alt="Logo colegio">
                <h3>Instituto de <br> Tecnologia</h3>
            </div>

            <nav id="nav-header">
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath}/area-restrita/index.jsp">Página inicial</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/area-restrita/usuarios">Observação</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/area-restrita/superadms">Perfil</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/area-restrita/planos">Logout</a>
                    </li>
                </ul>
            </nav>
        </header>

        <div id="fundo_tela">

            <div id="topo">
                <div id="local">
                    <h1>Bem Vindo, Aluno!</h1>
                    <p>Visualize suas notas e desempenho escolar.</p>
                </div>

                <div class="filtro-card">
                    <form action="${pageContext.request.contextPath}/area-restrita/fabricas" method="get">
                        <input type="hidden" name="action" value="read">

                        <div class="filtragem">
                            <label>
                                Campo de Filtragem:
                                <select id="campoFiltro" name="campo_filtro" onchange="tipoCampoFabrica()">
                                    <option value="" selected>Nenhum selecionado</option>
                                    <option value="professor" data-type="text">Professor</option>
                                    <option value="materia" data-type="text">Matéria</option>
                                </select>
                            </label>
                        </div>

                        <div class="filtragem" id="filtroGeral">
                            <label for="valorFiltro">Digite o valor:</label>
                            <input type="text" id="valorFiltro" name="valor_filtro">
                        </div>

                        <div class="filtragem" style="display: none">
                            <label for="valorPlano">Valor Filtrado:</label>
                            <select name="valor_plano" id="valorPlano">
                                <option value="">--- SELECIONE ---</option>
                                <% for (String plano : planos.values()) {%>
                                    <option value="<%=plano%>">
                                        <%=plano%>
                                    </option>
                                    <% } %>
                            </select>
                        </div>

                        <div id="cabecalho">

                            <form action="${pageContext.request.contextPath}/area-restrita/fabricas" method="get">
                                <input type="hidden" name="action" value="read">
                                <button id="limpaFiltro" type="submit">Limpar Filtros</button>
                            </form>


                            <input type="submit" value="Filtrar" id="filtrar">
                        </div>
                    </form>
                </div>
            </div>

            <form action="">
                <% Aluno aluno=(Aluno) request.getAttribute("aluno"); %>
                    <div id="informacoes_gerais">
                        <div id="informacao">
                            <h3>Média Geral:</h3>
                            <p>
                                <%= aluno.getMediaGeral() %>
                            </p>
                        </div>
                        <div id="informacao">
                            <h3>Notas baixas:</h3>
                            <% for (AlunosDTO aluno : aluno) { %>
                                <p>
                                    <%= aluno.getNotasBaixas()%>
                                </p>
                                <% } %>
                        </div>
                        <div id="informacao">
                            <h3>Melhor matéria:</h3>
                            <p>
                                <%= aluno.getMelhorMateria() %>
                            </p>
                        </div>
                    </div>
            </form>

        </div>


        <div id="tela_principal">

            <div id="tabela_usuarios">
                <h3>Disciplina</h3>
                <hr>
                <table border="0">
                    <tr id="titulo_tabela">
                        <th>Professor</th>
                        <th>Matéria</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média </th>
                        <th>Observações</th>
                    </tr>
                    <% for (AlunosDTO aluno : aluno) { %>
                        <tr>
                            <td>
                                <%= aluno.getProfessor()
                            </td>
                            <td>
                                <%= aluno.getMateria() %>
                            </td>
                            <td>
                                <%= aluno.getNota1() %>
                            </td>
                            <td>
                                <%= aluno.getNota2() %>
                            </td>
                            <td>
                                <%= aluno.getMedia() %>
                            </td>
                            <td>
                                <%= aluno.getObservacao() %>
                            </td>
                            <td>
                                <form action="${pageContext.request.contextPath}/area-restrita/fabricas" method="get">
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
        </div>

    </main>
    <script src=""></script>
</body>

</html>