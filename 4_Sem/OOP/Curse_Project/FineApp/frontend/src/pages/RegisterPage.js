import React, { useState } from 'react';
import { Button, TextField, Container, Typography, Box, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { validatePasswordMatch } from '../utils/validationService';

export default function RegisterPage() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: '',
    password: '',
    confirm: ''
  });

  const [errors, setErrors] = useState({
    passwordMatch: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;

    const newFormData = { ...formData, [name]: value };
    setFormData(newFormData);

    // Проверка совпадения паролей
    if (name === 'password' || name === 'confirm') {
      const errorMsg = validatePasswordMatch(newFormData.password, newFormData.confirm);
      setErrors((prev) => ({ ...prev, passwordMatch: errorMsg }));
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    const passwordError = validatePasswordMatch(formData.password, formData.confirm);
    if (passwordError) {
      setErrors((prev) => ({ ...prev, passwordMatch: passwordError }));
      return;
    }

    // TODO: отправить запрос на бэкенд
    console.log("Регистрация:", formData);
  };

  return (
    <Container maxWidth="xs">
      <Box mt={8} display="flex" flexDirection="column" alignItems="center">
        <Typography variant="h5">Регистрация</Typography>
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
          <TextField
            margin="normal"
            fullWidth
            label="Повтор пароля"
            type="password"
            name="confirm"
            value={formData.confirm}
            onChange={handleChange}
            error={!!errors.passwordMatch}
            helperText={errors.passwordMatch}
          />
          <Button type="submit" fullWidth variant="contained" sx={{ mt: 2 }}>
            Зарегистрироваться
          </Button>
        </Box>
        <Box mt={2}>
          <Link href="#" onClick={() => navigate('/login')} underline="hover">
            Уже есть аккаунт? Войти
          </Link>
          <br />
          <Link href="#" onClick={() => navigate('/')} underline="hover">
            ← На главную
          </Link>
        </Box>
      </Box>
    </Container>
  );
}
