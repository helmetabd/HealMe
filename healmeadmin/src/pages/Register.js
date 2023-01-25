import React, { useState, useEffect } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import logo from "../img/logo5.PNG"

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
            <div className="row">
                <div className="col-md-6 offset-md-3 insert">
                    <h2 className="text-center text-dark mt-5">Register Page</h2>
                    <div className="card my-5">

                        <form className="card-body cardbody-color p-lg-5">

                            <div className="text-center">
                                <img src={logo}
                                     className="img-fluid profile-image-pic img-thumbnail rounded-circle my-3"
                                     width="200px" alt="profile"/>
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
                                        className="btn btn-warning btn-color px-5 mb-5 w-100">Register
                                </button>
                            </div>
                        </form>
                        <div id="emailHelp" className="form-text text-center mb-5 text-dark">Have an account? <Link className="nav-link active link-dark" aria-current="page" to="/login">Login</Link></div>
                    </div>
                </div>
            </div>
        </div>
  )
}

export default Register