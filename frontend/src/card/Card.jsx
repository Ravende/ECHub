import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../App.css';
import './card.css';

export function Card() {
  const movePage = useNavigate();
  function gopage() {
    movePage('/cardinfo');
  }
  const [cafeDataList, setCafeDataList] = useState([]);
  const getToday = () => {
    const days = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];
    const todayIndex = new Date().getDay(); // 0부터 일요일, 1부터 월요일, ..., 6부터 토요일
    return days[todayIndex];
  };
  const today = getToday();

  useEffect(() => {
    // Replace 'your-backend-url' with the actual URL of your backend service
    axios
      .get('https://echubserver.shop:8080/api/cafe') // Updated endpoint
      .then(response => {
        console.log('Cafe Data:', response.data);
        setCafeDataList(response.data); // Assuming the response is a list of cafes
      })
      .catch(error => {
        console.error('Error fetching cafe data:', error);
      });
  }, []);

  return (
    <div>
      {cafeDataList.map(cafeData => (
        <div key={cafeData.cafeId} className="cafe-box">
          <button className="cafe-name" onClick={gopage}>
            {cafeData.cafeName}
          </button>
          <p className="operating-hours">{`영업시간: ${cafeData.businessHour[today] || '-'} `}</p>
          <p className="operating-state" style={{ color: cafeData.businessStatus === '영업 중' ? 'blue' : 'red' }}>
            {cafeData.businessStatus === '영업 중' ? '| 영업중' : '| 영업종료'}
          </p>
          <p className="menu" style={{ fontSize: '17px' }}>
            {cafeData.address}
          </p>

          <div className="hash-warp">
            {cafeData.hashtag.map(tag => (
              <div key={tag} className="hash">
                {tag}
              </div>
            ))}
          </div>
          <div className="cafe-image">
            <img id="cafeimg" alt={`cafeimg-${cafeData.cafeId}`} src={cafeData.imageUrl} />
          </div>
        </div>
      ))}
    </div>
  );
}
