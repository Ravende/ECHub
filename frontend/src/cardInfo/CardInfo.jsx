import '../App.css';
import './cardInfo.css';

export function CardInfo() {
  return (
    <div className="cafeinfo-box">
      <img id= "back_icon" alt = "back" src = "./assets/back_icon.png" />
      <div id="cafe-name">카페명</div>
      <div className="cafe-image-info">
        <img id="cafeinfoimg" alt="cafeimg2" src="./assets/cafeinfoimage.jpg" />
      </div>
      <div id="operating-hours">영업시간 : 09:00-20:00</div>
      <div id="operating-state">영업상태 : 영업중</div>
      <div className="hash-warp">
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        
      </div>
      <div id="menu">대표메뉴 </div>

      <div className="menulist-wrap">
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
      
      </div>
      
    </div>
  );
}
