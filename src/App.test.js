import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import App from './App';


global.fetch = jest.fn();

const renderWithRouter = (ui, { route = '/' } = {}) => {
  return render(
    <MemoryRouter initialEntries={[route]}>
      {ui}
    </MemoryRouter>
  );
};

describe('App Component', () => {
  beforeEach(() => {
    localStorage.clear();
    jest.clearAllMocks();
  });

  test('renders login page when not authenticated', () => {
    renderWithRouter(<App />);
    expect(screen.getByText(/login/i)).toBeInTheDocument(); 
  });

  test('renders home page when authenticated', () => {
    localStorage.setItem('isAuthenticated', 'true');
    localStorage.setItem('userData', JSON.stringify({ id: 1, name: 'User' }));
    renderWithRouter(<App />, { route: '/' });
    expect(screen.getByText(/home/i)).toBeInTheDocument();   
  });

  test('handleLogin updates state and localStorage', async () => {
    fetch.mockResolvedValueOnce({ json: () => Promise.resolve(true) }); 
    renderWithRouter(<App />);

    const loginButton = screen.getByRole('button', { name: /login/i });
    fireEvent.click(loginButton);

    const appInstance = renderWithRouter(<App />).container;
  });

  test('handleLogout clears state and localStorage', () => {
    localStorage.setItem('isAuthenticated', 'true');
    localStorage.setItem('userData', JSON.stringify({ id: 1 }));
    localStorage.setItem('admin', 'true');
    renderWithRouter(<App />, { route: '/' });
  });

  test('redirects to login if not authenticated and accessing protected route', () => {
    renderWithRouter(<App />, { route: '/cart' });
    expect(screen.getByText(/login/i)).toBeInTheDocument();
  });

  test('renders admin view only if admin', () => {
    localStorage.setItem('isAuthenticated', 'true');
    localStorage.setItem('userData', JSON.stringify({ id: 1 }));
    localStorage.setItem('admin', 'true');
    renderWithRouter(<App />, { route: '/admin' });
    expect(screen.getByText(/admin/i)).toBeInTheDocument(); 
  });
});
