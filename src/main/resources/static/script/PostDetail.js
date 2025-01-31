document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("submit-button").addEventListener("click", validateContent);
})

async function validateContent(event) {
    event.preventDefault();

    //csrf token
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
    const csrfToken = document.querySelector('meta[name="_csrf"]').content;

    const post = window.location.pathname;
    const postId = post.replace(/[^0-9]/g, "");

    const content = document.getElementById("comment-write").value.trim();

    //headers 에 csrfToken 설정
    //json 타입
    const jsonHeaders = {
        "Content-Type": "application/json",
    };
    jsonHeaders[csrfHeader] = csrfToken;

    // 내용이 비어있는 경우 경고 발생
    if(!content) {
        alert("댓글을 입력해주세요.");
        return;
    }

    // JSON 데이터 생성
    const data = {
        content: content,
    };

    try {
        // JSON 전송
        const response = await fetch("/comments/" + postId + "/register", {
            method: "POST",
            headers: jsonHeaders,
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert("댓글이 등록되었습니다.");
            window.location.href="/" + postId;
        } else {
            const errorData = await response.json();
            alert(`오류 발생: ${errorData.status}, ${errorData.error}`);
        }
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
    }
}