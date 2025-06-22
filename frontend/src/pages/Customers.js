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
import api from '../api';

const emptyCustomer = {
  id: null,
  name: '',
  phone: '',
  email: '',
  city: '',
  address: ''
};

function Customers() {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = useState(false);
  const [editData, setEditData] = useState(emptyCustomer);

  const fetchCustomers = async () => {
    setLoading(true);
    try {
      const res = await api.get('/customers', {
        params: {
          page: 0,
          pageSize: 1000  // burayı artırdık
        }
      });
      const list = res.data?.data?.items || [];
      setCustomers(list);
    } catch (err) {
      setError('Failed to load customers');
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
      fetchCustomers();
    } catch {
      setError('Failed to delete customer');
    }
  };

  const handleOpen = (c = emptyCustomer) => {
    setEditData(c);
    setOpen(true);
  };

  const handleClose = () => {
    setEditData(emptyCustomer);
    setOpen(false);
  };

  const handleSave = async () => {
    try {
      if (editData.id) {
        await api.put(`/customers/${editData.id}`, editData);
      } else {
        await api.post('/customers', editData);
      }
      handleClose();
      fetchCustomers();
    } catch {
      setError('Failed to save customer');
    }
  };

  const handleChange = (field) => (e) => {
    setEditData({ ...editData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Customers
      </Typography>
      <Button variant="contained" onClick={() => handleOpen()} sx={{ mb: 2 }}>
        Add Customer
      </Button>

      {loading ? (
        <CircularProgress />
      ) : error ? (
        <Typography color="error">{error}</Typography>
      ) : (
        <Table>
          <TableHead>
            <TableRow>
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
                  <Button size="small" onClick={() => handleOpen(c)}>Edit</Button>
                  <Button size="small" color="error" onClick={() => handleDelete(c.id)}>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={open} onClose={handleClose} fullWidth>
        <DialogTitle>{editData.id ? 'Edit Customer' : 'Add Customer'}</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField label="Name" value={editData.name} onChange={handleChange('name')} />
          <TextField label="Phone" value={editData.phone} onChange={handleChange('phone')} />
          <TextField label="Email" value={editData.email} onChange={handleChange('email')} />
          <TextField label="City" value={editData.city} onChange={handleChange('city')} />
          <TextField label="Address" value={editData.address} onChange={handleChange('address')} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave} variant="contained">Save</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default Customers;
