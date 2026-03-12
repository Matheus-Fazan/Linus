let chartAprovados;
let chartReprovados;
let chartMedia;
let chartAlunos;
let chartMediaMateria;

async function carregarDados() {

    const turma = document.getElementById("filtroTurma").value;
    const nota = document.getElementById("filtroNota").value;

    const res = await fetch(`http://localhost:3000/api/dados?turma=${turma}&nota=${nota}`);
    const dados = await res.json();

    atualizarCards(dados.cards);
    atualizarGraficos(dados.graficos);
    atualizarTabela(dados.listaCriticos);
}

function atualizarCards(cards) {
    document.getElementById("totalAlunos").innerText = cards.alunos;
    document.getElementById("totalProfessores").innerText = cards.professores;
    document.getElementById("totalTurmas").innerText = cards.turmas;
}

function atualizarGraficos(graficos) {

    if (chartAprovados) chartAprovados.destroy();
    if (chartReprovados) chartReprovados.destroy();
    if (chartMedia) chartMedia.destroy();
    if (chartMediaMateria) chartMediaMateria.destroy();
    if (chartAlunos) chartAlunos.destroy();

    chartAprovados = new Chart(document.getElementById('aprovacoesChart'), {
        type: 'bar',
        data: {
            labels: graficos.aprovados.labels,
            datasets: [{
                label: "Quantidade de alunos aprovados",
                data: graficos.aprovados.values,
                backgroundColor: '#7b5cff',
                borderRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });

    chartReprovados = new Chart(document.getElementById('reprovacoesChart'), {
        type: 'bar',
        data: {
            labels: graficos.reprovados.labels,
            datasets: [{
                label: "Quantidade de alunos reprovados",
                data: graficos.reprovados.values,
                backgroundColor: '#7fd8d4',
                borderRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });

    chartMedia = new Chart(document.getElementById('mediaChart'), {
        type: 'bar',
        data: {
            labels: graficos.media.labels,
            datasets: [{
                label: "Média das notas por turma",
                data: graficos.media.values,
                backgroundColor: '#a66bff',
                borderRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });

    chartMediaMateria = new Chart(document.getElementById('mediaMateriaChart'), {
        type: 'bar',
        data: {
            labels: graficos.mediaMateria.labels,
            datasets: [{
                label: "Média das notas por matéria",
                data: graficos.mediaMateria.values,
                backgroundColor: '#ffb347',
                borderRadius: 6
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });

    chartAlunos = new Chart(document.getElementById('qtdAlunosChart'), {
        type: 'pie',
        data: {
            labels: graficos.alunosPorTurma.labels,
            datasets: [{
                data: graficos.alunosPorTurma.values,
                backgroundColor: ['#7b5cff', '#a66bff', '#7fd8d4', '#cfcfcf']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
        }
    });
}

function atualizarTabela(lista) {

    const tbody = document.getElementById("tabelaCriticos");
    tbody.innerHTML = "";

    lista.forEach(aluno => {
        tbody.innerHTML += `
            <tr>
                <td>${aluno.turma}</td>
                <td>${aluno.matricula}</td>
                <td>${aluno.nome}</td>
                <td>${aluno.qtdRecuperacoes}</td>
            </tr>
        `;
    });
}

document.getElementById("btnAplicarFiltro")
    .addEventListener("click", carregarDados);

document.addEventListener("DOMContentLoaded", carregarDados);