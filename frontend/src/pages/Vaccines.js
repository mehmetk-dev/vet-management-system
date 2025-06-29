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
import { api, extractList } from '../api';
import SnackbarAlert from '../components/SnackbarAlert';

const emptyVaccine = { id: null, name: '', code: '', protectionStartDate: '', protectionFinishDate: '', animalId: '' };

function Vaccines() {
  const [vaccines, setVaccines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState(emptyVaccine);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'success' });

  const fetchVaccines = async () => {
    setLoading(true);
    try {
      const res = await api.get('/vaccines');
      setVaccines(extractList(res));
    } catch (err) {
      setSnackbar({ open: true, message: 'Failed to load vaccines', severity: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { fetchVaccines(); }, []);

  const handleOpen = () => {
    setFormData(emptyVaccine);
    setOpen(true);
  };
  const handleClose = () => setOpen(false);

  const handleSave = async () => {
    try {
      await api.post('/vaccines', formData);
      setSnackbar({ open: true, message: 'Vaccine saved', severity: 'success' });
      handleClose();
      fetchVaccines();
    } catch (err) {
      setSnackbar({ open: true, message: 'Could not save vaccine', severity: 'error' });
    }
  };

  const handleChange = (field) => (e) => {
    setFormData({ ...formData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" color="primary" gutterBottom>
        Vaccines
      </Typography>
      <Button variant="contained" color="primary" onClick={handleOpen}>Add Vaccine</Button>
      {loading ? (
        <CircularProgress />
      ) : (
        <Table sx={{ mt: 2 }}>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Code</TableCell>
              <TableCell>Start</TableCell>
              <TableCell>Finish</TableCell>
              <TableCell>Animal</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {vaccines.map((v) => (
              <TableRow key={v.id}>
                <TableCell>{v.id}</TableCell>
                <TableCell>{v.name}</TableCell>
                <TableCell>{v.code}</TableCell>
                <TableCell>{v.protectionStartDate}</TableCell>
                <TableCell>{v.protectionFinishDate}</TableCell>
                <TableCell>{v.animalId}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={open} onClose={handleClose} fullWidth maxWidth="sm">
        <DialogTitle>Add Vaccine</DialogTitle>
        <DialogContent sx={{ display: 'flex', flexDirection: 'column', gap: 2, mt: 1 }}>
          <TextField label="Name" value={formData.name} onChange={handleChange('name')} />
          <TextField label="Code" value={formData.code} onChange={handleChange('code')} />
          <TextField
            label="Protection Start"
            type="date"
            InputLabelProps={{ shrink: true }}
            value={formData.protectionStartDate}
            onChange={handleChange('protectionStartDate')}
          />
          <TextField
            label="Protection Finish"
            type="date"
            InputLabelProps={{ shrink: true }}
            value={formData.protectionFinishDate}
            onChange={handleChange('protectionFinishDate')}
          />
          <TextField label="Animal ID" value={formData.animalId} onChange={handleChange('animalId')} />
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

export default Vaccines;
