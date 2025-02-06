// 폼 제출 이벤트와 validateForm 연결
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("form").addEventListener("submit", validateForm);

    const nicknameInput = document.getElementById("nickname");
    const submitButton = document.getElementById("submit-button");
    const withdrawButton = document.getElementById("withdraw-button");

    // 닉네임의 초기 값 저장
    const initialNickname = nicknameInput.value;

    if (submitButton != null) {
        // 입력 값이 변경될 때 버튼 활성화 여부 확인
        nicknameInput.addEventListener("input", function () {
            if (nicknameInput.value.trim() === initialNickname.trim()) {
                submitButton.disabled = true; // 변경되지 않았으면 비활성화
            } else {
                submitButton.disabled = false; // 변경되었으면 활성화
            }
        });

        withdrawButton.addEventListener("click", withdraw);
    }
});

//headers 에 csrfToken 설정
function getJsonHeaders() {
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    return {
        "Content-Type": "application/json",
        [csrfHeader]: csrfToken,
    }
}

async function sendRequest(url, method) {
    try {
        const response = await fetch(url, {
            method: method,
            headers: getJsonHeaders(),
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(`오류 발생: ${errorData.status}, ${errorData.error}`);
        }
        return response;
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
        throw error;
    }
}

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

async function withdraw() {
    if (!confirm("정말 탈퇴하시겠습니까?")) {
        return;
    }

    const userId = this.dataset.userId;

    try {
        await sendRequest(`/user/withdraw/${userId}`, "DELETE");
        window.location.href="/";
        alert("탈퇴가 완료되었습니다.");
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
    }
}