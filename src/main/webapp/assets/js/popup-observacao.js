function abrirPopupObservacao() {
    document.getElementById('obs-matricula').value          = '';
    document.getElementById('obs-texto').value              = '';
    document.getElementById('obs-contador').textContent     = '0 / 255 caracteres';
    document.getElementById('popup-observacao').style.display = 'flex';
}

function fecharPopupObservacao() {
    document.getElementById('popup-observacao').style.display = 'none';
}

document.getElementById('obs-texto').addEventListener('input', function () {
    document.getElementById('obs-contador').textContent = this.value.length + ' / 255 caracteres';
});

document.getElementById('popup-observacao').addEventListener('click', function (e) {
    if (e.target === this) fecharPopupObservacao();
});