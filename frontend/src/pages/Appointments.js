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

const emptyAppointment = {
  id: null,
  appointmentDate: '',
  doctorId: '',
  animalId: ''
};

function Appointments() {
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState(emptyAppointment);

  const fetchAppointments = async () => {
    setLoading(true);
    try {
      const res = await api.get('/appointments');

      // Pageable destekli yapı kontrolü
      const list = Array.isArray(res.data)
        ? res.data
        : res.data.data?.items || res.data.data?.content || [];

      setAppointments(list);
    } catch (err) {
      setError('Failed to load appointments');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAppointments();
  }, []);

  const handleDelete = async (id) => {
    try {
      await api.delete(`/appointments/${id}`);
      fetchAppointments();
    } catch {
      setError('Failed to delete appointment');
    }
  };

  const handleOpen = () => {
    setFormData(emptyAppointment);
    setOpen(true);
  };

  const handleClose = () => {
    setFormData(emptyAppointment);
    setOpen(false);
  };

  const handleSave = async () => {
    try {
      await api.post('/appointments', formData);
      handleClose();
      fetchAppointments();
    } catch (err) {
      setError('Failed to save appointment');
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

      <Button variant="contained" onClick={handleOpen} sx={{ mb: 2 }}>
        Schedule Appointment
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
                  <Button color="error" size="small" onClick={() => handleDelete(a.id)}>
                    Delete
                  </Button>
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
          <Button variant="contained" onClick={handleSave}>
            Save
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default Appointments;
