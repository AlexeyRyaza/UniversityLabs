import React from 'react';
import { Container, Typography, Box } from '@mui/material';

export default function HomePage() {
  return (
    <Container maxWidth="md">
      <Box mt={8}>
        <Typography variant="h4" align="center">Добро пожаловать в FineApp!</Typography>
        <Typography variant="body1" align="center" sx={{ mt: 2 }}>
          Здесь будет ваша главная страница после входа в систему.
        </Typography>
      </Box>
    </Container>
  );
}
