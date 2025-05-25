import React, { useState } from 'react';
import { DragDropContext, Droppable, Draggable } from '@hello-pangea/dnd';
import AccountCard from './AccountCard';
import AccountModal from './AccountModal';
import './styles/AccountsTab.css';

const initialAccounts = [
  { id: '1', name: 'Аккаунт 1', balance: 5000, color: '#f28b82' },
  { id: '2', name: 'Аккаунт 2', balance: 3000, color: '#aecbfa' },
  { id: '3', name: 'Аккаунт 3', balance: 5000, color: '#f28b82' },
  { id: '4', name: 'Аккаунт 4', balance: 5000, color: '#f28b82' },
  { id: '5', name: 'Аккаунт 5', balance: 5000, color: '#f28b82' },
  { id: '6', name: 'Аккаунт 6', balance: 5000, color: '#f28b82' },
  { id: '7', name: 'Аккаунт 7', balance: 5000, color: '#f28b82' },
  { id: '8', name: 'Аккаунт 8', balance: 5000, color: '#f28b82' },
];

const AccountsTab = () => {
  const [accounts, setAccounts] = useState(initialAccounts);
  const [selectedAccount, setSelectedAccount] = useState(null);

  const onDragEnd = (result) => {
    if (!result.destination) return;

    const reordered = Array.from(accounts);
    const [moved] = reordered.splice(result.source.index, 1);
    reordered.splice(result.destination.index, 0, moved);
    setAccounts(reordered);
  };

  return (
    <div className="accounts-tab">
      <div className="accounts-header">
        <h2>Аккаунты</h2>
        <div className="total-balance">
          Текущий баланс: {accounts.reduce((sum, a) => sum + a.balance, 0)} ₽
        </div>
      </div>

      <DragDropContext onDragEnd={onDragEnd}>
        <Droppable droppableId="accounts">
          {(provided) => (
            <div
              className="accounts-list"
              {...provided.droppableProps}
              ref={provided.innerRef}
            >
              {accounts.map((account, index) => (
                <Draggable key={account.id} draggableId={account.id.toString()} index={index}>
                  {(provided) => (
                    <div
                      className="draggable-account"
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                    >
                      <AccountCard
                        account={account}
                        onClick={() => setSelectedAccount(account)}
                      />
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </div>
          )}
        </Droppable>
      </DragDropContext>

      {selectedAccount && (
        <AccountModal
          account={selectedAccount}
          onClose={() => setSelectedAccount(null)}
        />
      )}
    </div>
  );
};

export default AccountsTab;
