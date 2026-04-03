<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입 · instagram</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&family=Noto+Sans+KR:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/instagram.css">
</head>
<body>

<div class="auth-page">
    <div class="auth-wrap">

        <%-- 폰 목업 --%>
        <div class="phone">
            <div class="phone-notch">
                <div class="phone-pill"></div>
            </div>
            <div class="phone-screen">
                <div class="phone-post">
                    <div class="phone-post-header">
                        <div class="phone-post-avatar">🌿</div>
                        <span class="phone-post-username">new_user</span>
                    </div>
                    <div class="phone-post-img"
                         style="background: linear-gradient(45deg,#a18cd1,#fbc2eb)"></div>
                    <div class="phone-post-actions">❤️ 💬 📤</div>
                    <div class="phone-post-caption">첫 번째 게시물 🌱</div>
                </div>
                <div class="phone-post">
                    <div class="phone-post-header">
                        <div class="phone-post-avatar">🎨</div>
                        <span class="phone-post-username">art_lover</span>
                    </div>
                    <div class="phone-post-img"
                         style="background: linear-gradient(45deg,#fd7043,#ffca28)"></div>
                    <div class="phone-post-actions">🤍 💬 📤</div>
                    <div class="phone-post-caption">오늘의 작업물 🖌️</div>
                </div>
            </div>
        </div>

        <%-- 회원가입 폼 --%>
        <div class="auth-area">
            <div class="auth-box">

                <div class="auth-logo">instagram</div>
                <p class="auth-subtitle">친구들의 사진과 동영상을<br>보려면 가입하세요.</p>

                <div id="alert-box" class="alert"></div>

                <div class="field">
                    <input class="field-input" type="text" id="name" placeholder="이름">
                    <label class="field-label" for="name">이름</label>
                </div>

                <div class="field-row">
                    <div class="field">
                        <input class="field-input" type="email" id="email" placeholder="이메일">
                        <label class="field-label" for="email">이메일</label>
                    </div>

                </div>

                <button class="btn btn-blue" id="register-btn" style="margin-top:12px;" onclick="가입하기()">
                    가입하기
                </button>

                <p style="font-size:12px; color:#8e8e8e; text-align:center; margin-top:16px; line-height:1.6;">
                    가입하면 instagram의
                    <a href="#" style="color:#00376b;">약관</a>,
                    <a href="#" style="color:#00376b;">개인정보처리방침</a>에 동의하는 것입니다.
                </p>

            </div>

            <div class="auth-link-box">
                계정이 있으신가요? <a href="/login">로그인</a>
            </div>
        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<script src="/static/js/register.js"></script>
</body>
</html>