function togglePassword() {
    // 지금과 같이 password 를 input이 이라는 이름으로 작성을 해두면
    //다른 개발자가 나의 코드를 봤을 때 input이 어떤 input인가? email?? password ??인지 알 수 없는 상태
    //명칭은 다른 개발자가 봤을 때 아 이게 이거구나 하고 알 수 있는 명명규칙을 사용하는 것이 좋다
    const input = document.getElementById("password");   // input-> password로 교체해주는 것이 좋다
    const btn   = document.getElementById("pw-toggle-btn");   // 버튼은 로그인 버튼만 있는게 아닐 수 있다.
    //btn 또한 pw-btn 처럼 어떤 버튼인지 작성
    // ai 같은 경우 이름 단순 추천 XXXXX 명명 규칙은 누구나 확실하게 알 수 있는 명칭으로 작성

    if (input.type === "password") {                     // TODO 1-3: 조건 작성
        input.type  = "text";                          // TODO 1-4: 변경할 타입
        btn.textContent = "숨기기";                      // TODO 1-5: 버튼 텍스트
    } else {
        input.type  = "password";                          // TODO 1-6: 변경할 타입
        btn.textContent = "표시";                      // TODO 1-7: 버튼 텍스트
    }
}

async function 로그인() {
    const email    = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (!email || !password) {
        alert("이메일과 비밀번호를 입력하세요.");
        return;
    }

    const res = await fetch("/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
    });

    const data = await res.json();

    if (res.ok) {
        window.location.href = "/";
    } else {
        const el = document.getElementById("alert-box");
        el.className  = "alert alert-danger";
        el.textContent = data.message;
    }
}