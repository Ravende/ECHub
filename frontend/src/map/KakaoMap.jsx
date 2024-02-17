// eslint-disable-next-line import/no-extraneous-dependencies
import { Map, MapInfoWindow, MapMarker } from 'react-kakao-maps-sdk';
import { useRef } from 'react';
import useKakaoLoader from './useKakaoLoader.jsx';

export default function KakaoMap() {
  useKakaoLoader();

  // eslint-disable-next-line
  const mapRef = useRef(null); /* prettier-ignore */

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
    <div className={'section'}>
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
            height: '600px',
            position: 'relative',
            overflow: 'hidden,',
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

          {/* 인포윈도우 생성 */}
          <MapInfoWindow
            position={{
              // 인포윈도우 위치
              zIndex: 1,
            }}
            removable={true} // 인포윈도우 x버튼 표시
          >
            {/* 인포윈도우에 표출될 내용으로 HTML 문자열이나 React Component가 가능합니다 */}
            <div style={{ padding: '5px', color: '#000' }}>plcae.place_name</div>
          </MapInfoWindow>

          {/* 마커 생성 */}
          <MapMarker
            position={{
              // 마커 위치
              lat: 37.559716,
              lng: 126.945468,
            }}>
            {/* MapMarker의 자식을 넣어줌으로 해당 자식이 InfoWindow로 만들어지게 합니다 */}
          </MapMarker>
        </Map>
      </div>
    </div>
  );
}
