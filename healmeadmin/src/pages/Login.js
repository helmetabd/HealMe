import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import logo from "../img/Picture1.png"

function Login() {
  const navigate = useNavigate();
  const location = useLocation();
  const [ userLogin, setUserLogin ] = useState({ username: '', password: ''});

  const handleOnChange = (e) => {
    setUserLogin(state => {
        return { ...state, [e.target.id]: e.target.value }
    })
  }

  const signIn = (e) => {
    e.preventDefault();
    fetch(`http://localhost:8083/api/authenticate`, { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            }, 
            body: JSON.stringify(userLogin)
    })
    .then(response => {
        if (response.status === 200) {
            return response.json();
        }
    })
    .then(data => {
        // console.log(data);
        let dataToken = "Bearer " + data.token;
        localStorage.setItem("Authorization", dataToken);
        if (location.state) {
            navigate(`${location.state.from.pathname}`)
        } else {
            navigate('/');
        }
    })
    .catch(err => {
        console.log(err);
    })
  }

  return (
    <div className="container">
        <div className="row justify-content-center">
            <div className="col-md-6 insertCard">
                <h2 className="text-center text-dark mt-4">Login Page</h2>
                <div className="card border-light my-3">
                    <form className="card-body cardbody-color p-lg-3">
                        <div className="d-flex flex-row align-items-center justify-content-center me-5">
                            <img src={logo}
                                className="img-fluid profile-image-pic img-thumbnail my-3 border-light"
                                width="120px" alt="profile"/>
                            <p className='text-center' style={{color: '#5f96d7', fontSize: 25, marginBottom: 0}}>Heal<span style={{color: '#a0a7b5'}}>Me</span><br/>Admin</p>
                        </div>
                        <div className="mb-3">
                            <input type="text" className="form-control" id="username" aria-describedby="emailHelp" placeholder='username'
                                onChange={handleOnChange} value={userLogin.username}/>
                        </div>
                        <div className="mb-3">
                            <input type="password" className="form-control" id="password" placeholder="password"
                                onChange={handleOnChange} value={userLogin.password}/>
                        </div>
                        <div className="text-center">
                            <button type="submit" onClick={signIn}
                                className="btn btn-warning btn-color px-5 mb-4 w-auto">Login
                            </button>
                        </div>
                    </form>
                    <div id="emailHelp" className="form-text text-center mb-3 text-dark">Not Registered? <Link className="nav-link active link-dark" aria-current="page" to="/registration">Sign Up</Link></div>
                </div>
            </div>
        </div>
    </div>
    // <a class="navbar-brand d-flex flex-row align-items-center p-0 " href="#">
    // <img src={logo} alt="Logo" width="50" height="50" class="d-inline-block align-text-center"/>
    // <p className='text-center' style={{color: '#5f96d7', fontSize: 14, marginBottom: 0}}>Heal<span style={{color: '#a0a7b5'}}>Me</span><br/>Admin</p>
    // </a>
  )
}

export default Login