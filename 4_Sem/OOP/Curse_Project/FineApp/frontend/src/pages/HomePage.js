import React, { useState } from 'react';
import BottomNav from '../components/BottomNav';
import AccountsTab from '../components/Account/AccountsTab';
import CategoriesTab from '../components/Category/CategoriesTab';
import OperationsTab from '../components/Operation/OperationsTab';
import UserProfileDrawer from '../components/UserProfile/UserProfileDrawer';
import './HomePage.css';

const HomePage = () => {
  const [activeTab, setActiveTab] = useState('operations');
  const [showProfileDrawer, setShowProfileDrawer] = useState(false);

  const renderContent = () => {
    switch (activeTab) {
      case 'accounts':
        return <AccountsTab />;
      case 'categories':
        return <CategoriesTab />;
      case 'operations':
      default:
        return <OperationsTab />;
    }
  };

  return (
    <div className="home-container">
      <div className="total-balance">Баланс: 10 000 ₽</div>
      <div className="tab-content">{renderContent()}</div>
      <BottomNav
        activeTab={activeTab}
        setActiveTab={setActiveTab}
        onProfileClick={() => setShowProfileDrawer(true)}
      />
      {showProfileDrawer && (
        <UserProfileDrawer onClose={() => setShowProfileDrawer(false)} />
      )}
    </div>
  );
};

export default HomePage;
