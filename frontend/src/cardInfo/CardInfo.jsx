import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
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
  const { cafeId } = useParams();
  const [cafeData, setCafeData] = useState([]);
  const getToday = () => {
    const days = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];
    const todayIndex = new Date().getDay(); // 0부터 일요일, 1부터 월요일, ..., 6부터 토요일
    return days[todayIndex];
  };
  const today = getToday();

  useEffect(() => {
    // Replace 'your-backend-url' with the actual URL of your backend service
    axios
      .get('https://echubserver.shop:8080/api/cafe/${cafeId}') // Updated endpoint
      .then(response => {
        console.log('Cafe Data:', response.data);
        setCafeData(response.data); // Assuming the response is a list of cafes
      })
      .catch(error => {
        console.error('Error fetching cafe data:', error);
      });
  }, []);

  return (
    
      <div className="cafeinfo-box">

      <button className = "backbtn"  onClick={goPage}>

        <img id="back_icon" alt="back" src="./assets/back_icon.png" />
      </button>

      <div className="cafe-image-info">
        <img id="cafeinfoimg" alt="cafeimg2" src={cafeData.imageUrl} />
      </div>
      <div id="cafe-name">{cafeData.cafeName}</div>
      {/* <div className="hash-warp">
            {cafeData.hashtag.map(tag => (
              <div key={tag} className="hash_info">
                {tag}
              </div>
            ))}</div>
      <div id="operating-hours">{`영업시간: ${cafeData.businessHour[today] || '-'} `}</div> */}

      {activeComponent === 'review' && <Review />}
      {activeComponent === 'write' && <Write />}

      <div>

        <button className="review_button" onClick={goReview}>

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

