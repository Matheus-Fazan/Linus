<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <title>Instituto Linus - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style/styleDashboard.css">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
</head>

<body style="background-color: #efefef">

<jsp:include page="headerAdmin.jsp"/>

<main>

    <section class="welcome">
        <h1>Bem Vindo, Adm!</h1>
        <p>Acompanhe seus alunos, registre notas e faça observações</p>
    </section>

    <section class="dashboard">
        <h2>Análises das turmas:</h2>

        <div class="filter-area">
            <label for="filtroTurma">Filtrar por turma:</label>
            <select id="filtroTurma">
                <option value="Todos">Todos</option>
                <option value="1">1°ANO</option>
                <option value="2">2°ANO</option>
                <option value="3">3°ANO</option>
            </select>

            <label for="filtroNota">Filtrar por nota:</label>
            <select id="filtroNota">
                <option value="n1">n1</option>
                <option value="n2">n2</option>
                <option value="media">media</option>
            </select>

            <button id="btnAplicarFiltro">Aplicar</button>
        </div>

        <div class="grid">

            <div class="stats-row">
                <div class="stat-box">
                    <p>Nº de Alunos</p>
                    <h3 id="totalAlunos">0</h3>
                </div>
                <div class="stat-box">
                    <p>Nº de Professores</p>
                    <h3 id="totalProfessores">0</h3>
                </div>
                <div class="stat-box">
                    <p>Qtd de turmas</p>
                    <h3 id="totalTurmas">0</h3>
                </div>
            </div>

            <div class="card">
                <h3>Alunos por ano</h3>
                <div class="chart-wrapper">
                    <canvas id="qtdAlunosChart"></canvas>
                </div>
            </div>

            <div class="card">
                <h3>Média por ano</h3>
                <div class="chart-wrapper">
                    <canvas id="mediaChart"></canvas>
                </div>
            </div>

            <div class="card">
                <h3>Nº de aprovados por turma</h3>
                <div class="chart-wrapper">
                    <canvas id="aprovacoesChart"></canvas>
                </div>
            </div>

            <div class="card">
                <h3>Nº de reprovados por turma</h3>
                <div class="chart-wrapper">
                    <canvas id="reprovacoesChart"></canvas>
                </div>
            </div>

            <div class="card full-width">
                <h3>Média por matéria</h3>
                <div class="chart-wrapper">
                    <canvas id="mediaMateriaChart"></canvas>
                </div>
            </div>

        </div>

        <section class="lista-criticos">
            <h2>Alunos Críticos</h2>

            <table>
                <thead>
                <tr>
                    <th>Turma</th>
                    <th>Matrícula</th>
                    <th>Nome</th>
                    <th>Matérias em Recuperação</th>
                </tr>
                </thead>
                <tbody id="tabelaCriticos">
                </tbody>
            </table>
        </section>

    </section>

</main>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="${pageContext.request.contextPath}/assets/js/script.js"></script>

</body>

</html>