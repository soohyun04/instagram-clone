<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시물 작성 - Instagram</title>
    <link rel="stylesheet" href="/static/css/instagram.css">
    <link rel="stylesheet" href="/static/css/nav.css">
    <link rel="stylesheet" href="/static/css/board-write.css">

</head>
<body>
<%@include file="../common/nav.jsp" %>

<div class="write-wrap">
    <!-- 헤더 -->
    <div class="write-header">
        <button class="cancel-btn" onclick="history.back()">취소</button>
        <span>새 게시물</span>
        <button id="공유버튼" onclick="게시물등록()">공유하기</button>
    </div>

    <!-- 이미지 업로드 -->
    <div class="image-upload-area" id="이미지영역">
        <div class="upload-placeholder">
            <span>🖼️</span>
            <p>사진을 선택하세요</p>
        </div>
        <img id="미리보기이미지" alt="미리보기">
        <input type="file" accept="image/*" onchange="이미지미리보기(this)">
    </div>

    <!-- 본문 -->
    <div class="write-body">
        <div class="editor-wrapper">
            <div id="본문에디터"
                 class="content-editor"
                 contenteditable="true"
                 data-placeholder="문구를 입력하세요... @멘션, #해시태그를 사용해보세요"
                 oninput="에디터입력처리(event)"
                 onkeydown="에디터키입력처리(event)">
            </div>

            <!-- @ 멘션 자동완성 드롭다운 -->
            <div id="멘션드롭다운" class="autocomplete-dropdown"></div>

            <!-- # 해시태그 자동완성 드롭다운 -->
            <div id="해시태그드롭다운" class="autocomplete-dropdown"></div>
        </div>
    </div>

    <div class="write-footer">
        <span>@멘션, #해시태그 입력 가능</span>
        <span class="char-count" id="글자수표시">0 / 2200</span>
    </div>
</div>
<!--
스크립트 에서는 board 라는 폴더 내에 존재하는데 왜 ../static 이 아니라 /static 일까?!
script src="../static/js/board-write.js"></script
<script src="/static/js/board-write.js" type="text/javascript; charset=UTF-8"></script> -->
<script src="/static/js/board-write.js" charset="UTF-8"></script>

</body>
</html>