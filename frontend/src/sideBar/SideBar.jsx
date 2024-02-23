// SideBar.jsx 수정본
import React, { useState } from 'react';
import '../App.css';
import { Card } from '../card'; 
import { Hashtags } from '../hashtags'; 
import { Search } from '../search'; 
import './sideBar.css';

export function SideBar() {
  const [searchQuery, setSearchQuery] = useState('');
  const [selectedTag, setSelectedTag] = useState(''); 

  const handleSearchChange = query => {
    setSearchQuery(query);
  };

  const handleSelectHashtag = tag => { // 단일 해시태그 처리로 변경
    setSelectedTag(tag); // 첫 번째 선택된 태그만 저장
  };

  return (
    <aside>
      <div id="side">
        <Search onSearchChange={handleSearchChange} />
        <Hashtags onSelectHashtag={handleSelectHashtag} />
        <Card searchQuery={searchQuery} selectedTag={selectedTag} /> 
      </div>
    </aside>
  );
}