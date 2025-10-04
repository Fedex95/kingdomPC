import { render, screen, waitFor, act } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Layout from './Layout';

global.fetch = jest.fn();

const mockOnLogout = jest.fn();
const mockUserData = { id: 1, role: 'ADMIN' };
const mockChildren = <div>Test Children</div>;

describe('Layout Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    (global.fetch as jest.Mock).mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(true)
    });
  });

  test('renders layout container', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Layout onLogout={mockOnLogout} cartItemsCount={0} userData={mockUserData}>
            {mockChildren}
          </Layout>
        </MemoryRouter>
      );
    });
    const container = document.querySelector('.layout-container');
    expect(container).toBeInTheDocument();
  });

  test('renders navbar', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Layout onLogout={mockOnLogout} cartItemsCount={0} userData={mockUserData}>
            {mockChildren}
          </Layout>
        </MemoryRouter>
      );
    });
  });

  test('renders children', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Layout onLogout={mockOnLogout} cartItemsCount={0} userData={mockUserData}>
            {mockChildren}
          </Layout>
        </MemoryRouter>
      );
    });
    expect(screen.getByText('Test Children')).toBeInTheDocument();
  });

  test('renders main content', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Layout onLogout={mockOnLogout} cartItemsCount={0} userData={mockUserData}>
            {mockChildren}
          </Layout>
        </MemoryRouter>
      );
    });
    const main = document.querySelector('.main-content');
    expect(main).toBeInTheDocument();
  });

  test('calls fetch to verify admin', async () => {
    await act(async () => {
      render(
        <MemoryRouter>
          <Layout onLogout={mockOnLogout} cartItemsCount={0} userData={mockUserData}>
            {mockChildren}
          </Layout>
        </MemoryRouter>
      );
    });
    await waitFor(() => {
      expect((global.fetch as jest.Mock)).toHaveBeenCalledWith('http://localhost:8080/usuarios/1/admin');
    });
  });
});