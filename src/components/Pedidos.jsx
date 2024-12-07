import React, { useEffect, useState } from 'react';
import { TreeTable } from 'primereact/treetable';
import { Column } from 'primereact/column';

export default function Pedidos({ userId }) {
    const [pedidos, setPedidos] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchHistorial = async () => {
            try {
                const response = await fetch(`http://localhost:8080/historial/${userId}`);
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
            return total + (detalle.precio || 0);
        }, 0);
    };

    const transformarPedidos = (pedidos) => {
        return (pedidos || []).map((pedido) => {
            const total = calcularTotal(pedido.detalles || []);

            return {
                key: pedido.id,
                data: {
                    fechaCompra: new Date(pedido.fecha).toLocaleString(),
                    total,
                    usuario: `${pedido.usuario.nombre} (${pedido.usuario.usuario})`,
                },
                children: (pedido.detalles || []).map((detalle) => ({
                    key: detalle.id,
                    data: {
                        nombreProducto: detalle.nombreProducto,
                        cantidad: detalle.cantidad,
                        precio: detalle.precio,
                    },
                })),
            };
        });
    };

    const nodes = transformarPedidos(pedidos);

    return (
        <div className="card">
            <TreeTable value={nodes} tableStyle={{ minWidth: '50rem' }}>
                <Column field="fechaCompra" header="Fecha de compra" expander />
                <Column field="usuario" header="Usuario" />
                <Column field="total" header="Total" body={(rowData) => <span>${rowData.data.total}</span>} />
                <Column field="nombreProducto" header="Nombre" />
                <Column field="cantidad" header="Cantidad" />
                <Column field="precio" header="Precio" />
            </TreeTable>
        </div>
    );
}