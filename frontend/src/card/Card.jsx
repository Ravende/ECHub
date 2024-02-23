import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../App.css';
import './card.css';

export function Card({ searchQuery, selectedTag }) {
  const movePage = useNavigate();
  const [cafeDataList, setCafeDataList] = useState([]);

  const getToday = () => {
    const days = ['SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'];
    const todayIndex = new Date().getDay();
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
  

  const filteredCafeData = cafeDataList.filter(cafe => 
    cafe.cafeName.toLowerCase().includes(searchQuery.toLowerCase()) &&
    (!selectedTag || (cafe.hashtagid && cafe.hashtagid.includes(selectedTag)))
  );
  

  const gopage = (cafeId) => {
    movePage(`/cardinfo/${cafeId}`);
  };

  return (
    <div>
  
      
      {filteredCafeData.map(cafeData => (
        <div key={cafeData.cafeId} className="cafe-box">
          <button className="cafe-name" onClick={() => gopage(cafeData.cafeId)}>
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
