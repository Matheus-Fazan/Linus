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
<jsp:include page="headerAluno.jsp"/>
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
<div id="toast-erro">
    <%= messege %>
    <script>setTimeout(() => document.getElementById('toast-erro').style.left = '20px', 100);</script>
</div>
<% } %>

<% } %>
</body>

</html>