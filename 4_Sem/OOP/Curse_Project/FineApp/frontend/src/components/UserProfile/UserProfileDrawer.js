import React, { useEffect, useState } from 'react';
import { FaUserCircle } from 'react-icons/fa';
import './styles/UserProfileDrawer.css';
import { logout } from '../../api/authService';
import { useNavigate } from 'react-router-dom';

const UserProfileDrawer = ({ onClose }) => {
  const [username, setUsername] = useState('example_user');
  const [editing, setEditing] = useState(false);
  const [newUsername, setNewUsername] = useState(username);
  const [closing, setClosing] = useState(false);
  const navigate = useNavigate();

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

  const handleUsernameSave = () => {
    setUsername(newUsername);
    setEditing(false);
  };

  return (
    <div className={`profile-drawer-overlay ${closing ? 'fade-out' : ''}`} onClick={handleOutsideClick}>
      <div className={`profile-drawer ${closing ? 'slide-out' : 'slide-in'}`}>
        <div className="profile-header">
          <h2>Профиль</h2>
        </div>

        {/* Иконка */}
        <div className="profile-icon-wrapper">
          <FaUserCircle className="profile-icon" />
        </div>

        <div className="profile-content">
          {/* Имя пользователя */}
          {editing ? (
            <div className="username-edit">
              <input
                type="text"
                value={newUsername}
                onChange={(e) => setNewUsername(e.target.value)}
              />
              <button onClick={handleUsernameSave}>Сохранить</button>
            </div>
          ) : (
            <button className="username-button" onClick={() => setEditing(true)}>
              {username}
            </button>
          )}
 
          {/* Кнопки */}
          <button 
            className='sign-out-button'
            onClick={() => {
              logout()
              navigate('/login');
              }}>
            Выйти
          
          </button>
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
  );
};

export default UserProfileDrawer;
