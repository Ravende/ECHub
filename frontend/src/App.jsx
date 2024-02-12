import './App.css';
import Card from './Card';
import React, {useState} from 'react';
import CardInfo from './CardInfo';
export default function App() {
  return (
    <>
      <header>
        <div id="logo">
          <h2>1 project</h2>
        </div>
        <div id="siteName">
          <h1>Ewha w. univ cafe Map</h1>
        </div>
      </header>
      <aside>
        <div id="side">
          <form action method="GET">
            <label htmlFor="cafe-search" />
            <input type="search" id="cafe-search" name="ps" placeholder="Search" />
          </form>
          <br />
          <select id="hashtags" name="hashtags">
            <option value=""># 해시태그</option>
            <option value=""># 매장이_넓어요</option>
            <option value="학생"># 인스타_핫플</option>
            <option value="학생"># 대화하기_좋아요</option>
            <option value="학생"># 포토존</option>
            <option value="학생"># 친절해요</option>
            <option value="학생"># 매장이_청결해요</option>
            <option value="학생"># 가성비가_좋아요</option>
            <option value="학생"># 카공하기_좋아요</option>
            <option value="학생"># 분위기_있어요</option>
            <option value="학생"># 특별한_메뉴가_있어요</option>
            <option value="학생"># 음료가_맛있어요</option>
            <option value="학생"># 디저트가_맛있어요</option>
            <option value="학생"># 화장실이_깨끗해요</option>


          </select>
          <br/>
          <br/>
          <Card/>
          <Card/>
          <CardInfo/>
        </div>
      </aside>
    </>
  );
}
