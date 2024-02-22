// sideBar.js
import React, { useState } from 'react';
import '../App.css';
import { Card } from '../card';
import { Hashtags } from '../hashtags';
import { Search } from '../search';
import './sideBar.css';

export function SideBar() {
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearchChange = (query) => {
    setSearchQuery(query);
  };

  return (
    <aside>
      <div id="side">
        <Search onSearchChange={handleSearchChange} />
        <Hashtags />
        {[...Array(60)].map((_, index) => (
          <Card key={index} searchQuery={searchQuery} />  ))}
      </div>
    </aside>
  );
}
