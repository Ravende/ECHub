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

  const [cafeData, setCafeData] = useState({});

  const dayMapping = {
    MONDAY: '월요일',
    TUESDAY: '화요일',
    WEDNESDAY: '수요일',
    THURSDAY: '목요일',
    FRIDAY: '금요일',
    SATURDAY: '토요일',
    SUNDAY: '일요일',
  };
  const [reviews, setReviews] = useState([]);
  const addReview = review => {
    setReviews(currentReviews => [...currentReviews, review]);

  };

  useEffect(() => {
    const fetchCafeDetails = async () => {
      try {
        const response = await axios.get(`https://echubserver.shop:8080/api/cafe/${cafeId}`); // 가정한 API 경로
        setCafeData(response.data);
      } catch (error) {
        console.error('Fetching cafe details failed', error);

      }
    };

    fetchCafeDetails();
  }, [cafeId]);
  return (

    <div className="cafeinfo-box">
      <button className="backbtn" onClick={goPage}>

        <img id="back_icon" alt="back" src="../public/assets/back_icon.png" />
      </button>

      <div className="cafe-image-info">
        <img id="cafeinfoimg" alt="cafeimg2" src={cafeData.imageUrl} />
      </div>
      <div id="cafe-name">{cafeData.cafeName}</div>

      <div className="hash-warp">
        {cafeData.hashtag &&
          cafeData.hashtag.map(tag => (
            <div key={tag} className="hash_info">
              {tag}
            </div>
          ))}
      </div>
      <div id="operating-hours">
        {cafeData.businessHour &&
          Object.entries(cafeData.businessHour).map(([day, hours]) => (
            <span key={day}>
              {`${dayMapping[day]}: ${hours}`}
              <br />
            </span>
          ))}
      </div>

      <Menu />


      <div>
        {activeComponent === 'write' && <Write onAddReview={addReview} setActiveComponent={setActiveComponent} />}
        {activeComponent === 'review' && <Review reviews={reviews} />}

        <button className="write_button" onClick={() => setActiveComponent('write')}>
          글쓰기
        </button>

 <div>
      {activeComponent === 'write' && <Write onAddReview={addReview} setActiveComponent={setActiveComponent} />}
      {activeComponent === 'review' && <Review reviews={reviews} />}
      
      <button className="write_button" onClick={() => setActiveComponent('write')}>글쓰기</button>
    </div>

    </div>
  );
}
