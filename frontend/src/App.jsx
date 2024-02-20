import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import { CardInfoContainer } from './cardInfo';
import { Main } from './main';

export default function App() {
  // <Router>
  //   <Switch>
  //     <Route path="/" component={SideBar} />
  //     <Route path="/cardInfo" component={CardInfo} />
  //   </Switch>
  // </Router>;

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/cardInfo" element={<CardInfoContainer />} />
      </Routes>
    </Router>
  );
}
