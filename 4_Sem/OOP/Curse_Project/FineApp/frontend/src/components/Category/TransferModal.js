import React, { useState, useEffect, useRef } from 'react';
import './styles/TransferModal.css';

const TransferModal = ({
  accounts,
  categories,
  defaultAccount = null,
  defaultCategory,
  onClose,
  onSave,
}) => {
  const [selectedAccount, setSelectedAccount] = useState(defaultAccount);
  const [selectedCategory, setSelectedCategory] = useState(defaultCategory);
  const [amount, setAmount] = useState('');
  const [comment, setComment] = useState('');
  const modalRef = useRef(null);

  useEffect(() => {
    // Закрытие при клике вне модалки
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose();
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [onClose]);

  useEffect(() => {
    setSelectedAccount(defaultAccount);
    setSelectedCategory(defaultCategory);
  }, [defaultAccount, defaultCategory]);

  const handleSave = () => {
    // Заглушка — здесь потом будет логика обработки
    console.log('Добавляем перевод:', {
      account: selectedAccount,
      category: selectedCategory,
      amount,
      comment,
    });
    if (onSave) onSave({ account: selectedAccount, category: selectedCategory, amount, comment });
    onClose();
  };

  return (
    <div className="transfer-modal-backdrop">
      <div className="transfer-modal" ref={modalRef}>
        <h3>Добавить перевод</h3>

        <label>Аккаунт:</label>
        <select
          value={selectedAccount ? selectedAccount.id : ''}
          onChange={(e) => {
            const acc = accounts.find(a => a.id === e.target.value);
            setSelectedAccount(acc);
          }}
        >
          <option value="" disabled>Выберите аккаунт</option>
          {accounts.map(acc => (
            <option key={acc.id} value={acc.id}>{acc.name}</option>
          ))}
        </select>

        <label>Категория:</label>
        <select
          value={selectedCategory ? selectedCategory.id : ''}
          onChange={(e) => {
            const cat = categories.find(c => c.id === e.target.value);
            setSelectedCategory(cat);
          }}
        >
          <option value="" disabled>Выберите категорию</option>
          {categories.map(cat => (
            <option key={cat.id} value={cat.id}>{cat.title}</option>
          ))}
        </select>

        <label>Сумма:</label>
        <input
          type="number"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
          placeholder="Введите сумму"
        />

        <label>Комментарий:</label>
        <input
          type="text"
          value={comment}
          onChange={(e) => setComment(e.target.value)}
          placeholder="Добавить комментарий"
        />

        <button className="transfer-add-btn" onClick={handleSave} disabled={!amount || !selectedAccount || !selectedCategory}>
          Добавить
        </button>
      </div>
    </div>
  );
};

export default TransferModal;
