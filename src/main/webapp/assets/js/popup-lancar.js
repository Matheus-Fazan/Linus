function abrirPopupLancarNota() {
    document.getElementById('popup-lancar-matricula').value = '';
    document.getElementById('popup-lancar-n1').value = '';
    document.getElementById('popup-lancar-n2').value = '';
    document.getElementById('popup-lancar-observacao-nota').value = '';
    calcularPrevia();
    document.getElementById('popup-lancar').style.display = 'flex';
}

function fecharPopupLancarNota() {
    document.getElementById('popup-lancar').style.display = 'none';
}

document.getElementById('popup-lancar').addEventListener('click', function (e) {
    if (e.target === this) fecharPopupLancarNota();
});
function calcularPrevia() {
    const n1 = parseFloat(document.getElementById('popup-lancar-n1').value);
    const n2 = parseFloat(document.getElementById('popup-lancar-n2').value);
    const mediaEl = document.getElementById('popup-lancar-media');

    if (!isNaN(n1) && !isNaN(n2)) {
        const media = ((n1 + n2) / 2).toFixed(1);
        mediaEl.textContent = media;
        mediaEl.style.color = media >= 7 ? '#21B558' : '#81009f';
    } else {
        mediaEl.textContent = '-';
        mediaEl.style.color = 'inherit';
    }
}

document.getElementById('popup-lancar').addEventListener('click', function (e) {
    if (e.target === this) fecharPopupLancarNota();
});