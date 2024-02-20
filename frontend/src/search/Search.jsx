import "./search.css";

export function Search() {
    return (
<form action method="GET">
          <label htmlFor="cafe-search" />
          <input type="search" id="cafe-search" name="ps" placeholder="Search" />
        </form>

    );
}