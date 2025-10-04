import { render, screen, fireEvent, waitFor } from '@testing-library/react';
import Menu from './Producto';

const mockFetch = jest.fn();
global.fetch = mockFetch;

beforeAll(() => {
  // Mock createStylesheet to avoid CSS parsing errors
  const helpers = require('jsdom/lib/jsdom/living/helpers/stylesheets');
  helpers.createStylesheet = jest.fn(() => ({}));
});

describe('Menu Component', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('fetches and displays products', async () => {
    const mockProducts = [
      {
        id: 1,
        nombre: 'Producto 1',
        descripcion: 'Descripción 1',
        precio: 10.00,
        categoria: 'Mouse',
        imagenURL: 'url1',
      },
    ];
    mockFetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockProducts),
    });

    render(<Menu userData={{ id: 1 }} />);

    await waitFor(() => {
      expect(screen.getByText('Producto 1')).toBeInTheDocument();
      expect(screen.getByText('$10.00')).toBeInTheDocument();
    });
  });

  test('opens dialog on product click', async () => {
    const mockProducts = [
      {
        id: 1,
        nombre: 'Producto 1',
        descripcion: 'Descripción 1',
        precio: 10.00,
        categoria: 'Mouse',
        imagenURL: 'url1',
      },
    ];
    mockFetch.mockResolvedValueOnce({
      ok: true,
      json: () => Promise.resolve(mockProducts),
    });

    render(<Menu userData={{ id: 1 }} />);

    await waitFor(() => {
      fireEvent.click(screen.getByText('Producto 1'));
      expect(screen.getByText('Detalles del plato')).toBeInTheDocument();
    });
  });

  test('adds to cart successfully', async () => {
    const mockProducts = [
      {
        id: 1,
        nombre: 'Producto 1',
        descripcion: 'Descripción 1',
        precio: 10.00,
        categoria: 'Mouse',
        imagenURL: 'url1',
      },
    ];
    mockFetch
      .mockResolvedValueOnce({
        ok: true,
        json: () => Promise.resolve(mockProducts),
      })
      .mockResolvedValueOnce({
        ok: true,
      });

    render(<Menu userData={{ id: 1 }} />);

    await waitFor(() => {
      fireEvent.click(screen.getByText('Producto 1'));
    });

    fireEvent.click(screen.getByText('Agregar al carrito'));

    await waitFor(() => {
      expect(screen.getByText('Producto 1 agregado al carrito')).toBeInTheDocument();
    });
  });

  test('handles add to cart error', async () => {
    const mockProducts = [
      {
        id: 1,
        nombre: 'Producto 1',
        descripcion: 'Descripción 1',
        precio: 10.00,
        categoria: 'Mouse',
        imagenURL: 'url1',
      },
    ];
    mockFetch
      .mockResolvedValueOnce({
        ok: true,
        json: () => Promise.resolve(mockProducts),
      })
      .mockResolvedValueOnce({
        ok: false,
      });

    render(<Menu userData={{ id: 1 }} />);

    await waitFor(() => {
      fireEvent.click(screen.getByText('Producto 1'));
    });

    fireEvent.click(screen.getByText('Agregar al carrito'));

    await waitFor(() => {
      expect(screen.getByText('No se pudo agregar al carrito')).toBeInTheDocument();
    });
  });
});