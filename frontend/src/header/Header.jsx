import { useNavigate } from 'react-router-dom';
import './header.css';
import coffeeBeans from './coffee.png';
import coffeeCup from './CoffeeCup.png';
import logo from './LogoWhite.png';

export function Header() {
  const movePage = useNavigate();
  const goPage = () => {
    movePage('/');
  };

  return (
    <header>
      <div id="logo" onClick={goPage}>
        <img src={logo} alt="logo" />
      </div>
      <div id="siteName" onClick={goPage}>
        <h1>Ewha Cafe Hub</h1>
        <div id="coffeeImage1">
          <img src={coffeeCup} alt="coffeeCup" />
        </div>
        <div id="coffeeImage2">
          <img src={coffeeBeans} alt="coffeeBeans" />
        </div>
      </div>
      {/* <div id="container">
        <button className="loginBtn">로그인</button>
        
        <button className="joinBtn">회원가입</button>
      </div> */}
    </header>
  );
}
