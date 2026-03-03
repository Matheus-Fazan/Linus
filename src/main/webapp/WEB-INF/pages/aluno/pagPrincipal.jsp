<%@ page import="com.linus.dto.EstatisticasBoletimDto" %>
<%@ page import="com.linus.dto.BoletimDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

    <html lang="pt-br">

    <% List<BoletimDto> BoletimDTO = (List<BoletimDto>) request.getAttribute("boletimDTO");
            EstatisticasBoletimDto estatistica=(EstatisticasBoletimDto) request.getAttribute("estatistica");
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

                        <div class="informacoes_gerais">

                            <div class="informacao">
                                <h3><a href="">Gerar Boletim</a></h3>
                            </div>

                            <div class="informacao">
                                <h3>Notas baixas:</h3>
                                    <p>
                                        <%= estatistica.notasBaixas%>
                                    </p>
                            </div>

                            <div class="informacao">
                                <h3>Melhor matéria:</h3>
                                <p>
                                    <%= estatistica.situacao %>
                                </p>
                            </div>

                        </div>

                    </div>


                    <div class="tela_principal">

                        <div class="tabela_usuarios">
                            <hr>
                            <section class="card-tabela">
                                <table border="0">
                                    <tr id="titulo_tabela">
                                        <th>Professor</th>
                                        <th>Matéria</th>
                                        <th>Nota 1</th>
                                        <th>Nota 2</th>
                                        <th>Média</th>
                                        <th>Observações</th>
                                    </tr>

                                    <% for (BoletimDto boletim : BoletimDTO) { %>
                                        <tr>
                                            <td>
                                                <%= boletim.getProfessor() %>
                                            </td>
                                            <td>
                                                <%= boletim.getMateria() %>
                                            </td>
                                            <td>
                                                <%= boletim.n1 %>
                                            </td>
                                            <td>
                                                <%= boletim.n2 %>
                                            </td>
                                            <td>
                                                <%= boletim.media %>
                                            </td>
                                            <td>
                                                <%= boletim.observacao %>
                                            </td>
                                        </tr>
                            </section>
                            <% } %>
                                </table>
                        </div>

                    </div>

                </main>
            </body>

    </html>