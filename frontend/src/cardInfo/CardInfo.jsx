import '../App.css';
import './cardInfo.css';
import { useNavigate } from 'react-router-dom';
import { Review } from '../review';
import { Write } from '../write';
import { Menu } from '../menu';

export function CardInfo() {
  const movePage = useNavigate();

  function gopage(){
    movePage('/');
  }

  return (
    <div className="cafeinfo-box">
      
      <button onClick = {gopage}><img id="back_icon" alt="back" src="./assets/back_icon.png" /></button>
      <div id="cafe-name">카페명2</div>
      
      <div className="cafe-image-info">
        <img id="cafeinfoimg" alt="cafeimg2" src="./assets/cafeinfoimage.jpg" />
      </div>
      <div className="hash-warp">
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
      </div>
      <div id="operating-hours">평일 12:0~22:00 / 주말 11:00~22:00</div>
      
      <Review />
      <Write />
      <Menu />
      
    </div>
  );
}
