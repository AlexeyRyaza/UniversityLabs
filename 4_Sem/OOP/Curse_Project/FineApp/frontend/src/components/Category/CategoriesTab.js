import React, { useEffect, useState } from 'react';
import { getAllCategories } from '../../api/categoryService';
import CategoryTab from './CategoryTab';
import CategoryModal from './CategoryModal';
import TransferModal from './TransferModal'; // добавь импорт своего TransferModal
import { DragDropContext } from '@hello-pangea/dnd';
import './styles/CategoriesTab.css';

const CategoriesTab = () => {
  const [incomeCategories, setIncomeCategories] = useState([]);
  const [expenseCategories, setExpenseCategories] = useState([]);
  const [showModal, setShowModal] = useState(false);

  // Добавленные стейты
  const [selectedCategory, setSelectedCategory] = useState(null);
  const [showTransferModal, setShowTransferModal] = useState(false);

  useEffect(() => {
    const savedIncome = localStorage.getItem('fineapp_income_categories');
    const savedExpense = localStorage.getItem('fineapp_expense_categories');

    if (savedIncome && savedExpense) {
      setIncomeCategories(JSON.parse(savedIncome));
      setExpenseCategories(JSON.parse(savedExpense));
    } else {
      getAllCategories()
        .then((data) => {
          const income = data.filter((c) => c.type === 'INCOME');
          const expense = data.filter((c) => c.type === 'EXPENSE');
          setIncomeCategories(income);
          setExpenseCategories(expense);
        })
        .catch((err) => console.error('Ошибка загрузки категорий:', err));
    }
  }, []);

  useEffect(() => {
    localStorage.setItem('fineapp_income_categories', JSON.stringify(incomeCategories));
  }, [incomeCategories]);

  useEffect(() => {
    localStorage.setItem('fineapp_expense_categories', JSON.stringify(expenseCategories));
  }, [expenseCategories]);

  const totalIncome = incomeCategories.reduce((acc, c) => acc + Number(c.totalAmount), 0);
  const totalExpense = expenseCategories.reduce((acc, c) => acc + Number(c.totalAmount), 0);

  const addCategory = (newCategory) => {
    if (newCategory.type === 'INCOME') {
      setIncomeCategories((prev) => [...prev, newCategory]);
    } else {
      setExpenseCategories((prev) => [...prev, newCategory]);
    }
  };

  const onDragEnd = (result) => {
    const { source, destination } = result;
    if (!destination || source.droppableId !== destination.droppableId) return;

    const list = source.droppableId === 'INCOME' ? [...incomeCategories] : [...expenseCategories];
    const [moved] = list.splice(source.index, 1);
    list.splice(destination.index, 0, moved);

    if (source.droppableId === 'INCOME') setIncomeCategories(list);
    else setExpenseCategories(list);
  };

  // Обработчик клика по категории
  const onCategoryClick = (category) => {
    setSelectedCategory(category);
    setShowTransferModal(true);
  };

  return (
    <div>
      <button className="add-category-btn" onClick={() => setShowModal(true)}>
        + Добавить категорию
      </button>

      <DragDropContext onDragEnd={onDragEnd}>
        <div style={{ display: 'flex', gap: '20px' }}>
          <CategoryTab
            title="Расходы"
            sum={totalExpense}
            categories={expenseCategories}
            droppableId="EXPENSE"
            onCategoryClick={onCategoryClick} // передали пропс
          />
          <CategoryTab
            title="Доходы"
            sum={totalIncome}
            categories={incomeCategories}
            droppableId="INCOME"
            onCategoryClick={onCategoryClick} // передали пропс
          />
        </div>
      </DragDropContext>

      {showModal && (
        <CategoryModal
          onClose={() => setShowModal(false)}
          onSave={(category) => {
            addCategory(category);
            setShowModal(false);
          }}
        />
      )}

      {showTransferModal && selectedCategory && (
        <TransferModal
          accounts={[]} // сюда нужно подставить реальные аккаунты
          categories={[...incomeCategories, ...expenseCategories]}
          defaultCategory={selectedCategory}
          onClose={() => setShowTransferModal(false)}
          onSave={(data) => {
            console.log('Добавлен перевод:', data);
            setShowTransferModal(false);
          }}
        />
      )}
    </div>
  );
};

export default CategoriesTab;
