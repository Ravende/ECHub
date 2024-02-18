// import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Header } from '../header';
import { SideBar } from '../sideBar';
import KakaoMap from '../map/KakaoMap.jsx';
// import {CardInfo} from '../cardInfo';
import "./main.css";


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

      {/* <Router>
<Switch>
    <Route path="/" component = {SideBar} />
    <Route path="/cardInfo" component = {CardInfo} />
</Switch>
      
      </Router> */}

    </>
  );
}
