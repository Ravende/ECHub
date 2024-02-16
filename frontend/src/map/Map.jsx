import React from 'react';
import MapComponent from './MapComponent';

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
            <label htmlFor="cafe-search"></label>
            <input type="search" id="cafe-search" name="ps" placeholder="Search" />
          </form>
          <br />
          <select id="hashtags" name="hashtags"></select>
          <br />
          <br />
          <div className="cafe-box">
            <div className="cafe-name">카페명</div>
            <div className="operating-hours">영업시간 : 09:00-08:00</div>
            <div className="operating-state">영업상태</div>
            <div className="menu">대표메뉴</div>
          </div>
          <div className="cafe-box">
            <div className="cafe-name">카페명</div>
            <div className="operating-hours">영업시간 : 09:00-08:00</div>
            <div className="operating-state">영업상태</div>
            <div className="menu">대표메뉴</div>
          </div>
        </div>
      </aside>
      <section>
        <div>
          <MapComponent />
        </div>
      </section>
    </>
  );
}
