import React from 'react';
import { Button, TextField, Container, Typography, Box, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function RegisterPage() {
  const navigate = useNavigate();

  return (
    <Container maxWidth="xs">
      <Box mt={8} display="flex" flexDirection="column" alignItems="center">
        <Typography variant="h5">Регистрация</Typography>
        <Box component="form" noValidate sx={{ mt: 2 }}>
          <TextField margin="normal" fullWidth label="Email" name="email" />
          <TextField margin="normal" fullWidth label="Пароль" type="password" name="password" />
          <TextField margin="normal" fullWidth label="Повтор пароля" type="password" name="confirm" />
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
