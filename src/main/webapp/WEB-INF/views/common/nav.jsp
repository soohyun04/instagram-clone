<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="nav">
    <div class="nav-inner">
        <a href="/" class="nav-logo">instagram</a>

        <div id="검색창" class="nav-search-wrapper">
            <div class ="nav-search-box">
                <span class="nav-search-icon">검색아이콘</span>
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
            <a class="nav-icon">글쓰기</a>

            <c:choose>
                <c:when test="${not empty loginUser}">
                    <a href="/user/profile">
                        <c:choose>
                            <c:when test="${not empty loginUser.profile_img}">
                                <img class="nav-avatar" src="${loginUser.profile_img}" alt="프로필">
                            </c:when>
                            <c:otherwise>
                                <span class="nav-icon">👤</span>
                            </c:otherwise>
                        </c:choose>
                    </a>
                </c:when>
                <c:otherwise>
                    <a href="/user/login" class="nav-login">로그인</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>