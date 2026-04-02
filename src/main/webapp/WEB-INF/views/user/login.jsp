<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>로그인 · instagram</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&family=Noto+Sans+KR:wght@300;400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/instagram.css">
</head>
<body>

<div class="layout">
    <div class="auth-wrap">

        <%-- 폰 목업 --%>
        <div class="phone">
            <div class="phone-notch">
                <div class="phone-pill"></div>
            </div>
            <div class="phone-screen">
                <div class="phone-post">
                    <div class="phone-post-header">
                        <div class="phone-post-avatar">🌸</div>
                        <span class="phone-post-username">spring_lover</span>
                    </div>
                    <div class="phone-post-img"
                         style="background: linear-gradient(45deg,#f09433,#dc2743)"></div>
                    <div class="phone-post-actions">❤️ 💬 📤</div>
                    <div class="phone-post-caption">Spring Boot로 만든 하루 ☕</div>
                </div>
                <div class="phone-post">
                    <div class="phone-post-header">
                        <div class="phone-post-avatar">🚀</div>
                        <span class="phone-post-username">dev_kim</span>
                    </div>
                    <div class="phone-post-img"
                         style="background: linear-gradient(45deg,#4facfe,#00f2fe)"></div>
                    <div class="phone-post-actions">🤍 💬 📤</div>
                    <div class="phone-post-caption">JWT 인증 구현 완료! 🎉</div>
                </div>
            </div>
        </div>

        <%-- 로그인 폼 --%>
        <div class="auth-area">
            <div class="auth-box">

                <div class="auth-logo">instagram</div>

                <div id="alert-box" class="alert"></div>

                <div class="field">
                    <input class="field-input" type="email" id="email" placeholder="이메일">
                    <label class="field-label" for="email">이메일</label>
                </div>

                <div class="field">
                    <input class="field-input" type="password" id="password" placeholder="비밀번호">
                    <label class="field-label" for="password">비밀번호</label>
                    <button class="field-pw-toggle" id="pw-toggle-btn" type="button" onclick="togglePassword()">표시</button>
                </div>

                <button class="btn btn-blue" id="login-btn" style="margin-top:8px;" onclick="로그인()">
                    로그인
                </button>
                <span class="spinner" id="spinner"></span>

                <div class="divider">
                    <div class="divider-line"></div>
                    <span class="divider-text">또는</span>
                    <div class="divider-line"></div>
                </div>
                <style>
                    .btn-kakao {
                        background-color: #FEE500;
                        color: #191919;
                        font-weight: 600;
                        border: none;
                        border-radius: 6px;
                        padding: 10px;
                        width: 100%;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        text-decoration: none;
                        font-size: 14px;
                        margin-bottom: 8px;
                    }
                    .btn-kakao:hover {
                        background-color: #F0D800;
                        color: #191919;
                    }
                </style>
                <a href="/api/kakao/login" class="btn btn-kakao">
                    카카오 로그인
                </a>
                <a href="/user/find-email" style="font-size:12px; color:#00376b; margin-top:8px;">
                    비밀번호를 잊으셨나요?
                </a>

            </div>

            <div class="auth-link-box">
                계정이 없으신가요? <a href="/user/register">가입하기</a>
            </div>
        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<script src="/static/js/login.js"></script>
</body>
</html>

