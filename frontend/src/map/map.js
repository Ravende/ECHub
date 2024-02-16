// <div class="map_wrap" style="width:100%;height:600px;">
//     <div id="map" style="width:100%;height:600px;position:relative;overflow:hidden;"></div>
//     <!-- 지도 확대, 축소 컨트롤 div -->
//     <div class="custom_zoomcontrol radius_border">
//         <span onclick="zoomIn()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png"
//                 alt="확대"></span>
//         <span onclick="zoomOut()"><img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png"
//                 alt="축소"></span>
//     </div>
// </div>

/* 지도 생성 */
const mapContainer = document.getElementById('map');
const mapOptions = {
  center: new kakao.maps.LatLng(37.559716, 126.945468), // 중심좌표
  level: 3, // 지도 확대 레벨
};

const map = new kakao.maps.Map(mapContainer, mapOptions);

/* 지도 확대, 축소 버튼 */
function zoomIn() {
  map.setLevel(map.getLevel() - 1);
}
function zoomOut() {
  map.setLevel(map.getLevel() + 1);
}

// 장소 검색 객체
const ps = new kakao.maps.services.Places(map);
// 키워드 검색
ps.keywordSearch('카페', placesSearchCB, { radius: 1000, location: new kakao.maps.LatLng(37.559716, 126.945468) });

// 카테고리 검색 - 카페: CE7
ps.categorySearch('CE7', placesSearchCB, { radius: 1000, location: new kakao.maps.LatLng(37.559716, 126.945468) });

// 키워드 검색 완료 시 호출되는 콜백함수
function placesSearchCB(data, status, pagination) {
  if (status === kakao.maps.services.Status.OK) {
    for (let i = 0; i < data.length; i++) {
      displayMarker(data[i]);
    }
  }
}

/* 인포윈도우 */
// var iwContent = '<div style="padding:5px;font-size:20px;">' + place.place_name + '</div>'; // 인포윈도우 내용
const iwRemoveable = true; // 닫는 x버튼이 표시

const infowindow = new kakao.maps.InfoWindow({
  zIndex: 1,
  // content: iwContent,
  removable: iwRemoveable,
});

/* 마커 */
function displayMarker(place) {
  const marker = new kakao.maps.Marker({
    map,
    position: new kakao.maps.LatLng(place.y, place.x),
  });

  // 마커에 클릭이벤트
  kakao.maps.event.addListener(marker, 'click', () => {
    // 클릭 시 상세 페이지 접속
  });
  // 마우스오버 이벤트
  kakao.maps.event.addListener(marker, 'mouseover', () => {
    infowindow.setContent('<div style="padding:5px;font-size:12px;">' + place.place_name + '</div>'); // 장소명 표출
    infowindow.open(map, marker); // 마우스오버 -> 인포윈도우 표시
  });
  kakao.maps.event.addListener(marker, 'mouseout', () => {
    infowindow.close(); // 마우스오버 -> 인포윈도우 제거
  });
}

// 마커 지도 위에 표시
marker.setMap(map);
