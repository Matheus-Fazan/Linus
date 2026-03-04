<%@ page import="java.util.Map" %>
<%@ page import="com.linus.dto.MateriaDto" %>
<%@ page import="com.linus.model.dao.Materia" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Map<String, String> materia = (Map<String, String>) request.getAttribute("materia"); %>
<% Map<String, String> turma = (Map<String, String>) request.getAttribute("turma"); %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/pagPrincipal.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/perfil.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/crud_geral.css">
    <title>Criar Acesso</title>
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
                    <a href="${pageContext.request.contextPath}/admin/pagPrincipal.jsp">Página inicial</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/criarAcesso.jsp">Observação</a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/admin/perfil.jsp">Perfil</a>
                </li>
                <li>
                    <a href="index.html">Logout</a>
                </li>
            </ul>
        </nav>
    </header>

    <div class="topo">
        <h1>Crie um novo acesso</h1>
        <p>Visualize suas informações e as edite.</p>
    </div>

    <div class="acess">
        <div class="form-perfil">
            <div class="main-container">
                <h2 class="acess_title">Crie um novo aluno</h2>
                <form action="" method="post">
                    <label for="nome">Nome:</label>
                    <input type="text" name="nome" placeholder="Digite o nome do aluno">

                    <label for="cpf">CPF:</label>
                    <input type="text" name="cpf" placeholder="Digite o cpf do aluno">

                    <label for="turma">Turma:</label>
                    <select id="turma" name="turma" required>
                        <% for (String reposta : turma.keySet()) { %>
                        <option value="<%= reposta %>" <%=reposta == turma.getId() ? "selected" : "" %>>
                            <%= turma.get(reposta) %>
                        </option>
                        <% } %>
                    </select>]

                </form>
                <button type="submit">Salvar</button>
        </div>
    </div>

    <div class="form-perfil">
        <div class="main-container">
            <h2 class="acess_title">Crie um novo professor</h2>
            <form action="" method="post">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" placeholder="Digite o nome professor">

                <label for="usuario">Usuário:</label>
                <input type="text" id="usuario" placeholder="Digite o nome do usuário">

                <label for="disciplina">Disciplina:</label>
                <select id="disciplina" name="disciplina" required>
                    <% for (String materiaDto : materia.keySet()) { %>
                    <option value="<%= materiaDto %>" <%=materiaDto == materia.getId() ? "selected" : "" %>>
                        <%= materia.get(materiaDto) %>
                    </option>
                    <% } %>
                </select>

                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" placeholder="Digite o CPF">

                <label for="email">Email:</label>
                <input type="text" id="email" placeholder="Digite o email do professor">

                <label for="senha">Senha:</label>
                <input type="password" id="senha" placeholder="Digite a senha do professor">

            </form>
            <button type="submit">Salvar</button>
        </div>
    </div>
</div></body>
</html>
