import { render, screen, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Navbar from './Navbar';

const mockUserMenu = { current: { toggle: jest.fn() } };
const mockUserMenuItems = [
  { label: 'Perfil', command: jest.fn() },
  { label: 'Cerrar Sesión', command: jest.fn() },
];
const mockUserData = { nombre: 'Juan', apellido: 'Pérez' };
const mockHandleNavigation = jest.fn();

beforeAll(() => {
  // Mock createStylesheet to avoid CSS parsing errors
  const helpers = require('jsdom/lib/jsdom/living/helpers/stylesheets');
  helpers.createStylesheet = jest.fn(() => ({}));
});

describe('Navbar Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders navbar with brand link', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={0}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    expect(screen.getByText('Electro Master')).toBeInTheDocument();
    expect(screen.getByRole('link', { name: 'Electro Master' })).toHaveAttribute('href', '/home');
  });

  test('displays user name and surname', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={0}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    expect(screen.getByText('Juan')).toBeInTheDocument();
    expect(screen.getByText('Pérez')).toBeInTheDocument();
  });

  test('shows cart badge when cartItemsCount > 0', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={3}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    expect(screen.getByText('3')).toBeInTheDocument();
  });

  test('does not show cart badge when cartItemsCount is 0', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={0}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    expect(screen.queryByText('0')).not.toBeInTheDocument();
  });

  test('renders cart and orders buttons', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={0}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    const buttons = screen.getAllByRole('button');
    expect(buttons).toHaveLength(3);
    expect(buttons[0].querySelector('.pi-shopping-cart')).toBeInTheDocument();
    expect(buttons[1].querySelector('.pi-clipboard')).toBeInTheDocument();
    expect(buttons[2].querySelector('.pi-user')).toBeInTheDocument();
  });

  test('renders user menu button', () => {
    render(
      <MemoryRouter>
        <Navbar
          cartItemsCount={0}
          handleNavigation={mockHandleNavigation}
          userMenuItems={mockUserMenuItems}
          userMenu={mockUserMenu}
          userData={mockUserData}
        />
      </MemoryRouter>
    );
    const buttons = screen.getAllByRole('button');
    expect(buttons[2].querySelector('.pi-user')).toBeInTheDocument();
  });
});