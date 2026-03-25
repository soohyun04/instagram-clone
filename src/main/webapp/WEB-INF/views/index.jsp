<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>instagram</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&family=Noto+Sans+KR:wght@300;400;500;600&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/static/css/instagram.css">
</head>
<body>

<%@include file="common/nav.jsp"%>

<%-- MAIN --%>
<div class="layout">

    <%-- 피드 --%>
    <div class="feed">

        <%-- 스토리 --%>
        <div class="story-list">
            <c:if test="${not empty loginUser}">
                <button class="story">
                    <div class="story-add">➕</div>
                    <span class="story-name">내 스토리</span>
                </button>
            </c:if>

            <%-- 스토리 목록 (Controller: model.addAttribute("stories", ...)) --%>
            <c:forEach var="story" items="${stories}">
                <div class="story">
                    <div class="story-ring">
                        <c:choose>
                            <c:when test="${not empty story.user.profile_img}">
                                <img class="story-avatar" src="${story.user.profile_img}" alt="${story.user.name}">
                            </c:when>
                            <c:otherwise>
                                <div class="story-avatar">👤</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <span class="story-name">${story.user.name}</span>
                </div>
            </c:forEach>

            <%-- 스토리 없을 때 샘플 --%>
            <c:if test="${empty stories}">
                <div class="story">
                    <div class="story-ring">
                        <div class="story-avatar">🌸</div>
                    </div>
                    <span class="story-name">spring</span>
                </div>
                <div class="story">
                    <div class="story-ring">
                        <div class="story-avatar">🚀</div>
                    </div>
                    <span class="story-name">dev</span>
                </div>
                <div class="story">
                    <div class="story-ring">
                        <div class="story-avatar">☕</div>
                    </div>
                    <span class="story-name">java</span>
                </div>
                <div class="story">
                    <div class="story-ring">
                        <div class="story-avatar">🌿</div>
                    </div>
                    <span class="story-name">nature</span>
                </div>
            </c:if>
        </div>

        <%-- 포스트 목록 (Controller: model.addAttribute("posts", ...)) --%>
        <c:choose>
            <c:when test="${not empty posts}">
                <c:forEach var="post" items="${posts}" varStatus="vs">
                    <article class="post" style="animation-delay: ${vs.index * 0.08}s">

                        <div class="post-header">
                            <div class="post-avatar-ring">
                                <c:choose>
                                    <c:when test="${not empty post.user.profile_img}">
                                        <img class="post-avatar" src="${post.user.profile_img}" alt="">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="post-avatar">👤</div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <div>
                                <div class="post-username">${post.user.name}</div>
                                <div class="post-location">${post.location}</div>
                            </div>
                            <button class="post-more">···</button>
                        </div>

                        <div class="post-image">
                            <c:choose>
                                <c:when test="${not empty post.imageUrl}">
                                    <img src="${post.imageUrl}" alt="게시물 이미지">
                                </c:when>
                                <c:otherwise>🖼️</c:otherwise>
                            </c:choose>
                        </div>

                        <div class="post-actions">
                            <button class="action-btn" onclick="toggleLike(this)">🤍</button>
                            <button class="action-btn">💬</button>
                            <button class="action-btn">📤</button>
                            <button class="action-btn action-save">🔖</button>
                        </div>

                        <div class="post-likes" data-count="${post.likeCount}">좋아요 ${post.likeCount}개</div>

                        <div class="post-caption">
                            <span class="post-caption-user">${post.user.name}</span>${post.content}
                        </div>

                        <c:if test="${post.commentCount > 0}">
                            <button class="post-comments-link">댓글 ${post.commentCount}개 모두 보기</button>
                        </c:if>

                        <div class="post-time">${post.timeAgo}</div>

                        <div class="comment-row">
                            <input class="comment-input"
                                   type="text"
                                   placeholder="댓글 달기..."
                                   oninput="onCommentInput(this)">
                            <button class="comment-btn">게시</button>
                        </div>
                    </article>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <c:if test="${empty loginUser}">
                    <div class="empty">
                        <div class="empty-icon">📸</div>
                        <h3>친구들의 사진과 동영상</h3>
                        <p>계정을 만들어 친구들의 사진과 동영상을 확인하세요.</p>
                        <a href="/user/register" class="btn btn-blue"
                           style="display:inline-block;width:auto;padding:8px 24px;">가입하기</a>
                    </div>
                </c:if>
            </c:otherwise>
        </c:choose>
    </div>

    <%-- 사이드바 --%>
    <aside class="sidebar">

        <c:choose>
            <c:when test="${not empty loginUser}">
                <div class="sidebar-profile">
                    <div class="sidebar-avatar-ring">
                        <c:choose>
                            <c:when test="${not empty loginUser.profile_img}">
                                <img class="sidebar-avatar" src="${loginUser.profile_img}" alt="프로필">
                            </c:when>
                            <c:otherwise>
                                <div class="sidebar-avatar">👤</div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div>
                        <div class="sidebar-name">${loginUser.name}</div>
                        <div class="sidebar-email">${loginUser.email}</div>
                    </div>
                    <button class="sidebar-logout" onclick="logout()">로그아웃</button>
                </div>
            </c:when>

            <c:otherwise>
                <div class="guest-box">
                    <p>로그인하고 친구들의 게시물을 확인하세요.</p>
                    <a href="/user/login" class="btn btn-blue" style="margin-bottom:8px;">로그인</a>
                    <a href="/user/register" class="btn btn-outline">가입하기</a>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="sidebar-title">
            <span>회원님을 위한 추천</span>
            <a href="#">모두 보기</a>
        </div>

        <%-- 추천 유저 (Controller: model.addAttribute("suggestedUsers", ...)) --%>
        <c:choose>
            <c:when test="${not empty suggestedUsers}">
                <c:forEach var="suggest" items="${suggestedUsers}">
                    <div class="suggest">
                        <div class="suggest-avatar">
                            <c:choose>
                                <c:when test="${not empty suggest.profile_img}">
                                    <img src="${suggest.profile_img}" alt="${suggest.name}">
                                </c:when>
                                <c:otherwise>👤</c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                            <div class="suggest-name">${suggest.name}</div>
                            <div class="suggest-sub">회원님을 위한 추천</div>
                        </div>
                        <button class="follow-btn" onclick="toggleFollow(this)">팔로우</button>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="suggest">
                    <div class="suggest-avatar">🌸</div>
                    <div>
                        <div class="suggest-name">spring_lover</div>
                        <div class="suggest-sub">회원님을 위한 추천</div>
                    </div>
                    <button class="follow-btn" onclick="toggleFollow(this)">팔로우</button>
                </div>
                <div class="suggest">
                    <div class="suggest-avatar">🚀</div>
                    <div>
                        <div class="suggest-name">java_dev_kr</div>
                        <div class="suggest-sub">회원님을 위한 추천</div>
                    </div>
                    <button class="follow-btn" onclick="toggleFollow(this)">팔로우</button>
                </div>
                <div class="suggest">
                    <div class="suggest-avatar">☕</div>
                    <div>
                        <div class="suggest-name">backend_kim</div>
                        <div class="suggest-sub">회원님을 위한 추천</div>
                    </div>
                    <button class="follow-btn" onclick="toggleFollow(this)">팔로우</button>
                </div>
            </c:otherwise>
        </c:choose>

        <%@include file="common/footer.jsp"%>
    </aside>
</div>

<script src="/static/js/index.js"></script>
</body>
</html>