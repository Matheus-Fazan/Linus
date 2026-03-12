<%@ page import="com.linus.dto.AlunoProfessorDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.linus.dto.ObservacaoProfessorDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<AlunoProfessorDto> notas       = (List<AlunoProfessorDto>) request.getAttribute("notas");
    List<ObservacaoProfessorDto>     observacoes = (List<ObservacaoProfessorDto>)     request.getAttribute("observacoes");
    Boolean encontrado                  = (Boolean) request.getAttribute("encontrado");
    String  error                       = (String)  request.getAttribute("error");
    String  success                     = (String)  request.getAttribute("success");
    String  filtro                      = request.getParameter("matricula");
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

<jsp:include page="headerProfessor.jsp"/>


<div class="page-container">
    <main class="main-content">

        <div class="page-header">
            <h1>Visualização dos alunos</h1>
            <p>Acompanhe seus alunos, registre notas e faça observações.</p>
        </div>

        <% if (success != null) { %>
        <p class="success-message"><%= success %></p>
        <% } %>
        <% if (error != null) { %>
        <p class="error-message"><%= error %></p>
        <% } %>

        <section class="table-section">
            <header class="table-header">
                <h2>Alunos</h2>
                <div class="table-actions">
                    <form action="${pageContext.request.contextPath}/professor/inicio" method="get" class="search-bar">
                        <input type="search"
                               name="matricula"
                               placeholder="Matricula do estudante:"
                               value="<%= filtro != null ? filtro : "" %>">
                        <button type="submit">Buscar</button>
                    </form>
                </div>
            </header>

            <% if (encontrado != null && encontrado) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <th>Turma</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média</th>
                        <th>Situação</th>
                        <th>Observações</th>
                        <th>Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (AlunoProfessorDto n : notas) { %>
                    <tr>
                        <td><%= n.matricula %></td>
                        <td><%= n.nome %></td>
                        <td><%= n.turma %></td>
                        <td><%= n.n1 %></td>
                        <td><%= n.n2 %></td>
                        <td><%= n.media %></td>
                        <td><%= n.situacao %></td>
                        <td><%= n.observacao %></td>
                        <td class="action-buttons">
                            <a href="${pageContext.request.contextPath}/aluno/boletim/pdf?matricula=<%= n.matricula %>"
                               class="btn-action btn-gray" title="Gerar boletim PDF">PDF</a>
                            <button type="button"
                                    class="btn-action btn-edit"
                                    title="Editar notas"
                                    onclick="abrirPopupEdicao(<%= n.idNota %>, '<%= n.nome %>', <%= n.n1 %>, <%= n.n2 %>, '<%= n.observacao %>')">
                                Editar
                            </button>
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
            <header class="table-header">
                <h2>Observações</h2>
                <div class="table-actions">
                    <button type="button"
                            class="btn-action btn-gray"
                            title="Adicionar observação"
                            onclick="abrirPopupEdicao(null, null, null, null, null)">
                        Adicionar Observação
                    </button>
                </div>

            </header>

            <% if (observacoes != null && !observacoes.isEmpty()) { %>
            <div class="table-container">
                <table>
                    <thead>
                    <tr>
                        <th>Aluno</th>
                        <th>Disciplina</th>
                        <th>Professor</th>
                        <th>Observação</th>
                        <th>Data</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (ObservacaoProfessorDto o : observacoes) { %>
                    <tr>
                        <td><%= o.getNomeAluno() %></td>
                        <td><%= o.getDisciplina() %></td>
                        <td><%= o.getNomeProfessor() %></td>
                        <td><%= o.getObservacao() %></td>
                        <td><%= o.getDataCriacao() %></td>
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

<div class="overlay" id="popup-edicao" style="display: none;">
    <div class="popup-container">
        <h1>Editar Nota</h1>
        <p id="popup-nome-aluno"></p>

        <form id="form-editar-nota"
              action="${pageContext.request.contextPath}/professor/notas/alterar"
              method="post">

            <input type="hidden" name="idNota" id="popup-id-nota">

            <div class="popup-field">
                <label for="popup-n1">Nota 1</label>
                <input type="number" id="popup-n1" name="n1"
                       min="0" max="10" step="0.1"
                       placeholder="0.0"
                       oninput="calcularPrevia()">
            </div>

            <div class="popup-field">
                <label for="popup-n2">Nota 2</label>
                <input type="number" id="popup-n2" name="n2"
                       min="0" max="10" step="0.1"
                       placeholder="0.0"
                       oninput="calcularPrevia()">
            </div>

            <div class="popup-field">
                <label for="popup-observacao">Observação</label>
                <input type="text" id="popup-observacao" name="observacao"
                       placeholder="Digite sua observação">
            </div>

            <div class="popup-previa">
                <span>Média:</span>
                <strong id="popup-media">-</strong>
            </div>

            <div class="popup-actions">
                <button style="color: black" type="button" class="btn btn-secondary" onclick="fecharPopupEdicao()">Cancelar</button>
                <button type="submit" class="btn btn-primary">Salvar alterações</button>
            </div>
        </form>
    </div>
</div>
<script src="${pageContext.request.contextPath}/assets/js/popup-edicao.js"></script>

</body>
</html>
