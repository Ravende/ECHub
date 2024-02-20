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
    <div className="hash-warp">
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div> 
      </div>
      <div id="operating-hours">평일 12:0~22:00 / 주말 11:00~22:00</div>
      
      <div className="review-box">
        {/* <img id= "cof_icon" alt = "cof" src = "./assets/cofimg.png" />
         */}
        <div id="review-name">평점</div>
        <div className="review-wrap">
          <div className="review">
            <h3>리뷰</h3>
            <h4>멀티탭이 많아요</h4>
          </div>
          {/* <div className="review">
          <h3>리뷰</h3>
          <h4>멀티탭이 많아요</h4>
        </div> */}
        </div>
      </div>
      <div id="menu">대표메뉴</div>
      <div className="menulist-wrap">
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
        <img id="menulist" alt="menu" src="./assets/americano.png" />
      
      </div>
      
    </div>
  );
}
