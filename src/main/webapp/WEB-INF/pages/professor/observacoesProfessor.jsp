<%@ page import="com.linus.model.dao.ObservacaoDTO" %>
<%@ page import="com.linus.dto.ObservacaoDto" %>
<%@ page import="java.util.List" %>
<%@ page import="com.linus.model.dao.Observacao" %>
<%@ page import="com.linus.dto.ProfessorDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pt-br">

<head>
    <title>Observações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoesProfessor.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto Linus </h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/tabelaNotas.jsp">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/observacoesProfessor.jsp">Observação</a>
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
    <div class="topo">
        <div class="subtitle">
            <h2>Observações</h2>
            <p>Visualize os comentários de seus professores</p>
        </div>
        <div class="buttons">
            <button>Filtrar</button>
            <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/criarObservacao.jsp"><button>+ Criar Observação</button></a>
        </div>
    </div>
    <%
        ProfessorDto professor = (ProfessorDto) request.getAttribute("professor");

        List<Observacao> observacoes = (List<Observacao>) request.getAttribute("observacoes");
        for (Observacao observacao : observacoes) {
    %>

    <div class="card">
        <div class="row">
            <p><strong>Disciplina:</strong>
                <%= professor.aDefinir %>
            </p>
            <p><strong>Professor:</strong>
                <%= professor.nome %>
            </p>
        </div>
        <hr>
        <div class="row">
            <p><strong>Observação:</strong>
                <%= observacao.getObservacao() %>
            </p>
            <p><strong>Data:</strong>
                <%= observacao.getDataCriacao() %>
            </p>
        </div>
    </div>
</main>
<%
    }
%>
</body>

</html>