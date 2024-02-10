export default function cafeBox(props) {
  return (
    <div className="cafe-box">
      <div className="cafe-name">{props.name}</div>
      <div className="operating-hours">영업시간 : 09:00-08:00</div>
      <div className="operating-state">영업상태</div>
      <div className="menu">대표메뉴</div>
    </div>
  );
}
