import { useState, useRef } from 'react';
import { useNavigate } from 'react-router-dom';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import { Link } from 'react-router-dom';
import '../styles/Login.css';


function Login({ onLogin }) {
    const [usuario, setUsuario] = useState('');
    const [pass, setPass] = useState('');
    const toast = useRef(null);
    const navigate = useNavigate();

    const handleLogin = async (e) => {
        e.preventDefault();

        if (!usuario || !pass) {
            toast.current.show({
                severity: 'warn',
                summary: 'Advertencia',
                detail: 'Por favor complete todos los campos',
                life: 3000
            });
            return;
        }

        try {
            const verifyResponse = await fetch(
                `http://localhost:8080/usuarios/viewPass?usuario=${usuario}&pass=${pass}`,
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (verifyResponse.status === 200) {
                const usersResponse = await fetch('http://localhost:8080/usuarios/get/all', {
                    method: 'GET',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (usersResponse.ok) {
                    const users = await usersResponse.json();
                    const currentUser = users.find(user => user.usuario === usuario);

                    if (currentUser) {
                        onLogin(currentUser);
                        toast.current.show({
                            severity: 'success',
                            summary: 'Éxito',
                            detail: 'Inicio de sesión exitoso',
                            life: 3000
                        });
                        setUsuario('');  
                        setPass('');
                        navigate('/home');  
                    }
                }
            } else {
                toast.current.show({
                    severity: 'error',
                    summary: 'Error',
                    detail: 'Usuario o contraseña incorrectos',
                    life: 3000
                });
            }
        } catch (error) {
            console.error('Error:', error);
            toast.current.show({
                severity: 'error',
                summary: 'Error',
                detail: 'Error al iniciar sesión',
                life: 3000
            });
        }
    };

    return (
        <div className="login-container">
            <Toast ref={toast} />
            <div className="login-box">
                <div className="login-image-side">
                    <div className="overlay">
                        <h2>Electro Master</h2>
                        <p>Los mejores productos electrónicos</p>
                    </div>
                </div>
                
                <div className="login-form-side">
                    <div className="login-header">
                        <h1>Iniciar Sesión</h1>
                    </div>

                    <form className="login-form" onSubmit={handleLogin}>
                        <div className="form-group">
                            <label htmlFor="usuario">Usuario</label>
                            
                                <InputText
                                    id="usuario"
                                    value={usuario}
                                    onChange={(e) => setUsuario(e.target.value)}
                                    placeholder="Ingresa tu usuario"
                                    className="w-full"
                                />
                        </div>

                        <div className="form-group">
                            <label htmlFor="pass">Contraseña</label>
                            <Password
                                id="pass"
                                value={pass}
                                onChange={(e) => setPass(e.target.value)}
                                feedback={false}
                                placeholder="Ingresa tu contraseña"
                                className="w-full"
                            />
                        </div>

                        <Button
                            label="Iniciar Sesión"
                            icon="pi pi-sign-in"
                            disabled={!usuario || !pass}
                            className="login-button"
                        />

                        <div className="register-prompt">
                            ¿No tienes una cuenta? {' '}
                            <Link to="/register">
                                Regístrate aquí
                            </Link>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default Login;
