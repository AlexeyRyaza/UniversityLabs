// src/pages/LoginPage.jsx
import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../api/authService';

export default function LoginPage() {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
  e.preventDefault();
  setError('');

  try {
    await loginUser(formData); // сохраняет токен и user внутри
    navigate('/home');
  } catch (err) {
    setError('Неверный email или пароль');
  }
};


  return (
    <Container maxWidth="xs">
      <Box mt={8} display="flex" flexDirection="column" alignItems="center">
        <Typography variant="h5">Вход</Typography>
        <Box component="form" noValidate sx={{ mt: 2 }} onSubmit={handleSubmit}>
          <TextField
            margin="normal"
            fullWidth
            label="Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
          <TextField
            margin="normal"
            fullWidth
            label="Пароль"
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
          {error && <Typography color="error">{error}</Typography>}
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
            Войти
          </Button>
        </Box>
        <Box mt={2}>
          <Link href="#" onClick={() => navigate('/register')} underline="hover">
            Нет аккаунта? Зарегистрироваться
          </Link>
        </Box>
      </Box>
    </Container>
  );
}
