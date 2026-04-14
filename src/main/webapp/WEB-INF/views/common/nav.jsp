<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="/static/css/board-write.css">
<nav class="nav">
    <div class="nav-inner">
        <a href="/" class="nav-logo">instagram</a>

        <div id="검색창" class="nav-search-wrapper">
            <div class ="nav-search-box">
                <span class="nav-search-icon">🔎</span>
                <input id="검색입력"
                       class="nav-search-input"
                       type="text"
                       placeholder="검색"
                       autocomplete="off">
            </div>
            <div class="hashtag-dropdown"
                 id="태그드롭">
                <p class="hashtag-dropdown-title">인기 해시태그</p>
                <ul class="hashtag-list"
                    id="태그목록">
                    <li class="hashtag-loading">불러오는 중 ...</li>
                </ul>
            </div>
        </div>

        <div class="nav-icons">
            <a class="nav-icon" href="/">홈</a>
            <a class="nav-icon" href="/map">지도</a>
            <a class="nav-icon" href="/board/write">글쓰기</a>

            <c:choose>
                <c:when test="${not empty loginUser}">
                    <a href="/user/profile">
                        <c:choose>
                            <c:when test="${not empty loginUser.profileImg}">
                                <img class="nav-avatar" src="${loginUser.profileImg}" alt="프로필">
                            </c:when>
                            <c:otherwise>
                                <span class="nav-icon">👤</span>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/login" class="nav-login">로그인</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>

<script src="static/js/nav.js"></script>
<script src="static/js/index.js"></script>
<%--
이렇게 두면 nav.jsp 를 include 하는 모든 jsp 에서는
nav.js와 index.js을 사용할 수 있다.
--%>