import React from 'react';
import './hashtags.css';

export function Hashtags({ onSelectHashtag }) {
  const handleHashtagChange = e => {
    const selectedValue = e.target.value;
    onSelectHashtag(selectedValue); // 선택된 하나의 해시태그를 상위 컴포넌트로 전달
  };

  return (
    <select id="hashtags" name="hashtags" onChange={handleHashtagChange}>
      <option value="">해시태그 선택...</option>
      <option value="매장이_넓어요"># 매장이_넓어요</option>
      <option value="인스타_핫플"># 인스타_핫플</option>
      <option value="대화하기_좋아요"># 대화하기_좋아요</option>
      <option value="포토존"># 포토존</option>
      <option value="친절해요"># 친절해요</option>
      <option value="매장이_청결해요"># 매장이_청결해요</option>
      <option value="가성비가_좋아요"># 가성비가_좋아요</option>
      <option value="카공하기_좋아요"># 카공하기_좋아요</option>
      <option value="분위기_있어요"># 분위기_있어요</option>
      <option value="특별한_메뉴가_있어요"># 특별한_메뉴가_있어요</option>
      <option value="음료가_맛있어요"># 음료가_맛있어요</option>
      <option value="디저트가_맛있어요"># 디저트가_맛있어요</option>
      <option value="화장실이_깨끗해요"># 화장실이_깨끗해요</option>
    </select>
  );
}
