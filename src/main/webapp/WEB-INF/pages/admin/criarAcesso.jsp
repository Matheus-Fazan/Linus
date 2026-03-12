<%@ page import="java.util.List" %>
<%@ page import="com.linus.model.dao.Materia" %>
<%@ page import="com.linus.model.dao.Turma" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% List<Materia> materias = (List<Materia>) request.getAttribute("materias"); %>
<% List<Turma> turmas = (List<Turma>) request.getAttribute("turmas"); %>

<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/criarAcesso.css">
    <title>Criar Acesso</title>
</head>
<body>
    <jsp:include page="headerAdmin.jsp"/>

    <div class="topo">
        <h1>Crie um novo acesso</h1>
        <p>Visualize suas informações e as edite.</p>
    </div>

    <div class="acess">
        <div class="form-perfil">
            <div class="main-container">
            <h2 class="acess_title">Crie um novo aluno</h2>
            <form action="${pageContext.request.contextPath}/admin/adicionar-aluno" method="post">
                <label for="nome-aluno">Nome:</label>
                <input type="text" id="nome-aluno" name="nome" placeholder="Digite o nome do aluno" required>

                <label for="cpf-aluno">CPF:</label>
                <input type="text" id="cpf-aluno" name="cpf" placeholder="Digite o cpf do aluno" required>

                <label for="turma">Turma:</label>
                <select id="turma" name="turma" required style="width:100%; padding:8px; border-radius:8px; border:1px solid #ccc; margin-top:5px;">
                    <% if (turmas != null) {
                        for (Turma turma : turmas) { %>
                    <option value="<%= turma.getId() %>">
                        <%= turma.getNome() %>
                    </option>
                    <% }
                    } %>
                </select>

                <button type="submit" class="btn-roxo">Salvar</button>
            </form>
            </div>
        </div>

        <div class="form-perfil">
            <div class="main-container">
            <h2 class="acess_title">Crie um novo professor</h2>
            <form action="${pageContext.request.contextPath}/admin/adicionar-professor" method="post">

                <label for="nome">Nome:</label>
                <input type="text" id="nome" name="nome" placeholder="Digite o nome professor" required>

                <label for="usuario">Usuário:</label>
                <input type="text" id="usuario" name="usuario" placeholder="Digite o nome do usuário" required>

                <label for="disciplina">Disciplina:</label>
                <select id="disciplina" name="id_materia" required style="width:100%; padding:8px; border-radius:8px; border:1px solid #ccc; margin-top:5px;">
                    <% if (materias != null) {
                        for (Materia materia : materias) { %>
                    <option value="<%= materia.getId() %>">
                        <%= materia.getNome() %>
                    </option>
                    <% }
                    } %>
                </select>

                <label for="cpf">CPF:</label>
                <input type="text" id="cpf" name="cpf" placeholder="Digite o CPF" required>

                <label for="email">Email:</label>
                <input type="email" id="email" name="email" placeholder="Digite o email do professor" required>

                <label for="senha">Senha:</label>
                <input type="password" id="senha" name="senha" placeholder="Digite a senha do professor" required>

                <button type="submit" class="btn-roxo">Salvar</button>
            </form>
            </div>
        </div>
    </div>
</body>
</html>
