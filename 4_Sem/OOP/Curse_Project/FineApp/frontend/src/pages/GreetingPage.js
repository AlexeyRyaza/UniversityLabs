import React from 'react';
import { Button, Container, Typography, Box } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function GreetingPage() {
  const navigate = useNavigate();

  return (
    <Container maxWidth="sm">
      <Box mt={8} textAlign="center">
        <Typography variant="h4" gutterBottom>
          Добро пожаловать в FineApp!
        </Typography>
        <Typography variant="body1" mb={4}>
          Управляйте своими финансами легко и удобно
        </Typography>
        <Button variant="contained" color="primary" onClick={() => navigate('/login')} sx={{ m: 1 }}>
          Войти
        </Button>
        <Button variant="outlined" color="primary" onClick={() => navigate('/register')} sx={{ m: 1 }}>
          Зарегистрироваться
        </Button>
      </Box>
    </Container>
  );
}
