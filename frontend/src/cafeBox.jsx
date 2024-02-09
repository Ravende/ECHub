import React from 'react';

function cafeBox(props) {
  return (
    <div class="cafe-box">
      <div class="cafe-name">{props.name}</div>
      <div class="operating-hours">영업시간 : 09:00-08:00</div>
      <div class="operating-state">영업상태</div>
      <div class="menu">대표메뉴</div>
    </div>
  );
}

export default cafeBox;
