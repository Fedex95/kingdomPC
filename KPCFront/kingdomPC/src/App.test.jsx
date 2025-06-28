import { render, screen } from '@testing-library/react';
import App from './App';

test('renders login when not authenticated', () => {
  localStorage.clear();
  render(<App />);
  expect(screen.getByPlaceholderText(/usuario/i)).toBeInTheDocument();
});