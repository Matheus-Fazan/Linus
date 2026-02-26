<%@ page import="com.linus.model.dao.ObservacaoDTO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Observações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/assets/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/assets/style/observacoesProfessor.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/WEB-INF/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/WEB-INF/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto Linus </h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/index.html">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/observacaoAluno.jsp">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/perfil.jsp">Perfil</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages">Logout</a>
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
            <a href="${pageContext.request.contextPath}/WEB-INF/pages/professor/criarObservacao.html"><button>+ Criar Observação</button></a>
        </div>
    </div>
    <%
        List<ObservacaoDTO> observacoes = (List<ObservacaoDTO>) request.getAttribute("observacoes");
        for (ObservacaoDTO observacao : observacoes) {
    %>

    <div class="card">
        <div class="row">
            <p><strong>Disciplina:</strong>
                <%= observacao.getDisciplina() %>
            </p>
            <p><strong>Professor:</strong>
                <%= observacao.getNomeProfessor() %>
            </p>
        </div>
        <hr>
        <div class="row">
            <p><strong>Observação:</strong>
                <%= observacao.getObservacao() %>
            </p>
            <p><strong>Data:</strong>
                <%= observacao.getData() %>
            </p>
        </div>
    </div>
</main>
<%
    }
%>
</body>

</html>