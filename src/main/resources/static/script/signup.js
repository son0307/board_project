// 폼 제출 이벤트와 validateForm 연결
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("form").addEventListener("submit", validateForm);
});

// 폼 제출 시 입력 내용 확인
async function validateForm(event) {

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();
    const nickname = document.getElementById("nickname").value.trim();

    // ID가 비어 있는 경우 경고 발생
    if (!username) {
        alert("ID를 입력해주세요.");
        event.preventDefault();
        return;
    }

    // 비밀번호가 비어 있는 경우 경고 발생
    if (!password) {
        alert("비밀번호를 입력해주세요.");
        event.preventDefault();
        return;
    }

    // 닉네임이 비어 있는 경우 경고 발생
    if (!nickname) {
        alert("닉네임을 입력해주세요.");
        event.preventDefault();
        return;
    }
}