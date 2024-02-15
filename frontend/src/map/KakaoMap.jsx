// eslint-disable-next-line import/no-extraneous-dependencies
import { Map } from 'react-kakao-maps-sdk';
import useKakaoLoader from './useKakaoLoader.jsx';
import MapComponent from './map';

export function KakaoMap() {
  useKakaoLoader();

  return (
    <div>
      <Map // 지도를 표시할 Container
        id="map"
        center={{
          // 지도의 중심좌표
          lat: 33.450701,
          lng: 126.570667,
        }}
        style={{
          // 지도의 크기
          width: '100%',
          height: '600px',
        }}
        level={3} // 지도의 확대 레벨
      />
    </div>
  );
}
