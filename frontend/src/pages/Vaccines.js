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

const emptyVaccine = { id: null, name: '', code: '', protectionStartDate: '', protectionFinishDate: '', animalId: '' };

function Vaccines() {
  const [vaccines, setVaccines] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [open, setOpen] = useState(false);
  const [formData, setFormData] = useState(emptyVaccine);

  const fetchVaccines = async () => {
    setLoading(true);
    try {
      const res = await api.get('/vaccines');
      const list = res.data.data ? res.data.data.content : res.data;
      setVaccines(list);
    } catch (err) {
      setError('Failed to load vaccines');
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
      handleClose();
      fetchVaccines();
    } catch (err) {
      setError('Failed to save vaccine');
    }
  };

  const handleChange = (field) => (e) => {
    setFormData({ ...formData, [field]: e.target.value });
  };

  return (
    <div>
      <Typography variant="h4" gutterBottom>
        Vaccines
      </Typography>
      <Button variant="contained" onClick={handleOpen}>Add Vaccine</Button>
      {loading ? (
        <CircularProgress />
      ) : error ? (
        <Typography color="error">{error}</Typography>
      ) : (
        <Table sx={{ mt: 2 }}>
          <TableHead>
            <TableRow>
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

      <Dialog open={open} onClose={handleClose} fullWidth>
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
          <Button onClick={handleClose}>Cancel</Button>
          <Button onClick={handleSave}>Save</Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}

export default Vaccines;
