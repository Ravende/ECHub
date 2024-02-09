import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <aside>
      <div id="side">
        <form action="" method="GET">
          <label htmlFor="cafe-search"></label>
          <input type="search" id="cafe-search" name="ps" placeholder="Search" />
        </form>

        <br/>

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
        <br /><br />

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
  );
}

export default App;
