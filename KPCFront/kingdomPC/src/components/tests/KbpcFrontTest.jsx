import React from 'react';
import { render, screen } from '@testing-library/react';
import Home from '../Home';
import Navbar from '../Navbar';
import Login from '../Login';

describe('Front-end basic component tests', () => {
  test('Home component renders correctamente', () => {
    render(<Home />);
    expect(screen.getByText(/home/i)).toBeInTheDocument();
  });

  test('Navbar muestra el logo', () => {
    render(<Navbar />);
    expect(screen.getByAltText(/logo/i)).toBeInTheDocument();
  });

  test('Login muestra el campo usuario', () => {
    render(<Login />);
    expect(screen.getByPlaceholderText(/usuario/i)).toBeInTheDocument();
  });
});