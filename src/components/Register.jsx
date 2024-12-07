import { useState, useRef } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Toast } from 'primereact/toast';
import '../styles/Register.css';
import PasswordInput from 'prime-react/PasswordInput';

function Register() {
  const [formData, setFormData] = useState({
    nombre: '',
    apellido: '',
    usuario: '',
    cedula: '',
    email: '',
    pass: '',
    telefono: '',
    direccion: '',
    nombreTarjeta: '',
    numeroTarjeta: '',
    fechaValidez: '',
    cvv: '',
    rol: ''
  });
  const toast = useRef(null);
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === 'checkbox' ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const {
      nombre, apellido, usuario, cedula, email, pass, telefono, direccion, nombreTarjeta, numeroTarjeta, fechaValidez,
      cvv
    } = formData;

    if (!nombre || !apellido || !usuario || !cedula || !email || !pass || !telefono
      || !direccion || !nombreTarjeta || !numeroTarjeta || !fechaValidez || !cvv) {
      toast.current.show({
        severity: 'warn',
        detail: 'Completa todos los campos',
        life: 3000,
      });
      return;
    }

    const userData = {
      id: 0,
      nombre: nombre,
      apellido: apellido,
      usuario: usuario,
      cedula: cedula,
      email: email,
      pass: pass,
      telefono: telefono,
      direccion: direccion,
      nombreTarjeta: nombreTarjeta,
      numeroTarjeta: numeroTarjeta,
      fechaValidez: fechaValidez,
      cvv: cvv,
      rol: 'USER',
    };

    try {

      const response = await fetch('http://localhost:8080/usuarios/createUsuario', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });


      if (response.ok) {
        toast.current.show({
          severity: 'success',
          summary: 'Éxito',
          detail: 'Usuario registrado exitosamente',
          life: 3000,
        });
        navigate('/login');
      } else {
        const errorData = await response.json();
        toast.current.show({
          severity: 'error',
          summary: 'Error',
          detail: errorData.message || 'Error al registrar el usuario',
          life: 3000,
        });
      }
    } catch (error) {
      console.error('Error al conectar con el servidor:', error);
      toast.current.show({
        severity: 'error',
        summary: 'Error',
        detail: 'No se pudo conectar con el servidor',
        life: 3000,
      });
    }
  };


  return (
    <div className="register">
      <Toast ref={toast} />
      <div className="image-side">
        <div className="overlay">
          <p>No te pierdas de nuestras ofertas</p>
        </div>
      </div>

      <div className="form-side">
        <h1>Crear cuenta</h1>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <h1>Ingrese sus datos personales</h1>
            <label htmlFor="nombre">Nombre</label>
            <InputText
              id="nombre"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="apellido">Apellido</label>
            <InputText
              id="apellido"
              name="apellido"
              value={formData.apellido}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="cedula">Cédula</label>
            <InputText
              id="cedula"
              name="cedula"
              value={formData.cedula}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="telefono">Teléfono</label>
            <InputText
              id="telefono"
              name="telefono"
              value={formData.telefono}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="direccion">Dirección</label>
            <InputText
              id="direccion"
              name="direccion"
              value={formData.direccion}
              onChange={handleChange}
              className="w-full"
            />

            <label htmlFor="email">Correo</label>
            <InputText
              id="email"
              name="email"
              type="email"
              value={formData.email}
              onChange={handleChange}
              placeholder="ejemplo@ejemplo.com"
              className="w-full"
            />

            <label htmlFor="usuario">Nombre de usuario</label>
            <InputText
              id="usuario"
              name="usuario"
              value={formData.usuario}
              onChange={handleChange}
              className="w-full"
            />

            <label htmlFor="pass">Contraseña</label>
            <PasswordInput
              id="pass"
              name="pass"
              value={formData.pass}
              onChange={handleChange}
              className="w-full"
            />
          </div>
          <div className="form-group">
            <h1>Ingrese un método de pago</h1>
            <label htmlFor="nombreTarjeta">Titular de la tarjeta</label>
            <InputText
              id="nombreTarjeta"
              name="nombreTarjeta"
              value={formData.nombreTarjeta}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="numeroTarjeta">Dígitos</label>
            <InputText
              id="numeroTarjeta"
              name="numeroTarjeta"
              value={formData.numeroTarjeta}
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="fechaValidez">Fecha de validación</label>
            <InputText
              id="fechaValidez"
              name="fechaValidez"
              value={formData.fechaValidez}
              placeholder="15/21"
              onChange={handleChange}
              className="w-full"
            />
            <label htmlFor="cvv">CVV</label>
            <InputText
              id="cvv"
              name="cvv"
              value={formData.cvv}
              placeholder="Revise al reverso de la tarjeta"
              onChange={handleChange}
              className="w-full"
            />
          </div>

          <Button
            label="Registrarse"
            icon="pi pi-user-plus"
            className="register-button"
          />
          <div className="login-prompt">
            ¿Ya tienes una cuenta?{' '}
            <Link to="/login" className="login-link">
              Inicia sesión
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}

export default Register;