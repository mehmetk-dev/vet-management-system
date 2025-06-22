import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { Box } from '@mui/material';
import Sidebar from './components/Sidebar';
import Dashboard from './pages/Dashboard';
import Customers from './pages/Customers';
import Animals from './pages/Animals';
import Appointments from './pages/Appointments';
import Vaccines from './pages/Vaccines';

function App() {
  return (
    <Router>
      <Box sx={{ display: 'flex' }}>
        <Sidebar />
        <Box component="main" sx={{ flexGrow: 1, p: 2 }}>
          <Routes>
            <Route path="/" element={<Dashboard />} />
            <Route path="/customers" element={<Customers />} />
            <Route path="/animals" element={<Animals />} />
            <Route path="/appointments" element={<Appointments />} />
            <Route path="/vaccines" element={<Vaccines />} />
          </Routes>
        </Box>
      </Box>
    </Router>
  );
}

export default App;
