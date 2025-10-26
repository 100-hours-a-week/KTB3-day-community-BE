const agree = document.getElementById('agree');
const proceedBtn = document.getElementById('proceedBtn');

function updateButton() {
    if (agree.checked) {
        proceedBtn.disabled = false;
        proceedBtn.classList.add('enabled');
    } else {
        proceedBtn.disabled = true;
        proceedBtn.classList.remove('enabled');
    }
}
agree.addEventListener('change', updateButton);
updateButton();

proceedBtn.addEventListener('click', () => {
    if (!agree.checked) {
        alert('이용약관에 동의해 주세요.');
        return;
    }
    const next = proceedBtn.dataset.nextPage;
    window.location.href = next;
});
