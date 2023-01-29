import React, { useState } from 'react'
import { Link, useLocation, NavLink } from 'react-router-dom'
import logo from "../img/Picture1.png"

function NavBar() {
    
    const location = useLocation();

    const [isActive, setIsActive] = useState()

  return (
    <nav className="navbar navbar-expand-lg bg-light sticky-top navigate">
        <div className="container-fluid">
            <div className="collapse navbar-collapse" id="navbarSupportedContent">
                <div className='borderLogo justify-content-center'>
                    <a class="navbar-brand d-flex flex-row align-items-center p-0 " href="#">
                        <img src={logo} alt="Logo" width="50" height="50" class="d-inline-block align-text-center"/>
                        <p className='text-center' style={{color: '#5f96d7', fontSize: 14, marginBottom: 0}}>Heal<span style={{color: '#a0a7b5'}}>Me</span><br/>Admin</p>
                    </a>
                </div>
                <ul className="navbar-nav nav nav-pills me-auto mb-2 mb-lg-0 ">
                    <li className="nav-item m-1">
                        <NavLink className={({isActive}) => (isActive ? "nav-link bg-primary" : "nav-link")} aria-current="page" to="/">Home</NavLink>
                    </li>
                    <li className="nav-item m-1">
                        <NavLink className={({isActive}) => (isActive ? "nav-link bg-primary" : "nav-link")} aria-current="page" to="/medicine">Medicines</NavLink>
                    </li>
                    <li className="nav-item m-1">
                        <NavLink className={({isActive}) => (isActive ? "nav-link bg-primary" : "nav-link")} aria-current="page" to="/hospital">Hospitals</NavLink>
                    </li>
                    {/* <li className="nav-item m-1">
                        <NavLink className={({isActive}) => (isActive ? "nav-link bg-primary" : "nav-link")} aria-current="page" to="/cart">Cart</NavLink>
                    </li> */}
                </ul>
                <ul className="navbar-nav nav nav-pills justify-content-end mb-2 mb-lg-0 ">
                    <li className="nav-item m-1">
                        {(!localStorage.getItem('Authorization')) 
                        ? <NavLink className={({isActive}) => (isActive ? "nav-link bg-secondary" : "nav-link")} aria-current="page" to="/login">Login</NavLink> 
                        : <NavLink className="nav-link bg-secondary" aria-current="page" to="" onClick={() => localStorage.removeItem("Authorization")}>Logout</NavLink>}
                    </li>    
                </ul>
            {/* <form className="d-flex" role="search">
                <input className="form-control me-2" type="search" placeholder="Search" aria-label="Search" />
                <button className="btn btn-outline-success" type="submit">Search</button>
            </form> */}
            </div>
        </div>
        </nav>
  )
}

export default NavBar