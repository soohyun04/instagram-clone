<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="nav">
    <div class="nav-inner">
        <a href="/" class="nav-logo">instagram</a>

        <div class="nav-icons">
            <button class="nav-btn">
                <img src ="/static/img/icon-home.png" alt="홈" class="nav-icon">
            </button>
            <button class="nav-btn">
                <img src ="/static/img/icon-search.png" alt="검색" class="nav-icon">
            </button>
            <button class="nav-btn">
                <img src ="/static/img/icon-newpost.png" alt="새 게시물" class="nav-icon">
            </button>
            <button class="nav-btn">
                <img src ="/static/img/icon-like.png" alt="좋아요" class="nav-icon">
            </button>
            <button class="nav-btn">
                <img src ="/static/img/icon-more.png" alt="더보기" class="nav-icon">
            </button>

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