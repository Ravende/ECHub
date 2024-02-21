import './header.css';
import coffeeImage from './coffee.png';
import logo from './LogoWhite.png';

export function Header() {
  // const [isPopupOpen, setPopupOpen] = useState(false);

  // const openPopup = () => {
  //   setPopupOpen(true);
  // };

  // const closePopup = () => {
  //   setPopupOpen(false);
  // };

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

      {/* {isPopupOpen && (
        <div className="loginPopup">
          <div className="modal-content">
            <span className="close" onClick={closePopup}>
              &times;
            </span>
            <h2>로그인</h2>
            <div id="loginForm">
              <form>
                <ul>
                  <li>
                    <label for="name">이 름</label>
                    <input type="text" id="name" />
                  </li>
                  <li>
                    <label for="phone">비밀번호</label>
                    <input type="password" id="phone" />
                  </li>
                </ul>
              </form>
            </div>
          </div>
        </div>
      )} */}
    </header>
  );
}
