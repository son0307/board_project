document.addEventListener('DOMContentLoaded', function () {
    document.getElementById("submit-button").addEventListener("click", validateContent);

    const deleteButton = document.getElementById("delete-button")
    if(deleteButton != null) {
        deleteButton.addEventListener("click", deletePost);
    }

    document.querySelectorAll(".delete-comment-btn").forEach(button => {
       button.addEventListener("click", deleteComment)
    });

    document.querySelector(".like-button")?.addEventListener("click", toggleLike);
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

async function sendRequest(url, method, data = null) {
    try {
        const options = {
            method,
            headers: getJsonHeaders(),
        };

        if (data) {
            options.body = JSON.stringify(data);
        }

        const response = await fetch(url, options);


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

async function validateContent(event) {
    event.preventDefault();

    const postId = window.location.pathname.replace(/[^0-9]/g, "");
    const content = document.getElementById("comment-write").value.trim();

    // 내용이 비어있는 경우 경고 발생
    if(!content) {
        alert("댓글을 입력해주세요.");
        return;
    }

    // JSON 데이터 생성
    const data = { content };

    try {
        // 요청 전송
        await sendRequest(`/comments/${postId}/register`, "POST", data);

        alert("댓글이 등록되었습니다.");
        window.location.href = "/" + postId;
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
    }
}

async function deletePost() {
    if (!confirm("게시글을 삭제하시겠습니까?")) {
        return;
    }

    const postId = window.location.pathname.replace(/[^0-9]/g, "");

    try {
        // JSON 전송
        await sendRequest("/post/delete/" + postId, "DELETE");
        alert("게시글이 삭제되었습니다.");
        window.location.href="/";
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
        alert("서버와 통신 중 오류가 발생했습니다.");
    }
}

async function deleteComment() {
    if (!confirm("댓글을 삭제하시겠습니까?")) {
        return;
    }

    const postId = window.location.pathname.replace(/[^0-9]/g, "");
    const commentId = this.dataset.commentId;

    try {
        // JSON 전송
        await sendRequest(`/comments/${postId}/${commentId}`, "DELETE");
        alert("댓글이 삭제되었습니다.");
        location.reload();
    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
    }
}

async function toggleLike() {
    const postId = this.getAttribute("data-post-id");

    try {
        // 서버에 POST 요청 보내고 응답 데이터 받기
        const response = await sendRequest(`/post/toggle/${postId}`, "POST");
        const data = await response.json(); // JSON 데이터 파싱

        // 좋아요 상태 변경 (클래스 추가/제거)
        if (data.liked) {
            this.classList.add("liked");
        } else {
            this.classList.remove("liked");
        }

        // 좋아요 개수 업데이트
        this.textContent = `좋아요 (${data.favCount})`;

    } catch (error) {
        console.error("전송 중 오류 발생: ", error);
    }
}
