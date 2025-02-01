// 폼 제출 이벤트와 validateForm 연결
document.addEventListener("DOMContentLoaded", function () {
    document.getElementById("form").addEventListener("submit", validateForm);
});

// 폼 제출 시 입력 내용 확인
async function validateForm(event) {
    event.preventDefault(); // 기본 폼 제출 방지

    const title = document.getElementById("title").value.trim();
    const content = document.getElementById("content").value.trim();

    //csrf token
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    //headers 에 csrfToken 설정
    //json 타입
    const jsonHeaders = {
        "Content-Type": "application/json",
    };
    jsonHeaders[csrfHeader] = csrfToken;

    // 제목이 비어 있는 경우 경고 발생
    if (!title) {
        alert("제목을 입력해주세요.");
        return;
    }

    // 내용이 비어 있는 경우 경고 발생
    if (!content) {
        alert("내용을 입력해주세요.");
        return;
    }

    // JSON 데이터 생성
    const data = {
        title: title,
        content: content,
    };

    const params = window.location.search;

    try {
        // JSON 전송
        const response = await fetch(`/post/register${params}`, {
            method: "POST",
            headers: jsonHeaders,
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert("게시글이 등록되었습니다.");
            window.location.href="/";
        } else {
            const errorData = await response.json();
            alert(`오류 발생: ${errorData.status}, ${errorData.error}`);
        }
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
    }
}