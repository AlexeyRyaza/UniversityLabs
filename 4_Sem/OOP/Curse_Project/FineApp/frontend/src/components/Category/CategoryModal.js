import React, { useState } from 'react';
import './styles/CategoryModal.css';

const initialColors = [
  '#FF6B6B', '#4ECDC4', '#556270', '#C7F464', '#FF6B6B', '#C44D58',
];

const CategoryModal = ({ onClose, onSave }) => {
  const [title, setTitle] = useState('');
  const [type, setType] = useState('EXPENSE'); // по умолчанию Расходы
  const [color, setColor] = useState(initialColors[0]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!title.trim()) return alert('Введите название категории');

    const newCategory = {
      id: Date.now(),  // временный id
      title,
      type,
      color,
      totalAmount: 0,
      image: null,
    };

    onSave(newCategory);
  };

  return (
    <div className="modal-overlay" onClick={onClose}>
      <div className="modal-content" onClick={e => e.stopPropagation()}>
        <h2>Создать категорию</h2>
        <form onSubmit={handleSubmit}>
          <label>
            Название
            <input
              type="text"
              value={title}
              onChange={e => setTitle(e.target.value)}
              autoFocus
            />
          </label>

          <label>
            Тип
            <select value={type} onChange={e => setType(e.target.value)}>
              <option value="INCOME">Доход</option>
              <option value="EXPENSE">Расход</option>
            </select>
          </label>

          <label>
            Цвет
            <div className="color-picker">
              {initialColors.map(c => (
                <div
                  key={c}
                  className={`color-swatch ${color === c ? 'selected' : ''}`}
                  style={{ backgroundColor: c }}
                  onClick={() => setColor(c)}
                />
              ))}
            </div>
          </label>

          <div className="modal-buttons">
            <button type="submit">Создать</button>
            <button type="button" onClick={onClose}>Отмена</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CategoryModal;
