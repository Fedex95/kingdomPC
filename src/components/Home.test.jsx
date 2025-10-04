import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Home from './Home';

global.fetch = jest.fn();

const mockUserData = {
  id: 1
};

const mockProductos = [
  { id: 1, nombre: 'Producto 1', descripcion: 'Desc 1', precio: 100, categoria: 'Mouse', imagenURL: 'http://example.com/1.jpg' },
  { id: 2, nombre: 'Producto 2', descripcion: 'Desc 2', precio: 200, categoria: 'Teclado', imagenURL: 'http://example.com/2.jpg' }
];

describe('Home Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve(mockProductos)
    });
  });

  test('renders carousel component', () => {
    render(<Home userData={mockUserData} />);
    const carousel = document.querySelector('.p-carousel');
    expect(carousel).toBeInTheDocument();
  });

  test('displays featured products', async () => {
    render(<Home userData={mockUserData} />);
    await waitFor(() => {
      expect(screen.getByText('Producto 1')).toBeInTheDocument();
      expect(screen.getByText('Producto 2')).toBeInTheDocument();
    });
  });

  test('opens dialog on product click', async () => {
    render(<Home userData={mockUserData} />);
    await waitFor(() => {
      fireEvent.click(screen.getByText('Producto 1'));
      expect(screen.getByText('Especificaciones')).toBeInTheDocument();
    });
  });

  test('renders add to cart button in dialog', async () => {
    render(<Home userData={mockUserData} />);
    await waitFor(() => {
      fireEvent.click(screen.getByText('Producto 1'));
      expect(screen.getByText('Agregar al carrito')).toBeInTheDocument();
    });
  });

  test('renders toast component', () => {
    render(<Home userData={mockUserData} />);
    const toast = document.querySelector('.p-toast');
    expect(toast).toBeInTheDocument();
  });
});