import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import './App.css';
import { CardInfoContainer } from './cardInfo';
import { Main } from './main';

export default function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Main />} />
        <Route path="/cardInfo/:cafeId" element={<CardInfoContainer />} />
      </Routes>
    </Router>
  );
}
