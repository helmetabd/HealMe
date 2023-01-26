import { Routes, Route } from 'react-router-dom';
import './App.css';
import PrivateRoute from './components/PrivateRoute';
import NavBar from './components/NavBar';
import Medicine from './pages/Medicine';
import Medicines from './pages/Medicines';
import FormMedicine from './pages/Form Medicine';
import EditMedicine from './pages/Edit Medicine';
import Home from './pages/Home';
import Cart from './pages/Cart';
import Login from './pages/Login';
import Hospitals from './pages/Hospitals';
import Hospital from './pages/Hospital';
import FormHospital from './pages/Form Hospital';
import EditHospital from './pages/Edit Hospital';
import Register from './pages/Register';

function App() {
  return (
    <div className="App">
      <NavBar />

      <Routes>
        <Route path="/" element={ <Home/> } />

        <Route path="/login" element={ <Login/>} />

        <Route path="/registration" element={ <Register/>} />

        <Route path="/medicine" element= { 
          <PrivateRoute> 
            <Medicines /> 
          </PrivateRoute>
        } />
        <Route path="/hospital" element= { 
          <PrivateRoute> 
            <Hospitals /> 
          </PrivateRoute> 
        } />
        <Route path="/cart" element= { 
          <PrivateRoute> 
            <Cart /> 
          </PrivateRoute> 
        } />
        <Route path="/medicine/:id" element = { <Medicine/> } />
        <Route path="/medicine/add" element = { <FormMedicine/> } />
        <Route path="/medicine/edit" element = { <EditMedicine/> } />
        <Route path="/hospital/:id" element = { <Hospital/> } />
        <Route path="/hospital/add" element = { <FormHospital/> } />
        <Route path="/hospital/edit" element = { <EditHospital/> } />
      </Routes>
    </div>
  );
}

export default App;
