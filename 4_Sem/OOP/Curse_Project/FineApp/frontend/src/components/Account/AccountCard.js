import React from 'react';
import './styles/AccountCard.css';

const AccountCard = ({ account, onClick }) => {
  return (
    <div className="account-card" onClick={onClick}>
      <div className="account-icon" style={{ backgroundColor: account.color }}>
        {/* Здесь будет иконка */}
      </div>
      <div className="account-info">
        <div className="account-name">{account.name}</div>
        <div className="account-balance">{account.balance} ₽</div>
      </div>
    </div>
  );
};

export default AccountCard;
