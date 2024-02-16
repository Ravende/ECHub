// eslint-disable-next-line import/no-extraneous-dependencies
import { Map } from 'react-kakao-maps-sdk';
import { useRef } from 'react';
import useKakaoLoader from './useKakaoLoader.jsx';

const { kakao } = window;

export function KakaoMap() {
  useKakaoLoader();

  // eslint-disable-next-line
  const mapRef = useRef < kakao.maps.Map > null;

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
        <Map // 지도를 표시할 Container
          id="map"
          center={{
            // 지도의 중심좌표
            lat: 37.559716,
            lng: 126.945468,
          }}
          style={{
            // 지도의 크기
            width: '100%',
            height: '600px',
            position: 'relative',
            overflow: 'hidden,',
          }}
          level={3} // 지도의 확대 레벨
          ref={mapRef}></Map>

        {/* 지도 확대, 축소 컨트롤 div */}
        <div className="custom_zoomcontrol radius_border">
          <span onClick={zoomIn}>
            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_plus.png" alt="확대" />
          </span>
          <span onClick={zoomOut}>
            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/ico_minus.png" alt="축소" />
          </span>
        </div>
      </div>
    </div>
  );
}
