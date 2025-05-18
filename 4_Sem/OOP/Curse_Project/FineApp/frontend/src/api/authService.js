import axios from 'axios';

const BASE_URL = 'http://localhost:8080'; // без /api, ты используешь /auth и /users

export const checkEmailExists = async (email) => {
  try {
    const response = await axios.get(`${BASE_URL}/auth/check_email/${email}`);
    return response.data; // true / false
  } catch (error) {
    throw new Error("Ошибка при проверке email");
  }
};

export const loginUser = async (credentials) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/login`, credentials);
    const { token, user } = response.data;

    // Сохраняем JWT и данные пользователя
    localStorage.setItem('jwtToken', token);
    localStorage.setItem('currentUser', JSON.stringify(user));

    return { token, user };
  } catch (error) {
    console.error('Ошибка при входе:', error);
    throw error.response?.data || new Error('Ошибка входа');
  }
};

export const registerUser = async (user) => {
  try {
    const response = await axios.post(`${BASE_URL}/auth/register`, user);
    
    // Ожидаем структуру: { token: '...', user: {...} }
    const { token, user: createdUser } = response.data;

    // Сохраняем токен и пользователя в localStorage (или куда тебе нужно)
    localStorage.setItem('token', token);
    localStorage.setItem('currentUser', JSON.stringify(createdUser));

    return { token, user: createdUser };
  } catch (error) {
    const errorMessage = error.response?.data?.error || "Ошибка при создании пользователя";
    alert(errorMessage);
    throw new Error(errorMessage);
  }
};

