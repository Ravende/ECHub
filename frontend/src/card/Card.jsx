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

  useEffect(() => {
    // Replace 'your-backend-url' with the actual URL of your backend service
    axios.get('https://echubserver.shop:8080/api/cafe') // Updated endpoint
      .then(response => {
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
          <button className="cafe-name" onClick={gopage}>{cafeData.cafeName}</button>
          <p className="operating-hours">
            {`영업시간: ${cafeData.businessHour.opening_time} - ${cafeData.businessHour.closing_time}`}
          </p>
          <p className="operating-state" style={{ color: 'blue' }}>
            {cafeData.businessStatus}
          </p>
          <p className="menu">대표메뉴: {cafeData.bestmenu}</p>

          <div className="hash-warp">
            {cafeData.hashtag.map(tag => (
              <div key={tag} className="hash">{tag}</div>
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
