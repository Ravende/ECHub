// eslint-disable-next-line import/no-extraneous-dependencies
import { Map, MapMarker } from 'react-kakao-maps-sdk';
import { useRef, useState } from 'react';
import useKakaoLoader from './useKakaoLoader.jsx';

export default function KakaoMap() {
  useKakaoLoader();

  // eslint-disable-next-line
  const mapRef = useRef(null); /* prettier-ignore */

  const [isOpen, setIsOpen] = useState(false);

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

  return (
    <div className={'map_wrap'}>
      {/* 지도 생성 */}
      <Map // 지도를 표시할 Container
        id="map"
        center={{
          lat: 37.559716,
          lng: 126.945468,
        }}
        style={{
          width: '100%',
          height: '100vh',
          position: 'relative',
          overflow: 'hidden',
        }}
        level={3}
        ref={mapRef}>
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
        <MapMarker
          position={{
            // 마커 위치
            lat: 37.559716,
            lng: 126.945468,
          }}
          removable={true}
          clickable={true}
          onClick={() => setIsOpen(true)} // 클릭 이벤트
          onMouseOver={() => setIsOpen(true)} // 마우스오버 이벤트
          onMouseOut={() => setIsOpen(false)}>
          {/* 인포윈도우 생성 */}
          {isOpen && (
            <div className="InfoWindow">
              <h2 style={{ lineHeight: '2.0' }}>카페명</h2>
              <p style={{ lineHeight: '1.0' }}>
                월~금: 12:00~22:00
                <br />
                토,일: 11:00~22:00
              </p>
              <p style={{ lineHeight: '1.8', color: 'blue' }}>영업중</p>
            </div>
          )}
        </MapMarker>
      </Map>
    </div>
  );
}
