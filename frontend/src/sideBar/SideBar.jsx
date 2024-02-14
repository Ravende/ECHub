import { Card } from '../card';
import { CardInfo } from '../cardInfo';
import { Search } from '../search';
import { Hashtags } from '../hashtags';
import '../App.css';
import './sideBar.css';


export function SideBar() {
  return (
    <aside>
      <div id="side">
       {/* <Search />
        <br />
        <Hashtags />
        <br />
        <br />
        <Card />
  <Card /> */}
        <CardInfo />
      </div>
    </aside>
  );
}
