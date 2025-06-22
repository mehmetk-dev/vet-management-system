import React, { useEffect, useState } from 'react';
import {
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  TextField,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  CircularProgress
} from '@mui/material';
import api from '../api';
import SnackbarAlert from '../components/SnackbarAlert';

const emptyAppointment = { id: null, appointmentDate: '', doctorId: '', animalId: '' };

function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState(emptyAppointment);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const fetchAppointments = async () => {
    setLoading(true);
    try {
      const res = await api.get('/appointments');
      const list = res.data.data ? res.data.data.content : res.data;
      setAppointments(list);
    } catch (err) {
      setSnackbar({ open: true, message: 'Failed to load appointments', severity: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchAppointments(); }, []);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/appointments/${id}`);
      setSnackbar({ open: true, message: 'Appointment deleted', severity: 'success' });
      fetchAppointments();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not delete appointment', severity: 'error' });
    }
  };

  const handleOpen = () => {
    setFormData(emptyAppointment);
    setOpen(true);
  };
  const handleClose = () => setOpen(false);

  const handleSave = async () => {
    try {
      await api.post('/appointments', formData);
      setSnackbar({ open: true, message: 'Appointment saved', severity: 'success' });
      handleClose();
      fetchAppointments();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not save appointment', severity: 'error' });
    }
  };

  const handleChange = (field) => (e) => {
    setFormData({ ...formData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Appointments
      </Typography>
      <Button variant="contained" onClick={handleOpen}>Schedule Appointment</Button>
      {loading ? (
        <CircularProgress />
      ) : (
        <Table sx={{ mt: 2 }}>
          <TableHead>
            <TableRow>
              <TableCell>ID</TableCell>
              <TableCell>Date</TableCell>
              <TableCell>Doctor</TableCell>
              <TableCell>Animal</TableCell>
              <TableCell>Actions</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {appointments.map((a) => (
              <TableRow key={a.id}>
                <TableCell>{a.id}</TableCell>
                <TableCell>{a.appointmentDate}</TableCell>
                <TableCell>{a.doctorId}</TableCell>
                <TableCell>{a.animalId}</TableCell>
                <TableCell>
                  <Button color="error" size="small" onClick={() => handleDelete(a.id)}>Delete</Button>
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={open} onClose={handleClose} fullWidth>
        <DialogTitle>Schedule Appointment</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField
            label="Appointment Date"
            type="datetime-local"
            InputLabelProps={{ shrink: true }}
            value={formData.appointmentDate}
            onChange={handleChange('appointmentDate')}
          />
          <TextField label="Doctor ID" value={formData.doctorId} onChange={handleChange('doctorId')} />
          <TextField label="Animal ID" value={formData.animalId} onChange={handleChange('animalId')} />
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

export default Appointments;
