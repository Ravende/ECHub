import '../App.css';
import { Card } from '../card';
// import { CardInfo } from '../cardInfo';
import { Hashtags } from '../hashtags';
import { Search } from '../search';
import './sideBar.css';

export function SideBar() {
  return (
    <aside>
      <div id="side">
        <Search />
        <Hashtags />
        {[...Array(60)].map((_, index) => (
          <Card key={index} />
        ))}
      </div>
    </aside>
  );
}
