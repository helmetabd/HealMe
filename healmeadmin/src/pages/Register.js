import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import logo from "../img/Picture1.png"

function Register() {
  const navigate = useNavigate();
  const [ userRegister, setUserRegister ] = useState({ username: '', password: '', name: ''});

  const handleOnChange = (e) => {
    setUserRegister(state => {
        return { ...state, [e.target.id]: e.target.value }
    })
  }

  const signUp = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8083/api/register`, { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }, 
            body: JSON.stringify(userRegister)
    })
    .then(response => {
        if (response.status === 200) {
            return response;
        }
    })
    .then(data => {
        console.log(data);
        alert("User Register Success, Response from server: " + data);
        navigate("/login");
    })
    .catch(err => {
        console.log(err);
    })
  }

  return (
    <div className="container">
            <div className="row justify-content-center">
            <div className="col-md-6 insertCard">
                    <h2 className="text-center text-dark mt-4">Register Page</h2>
                    <div className="card border-light my-2">
                        <form className="card-body cardbody-color p-lg-2">
                            <div className="d-flex flex-row align-items-center justify-content-center me-5">
                                <img src={logo}
                                    className="img-fluid profile-image-pic img-thumbnail my-3 border-light"
                                    width="115px" alt="profile"/>
                                <p className='text-center' style={{color: '#5f96d7', fontSize: 24, marginBottom: 0}}>Heal<span style={{color: '#a0a7b5'}}>Me</span><br/>Admin</p>
                            </div>
                            <div className="mb-3">
                                <input type="text" className="form-control" id="username" aria-describedby="emailHelp" placeholder='username'
                                       onChange={handleOnChange} value={userRegister.username}/>
                            </div>
                            <div className="mb-3">
                                <input type="password" className="form-control" id="password" placeholder="password"
                                       onChange={handleOnChange} value={userRegister.password}/>
                            </div>
                            <div className="mb-3">
                                <input type="text" className="form-control" id="name" placeholder="name"
                                       onChange={handleOnChange} value={userRegister.name}/>
                            </div>
                            <div className="text-center">
                                <button type="submit" onClick={signUp}
                                        className="btn btn-warning btn-color px-5 mb-3 w-auto">Register
                                </button>
                            </div>
                        </form>
                        <div id="emailHelp" className="form-text text-center mb-2 text-dark">Have an account? <Link className="nav-link active link-dark" aria-current="page" to="/login">Login</Link></div>
                    </div>
                </div>
            </div>
        </div>
  )
}

export default Register