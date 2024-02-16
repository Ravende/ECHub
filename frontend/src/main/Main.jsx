import { Header } from '../header';
import { SideBar } from '../sideBar';
import KakaoMap from '../map/KakaoMap.jsx';

export function Main() {
  return (
    <>
      <Header />
      <SideBar />
      <KakaoMap />
    </>
  );
}
