import React from 'react';
import './hashtags.css';

export function Hashtags({ onSelectHashtag }) {
  const handleHashtagChange = e => {
    const selectedValue = e.target.value;
    onSelectHashtag(selectedValue); 
  };

  return (
    <select id="hashtags" name="hashtags" onChange={handleHashtagChange}>
      <option value="">해시태그 선택...</option>
      <option value="1"># 매장이_넓어요</option>
      <option value="2"># 인스타_핫플</option>
      <option value="3"># 대화하기_좋아요</option>
      <option value="4"># 포토존</option>
      <option value="5"># 친절해요</option>
      <option value="6"># 매장이_청결해요</option>
      <option value="7"># 가성비가_좋아요</option>
      <option value="8"># 카공하기_좋아요</option>
      <option value="9"># 분위기_있어요</option>
      <option value="10"># 특별한_메뉴가_있어요</option>
      <option value="11"># 음료가_맛있어요</option>
      <option value="12"># 디저트가_맛있어요</option>
      <option value="13"># 화장실이_깨끗해요</option>
    </select>
  );
}
