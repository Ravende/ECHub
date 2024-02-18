import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import { Header } from '../header';
import { SideBar } from '../sideBar';
import {CardInfo} from '../cardInfo';

import "./main.css";

export function Main() {
  return (
    <>
      <Header />
      <Router>
<Switch>
    <Route path="/" component = {SideBar} />
    <Route path="/cardInfo" component = {CardInfo} />
</Switch>
      
      </Router>
      
    </>
  );
}
