import './search.css';
import './fontello-306b2794/css/fontello-embedded.css';

export function Search() {
  return (
    <form action method="GET">
      <label htmlFor="cafe-search" />
      <div class="search-box">
        <input type="search" id="cafe-search" name="ps" placeholder="카페명" />
        <div class="icon-search"></div>
      </div>
    </form>
  );
}
