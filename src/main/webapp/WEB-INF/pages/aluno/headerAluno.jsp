<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<header>
    <div class="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/logo.png" alt="logo colegio">
        <h3>Instituto <br> Linus</h3>
    </div>

    <nav class="nav-header">
        <ul>
            <li>
                <a href="${pageContext.request.contextPath}/aluno/boletim">Página inicial</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/aluno/observacoes">Observação</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/aluno/perfil">Perfil</a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/logout">Logout</a>
            </li>
        </ul>
    </nav>
</header>