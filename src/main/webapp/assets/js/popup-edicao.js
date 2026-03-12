function abrirPopupEdicao(idNota, nomeAluno, n1, n2, observacao) {
    document.getElementById('popup-id-nota').value  = idNota;
    document.getElementById('popup-nome-aluno').textContent = 'Aluno: ' + nomeAluno;
    document.getElementById('popup-n1').value = n1;
    document.getElementById('popup-n2').value = n2;
    document.getElementById('popup-observacao-nota').value = observacao;
    calcularPrevia();
    document.getElementById('popup-edicao').style.display = 'flex';
}

function fecharPopupEdicao() {
    document.getElementById('popup-edicao').style.display = 'none';
}

function calcularPrevia() {
    const n1 = parseFloat(document.getElementById('popup-n1').value);
    const n2 = parseFloat(document.getElementById('popup-n2').value);
    const mediaEl = document.getElementById('popup-media');

    if (!isNaN(n1) && !isNaN(n2)) {
        const media = ((n1 + n2) / 2).toFixed(1);
        mediaEl.textContent = media;
        mediaEl.style.color = media >= 7 ? '#21B558' : '#81009f';
    } else {
        mediaEl.textContent = '—';
        mediaEl.style.color = 'inherit';
    }
}

document.getElementById('popup-edicao').addEventListener('click', function (e) {
    if (e.target === this) fecharPopupEdicao();
});