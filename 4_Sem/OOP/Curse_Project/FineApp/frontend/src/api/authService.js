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

export const autoLogin = async () => {
  try {
    const autoLoginResponse = await axios.post(`${BASE_URL}/auth/auto-login`);
    const autoData = autoLoginResponse?.data;

    if (autoData && autoData.token && autoData.user) {
      localStorage.setItem('jwtToken', autoData.token);
      localStorage.setItem('currentUser', JSON.stringify(autoData.user));
      return { token: autoData.token, user: autoData.user };
    }
    return null;
  } catch {
    return null;
  }
};

export const loginUser = async (credentials) => {
  try {
    // 1. Пытаемся авто-логиниться
    const autoLoginResponse = await axios.post(`${BASE_URL}/auth/auto-login`);
    const autoData = autoLoginResponse?.data;

    if (autoData && autoData.token && autoData.user) {
      localStorage.setItem('jwtToken', autoData.token);
      localStorage.setItem('currentUser', JSON.stringify(autoData.user));
      return { token: autoData.token, user: autoData.user };
    }

    // 2. Если авто-логин не сработал — обычный логин
    const response = await axios.post(`${BASE_URL}/auth/login`, credentials);
    const { token, user } = response.data;

    localStorage.setItem('jwtToken', token);
    localStorage.setItem('currentUser', JSON.stringify(user));
    // 3. Сохраняем пользователя
    await axios.post(`${BASE_URL}/auth/save-user`, user);

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


export const isAuthenticated = () => !!localStorage.getItem('jwtToken');


export const logout = async () => {
  try { 
    // Вызываем серверный logout (DELETE сессии / удаление авторизации)
    await axios.post(`${BASE_URL}/auth/logout`);
  } catch (error) {
    console.error('Ошибка при logout:', error);
    // Можно проигнорировать ошибку, чтобы logout все равно очистил localStorage
  } finally {
    // Чистим localStorage в любом случае
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('currentUser');
  }
};
