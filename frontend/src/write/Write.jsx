import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './write.css';
// import { Review } from '../review';

export function Write({ onAddReview, setActiveComponent }) {
  const navigate = useNavigate();
  //const [activeComponent, setActiveComponent] = useState('review');
  const [text, setText] = useState('');
  //const [reviewText, setReviewText] = useState('');
  const handleSubmit = () => {
    onAddReview(text);
    setActiveComponent('review'); // 리뷰 추가 후 리뷰 컴포넌트로 전환
  };

  const handleSave = () => {
    // Save the entered text (reviewText) as needed (e.g., to a database or state)
    // For now, let's log it to the console
    console.log('Saved review text:', reviewText);

    // Transition to the Review component
    navigate('/review?text=' + encodeURIComponent(reviewText));

    const goReview = () => {
      setActiveComponent('review');
    };
  };

  return (
    <div className="write-box">
      <div id="review-name">평점</div>
      <div className="write">
        <textarea id="write-text" value={text} onChange={e => setText(e.target.value)}></textarea>
        <button onClick={handleSubmit}>저장하기</button>
      </div>
    </div>
  );
}
