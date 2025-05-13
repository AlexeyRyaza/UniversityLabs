import React from 'react';

function App() {
  const handleClick = async () => {
    const response = await fetch('http://localhost:8080/api/test'); // заменишь на свой бэкенд URL
    const data = await response.text();
    alert(data);
  };

  return (
    <div style={{ padding: '2rem' }}>
      <button onClick={handleClick}>Нажми меня</button>
    </div>
  );
}

export default App;
