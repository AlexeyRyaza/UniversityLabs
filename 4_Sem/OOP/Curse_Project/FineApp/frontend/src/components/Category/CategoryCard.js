import React from 'react';
import './styles/CategoryCard.css';

const CategoryCard = ({ category, onClick }) => {
  return (
    <div className="category-card" onClick={onClick}>
      <div
        className="category-color-indicator"
        style={{ backgroundColor: category.color }}
      />
      <div className="category-content">
        <div className="category-title">{category.title}</div>
        <div className="category-amount">{category.totalAmount} ₽</div>
      </div>
    </div>
  );
};

export default CategoryCard;
