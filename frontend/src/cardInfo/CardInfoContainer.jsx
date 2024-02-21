import { Header } from '../header';
import KakaoMap from '../map/KakaoMap.jsx';
// import { SideBar } from '../sideBar/SideBar.jsx';
import { CardInfo } from './CardInfo.jsx';
import './cardInfoContainer.css';


export function CardInfoContainer() {
  return (
    <body>
      <Header />
      <main>
        <aside>
          <div id="side">

          <CardInfo />
          </div>

        </aside>
        
        <section>
          <KakaoMap />
        </section>
      </main>
    </body>
  );
}
