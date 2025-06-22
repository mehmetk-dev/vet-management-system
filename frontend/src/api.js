import axios from 'axios';

export const api = axios.create({
  baseURL: 'http://localhost:8080/v1'
});

export const extractList = (res) => {
  const data = res.data;
  if (data?.items) return data.items;
  if (data?.content) return data.content;
  if (data?.data?.items) return data.data.items;
  if (data?.data?.content) return data.data.content;
  return data?.data ?? data;
};

export default api;
