import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation, Link } from 'react-router-dom';
import logo from "../img/logo5.PNG"

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
        <div className="row">
            <div className="col-md-6 offset-md-3 insert">
                <h2 className="text-center text-dark mt-5">Login Page</h2>
                <div className="card my-5">
                    <form className="card-body cardbody-color p-lg-5">
                        <div className="text-center">
                            <img src={logo}
                                className="img-fluid profile-image-pic img-thumbnail rounded-circle my-3"
                                width="200px" alt="profile"/>
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
                                className="btn btn-warning btn-color px-5 mb-5 w-100">Login
                            </button>
                        </div>
                    </form>
                    <div id="emailHelp" className="form-text text-center mb-5 text-dark">Not Registered? <Link className="nav-link active link-dark" aria-current="page" to="/registration">Sign Up</Link></div>
                </div>
            </div>
        </div>
    </div>
  )
}

export default Login