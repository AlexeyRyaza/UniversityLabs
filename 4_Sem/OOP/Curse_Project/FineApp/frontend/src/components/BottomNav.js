import React from 'react';
import './BottomNav.css';

const BottomNav = ({ activeTab, setActiveTab, onProfileClick }) => {
  return (
    <div className="bottom-nav">
      <button
        className={activeTab === 'accounts' ? 'active' : ''}
        onClick={() => setActiveTab('accounts')}
      >
        Аккаунты
      </button>
      <button
        className={activeTab === 'categories' ? 'active' : ''}
        onClick={() => setActiveTab('categories')}
      >
        Категории
      </button>
      <button
        className={activeTab === 'operations' ? 'active' : ''}
        onClick={() => setActiveTab('operations')}
      >
        Операции
      </button>
      <button onClick={onProfileClick}>Профиль</button>
    </div>
  );
};

export default BottomNav;
