document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("submit-button").addEventListener("click", validateContent);
})

async function validateContent(event) {
    event.preventDefault();

    const post = window.location.pathname;
    const postId = post.replace(/[^0-9]/g, "");

    const content = document.getElementById("comment-write").value.trim();

    // 내용이 비어있는 경우 경고 발생
    if(!content) {
        alert("댓글을 입력해주세요.");
        event.preventDefault();
        return;
    }

    // JSON 데이터 생성
    const data = {
        content: content,
    };

    try {
        // JSON 전송
        const response = await fetch("/" + postId + "/comments/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        });

        if (response.ok) {
            alert("댓글이 등록되었습니다.");
            window.location.href="/" + postId;
        } else {
            const errorData = await response.json();
            alert(`오류 발생: ${errorData.message}`);
        }
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
    }
}