import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Review } from '../review';
import { Write } from '../write';
import { Menu } from '../menu';
import '../App.css';
import './cardInfo.css';

export function CardInfo() {
  const movePage = useNavigate();
  const [activeComponent, setActiveComponent] = useState('review');

  const goPage = () => {
    movePage('/');
  };

  const goReview = () => {
    setActiveComponent('review');
  };

  const goWrite = () => {
    setActiveComponent('write');
  };

  return (
    <div className="cafeinfo-box">
      <button onClick={goPage}>
        <img id="back_icon" alt="back" src="./assets/back_icon.png" />
      </button>

      <div className="cafe-image-info">
        <img id="cafeinfoimg" alt="cafeimg2" src="./assets/cafeinfoimage.jpg" />
      </div>
      <div id="cafe-name">카페명</div>
      <div className="hash-warp">
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
        <div className="hash_info">#포토존</div>
      </div>
      <div id="operating-hours">평일 12:0~22:00 / 주말 11:00~22:00</div>

      {activeComponent === 'review' && <Review />}
      {activeComponent === 'write' && <Write />}

      <div>
        <button className="write_button" onClick={goReview}>
          리뷰 보기
        </button>
        <button className="write_button" onClick={goWrite}>
          글쓰기
        </button>
      </div>

      <Menu />
    </div>
  );
}
