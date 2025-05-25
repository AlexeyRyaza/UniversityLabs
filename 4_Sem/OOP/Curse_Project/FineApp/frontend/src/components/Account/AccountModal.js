import React from 'react';
import './styles/AccountModal.css';

const AccountModal = ({ account, onClose }) => {
  const handleOverlayClick = (e) => {
    // Проверяем, кликнули ли именно по overlay, а не по дочерним элементам
    if (e.target === e.currentTarget) {
      onClose();
    }
  };

  return (
    <div className="modal-overlay" onClick={handleOverlayClick}>
      <div className="modal-content">
        <div className="modal-header">
          <div className="account-icon" style={{ backgroundColor: account.color }}>
            {/* Иконка */}
          </div>
          <div className="account-name">{account.name}</div>
        </div>
        <div className="modal-balance">Текущий баланс: {account.balance} ₽</div>
        <div className="modal-actions">
          <button>Кнопка 1</button>
          <button>Кнопка 2</button>
          <button>Кнопка 3</button>
        </div>
      </div>
    </div>
  );
};

export default AccountModal;
