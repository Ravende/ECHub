import './App.css';

export default function CardInfo(props) {
  return (
    <div className="cafeinfo-box">
      
      <div className="cafe-name">카페명</div>
      <div className="operating-hours">영업시간 : 09:00-20:00</div>
      <div className="operating-state">영업상태 : 영업중</div>
      <div className="menu">대표메뉴 : 생과일주스 </div>
      
      <div className="cafe-image"  >
        <img className="cafeimg" alt="cafeimg1" src ="cafeimage.jpg"  />
      </div>
      
      </div>
      


  );
}
