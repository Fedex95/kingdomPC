import { Card } from 'primereact/card';
import '../styles/Profile.css';

export default function Profile({ userData }) {
    return (
        <div className="profile-container">
            <h1 className="profile-title">Datos personales</h1>
            <Card className="profile-card">
                <div className="profile-info">
                    <div className="profile-item">
                        <h3>Nombres</h3>
                        <p>{userData?.nombre || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Apellidos</h3>
                        <p>{userData?.apellido || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Cédula</h3>
                        <p>{userData?.cedula || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Email</h3>
                        <p>{userData?.email || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Nombre de usuario</h3>
                        <p>{userData?.usuario || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Teléfono</h3>
                        <p>{userData?.telefono || 'No disponible'}</p>
                    </div>
                    <div className="profile-item">
                        <h3>Dirección</h3>
                        <p>{userData?.direccion || 'No disponible'}</p>
                    </div>
                </div>
            </Card>
        </div>

    );
}