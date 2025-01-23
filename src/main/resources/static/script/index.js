// 게시글 리스트 페이지 JS

// 검색 버튼 클릭 이벤트
document.addEventListener('DOMContentLoaded', () => {
   const keywordInput = document.getElementById('keyword-input');
   const searchButton = document.getElementById('search-button');

   keywordInput.addEventListener('keyup', function(event) {
       if(event.key === 'Enter') {
           performSearch()
       }
   })
   searchButton.addEventListener('click', () => performSearch());
});

// 키워드 입력창 텍스트 처리 함수
window.onload = function() {
    const urlParams = new URLSearchParams(window.location.search);
    const keyword = urlParams.get('keyword');

    if(keyword) {
        document.getElementById('keyword-input').value = keyword;
    }
}

// 검색 처리 함수
function performSearch() {
    const input = document.querySelector(".search-bar input");
    const keyword = encodeURIComponent(input.value);
    window.location.href = `http://localhost:8080/?keyword=${keyword}`;
}

// 이전 버튼 클릭 처리 함수
function handlePreviousPage(currentPage) {
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('page', currentPage - 1);
    window.location.href = currentUrl.toString();
}

// 다음 버튼 클릭 처리 함수
function handleNextPage(currentPage) {
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('page', currentPage + 1);
    window.location.href = currentUrl.toString();
}

// 페이지 번호 클릭 처리 함수
function handlePageNumber(pageNumber) {
    const currentUrl = new URL(window.location.href);
    currentUrl.searchParams.set('page', pageNumber);
    window.location.href = currentUrl.toString();
}