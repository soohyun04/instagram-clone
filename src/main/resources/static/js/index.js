//=========================
// 인기 해시태그 불러오기
//=========================
function 인기태그불러오기() {
    fetch('/api/hashtags/popular')
        .then(function (백엔드응답결과) {
            if (!백엔드응답결과.ok) throw new Error("해시태그 백엔드에서 가져오기 실패 : ", 백엔드응답결과.status);
            return 백엔드응답결과.json(); //문자열로 온 데이터들을 javascript 사용하는 json 형태로 변환해서 반환
        })
        .then(function (태그목록) {
            태그목록렌더링(태그목록);
        })
        .catch(function (에러) {
            console.error(에러);
        });
}

//=========================
// 해시태그 검색
//=========================
function 태그검색(키워드) {
    fetch('/api/hashtags/search?keyword=' + encodeURIComponent(키워드))
        .then(function (응답) {
            if (!응답.ok) throw new Error('검색 실패!')
            return 응답.json();
        })
        .then(function (태그목록) {
            태그목록렌더링(태그목록);
            드롭다운열기();
        })
        .catch(function (에러) {
            console.error(에러);
        });
}

//=========================
// 드롭다운 렌더링
//=========================
function  태그목록렌더링(태그목록) {
    const 목록 = document.getElementById("태그목록");
    if(!목록) return;
    목록.innerHTML= '';

    태그목록.forEach(function (태그){
        const 항목 = document.createElement('li');
        항목.className='hashtag-item';
        항목.innerHTML =
            '<span class="hashtag-tag">#' + 태그.name + '</span>'
            + '<span class="hashtag-count">' + 태그.count + '개 게시물</span>';
        // 만약 `` 를 사용하고 싶지 않다면 위와 같이 ++ 와 '' "" 조합으로 작성할 수 있다.

        항목.addEventListener('click', function () {
            document.getElementById('검색입력').value = '#' + 태그.name;
            드롭다운닫기();
            검색실행('#'+태그.name);
        })
        목록.appendChild(항목);
    })
}

//=========================
// 드롭다운 열기 / 닫기
//=========================
function  드롭다운열기(){
    const 드롭 = document.getElementById('태그드롭');
    if (드롭) 드롭.classList.add('active');
}

function  드롭다운닫기(){
    const 드롭 = document.getElementById('태그드롭');
    if (드롭) 드롭.classList.remove('active');
}
//=========================
// 검색 실행
//=========================
function  검색실행(키워드){
    const 입력값 = 키워드 || document.getElementById('검색입력').value.trim();
    if (!입력값) return;
    location.href = '/search?keyword=' + encodeURIComponent(입력값);
}
//=========================
// 초기화
//=========================

document.addEventListener('DOMContentLoaded', function () {
    const 검색입력 = document.getElementById('검색입력');
    if(!검색입력) return;

    검색입력.addEventListener('focus', function () {
        인기태그불러오기();
        드롭다운열기();
    });

    // function -> this  const this 가 없다.
    검색입력.addEventListener('input', function () {
        const 키워드 = this.value.trim();
        if (!키워드) {
            인기태그불러오기();
        } else {
            태그검색(키워드);
        }
    });

    검색입력.addEventListener('keydown', function (이벤트) {
        if(이벤트.key ==='Enter') {
            드롭다운닫기();
            검색실행();
        }

        if(이벤트.key === 'Escape') { 드롭다운닫기();}
    });

    document.addEventListener('click',function (이벤트){
        const 검색창 = document.getElementById('검색창');
        if(검색창 && !검색창.contains(이벤트.target)) {
            드롭다운닫기();
        }
    })
})









