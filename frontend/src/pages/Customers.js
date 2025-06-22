import React, { useEffect, useState } from 'react';
import {
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField
} from '@mui/material';
import { api, extractList } from '../api';
import SnackbarAlert from '../components/SnackbarAlert';

const emptyCustomer = { id: null, name: '', phone: '', email: '', city: '', address: '' };

function Customers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editData, setEditData] = useState(emptyCustomer);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const fetchCustomers = async () => {
    setLoading(true);
    try {
      const res = await api.get('/customers');
      setCustomers(extractList(res));
    } catch (err) {
      setSnackbar({ open: true, message: 'Failed to load customers', severity: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchCustomers();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/customers/${id}`);
      setSnackbar({ open: true, message: 'Customer deleted', severity: 'success' });
      fetchCustomers();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not delete customer', severity: 'error' });
    }
  };

  const handleOpen = (c = emptyCustomer) => {
    setEditData(c);
    setOpen(true);
  };

  const handleClose = () => setOpen(false);

  const handleSave = async () => {
    try {
      if (editData.id) {
        await api.put(`/customers/${editData.id}`, editData);
      } else {
        await api.post('/customers', editData);
      }
      setSnackbar({ open: true, message: 'Customer saved', severity: 'success' });
      handleClose();
      fetchCustomers();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not save customer', severity: 'error' });
    }
  };

  const handleChange = (field) => (e) => {
    setEditData({ ...editData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" color="primary" gutterBottom>
        Customers
      </Typography>
      <Button variant="contained" color="primary" onClick={() => handleOpen()}>Add Customer</Button>
      {loading ? (
        <CircularProgress />
      ) : (
        <Table sx={{ mt: 2 }}>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Phone</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>City</TableCell>
              <TableCell>Address</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {customers.map((c) => (
              <TableRow key={c.id}>
                <TableCell>{c.id}</TableCell>
                <TableCell>{c.name}</TableCell>
                <TableCell>{c.phone}</TableCell>
                <TableCell>{c.email}</TableCell>
                <TableCell>{c.city}</TableCell>
                <TableCell>{c.address}</TableCell>
                <TableCell>
                  <Button variant="contained" size="small" color="secondary" onClick={() => handleOpen(c)}>
                    Edit
                  </Button>
                  <Button variant="contained" size="small" color="error" onClick={() => handleDelete(c.id)} sx={{ ml: 1 }}>
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
        <DialogTitle>{editData.id ? 'Edit Customer' : 'Add Customer'}</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField label="Name" value={editData.name} onChange={handleChange('name')} />
          <TextField label="Phone" value={editData.phone} onChange={handleChange('phone')} />
          <TextField label="Email" value={editData.email} onChange={handleChange('email')} />
          <TextField label="City" value={editData.city} onChange={handleChange('city')} />
          <TextField label="Address" value={editData.address} onChange={handleChange('address')} />
        </DialogContent>
        <DialogActions>
          <Button variant="outlined" onClick={handleClose}>Cancel</Button>
          <Button variant="contained" color="primary" onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
      <SnackbarAlert snackbar={snackbar} setSnackbar={setSnackbar} />
    </div>
  );
}

export default Customers;
