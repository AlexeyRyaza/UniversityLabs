import React from 'react';
import CategoryCard from './CategoryCard';
import { Droppable, Draggable } from '@hello-pangea/dnd';
import './styles/CategoryTab.css';

const CategoryTab = ({ title, sum, categories, droppableId, onCategoryClick }) => {
  return (
    <div className="category-column">
      <div className="category-header">
        <span className="category-sum">{title}: {sum} ₽</span>
      </div>

      <Droppable droppableId={droppableId}>
        {(provided) => (
          <div
            className="category-list"
            ref={provided.innerRef}
            {...provided.droppableProps}
          >
            {categories.map((category, index) => (
              <Draggable key={category.id} draggableId={String(category.id)} index={index}>
                {(provided) => (
                  <div
                    className="draggable-category"
                    ref={provided.innerRef}
                    {...provided.draggableProps}
                    {...provided.dragHandleProps}
                    onClick={() => onCategoryClick && onCategoryClick(category)}
                  >
                    <CategoryCard category={category} />
                  </div>
                )}
              </Draggable>
            ))}

            {provided.placeholder}
          </div>
        )}
      </Droppable>
    </div>
  );
};

export default CategoryTab;
