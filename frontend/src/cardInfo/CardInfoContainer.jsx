import { Header } from '../header';
import KakaoMap from '../map/KakaoMap.jsx';
import { CardInfo } from './CardInfo.jsx';

export function CardInfoContainer() {
  return (
    <body>
      <Header />
      <main>
        <CardInfo />
        <section>
          <KakaoMap />
        </section>
      </main>
    </body>
  );
}
