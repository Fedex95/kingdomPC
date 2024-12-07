import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { useState } from 'react';
import './App.css';
import Layout from './components/Layout';
import Home from './components/Home';
import Login from './components/Login';
import Register from './components/Register';
import Cart from './components/Cart';
import Pedidos from './components/Pedidos';
import Producto from './components/Producto';
import PerfilUsuario from './components/PerfilUsuario';
import AdminView from './components/Adminview';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userData, setUserData] = useState(null);
  const [cartItems, setCartItems] = useState([]);
  const [admin, setAdmin] = useState(false);

  const handleLogin = (data) => {
    setIsAuthenticated(true);
    setUserData(data);

    fetch(`http://localhost:8080/usuarios/${data.id}/admin`)
      .then(response => response.json())
      .then(isAdminResponse => {
        setAdmin(isAdminResponse);
      })
      .catch((error) => console.error('Error verifying admin status:', error));
  };

  const handleLogout = () => {
    setIsAuthenticated(false);
    setUserData(null);
    setCartItems([]);
    setAdmin(false); 
  };

  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login onLogin={handleLogin} />} />
        <Route path="/register" element={<Register />} />

        {isAuthenticated ? (
          <>
            <Route path="/" element={<Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><Home userData={userData} /></Layout>} />
            <Route path="/producto" element={<Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><Producto userData={userData} /></Layout>} />
            <Route path="/perfil" element={<Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><PerfilUsuario userData={userData} /></Layout>} />
            <Route path="/admin" element={isAuthenticated && admin ? <Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><AdminView userData={userData} /></Layout> : <Navigate to="/login" replace />} />
            <Route path="/cart" element={<Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><Cart userData={userData} /></Layout>} />
            <Route path="/pedidos" element={<Layout onLogout={handleLogout} cartItemsCount={cartItems.length} userData={userData}><Pedidos userId={userData.id} /></Layout>} />
            <Route path="*" element={<Navigate to="/" replace />} />
          </>
        ) : (
          <Route path="*" element={<Navigate to="/login" replace />} />
        )}
      </Routes>
    </Router>
  );
}

export default App;