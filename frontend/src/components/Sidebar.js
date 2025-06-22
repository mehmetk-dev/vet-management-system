import React from 'react';
import { Drawer, List, ListItem, ListItemText, Toolbar } from '@mui/material';
import { Link } from 'react-router-dom';

const menuItems = [
  { text: 'Dashboard', path: '/' },
  { text: 'Customers', path: '/customers' },
  { text: 'Animals', path: '/animals' },
  { text: 'Appointments', path: '/appointments' },
  { text: 'Vaccines', path: '/vaccines' },
];

const Sidebar = () => (
  <Drawer variant="permanent" sx={{ width: 200, [`& .MuiDrawer-paper`]: { width: 200, boxSizing: 'border-box' } }}>
    <Toolbar />
    <List>
      {menuItems.map((item) => (
        <ListItem button key={item.text} component={Link} to={item.path}>
          <ListItemText primary={item.text} />
        </ListItem>
      ))}
    </List>
  </Drawer>
);

export default Sidebar;
