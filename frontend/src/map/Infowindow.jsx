const data = [];
const m_infos = [];
const positions = [];

axios
  .get('https://echubserver.shop:8080/api/cafe/search?q={keyword}')
  .then(response => {
    console.log('Cafe Data:', response.data);
    setCafeDataInfo(response.data); // Assuming the response is a list of cafes

    for (let i = 0; i < data.length; i++) {
      let m_g = data[i].place_name;
      let m_a = data[i].address_name;
      let m_x = data[i].x;
      let m_y = data[i].y;

      let m_infos = { 카페명: m_g, 카페주소: m_a, 카페경도: m_x, 카페위도: m_y };
      m_infos.push(m_info);
    }

    //  아래는 마커와 인포윈도우 여러개 표시
    for (let i = 0; i < m_infos.length; i++) {
      let cafeName = m_infos[i].place_name;
      let m_i_x = m_infos[i].x;
      let m_i_y = m_infos[i].y;
      let m_a = m_infos[i].address_name;

      var gb_position = {
        content: (
          <div key={cafeData.cafeId} className="InfoWindow">
            <h3 style={{ lineHeight: '2.0' }}>{cafeName}</h3>
            <p style={{ lineHeight: '1.0' }}>
              ${cafeData.businessHour.opening_time} - ${cafeData.businessHour.closing_time}
            </p>
            <p style={{ lineHeight: '1.8', color: 'blue' }}>{cafeData.businessStatus}</p>
          </div>
        ),
        latlng: new kakao.maps.LatLng(Lat, Lng),
      };
      positions.push(gb_position);
    }

    for (var i = 0; i < positions.length; i++) {
      var m_i_addr2 = m_infos[i]['매장주소'];
      var m_i_name2 = m_infos[i]['매장명'];

      var marker = new kakao.maps.Marker({
        map: map,
        position: positions[i].latlng,
        clickable: true,
      });
      var iwContent = `<div id="iw_con_info"><h5>${m_i_name2}</h5><h5>${m_i_addr2}</h5><h5>권역: ${m_i_gw2}</h5><h5>거점: ${m_i_gj2}</h5></div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      var infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable,
      });

      kakao.maps.event.addListener(marker, 'click', makeOverListener(map, marker, infowindow));
    }
  })
  .catch(error => {
    console.error('Error fetching cafe data:', error);
  });

// 인포윈도우를 표시하는 클로저를 만드는 함수
function makeOverListener(map, marker, infowindow) {
  return function () {
    infowindow.open(map, marker);
  };
}
// 인포윈도우를 닫는 클로저를 만드는 함수
function makeOutListener(infowindow) {
  return function () {
    infowindow.close();
  };
}

// return (
//   <div>
//     {cafeDataInfo.map(cafeData => (
//       <div key={cafeData.cafeId} className="InfoWindow">
//         <h3 style={{ lineHeight: '2.0' }}>{cafeData.cafeName}</h3>
//         <p style={{ lineHeight: '1.0' }}>
//           {`${cafeData.businessHour.opening_time} - ${cafeData.businessHour.closing_time}`}
//         </p>
//         <p style={{ lineHeight: '1.8', color: 'blue' }}>{cafeData.businessStatus}</p>
//       </div>
//     ))}
//   </div>
// );
