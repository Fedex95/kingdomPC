import React, { useState, useRef } from 'react';
import { Menubar } from 'primereact/menubar';
import { Dialog } from 'primereact/dialog';
import { Toast } from 'primereact/toast';
import AddProduct from './Addproduct';
import Pedido from './Pedido';
import DeleteProduct from './Deleteproduct';
import UpdateProduct from './Updateproduct';

export default function AdminMenu({ userData }) {
    const [displayAddDialog, setDisplayAddDialog] = useState(false);
    const [displayUpdateDialog, setDisplayUpdateDialog] = useState(false);
    const [displayDeleteDialog, setDisplayDeleteDialog] = useState(false);
    const [displayOrdersDialog, setDisplayOrdersDialog] = useState(false);
    const toast = useRef(null);

    const openDialog = (dialogType) => {
        switch (dialogType) {
            case 'add':
                setDisplayAddDialog(true);
                break;
            case 'update':
                setDisplayUpdateDialog(true);
                break;
            case 'delete':
                setDisplayDeleteDialog(true);
                break;
            case 'orders':
                setDisplayOrdersDialog(true);
                break;
            default:
                break;
        }
    };

    const closeDialog = () => {
        setDisplayAddDialog(false);
        setDisplayUpdateDialog(false);
        setDisplayDeleteDialog(false);
        setDisplayOrdersDialog(false);
    };

    const items = [
        {
            label: 'Agregar',
            icon: 'pi pi-plus',
            command: () => openDialog('add'),
        },
        {
            label: 'Actualizar',
            icon: 'pi pi-pencil',
            command: () => openDialog('update'),
        },
        {
            label: 'Eliminar',
            icon: 'pi pi-trash',
            command: () => openDialog('delete'),
        },
        {
            label: 'Pedidos',
            icon: 'pi pi-list',
            command: () => openDialog('orders'),
        },
    ];

    return (
        <div className="admin-container">
            <Toast ref={toast} />
            <Menubar model={items} />


            <Dialog
                header="Agregar producto"
                visible={displayAddDialog}
                onHide={closeDialog}
                draggable={false}
            >

                <AddProduct userId={userData.id} toast={toast} onClose={closeDialog} />
            </Dialog>

            <Dialog
                header="Actualizar producto"
                visible={displayUpdateDialog}
                onHide={closeDialog}
                draggable={false}
            >
                <UpdateProduct userId={userData.id} toast={toast} onClose={closeDialog} />
            </Dialog>

            <Dialog
                header="Eliminar producto"
                visible={displayDeleteDialog}
                onHide={closeDialog}
                draggable={false}
            >

                <DeleteProduct userId={userData.id} toast={toast} onClose={closeDialog} />
            </Dialog>


            <Dialog
                header="Pedidos"
                visible={displayOrdersDialog}
                onHide={closeDialog}
                style={{ width: '50vw' }}
                draggable={false}
            >
                { }
                <Pedido userData={userData} />
            </Dialog>
        </div>
    );
}
