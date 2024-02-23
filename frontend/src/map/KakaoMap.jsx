import { useEffect, useRef, useState } from 'react';
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import './map.css';
import useKakaoLoader from './useKakaoLoader.jsx';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function KakaoMap() {
  useKakaoLoader();

  const mapRef = useRef(null);

  const [info, setInfo] = useState(null);
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

  /* 요일 함수 */
  const getToday = () => {
    const days = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];
    const todayIndex = new Date().getDay(); // 0부터 일요일, 1부터 월요일, ..., 6부터 토요일
    return days[todayIndex];
  };
  const today = getToday();

  const [cafeDataInfo, setCafeDataInfo] = useState([]);

  useEffect(() => {
    axios
      .get('https://echubserver.shop:8080/api/cafe') // Updated endpoint
      .then(response => {
        console.log('Cafe Data:', response.data);
        const cafeDataInfo = response.data;

        const Markers = cafeDataInfo.map(cafeData => ({
          name: cafeData.cafeName,
          latlng: { lat: cafeData.latitude, lng: cafeData.longitude },
          hour: cafeData.businessHour[today],
          state: cafeData.businessStatus,
          content: cafeData.cafeName,
        }));
        setMarkers(Markers);
      })
      .catch(error => {
        console.error('Error fetching cafe data:', error);
      });
  }, [map]);

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
        level={2}
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
            key={`${marker.name}-${marker.latlng}`}
            position={marker.latlng}
            removable={true}
            clickable={true}
            onClick={() => gopage()} // 클릭 이벤트
            onMouseOver={() => {
              setInfo(marker);
            }} // 마우스오버 이벤트
            onMouseOut={() => setInfo(null)}>
            {/* 인포윈도우 생성 */}
            {info && info.content === marker.content && (
              <div className="InfoWindow">
                <h3 style={{ lineHeight: '2.0' }}>{marker.name}</h3>
                <p style={{ lineHeight: '1.0' }}>{`오늘 영업: ${marker.hour || '(정보 없음)'}`}</p>
                <p style={{ lineHeight: '1.8', color: marker.state === '영업 중' ? 'blue' : 'red' }}>
                  {marker.state === '영업 중' ? '영업중' : '영업종료'}
                </p>
              </div>
            )}
          </MapMarker>
        ))}
      </Map>
    </div>
  );
}
