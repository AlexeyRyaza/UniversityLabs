// src/pages/RegisterUsernamePage.jsx
import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import { registerUser } from '../api/authService';

export default function RegisterUsernamePage() {
  const [username, setUsername] = useState('');
  const navigate = useNavigate();
  const location = useLocation();

  const { email, password } = location.state || {};

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await registerUser({ email, password, username });
      alert("2");

      // ✅ Предполагается, что backend возвращает токен
      const token = response.token;
      if (token) {
        localStorage.setItem('jwtToken', token);
        navigate('/home');
      } else {
        alert('Регистрация прошла, но токен не получен');
      }
    } catch (error) {
      alert(error.message || 'Ошибка регистрации');
    }
  };

  return (
    <Container maxWidth="xs">
      <Box mt={8} display="flex" flexDirection="column" alignItems="center">
        <Typography variant="h5">Придумайте имя пользователя</Typography>
        <Box component="form" noValidate onSubmit={handleSubmit} sx={{ mt: 2 }}>
          <TextField
            margin="normal"
            fullWidth
            label="Username"
            name="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
            Завершить регистрацию
          </Button>
        </Box>
      </Box>
    </Container>
  );
}
