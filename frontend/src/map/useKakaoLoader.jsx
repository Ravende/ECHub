// eslint-disable-next-line import/no-extraneous-dependencies
import { useKakaoLoader as useKakaoLoaderOrigin } from 'react-kakao-maps-sdk';

export default function useKakaoLoader() {
  useKakaoLoaderOrigin({
    appkey: '843de9881c04c74982ab3924b1e63164',
    libraries: ['services'],
  });
}
