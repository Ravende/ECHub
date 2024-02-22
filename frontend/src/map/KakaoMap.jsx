import { useEffect, useRef, useState } from 'react';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import './map.css';
import useKakaoLoader from './useKakaoLoader.jsx';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function KakaoMap() {
  useKakaoLoader();

  const mapRef = useRef(null);

  const [isOpen, setIsOpen] = useState(false);
  const [info, setInfo] = useState();
  const [markers, setMarkers] = useState([]);
  const [map, setMap] = useState();
  const [{ lat, lng }, setLatLng] = useState({ lat: 37.559716, lng: 126.945468 });
  console.log(setLatLng);

  /* 지도 확대, 축소 버튼 */
  const zoomIn = () => {
    const map = mapRef.current;
    if (!map) return;
    map.setLevel(map.getLevel() - 1);
  };

  const zoomOut = () => {
    const map = mapRef.current;
    if (!map) return;
    map.setLevel(map.getLevel() + 1);
  };

  /* 마커 라우팅 */
  const movePage = useNavigate();
  function gopage() {
    movePage('/cardinfo');
  }
  const [cafeDataInfo, setCafeDataInfo] = useState([]);

  /* 장소 검색 */
  useEffect(() => {
    axios
      .get('https://echubserver.shop:8080/api/cafe/search?q={keyword}') // Updated endpoint
      .then(response => {
        console.log('Cafe Data:', response.data);
        setCafeDataInfo(response.data); // Assuming the response is a list of cafes
      })
      .catch(error => {
        console.error('Error fetching cafe data:', error);
      });

    if (!map) return;
    const ps = new window.kakao.maps.services.Places();

    // 키워드 검색 완료 시 호출되는 콜백함수
    const placesSearchCB = (data, status) => {
      if (status === window.kakao.maps.services.Status.OK) {
        // 검색된 장소 위치를 기준으로 지도 범위 재설정 (LatLngBounds 객체에 좌표 추가)
        const bounds = new window.kakao.maps.LatLngBounds();
        const markers = [];

        for (let i = 0; i < data.length; i++) {
          markers.push({
            position: {
              lat: data[i].y,
              lng: data[i].x,
            },
            content: data[i].place_name,
          });
          bounds.extend(new window.kakao.maps.LatLng(data[i].y, data[i].x));
        }
        setMarkers(markers);

        // 검색된 장소 위치를 기준으로 지도 범위 재설정
        map.setBounds(bounds);
      }
    };

    // 키워드 검색
    ps.keywordSearch('카페', placesSearchCB, {
      radius: 20000,
      location: new window.kakao.maps.LatLng(lat, lng),
      size: 15,
      page: 1,
      rect: '126.942065,37.562414,126.948871,37.557108',
    });
    // 카테고리 검색 - 카페: CE7
    ps.categorySearch('CE7', placesSearchCB, {
      radius: 20000,
      location: new window.kakao.maps.LatLng(lat, lng),
      size: 15,
      page: 1,
      rect: '126.942065,37.562414,126.948871,37.557108',
    });
  }, [map, lat, lng]);

  return (
    <div className={'map_wrap'}>
      {/* 지도 생성 */}
      <Map // 지도를 표시할 Container
        id="map"
        center={{
          lat,
          lng,
        }}
        style={{
          width: '100%',
          height: '100vh',
          position: 'relative',
          overflow: 'hidden',
        }}
        level={4}
        ref={mapRef}
        onCreate={setMap}>
        {/* 지도 확대, 축소 컨트롤 div */}
        <div className="custom_zoomcontrol radius_border">
          <span onClick={zoomIn}>
            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대" />
          </span>
          <span onClick={zoomOut}>
            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소" />
          </span>
        </div>

        {/* 마커 생성 */}
        {markers.map(marker => (
          <MapMarker
            key={`marker-${marker.content}-${marker.position.lat},${marker.position.lng}`}
            position={marker.position}
            removable={true}
            clickable={true}
            onClick={() => gopage()} // 클릭 이벤트
            onMouseOver={() => {
              setIsOpen(true);
              setInfo(marker);
            }} // 마우스오버 이벤트
            onMouseOut={() => setIsOpen(false)}>
            {/* 인포윈도우 생성 */}
            {isOpen && info && info.content === marker.content && (
              <div className="InfoWindow">
                <h3 style={{ lineHeight: '2.0' }}>{marker.content}</h3>
                <p style={{ lineHeight: '1.0' }}>
                  월~금: 12:00~22:00
                  <br />
                  토,일: 11:00~22:00
                </p>
                <p style={{ lineHeight: '1.8', color: 'blue' }}>영업중</p>
              </div>
            )}
          </MapMarker>
        ))}
      </Map>
    </div>
  );
}
