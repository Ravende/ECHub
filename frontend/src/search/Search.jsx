// search.js
import React, { useState } from 'react';
import './search.css';
import './fontello-306b2794/css/fontello-embedded.css';

export function Search({ onSearchChange }) {
  const [searchQuery, setSearchQuery] = useState('');

  const handleSearchChange = (e) => {
    const query = e.target.value;
    setSearchQuery(query);
    onSearchChange(query); // Notify the parent component about the search query change
  };

  return (
    <form action method="GET">
      <label htmlFor="cafe-search" />
      <div className="search-box">
        <input
          type="search"
          id="cafe-search"
          name="ps"
          placeholder="카페명"
          value={searchQuery}
          onChange={handleSearchChange}
        />
        
        <div className="icon-search"></div>
      </div>
    </form>
  );
}
