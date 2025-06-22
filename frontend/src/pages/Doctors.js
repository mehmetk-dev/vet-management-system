import React, { useEffect, useState } from 'react';
import {
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  CircularProgress
} from '@mui/material';
import api from '../api';
import SnackbarAlert from '../components/SnackbarAlert';

const emptyDoctor = { id: null, name: '', phone: '', email: '', specialty: '' };

function Doctors() {
  const [doctors, setDoctors] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [editData, setEditData] = useState(emptyDoctor);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const fetchDoctors = async () => {
    setLoading(true);
    try {
      const res = await api.get('/doctors');
      const list = res.data.data ? res.data.data.content : res.data;
      setDoctors(list);
    } catch (err) {
      setSnackbar({ open: true, message: 'Failed to load doctors', severity: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchDoctors();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/doctors/${id}`);
      setSnackbar({ open: true, message: 'Doctor deleted', severity: 'success' });
      fetchDoctors();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not delete doctor', severity: 'error' });
    }
  };

  const handleOpen = (d = emptyDoctor) => {
    setEditData(d);
    setOpen(true);
  };

  const handleClose = () => setOpen(false);

  const handleSave = async () => {
    try {
      if (editData.id) {
        await api.put(`/doctors/${editData.id}`, editData);
      } else {
        await api.post('/doctors', editData);
      }
      setSnackbar({ open: true, message: 'Doctor saved', severity: 'success' });
      handleClose();
      fetchDoctors();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not save doctor', severity: 'error' });
    }
  };

  const handleChange = (field) => (e) => {
    setEditData({ ...editData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Doctors
      </Typography>
      <Button variant="contained" onClick={() => handleOpen()}>
        Add Doctor
      </Button>
      {loading ? (
        <CircularProgress />
      ) : (
        <Table sx={{ mt: 2 }}>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Phone</TableCell>
              <TableCell>Email</TableCell>
              <TableCell>Specialty</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {doctors.map((d) => (
              <TableRow key={d.id}>
                <TableCell>{d.id}</TableCell>
                <TableCell>{d.name}</TableCell>
                <TableCell>{d.phone}</TableCell>
                <TableCell>{d.email}</TableCell>
                <TableCell>{d.specialty}</TableCell>
                <TableCell>
                  <Button size="small" onClick={() => handleOpen(d)}>Edit</Button>
                  <Button size="small" color="error" onClick={() => handleDelete(d.id)}>
                    Delete
                  </Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={open} onClose={handleClose} fullWidth>
        <DialogTitle>{editData.id ? 'Edit Doctor' : 'Add Doctor'}</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField label="Name" value={editData.name} onChange={handleChange('name')} />
          <TextField label="Phone" value={editData.phone} onChange={handleChange('phone')} />
          <TextField label="Email" value={editData.email} onChange={handleChange('email')} />
          <TextField label="Specialty" value={editData.specialty} onChange={handleChange('specialty')} />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
      <SnackbarAlert snackbar={snackbar} setSnackbar={setSnackbar} />
    </div>
  );
}

export default Doctors;
