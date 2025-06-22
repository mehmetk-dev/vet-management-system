import React from 'react';
import {
  Drawer,
  List,
  ListItem,
  ListItemText,
  Toolbar,
  ListItemIcon
} from '@mui/material';
import { Link, useLocation } from 'react-router-dom';
import DashboardIcon from '@mui/icons-material/Dashboard';
import PeopleIcon from '@mui/icons-material/People';
import PetsIcon from '@mui/icons-material/Pets';
import EventIcon from '@mui/icons-material/Event';
import VaccinesIcon from '@mui/icons-material/Vaccines';
import LocalHospitalIcon from '@mui/icons-material/LocalHospital';

const menuItems = [
  { text: 'Dashboard', path: '/', icon: <DashboardIcon /> },
  { text: 'Customers', path: '/customers', icon: <PeopleIcon /> },
  { text: 'Animals', path: '/animals', icon: <PetsIcon /> },
  { text: 'Appointments', path: '/appointments', icon: <EventIcon /> },
  { text: 'Vaccines', path: '/vaccines', icon: <VaccinesIcon /> },
  { text: 'Doctors', path: '/doctors', icon: <LocalHospitalIcon /> },
];

const Sidebar = () => {
  const location = useLocation();
  return (
    <Drawer
      variant="permanent"
      sx={{ width: 200, [`& .MuiDrawer-paper`]: { width: 200, boxSizing: 'border-box' } }}
    >
      <Toolbar />
      <List>
        {menuItems.map((item) => (
          <ListItem
            button
            key={item.text}
            component={Link}
            to={item.path}
            selected={location.pathname === item.path}
            sx={{ '&:hover': { bgcolor: 'action.hover' } }}
          >
            <ListItemIcon>{item.icon}</ListItemIcon>
            <ListItemText primary={item.text} />
          </ListItem>
        ))}
      </List>
    </Drawer>
  );
};

export default Sidebar;
