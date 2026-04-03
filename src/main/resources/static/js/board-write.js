
    /* ======================================================
    상태
    ====================================================== */
    const 상태 = { 모드: null, 키워드: '', 선택인덱스: -1, 타이머: null };

    const 에디터          = document.getElementById('본문에디터');
    const 멘션드롭다운    = document.getElementById('멘션드롭다운');
    const 해시태그드롭다운 = document.getElementById('해시태그드롭다운');

    /* ======================================================
    이미지 미리보기
    ====================================================== */
    function 이미지미리보기(입력요소) {
    const 파일 = 입력요소.files[0];
    if (!파일) return;

    document.getElementById('미리보기이미지').src = URL.createObjectURL(파일);
    document.getElementById('미리보기이미지').style.display = 'block';
    document.querySelector('.upload-placeholder').style.display = 'none';
}

    /* ======================================================
    에디터 입력 처리
    ====================================================== */
    function 에디터입력처리() {
    document.getElementById('글자수표시').textContent = `${에디터.innerText.length} / 2200`;
    트리거감지();
}

    /* ======================================================
    @ / # 트리거 감지
    ====================================================== */
    function 트리거감지() {
    const sel = window.getSelection();
    if (!sel.rangeCount) return;

    const 범위 = sel.getRangeAt(0).cloneRange();
    범위.selectNodeContents(에디터);
    범위.setEnd(sel.getRangeAt(0).endContainer, sel.getRangeAt(0).endOffset);

    const 일치 = 범위.toString().match(/(?:^|\s)([@#])(\S*)$/);

    if (!일치) {
    상태.모드 = null;
    [멘션드롭다운, 해시태그드롭다운].forEach(d => d.classList.remove('show'));
    return;
}

    상태.키워드 = 일치[2];

    if (일치[1] === '@') {
    상태.모드 = 'mention';
    해시태그드롭다운.classList.remove('show');
    멘션검색(상태.키워드);
} else {
    상태.모드 = 'hashtag';
    멘션드롭다운.classList.remove('show');
    해시태그검색(상태.키워드);
}
}

    /* ======================================================
    키보드 처리
    ====================================================== */
    function 에디터키입력처리(e) {
    const 드롭다운 = 상태.모드 === 'mention' ? 멘션드롭다운 : 해시태그드롭다운;
    if (!드롭다운.classList.contains('show')) return;

    const 항목들 = [...드롭다운.querySelectorAll('.dropdown-item')];

    const 동작 = {
    ArrowDown:  () => 상태.선택인덱스 = Math.min(상태.선택인덱스 + 1, 항목들.length - 1),
    ArrowUp:    () => 상태.선택인덱스 = Math.max(상태.선택인덱스 - 1, 0),
    Enter:      () => 상태.선택인덱스 >= 0 && 항목들[상태.선택인덱스].click(),
    Escape:     () => { 드롭다운.classList.remove('show'); 상태.모드 = null; },
};

    if (동작[e.key]) {
    e.preventDefault();
    동작[e.key]();
    항목들.forEach((항목, i) => 항목.classList.toggle('active', i === 상태.선택인덱스));
}
}

    /* ======================================================
    멘션 검색
    ====================================================== */
    function 멘션검색(키워드) {
    clearTimeout(상태.타이머);
    상태.타이머 = setTimeout(async () => {
    const 결과 = await API호출(`/api/users/search?keyword=${encodeURIComponent(키워드)}`);
    if (!결과?.length) { 멘션드롭다운.classList.remove('show'); return; }

    상태.선택인덱스 = -1;
    멘션드롭다운.innerHTML = 결과.map(u => `
            <div class="dropdown-item" onclick="멘션선택('${escape(u.name)}')">
                <div class="dropdown-avatar">${u.profileImg ? `<img src="${escape(u.profileImg)}">` : '👤'}</div>
                <div>
                    <div class="dropdown-name">@${escape(u.name)}</div>
                    <div class="dropdown-sub">${escape(u.email)}</div>
                </div>
            </div>`).join('');
    멘션드롭다운.classList.add('show');
}, 200);
}

    /* ======================================================
    해시태그 검색
    ====================================================== */
    function 해시태그검색(키워드) {
    clearTimeout(상태.타이머);
    상태.타이머 = setTimeout(async () => {
    const url = 키워드 ? `/api/hashtags/search?keyword=${encodeURIComponent(키워드)}` : '/api/hashtags/popular';
    const 결과 = await API호출(url);
    const 목록 = 키워드 ? 결과 : 결과?.slice(0, 5);
    if (!목록?.length) { 해시태그드롭다운.classList.remove('show'); return; }

    상태.선택인덱스 = -1;
    해시태그드롭다운.innerHTML = 목록.map(t => `
            <div class="dropdown-item" onclick="해시태그선택('${escape(t.name)}')">
                <div class="tag-icon">#</div>
                <div>
                    <div class="dropdown-name">#${escape(t.name)}</div>
                    <div class="dropdown-sub">게시물 ${(t.count ?? 0).toLocaleString()}개</div>
                </div>
            </div>`).join('');
    해시태그드롭다운.classList.add('show');
}, 200);
}

    /* ======================================================
    토큰 교체 (선택 시 @keyword → @실제이름 으로 교체)
    ====================================================== */
    function 현재토큰교체(교체문자열) {
    const sel = window.getSelection();
    const 노드 = sel.getRangeAt(0).endContainer;
    if (노드.nodeType !== Node.TEXT_NODE) return;

    const 커서 = sel.getRangeAt(0).endOffset;
    const 왼쪽 = 노드.textContent.slice(0, 커서);
    const 일치 = 왼쪽.match(/([@#]\S*)$/);
    if (!일치) return;

    const 시작 = 커서 - 일치[0].length;
    노드.textContent = 왼쪽.slice(0, 시작) + 교체문자열 + 노드.textContent.slice(커서);

    const 새범위 = document.createRange();
    새범위.setStart(노드, 시작 + 교체문자열.length);
    새범위.collapse(true);
    sel.removeAllRanges();
    sel.addRange(새범위);
}

    function 멘션선택(이름)    { 현재토큰교체(`@${이름} `); 멘션드롭다운.classList.remove('show');    상태.모드 = null; }
    function 해시태그선택(태그) { 현재토큰교체(`#${태그} `); 해시태그드롭다운.classList.remove('show'); 상태.모드 = null; }

    /* ======================================================
    게시물 등록
    ====================================================== */
    async function 게시물등록() {
    const 내용 = 에디터.innerText.trim();
    if (!내용) { alert('내용을 입력해주세요.'); return; }

    const 멘션목록    = [...내용.matchAll(/@(\S+)/g)].map(m => m[1]);
    const 해시태그목록 = [...내용.matchAll(/#(\S+)/g)].map(m => m[1]);

    /* TODO: fetch('/api/board/write.jsp', { method:'POST', body: JSON.stringify({내용, 멘션목록, 해시태그목록}) }) */
    const 버튼 = document.getElementById('공유버튼');
    버튼.disabled = true;
    버튼.textContent = '공유 중...';

    try {
    const 응답 = await fetch('/api/board/write', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({ 내용, 멘션목록, 해시태그목록 })
});

    if(!응답.ok) throw new Error(await 응답.text());

}catch (e){
    console.error('게시물 등록 실패: ', e);
    alert('게시물 등록에 실패했습니다. 다시 시도해주세요.');
    버튼.disabled = false;
    버튼.textContent = '공유하기';
}

    alert(`등록 준비 완료!\n멘션: ${멘션목록.join(', ') || '없음'}\n해시태그: ${해시태그목록.join(', ') || '없음'}`);
}

    /* ======================================================
    공통 유틸
    ====================================================== */
    async function API호출(url) {
    try { return await (await fetch(url)).json(); }
    catch (e) { console.error('API 오류', e); return null; }
}

    document.addEventListener('click', e => {
    if (!에디터.contains(e.target)) {
    [멘션드롭다운, 해시태그드롭다운].forEach(d => d.classList.remove('show'));
    상태.모드 = null;
}
});
