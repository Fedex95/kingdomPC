import React, { useEffect, useState } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import BACKEND_URL from './Config';

export default function Pedidos({ userId }) {
    const [pedidos, setPedidos] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchHistorial = async () => {
            try {
                const response = await fetch(`http://${BACKEND_URL}:8080/historial/${userId}`);
                if (!response.ok) {
                    throw new Error('Error al obtener el historial de pedidos');
                }
                const data = await response.json();
                setPedidos(data);
            } catch (error) {
                setError(error.message);
            }
        };

        fetchHistorial();
    }, [userId]);

    if (error) return <div>Error: {error}</div>;

    const calcularTotal = (detalles) => {
        if (!detalles || !Array.isArray(detalles)) return 0;

        return detalles.reduce((total, detalle) => {
            return total + (detalle.precio || 0) * (detalle.cantidad || 1);
        }, 0);
    };

    const transformarPedidos = (pedidos) => {
        return (pedidos || []).map((pedido) => {
            const total = calcularTotal(pedido.detalles || []);
            return {
                fechaCompra: new Date(pedido.fecha).toLocaleDateString('es-EC'),
                usuario: `${pedido.usuario.nombre} (${pedido.usuario.usuario})`,
                total: total.toFixed(2),
                detalles: pedido.detalles || [],
            };
        });
    };

    const transformedPedidos = transformarPedidos(pedidos);

    return (
        <div
            className="card"
            style={{
                display: 'flex',
                justifyContent: 'center',
                height: '100vh',  // Usar toda la altura de la pantalla
                margin: 0,        // Eliminar márgenes predeterminados
            }}
        >
            <div style={{ width: '100%' }}>  {/* Establecer el ancho de la tabla */}
                <DataTable
                    value={transformedPedidos}
                    scrollable

                    style={{ width: '100%', fontSize: '0.2em' }}
                >
                    <Column
                        field="fechaCompra"
                        header="Fecha de compra"
                        style={{ width: '10%' }}
                    />
                    <Column
                        field="total"
                        header="Total"
                        body={(rowData) => <span>${rowData.total}</span>}
                        style={{ width: '10%' }}
                    />
                    <Column
                        header="Nombre del producto"
                        body={(rowData) => rowData.detalles.map((detalle, index) => (
                            <div key={index}>{detalle.nombreProducto}</div>
                        ))}
                        style={{ width: '20%' }}
                    />
                    <Column
                        header="Cantidad"
                        body={(rowData) => rowData.detalles.map((detalle, index) => (
                            <div key={index}>{detalle.cantidad}</div>
                        ))}
                        style={{ width: '12%' }}
                    />
                    <Column
                        header="Precio"
                        body={(rowData) => rowData.detalles.map((detalle, index) => (
                            <div key={index}>{detalle.precio.toFixed(2)}</div>
                        ))}
                        style={{ width: '12%' }}
                    />
                </DataTable>
            </div>
        </div>
    );
}
