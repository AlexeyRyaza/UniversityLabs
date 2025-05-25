import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import GreetingPage from './pages/GreetingPage';
import LoginPage from './pages/LoginPage';
import RegisterPage from './pages/RegisterPage';
import RegisterUsernamePage from './pages/RegisterUsernamePage';
import HomePage from './pages/HomePage';
import PrivateRoute from './routes/PrivateRoute';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<GreetingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/register/username" element={<RegisterUsernamePage />} />
        <Route path="/home" element={
          <PrivateRoute>
          <HomePage />
          </PrivateRoute>
          } /> 
      </Routes>
    </Router>
  );
}

export default App;
