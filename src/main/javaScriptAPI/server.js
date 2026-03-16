const express = require("express");
const cors = require("cors");
const { Pool } = require("pg");

const app = express();

app.use(cors());

const pool = new Pool({
    user: "avnadmin",
    host: "banco-de-dados-institutojef-6ce7.c.aivencloud.com",
    database: "linusdb",
    password: "Colocar Senha do BD aqui",
    port: 24330,
    ssl: {
        rejectUnauthorized: false
    }
});

app.get("/api/dados", async (req, res) => {

    const { turma, nota } = req.query;

    const colunasPermitidas = ["n1", "n2", "media"];

    if (!colunasPermitidas.includes(nota)) {
        return res.status(400).json({ erro: "Nota inválida" });
    }

    try {

        let qtdAlunosTotal;
        let qtdSalasTotal;
        let qtdProfsTotal;
        let qtdAlunosChart;
        let mediaAnoChart;
        let mediaMateriaChart;
        let aprovadosChart;
        let reprovadosChart;
        let criticosLista;

        await pool.query(
            `update nota set media=((n1+n2)/2) where 1=1 and n1 is not null and n2 is not null`
        );

        qtdAlunosTotal = await pool.query(
            `select count(distinct matricula) as qtd_alunos from aluno`
        );

        qtdSalasTotal = await pool.query(
            `select count(distinct id) as qtd_turma from turma`
        );

        qtdProfsTotal = await pool.query(
            `select count(distinct id) as qtd_prof from professor`
        );

        if (turma === 'Todos') {

            qtdAlunosChart = await pool.query(
                `select
                  left(t.nome, 5) as turma,
                  count(distinct a.matricula) as total
              from aluno a
              inner join turma t
                  on t.id = a.id_turma
              group by 1`,
            );

            mediaAnoChart = await pool.query(
                `select
	            left(t.nome, 5) as turma,
	            avg(n.${nota}) as media
            from turma t
            join aluno a
	            on a.id_turma=t.id
            join nota n
	            on n.id_aluno = a.matricula
            group by t.nome`
            );

            mediaMateriaChart = await pool.query(
                `select
	            m.nome as materia,
	            avg(n.${nota}) as media
            from turma t
            join aluno a
	            on a.id_turma = t.id
            join nota n
	            on n.id_aluno = a.matricula
            join professor p
	            on n.id_professor = p.id
            join materia m
	            on p.id_materia=m.id
            group by m.nome`
            );

            aprovadosChart = await pool.query(
                `select 
                left(t.nome, 5) as turma,
                count(distinct aprovados.matricula) as qtd
            from turma t
            join (
                select a.matricula, a.id_turma
                from aluno a
                join nota n on n.id_aluno = a.matricula
                group by a.matricula, a.id_turma
                having min(n.${nota}) >= 7 
            ) as aprovados on aprovados.id_turma = t.id
            group by left(t.nome, 5);`,
            );

            reprovadosChart = await pool.query(
                `select 
                left(t.nome, 5) as turma,
                count(distinct reprovados.matricula) as qtd
            from turma t
            join (
                select a.matricula, a.id_turma
                from aluno a
                join nota n on n.id_aluno = a.matricula
                group by a.matricula, a.id_turma
                having min(n.${nota}) < 7 
            ) as reprovados on reprovados.id_turma = t.id
            group by left(t.nome, 5);`,
            );

            criticosLista = await pool.query(
                `with qtd_reprovadas as (
	            select
		            id_aluno,
		            count(distinct id) as qtd
	            from nota n
	            where n.${nota} < 7
	            group by id_aluno
            )
            select
	            left(t.nome, 5) as turma,
	            a.matricula as matricula,
	            a.nome as nome,
	            qr.qtd as qtd
            from turma t
            join aluno a
 	            on t.id = a.id_turma
            join qtd_reprovadas qr
	            on a.matricula = qr.id_aluno
            order by qtd asc
            limit 5`,
            );

        } else {

            qtdAlunosChart = await pool.query(
                `select
                  t.nome as turma,
                  count(distinct a.matricula) as total
              from aluno a
              inner join turma t
                  on t.id = a.id_turma
              where t.nome like $1
              group by 1`,
                [turma + '%']

            );

            mediaAnoChart = await pool.query(
                `select
	            t.nome as turma,
	            avg(n.${nota}) as media
            from turma t
            join aluno a
	            on a.id_turma=t.id
            join nota n
	            on n.id_aluno = a.matricula
            where t.nome like $1
            group by t.nome`,
                [turma + '%']
            );

            mediaMateriaChart = await pool.query(
                `select
	            m.nome as materia,
	            avg(n.${nota}) as media
            from turma t
            join aluno a
	            on a.id_turma = t.id
            join nota n
	            on n.id_aluno = a.matricula
            join professor p
	            on n.id_professor = p.id
            join materia m
	            on p.id_materia=m.id
            where t.nome like $1
            group by m.nome`,
                [turma + '%']
            );

            aprovadosChart = await pool.query(
                `select 
                t.nome as turma,
                count(distinct aprovados.matricula) as qtd
            from turma t
            join (
                select a.matricula, a.id_turma
                from aluno a
                join nota n on n.id_aluno = a.matricula
                group by a.matricula, a.id_turma
                having min(n.${nota}) >= 7 
            ) as aprovados on aprovados.id_turma = t.id
            group by t.nome
            having t.nome like $1`,
                [turma + '%']
            );

            reprovadosChart = await pool.query(
                `select 
                t.nome as turma,
                count(distinct reprovados.matricula) as qtd
            from turma t
            join (
                select a.matricula, a.id_turma
                from aluno a
                join nota n on n.id_aluno = a.matricula
                group by a.matricula, a.id_turma
                having min(n.${nota}) < 7 
            ) as reprovados on reprovados.id_turma = t.id
            group by t.nome
            having t.nome like $1`,
                [turma + '%']
            );

            criticosLista = await pool.query(
                `with qtd_reprovadas as (
	            select
		            id_aluno,
		            count(distinct id) as qtd
	            from nota n
	            where n.${nota} < 7
	            group by id_aluno
            )
            select
	            t.nome as turma,
	            a.matricula as matricula,
	            a.nome as nome,
	            qr.qtd as qtd
            from turma t
            join aluno a
 	            on t.id = a.id_turma
            join qtd_reprovadas qr
	            on a.matricula = qr.id_aluno
            where t.nome like $1
            order by qtd desc
            limit 5`,
                [turma + '%']
            );

        }

        const totalAlunos = Number(qtdAlunosTotal.rows[0].qtd_alunos);
        const totalTurmas = Number(qtdSalasTotal.rows[0].qtd_turma);
        const totalProfessores = Number(qtdProfsTotal.rows[0].qtd_prof);

        const alunosLabels = qtdAlunosChart.rows.map(r => r.turma);
        const alunosValores = qtdAlunosChart.rows.map(r => Number(r.total));

        const mediaLabels = mediaAnoChart.rows.map(r => r.turma);
        const mediaValores = mediaAnoChart.rows.map(r => Number(r.media));

        const mediaMateriaLabels = mediaMateriaChart.rows.map(r => r.materia);
        const mediaMateriaValores = mediaMateriaChart.rows.map(r => Number(r.media));

        const aprovadosLabels = aprovadosChart.rows.map(r => r.turma);
        const aprovadosValores = aprovadosChart.rows.map(r => Number(r.qtd));

        const reprovadosLabels = reprovadosChart.rows.map(r => r.turma);
        const reprovadosValores = reprovadosChart.rows.map(r => Number(r.qtd));

        const criticos = criticosLista.rows.map(r => ({
            turma: r.turma,
            matricula: r.matricula,
            nome: r.nome,
            qtdRecuperacoes: Number(r.qtd)
        }));

        res.json({
            cards: {
                alunos: totalAlunos,
                turmas: totalTurmas,
                professores: totalProfessores
            },
            graficos: {
                alunosPorTurma: {
                    labels: alunosLabels,
                    values: alunosValores
                },
                media: {
                    labels: mediaLabels,
                    values: mediaValores
                },
                aprovados: {
                    labels: aprovadosLabels,
                    values: aprovadosValores
                },
                mediaMateria: {
                    labels: mediaMateriaLabels,
                    values: mediaMateriaValores
                },
                reprovados: {
                    labels: reprovadosLabels,
                    values: reprovadosValores
                }
            },
            listaCriticos: criticos
        });

    } catch (error) {
        console.error(error);
        res.status(500).json({ erro: "Erro no servidor" });
    }

});

app.listen(3000, () => {
    console.log("API rodando no host http://localhost:3000");
});