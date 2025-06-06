import axios from 'axios'; // если у тебя есть файл с преднастроенным axios, иначе просто 'axios'

const BASE_URL = '/categories';

export const getAllCategories = async () => {
  const response = await axios.get(BASE_URL);
  return response.data;
};

export const getCategoryById = async (id) => {
  const response = await axios.get(`${BASE_URL}/${id}`);
  return response.data;
};

export const createCategory = async (category) => {
  const response = await axios.post(BASE_URL, category);
  return response.data;
};

export const updateCategory = async (category) => {
  const response = await axios.put(BASE_URL, category);
  return response.data;
};

export const deleteCategory = async (id) => {
  const response = await axios.delete(`${BASE_URL}/${id}`);
  return response.data;
};
