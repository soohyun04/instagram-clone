//=========================
// 인기 해시태그 불러오기
//=========================
const 인기태그불러오기 = async () => {
    try {
        const 백엔드응답결과 = await fetch('/api/hashtags/popular');
        if (!백엔드응답결과.ok) throw new Error("해시태그 백엔드에서 가져오기 실패 : ", 백엔드응답결과.status);
        const 태그목록 = await 백엔드응답결과.json();
        태그목록렌더링(태그목록);

    } catch (에러) {
        console.error(에러);
    }
}

//=========================
// 해시태그 검색
//=========================
const 태그검색 = async (키워드) => {
    try {
        const 응답 = await fetch('/api/hashtags/search?keyword=' + encodeURIComponent(키워드))
        if (!응답.ok) throw new Error('검색 실패!')
        const 태그목록 = 응답.json();
        태그목록렌더링(태그목록);
        드롭다운열기();

    } catch (에러) {
        console.error(에러);
    }
}

//=========================
// 드롭다운 렌더링
//=========================
const 태그목록렌더링 = (태그목록) => {
    const 목록 = document.getElementById("태그목록");
    if (!목록) return;
    목록.innerHTML = '';
    태그목록.forEach(function (태그) {
        const 항목 = document.createElement('li');
        항목.className = 'hashtag-item';
        항목.innerHTML =
            '<span class="hashtag-tag">#' + 태그.name + '</span>'
            + '<span class="hashtag-count">' + 태그.count + '개 게시물</span>';
        항목.addEventListener('click', function () {
            document.getElementById('검색입력').value = '#' + 태그.name;
            드롭다운닫기();
            검색실행('#' + 태그.name);
        })
        목록.appendChild(항목);
    })
}

//=========================
// 드롭다운 열기 / 닫기
//=========================
const 드롭다운열기 = () => {
    const 드롭 = document.getElementById('태그드롭');
    if (드롭) 드롭.classList.add('active');
}

const 드롭다운닫기 = () => {
    const 드롭 = document.getElementById('태그드롭');
    if (드롭) 드롭.classList.remove('active');
}

//=========================
// 검색 실행
//=========================
const 검색실행 = (키워드) => {
    const 입력값 = 키워드 || document.getElementById('검색입력').value.trim();
    if (!입력값) return;
    location.href = '/search?keyword=' + encodeURIComponent(입력값);
}

//=========================
// 초기화
//=========================

document.addEventListener('DOMContentLoaded', function () {

    const 검색입력 = document.getElementById('검색입력');
    if (!검색입력) return;

    검색입력.addEventListener('focus', function () {
        인기태그불러오기();
        드롭다운열기();
    });

    검색입력.addEventListener('input', function () {
        const 키워드 = this.value.trim();
        if (!키워드) 인기태그불러오기();
        else 태그검색(키워드);

        검색입력.addEventListener('keydown', function (이벤트) {
            if (이벤트.key === 'Enter') {
                드롭다운닫기();
                검색실행();
            }
            if (이벤트.key === 'Escape') 드롭다운닫기();
        });

        document.addEventListener('click', function (이벤트) {
            const 검색창 = document.getElementById('검색창');
            if (검색창 && !검색창.contains(이벤트.target)) 드롭다운닫기();
        })
    })
});


const logout = async () => {
    // TODO 1 : /api/logout 으로 POST 요청 보내기 ( async / await)
    const res = await fetch('/api/logout', {method: 'POST'});

    // TODO 2: 요청 성공 시 res.ok
    if (res.ok) window.location.href = "/";
    // TODO 3: 메인 페이지에서 로그인 요청 전으로 변경하고 nav.jsp 로그인 상태 전으로 변경처리
}