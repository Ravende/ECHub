import '../App.css';
import { Card } from '../card';
import { CardInfo } from '../cardInfo';
import { Hashtags } from '../hashtags';
import { Search } from '../search';
import './sideBar.css';

export function SideBar() {
  return (
    <aside>
      <div id="side">
        <Search />
        <br />
        <Hashtags />
        <br />
        <br />
        <CardInfo />
        <Card />
        <Card />
      </div>
    </aside>
  );
}
