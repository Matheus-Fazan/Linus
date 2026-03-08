<%@ page import="com.linus.dto.ObservacaoDto" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="pt-br">

<head>
    <title>Observações</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/observacoes.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/logo.png" type="image/x-icon">
</head>

<body>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3> Instituto Linus</h3>
    </div>
    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/pagPrincipal.jsp">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/WEB-INF/pages/aluno/observacaoAluno.jsp">Observação</a>
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
    <div class="subtitle">
        <h2>Observações</h2>
        <p>Visualize os comentários de seus professores</p>
    </div>
    <%
        List<ObservacaoDto> observacoes = (List<ObservacaoDto>) request.getAttribute("observacoes");

        if (observacoes != null) {
            for (ObservacaoDto observacao : observacoes){
    %>

    <div class="card">
        <div class="row">
            <p><strong>Disciplina:</strong>
                <%= observacao.materia %>
            </p>
            <p><strong>Professor:</strong>
                <%= observacao.nomeProfessor %>
            </p>
        </div>
        <hr>
        <div class="row">
            <p><strong>Observação:</strong>
                <%= observacao.observacao %>
            </p>
            <p><strong>Data:</strong>
                <%= observacao.dataPublicacao %>
            </p>
        </div>
    </div>
</main>

<%
        }
   } else {
%>

<% if (request.getAttribute("error") != null) {
    String messege = (String) request.getAttribute("error");
    request.setAttribute("error", null);
%>
<div id="toast-erro" style="position: fixed; bottom: 20px; left: -400px; width: 350px; background: #ff4d4f; color: white; padding: 16px 20px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.2); transition: left 0.4s ease; z-index: 9999;">
    <%= messege %>
    <script>setTimeout(() => document.getElementById('toast-erro').style.left = '20px', 100);</script>
</div>
<% } %>

<% } %>
</body>

</html>