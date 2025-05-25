import React, { useEffect, useState } from 'react';
import { AiOutlineUser } from 'react-icons/ai';
import './styles/UserProfileDrawer.css';

const UserProfileDrawer = ({ onClose }) => {
  const username = 'example_user';
  const [closing, setClosing] = useState(false);

  const handleOutsideClick = (e) => {
    if (e.target.classList.contains('profile-drawer-overlay')) {
      setClosing(true);
    }
  };

  useEffect(() => {
    document.body.style.overflow = 'hidden';
    return () => {
      document.body.style.overflow = 'auto';
    };
  }, []);

  useEffect(() => {
    if (closing) {
      const timer = setTimeout(() => {
        onClose();
      }, 300);
      return () => clearTimeout(timer);
    }
  }, [closing, onClose]);

  return (
    <div
      className={`profile-drawer-overlay ${closing ? 'fade-out' : ''}`}
      onClick={handleOutsideClick}
    >
      <div className={`profile-drawer ${closing ? 'slide-out' : 'slide-in'}`}>
        <div className="profile-header">
          <h2>Профиль</h2>
        </div>

        <div className="profile-icon-wrapper">
          <AiOutlineUser className="profile-icon" />
        </div>

        <div className="profile-content">
          <p>Имя пользователя: <strong>{username}</strong></p>

          <div className="profile-buttons">
            <button onClick={() => console.log('Выход')}>Выйти</button>
            <button
              className="delete-button"
              onClick={() => {
                const confirmed = window.confirm('Удалить пользователя?');
                if (confirmed) console.log('Пользователь удалён');
              }}
            >
              Удалить пользователя
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default UserProfileDrawer;
