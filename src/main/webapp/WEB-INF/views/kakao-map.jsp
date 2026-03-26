<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>카카오맵 마커</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="/static/css/kakao-map.css">
</head>
<body>
<%--    html css 트렌드
<header> = 태그 안에는 header 만들 때 기본으로 만들어진 css 가 장착
<div>    = 주로 사용하는 트렌드  -> 프레임워크 : react, vue 의 경우 div 를 기준으로 코딩을 진행한다.

--%>
<%-- --헤더 -- --%>
<div class="header">
    <div class="header-dot"></div>
    <h1>카카오맵 마커</h1>
    <span>총 5개 저장소</span>
</div>

<div class="map-wrap">
    <div id="map"></div>
    <div class="side-panel">
        <h2>장소 목록</h2>
        <div id="place-list"></div>
    </div>
</div>
<script>
    //=======================================================
    // 위도/경도 JSON 데이터
    // 실제 프로젝트에서는 Controller 에서 위도 경도를 db 로 전달받아
    // 동적으로 출력
    // API 연결 스크립트의 경우 최하단 배치
    // 웹 브라우저에서 map 생성하고 initMap 호출 과 같은 여러 작업을 한 다음에 마커를 찍는 작업
    //=======================================================
    const locations = [
        {id: 1, name: "경복궁", desc: "조선 시대 대표 궁궐", lat: 37.5796, lng: 126.9770, icon: "🏯", color: "#FF6B6B"},
        {id: 2, name: "남산서울타워", desc: "서울 야경 명소", lat: 37.5512, lng: 126.9882, icon: "🗼", color: "#4ECDC4"},
        {id: 3, name: "홍대 거리", desc: "젊음과 문화의 거리", lat: 37.5563, lng: 126.9236, icon: "🎨", color: "#FFE66D"},
        {id: 4, name: "강남역", desc: "서울 최대 번화가", lat: 37.4979, lng: 127.0276, icon: "🏙️", color: "#A8E6CF"},
        {id: 5, name: "한강공원", desc: "시민 휴식 공간", lat: 37.5283, lng: 126.9341, icon: "🌊", color: "#74B9FF"}
    ];

    //=======================================================
    // 사이드 패널 장소 목록 렌더링
    //=======================================================
    // placeList
    const 장소들 = document.getElementById("place-list");
    // location = loc index = idx button = btn String = str message = msg
    locations.forEach((loc, idx) => {
        const item = document.createElement("div");
        item.className = "place-item";
        item.id = "place-" + loc.id;
        item.innerHTML = `
        <div class="place-icon" style="background:${loc.color}22; color:${loc.color}">
            ${loc.icon}
        </div>
        <div class="place-info">
            <div class="place-name">${loc.name}</div>
            <div class="place-desc">${loc.desc}</div>
        </div>

        `;
        item.addEventListener("click", () => 마커이동(idx));
        장소들.appendChild(item);
    });

    // =====================================================
    // 카카오로 지도 띄우기
    // =====================================================
    //let 지도 ;
    //let 마커목록 = [];
    //let 인포윈도우목록 =[];
    let 지도, 마커목록 = [], 인포윈도우목록 = [];

    function 지도초기화() {
        const container = document.getElementById("map"); // 전체 지도 위치를 하나의 컨테이너로 잡는다.
        const options = {
            // 현재 위치를 기준으로 하고 싶어요 -> 현재 위치가 제대로 인식이 안될 수 있다.
            // 현재 위치 바꾸기
            center: new kakao.maps.LatLng(37.5400, 126.9700),  // LatLng(위도, 경도) 서울 중심으로 해서 가운데로 잡는다.
            level: 8
        };
        // 지도 생성하기
        지도 = new kakao.maps.Map(container, options);

        // 지도 컨트롤 추가
        지도.addControl(new kakao.maps.ZoomControl(), kakao.maps.ControlPosition.RIGHT);
        지도.addControl(new kakao.maps.MapTypeControl(), kakao.maps.ControlPosition.TOPRIGHT);

        // 마커 + 인포윈도우 생성
        locations.forEach((loc, idx) => {
            // 마커 생성
            const 마커 = new kakao.maps.Marker({ // Maker -> Marker
                map: 지도,
                position: new kakao.maps.LatLng(loc.lat, loc.lng),
                title: loc.name
            });

            // 인포윈도우 내용 (말풍선 팝업)
            const 인포윈도우내용 = `
            <div class="custom-infowindow">
                <div class="iw-name">${loc.icon} ${loc.name}</div>
                <div class="iw-desc">${loc.desc}</div>
                <span class="iw-badge" style="background:${loc.color}22; color:${loc.color}">
                   ${loc.lat.toFixed(4)} ${loc.lng.toFixed(4)}
                </span>
            </div>
            `;
            const 인포윈도우 = new kakao.maps.InfoWindow({
                content: 인포윈도우내용,
                removable: true, // 닫기 버튼을 표기하겠다.
            });

            // 마커 클릭 -> 다른 인포윈도우를 모두 닫고 해당 클릭된 인포윈도우 열기
            kakao.maps.event.addListener(마커, "click", () => {
                인포윈도우목록.forEach(iw => iw.close());
                인포윈도우.open(지도, 마커);
                사이드아이템강조(idx);
            });

            마커목록.push(마커);
            인포윈도우목록.push(인포윈도우);

        });
    }


    // 사이드 패널 클릭 -> 지도 이동 + 내부에서 인포윈도우 열기
    function 마커이동(idx) {
        const loc = locations[idx];
        지도.setCenter(new kakao.maps.LatLng(loc.lat, loc.lng));
        지도.setLevel(5);
        인포윈도우목록.forEach(iw => iw.close());
        인포윈도우목록[idx].open(지도, 마커목록[idx]);
        사이드아이템강조(idx);
    }

    // 사이드 아이템 강조 기능
    function 사이드아이템강조(idx) {
        document.querySelectorAll(".place-item").forEach(item => {item.classList.remove("active");});
        document.getElementById("place-"+locations[idx].id).classList.add("active");

    }


</script>
<%--
카카오에서 플랫폼 키를 가져올 때 , 테스트앱 -> 비즈앱에 되어 있는 키를 가져온다.
비즈앱에 있는 키의 경ㅇ우 사업자등록을 한 사람들이 정식적으로 운영하는 서비스이고,
이 서비스를 운영하기 위해서 개발자가 테스트하는 용도로 총 5개까지 만들어서 테스트 할 수 있도록 테스트앱 제공
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=18de8ecfa54c34e3e1fdd591e1d33a4e&onload=지도초기화"></script>
--%>
<%-- 비즈앱이 아닌 테스트앱 -> 비즈앱에 기재되어 있는 플랫촘 키에서 javascript키를 가져와 사용한다. --%>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2c071a49bcf535fcf9dd9e70e4c2fab5&autoload=false"></script>
<script>
    kakao.maps.load(function (){
        지도초기화();
    });
</script>
</body>
</html>