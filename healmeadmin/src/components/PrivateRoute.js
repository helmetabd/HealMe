import React from 'react';
import { Navigate, useLocation } from 'react-router-dom';

function PrivateRoute({ children }) {
  // harus me return ELEMENT
  const location = useLocation();

  if (!localStorage.getItem('Authorization')) { // kalo belum login
    // harus selalu diarahkan ke login page
    return <Navigate to='/login' state={{ from: location}}/>
  }

  return children; // component yang akan di render kalo sudah login
}

export default PrivateRoute