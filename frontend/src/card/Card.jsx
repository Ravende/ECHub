import '../App.css';
import './card.css';

export function Card() {
  return (
    <div className="cafe-box">
      <div className="cafe-name">카페명</div>
      <p className="operating-hours">영업시간: 09:00-20:00</p>
      <p className="operating-state" style={{ color: 'blue' }}>
        영업중
      </p>
      <p className="menu">대표메뉴 : 생과일주스 </p>

      <div className="hash-warp">
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
        <div className="hash">#포토존</div>
      </div>
      <div className="cafe-image">
        <img id="cafeimg" alt="cafeimg1" src="./assets/cafeimage.jpg" />
      </div>
    </div>
  );
}
