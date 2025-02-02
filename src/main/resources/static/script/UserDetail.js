// 폼 제출 이벤트와 validateForm 연결
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("form").addEventListener("submit", validateForm);

    const nicknameInput = document.getElementById("nickname");
    const submitButton = document.getElementById("submit-button");

    // 닉네임의 초기 값 저장
    const initialNickname = nicknameInput.value;

    // 입력 값이 변경될 때 버튼 활성화 여부 확인
    nicknameInput.addEventListener("input", function () {
        if (nicknameInput.value.trim() === initialNickname.trim()) {
            submitButton.disabled = true; // 변경되지 않았으면 비활성화
        } else {
            submitButton.disabled = false; // 변경되었으면 활성화
        }
    });
});

// 폼 제출 시 입력 내용 확인
async function validateForm(event) {

    const username = document.getElementById("username").value.trim();
    const nickname = document.getElementById("nickname").value.trim();

    // ID가 비어 있는 경우 경고 발생
    if (!username) {
        alert("ID를 입력해주세요.");
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