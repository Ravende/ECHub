import { Header } from '../header';
import KakaoMap from '../map/KakaoMap.jsx';
import { SideBar } from '../sideBar';
// import {CardInfo} from '../cardInfo';
// import "./main.css";

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
