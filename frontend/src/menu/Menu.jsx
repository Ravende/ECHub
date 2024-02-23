import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import './menu.css';

export function Menu() {
  const { cafeId } = useParams();
  const [cafeData, setCafeData] = useState({});

  useEffect(() => {
    axios
      .get(`https://echubserver.shop:8080/api/cafe/${cafeId}`) // Updated endpoint
      .then(response => {
        console.log('Cafe Data:', response.data);
        setCafeData(response.data); // Assuming the response is a list of cafes
      })
      .catch(error => {
        console.error('Error fetching cafe data:', error);
      });
  }, [cafeId]);

  return (
    <div id="menu">
      <div className="bestMenu">대표메뉴: {cafeData.bestMenu}</div>
    </div>
  );
}
