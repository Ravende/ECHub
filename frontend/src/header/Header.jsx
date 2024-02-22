import './header.css';
import coffeeImage from './coffee.png';
import logo from './LogoWhite.png';

export function Header() {
  return (
    <header>
      <div id="logo">
        <img src={logo} alt="logo" />
      </div>
      <div id="siteName">
        <h1>Ewha Cafe Hub</h1>
        <div id="coffeeImage1">
          <img src={coffeeImage} alt="coffeeImage" />
        </div>
        <div id="coffeeImage2">
          <img src={coffeeImage} alt="coffeeImage" />
        </div>
      </div>
      <div id="container">
        <button className="loginBtn">로그인</button>
        
        <button className="joinBtn">회원가입</button>
      </div>

     </header>
  );
}
