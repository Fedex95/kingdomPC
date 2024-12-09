import { useEffect, useState, useRef, useCallback } from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { useNavigate } from 'react-router-dom';
import '../styles/Cart.css';


function Cart({ userData }) {
    const [cartItems, setCartItems] = useState([]);
    const toast = useRef(null);
    const navigate = useNavigate();

    

    const fetchCartItems = useCallback(async () => {
        try {
            const response = await fetch(`http://localhost:8080/usuarios/get/all`);

            if (!response.ok) {
                throw new Error('Error al cargar el carrito');
            }

            const users = await response.json();
            const currentUser = users.find(user => user.id === userData.id);

            if (currentUser && currentUser.cart && currentUser.cart.items) {
                setCartItems(currentUser.cart.items);
            } else {
                setCartItems([]);
            }

        } catch (error) {
            console.error('Error:', error);
            toast.current.show({
                severity: 'error',
                summary: 'Error',
                detail: 'No se pudieron cargar los items del carrito',
                life: 3000
            });
        }
    }, [userData.id]);

    useEffect(() => {
        if (userData?.id) {
            fetchCartItems();
        }
    }, [userData, fetchCartItems]);

    const removeItem = async (cartId, itemId) => {
        if (!cartId) {
            console.error('Cart ID is undefined. Cannot remove item.');
            return;
        }

        try {
            const response = await fetch(`http://localhost:8080/cart/eliminar/${cartId}/${itemId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Error al eliminar el item');
            }

            toast.current.show({
                severity: 'success',
                summary: 'Éxito',
                detail: 'Producto eliminado del carrito',
                life: 3000
            });

            fetchCartItems();

        } catch (error) {
            console.error('Error:', error);
            toast.current.show({
                severity: 'error',
                summary: 'Error',
                detail: 'No se pudo eliminar el producto',
                life: 3000
            });
        }
    };

    const calculateTotal = () => {
        if (!cartItems || cartItems.length === 0) return 0;

        return cartItems.reduce((total, item) => {
            const precio = item.producto?.precio || 0;
            const cantidad = item.cantidad || 0;
            return total + (precio * cantidad);
        }, 0);
    };

    const handlePayment = async () => {
        try {
            if (!userData?.cart?.id) {
                toast.current.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'No se encontró el carrito',
                    life: 3000
                });
                return;
            }

            const response = await fetch(`http://localhost:8080/cart/pagar/${userData.cart.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error('Error al procesar el pago');
            }

            toast.current.show({
                severity: 'success',
                summary: '¡Éxito!',
                detail: 'Pago procesado correctamente',
                life: 3000
            });

            fetchCartItems();

        } catch (error) {
            console.error('Error:', error);
            toast.current.show({
                severity: 'error',
                summary: 'Error',
                detail: 'No se pudo procesar el pago',
                life: 3000
            });
        }
    };

    return (
        <div className="cart-container">
            <Toast ref={toast} />
            <h1 className="cart-title">Carrito</h1>

            {cartItems.length === 0 ? (
                <Card className="empty-cart">
                    <div className="empty-cart-content">
                        <i className="pi pi-shopping-cart" style={{ fontSize: '3rem' }}></i>
                        <h2>Tu carrito está vacío</h2>
                        <Button
                            label="Catálogo"
                            icon="pi pi-list"
                            onClick={() => navigate('/home')}
                        />
                    </div>
                </Card>
            ) : (
                <div className="cart-content">
                    <div className="cart-items">
                        {cartItems.map(item => (
                            <Card key={item.id} className="cart-item">
                                <div className="cart-item-content">
                                    <img
                                        src={item.producto?.imagen}
                                        alt={item.producto?.nombre}
                                        className="cart-item-image"
                                        onError={(e) => {
                                            e.target.onerror = null; 
                                            e.target.style.display = 'none'; 
                                        }}
                                    />
                                    <div className="cart-item-details">
                                        <h3>{item.producto?.nombre}</h3>
                                        <p className="cart-item-price">
                                            ${item.producto?.precio}
                                        </p>
                                    </div>
                                    <div className="cart-item-actions">
                                        <span className="cart-item-quantity">
                                            Cantidad: {item.cantidad}
                                        </span>
                                        <Button
                                            icon="pi pi-trash"
                                            className="p-button-danger p-button-text"
                                            onClick={() => removeItem(userData.cart?.id, item.id)}
                                            tooltip="Eliminar"
                                        />
                                    </div>
                                </div>
                            </Card>
                        ))}
                    </div>

                    <Card className="cart-summary">
                        <h3>Resumen del Pedido</h3>
                        <div className="cart-summary-content">
                            <div className="summary-row">
                                <span>Subtotal: <span>${calculateTotal().toFixed(2)}</span></span>
                                <span>Precio incluye IVA</span>
                                
                            </div>
                            <div className="summary-row">
                                <span>Envío: Gratis</span>
                            </div>
                            <div className="summary-total">
                                <span>Total:</span>
                                <span>${calculateTotal().toFixed(2)}</span>
                            </div>
                            <Button
                                label="Proceder al Pago"
                                icon="pi pi-shopping-cart"
                                className="p-button-success p-button-raised"
                                onClick={handlePayment}
                                disabled={cartItems.length === 0}
                            />
                        </div>
                    </Card>
                </div>
            )}
        </div>
    );

}

export default Cart;