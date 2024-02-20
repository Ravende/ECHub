import '../App.css';
import './card.css';
import axios from 'axios';

const API_URL = 'https://echubserver.shop:8080';
const data = axios
  .get('${API_URL}/api/cafe')
  .then(function (response) {
    // 성공 핸들링
    console.log(response);
  })
  .catch(function (error) {
    // 에러 핸들링
    console.log(error);
  })
  .finally(function () {
    // 항상 실행되는 영역
  });

console.log(data);

export function Card() {
  return (
    <div className="cafe-box">
      <div className="cafe-name">카페명</div>
      <div className="operating-hours">영업시간 : 09:00-20:00</div>
      <div className="operating-state">영업상태 : 영업중</div>
      <div className="menu">대표메뉴 : 생과일주스 </div>

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
