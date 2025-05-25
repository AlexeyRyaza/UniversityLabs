import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box } from '@mui/material';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { registerUser } from '../api/authService';

const BASE_URL = 'http://localhost:8080';

export default function RegisterUsernamePage() {
  const [username, setUsername] = useState('');
  const navigate = useNavigate();
  const location = useLocation();

  const { email, password } = location.state || {};

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await registerUser({ email, password, username });
      const token = response.token;
      const user = response.user;

      if (token) {
        localStorage.setItem('jwtToken', token);

        // ✅ Сохраняем пользователя после регистрации
        await axios.post(`${BASE_URL}/auth/save-user`, user);

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
