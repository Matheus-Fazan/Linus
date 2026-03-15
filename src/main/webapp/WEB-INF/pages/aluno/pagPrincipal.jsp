<%@ page import="com.linus.dto.EstatisticasBoletimDto" %>
<%@ page import="com.linus.dto.BoletimDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="pt-br">
<%
    List<BoletimDto> boletim = (List<BoletimDto>) request.getAttribute("boletim");
    EstatisticasBoletimDto estatistica = (EstatisticasBoletimDto) request.getAttribute("estatisticasBoletim");
%>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Aluno | Tela Inicial</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/tables.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
<jsp:include page="headerAluno.jsp"/>


<main>
    <div class="fundo_tela">
        <div class="top-part">
            <div class="topo">
                <h1>Bem Vindo, Aluno!</h1>
                <p>Visualize suas notas e desempenho escolar.</p>
            </div>

            <div class="informacoes_gerais">

                <div class="informacao">
                    <h3>
                        <a href="${pageContext.request.contextPath}/aluno/boletim/pdf?matricula=${sessionScope.idUsuario}">Gerar
                            Boletim</a></h3>
                </div>

                <% if (estatistica != null) { %>
                <div class="informacao">
                    <h3>Notas baixas: </h3>
                    <p><%= estatistica.notasBaixas %>
                    </p>
                </div>

                <div class="informacao">
                    <h3>Situação: </h3>
                    <p><%= estatistica.situacao %>
                    </p>
                </div>
                <% } %>

            </div>
        </div>
    </div>

    <div class="tela_principal">

        <div class="tabela_usuarios">
            <section class="table-container" style="width: 90%; margin: 20px auto 0; overflow: hidden">
                <table border="0">
                    <tr id="titulo_tabela">
                        <th>Matéria</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média</th>
                        <th>Observações</th>
                    </tr>

                    <% if (boletim != null) {
                        for (BoletimDto nota : boletim) { %>
                    <tr>
                        <td><%= nota.materia %>
                        </td>
                        <td><%= nota.n1 %>
                        </td>
                        <td><%= nota.n2 %>
                        </td>
                        <td><%= nota.media %>
                        </td>
                        <td><%= nota.observacao %>
                        </td>
                    </tr>
                    <% }
                    } %>

                </table>
            </section>
        </div>

    </div>

    <% if (request.getAttribute("error") != null) {
        String messege = (String) request.getAttribute("error");
        request.setAttribute("error", null);
    %>
    <div id="toast-erro"
         style="position: fixed; top: 20px; right: -400px; width: 350px; background: #ff4d4f; color: white; padding: 16px 20px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.2); transition: right 0.4s ease; z-index: 9999;">
        <%= messege %>
        <script>setTimeout(() => document.getElementById('toast-erro').style.right = '20px', 100);</script>
    </div>
    <% } %>

</main>
</body>
</html>
