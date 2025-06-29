import React, { useEffect, useState } from 'react';
import {
  Typography,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  TextField,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent
} from '@mui/material';
import { api, extractList } from '../api';
import SnackbarAlert from '../components/SnackbarAlert';

function Animals() {
  const [animals, setAnimals] = useState([]);
  const [filter, setFilter] = useState('');
  const [loading, setLoading] = useState(true);
  const [selected, setSelected] = useState(null);
  const [snackbar, setSnackbar] = useState({ open: false, message: '', severity: 'error' });

  const fetchAnimals = async () => {
    setLoading(true);
    try {
      const url = filter ? `/animals?name=${filter}` : '/animals';
      const res = await api.get(url);
      setAnimals(extractList(res));
    } catch (err) {
      setSnackbar({ open: true, message: 'Failed to load animals', severity: 'error' });
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchAnimals();
  }, [filter]);

  return (
    <div>
      <Typography variant="h4" color="primary" gutterBottom>
        Animals
      </Typography>
      <TextField label="Filter by name" value={filter} onChange={(e) => setFilter(e.target.value)} sx={{ mb: 2 }} />
      {loading ? (
        <CircularProgress />
      ) : (
        <Table>
          <TableHead>
            <TableRow sx={{ backgroundColor: '#f5f5f5' }}>
              <TableCell>ID</TableCell>
              <TableCell>Name</TableCell>
              <TableCell>Species</TableCell>
              <TableCell>Breed</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {animals.map((a) => (
              <TableRow key={a.id} hover onClick={() => setSelected(a)} style={{ cursor: 'pointer' }}>
                <TableCell>{a.id}</TableCell>
                <TableCell>{a.name}</TableCell>
                <TableCell>{a.species}</TableCell>
                <TableCell>{a.breed}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      )}

      <Dialog open={Boolean(selected)} onClose={() => setSelected(null)} fullWidth maxWidth="sm">
        <DialogTitle>Animal Details</DialogTitle>
        <DialogContent>
          {selected && (
            <div>
              <Typography>Name: {selected.name}</Typography>
              <Typography>Species: {selected.species}</Typography>
              <Typography>Breed: {selected.breed}</Typography>
              <Typography>Gender: {selected.gender}</Typography>
              <Typography>Color: {selected.colour}</Typography>
            </div>
          )}
        </DialogContent>
      </Dialog>
      <SnackbarAlert snackbar={snackbar} setSnackbar={setSnackbar} />
    </div>
  );
}

export default Animals;
