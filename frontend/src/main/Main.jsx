import { Header } from '../header';
import { SideBar } from '../sideBar';
import KakaoMap from '../map/KakaoMap.jsx';

export function Main() {
  return (
    <>
      <body>
        <Header />
        <main>
          <SideBar />
          <section>
            <KakaoMap />
          </section>
        </main>
      </body>
    </>
  );
}
