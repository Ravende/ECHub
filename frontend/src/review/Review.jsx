import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './review.css';
import { Write } from '../write';

export function Review({ reviews }) {
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
    <div className="review-box">
      <div id="review-name">평점</div>
      <div className="review">
        {reviews.map((review, index) => (
          <div key={index}>{review}</div>
        ))}
      </div>
    </div>
  );
}
