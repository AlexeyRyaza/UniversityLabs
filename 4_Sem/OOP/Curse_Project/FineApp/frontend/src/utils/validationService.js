export function validatePasswordMatch(password, confirm) {
  if (!password || !confirm) return '';
  if (password !== confirm) return 'Пароли не совпадают';
  return '';
}
