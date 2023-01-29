import React from 'react'
import { Link } from 'react-router-dom'

const Home = () => {
  return (
    <section id="hero" class="d-flex align-items-center">
      <div class="container">
        <h1>Welcome to HealMe Admin</h1>
        <h2>We are here to make our user happy, remember your deadline or no payment for you</h2>
        <h2 className='mt-3'>Let's get started</h2>
        <div className='d-flex flex-row justify-content-center'>
          <a href="/hospital" class="btn-get-started scrollto mx-1" style={{textDecoration: "none"}}>
            <i class="fa-regular fa-hospital"></i>
            <span className='mx-1'>Hospital</span>
          </a>
          <a href="/medicine" class="btn-get-started scrollto mx-1" style={{textDecoration: "none"}}>
            <i class="fa-solid fa-pills"></i>
            <span className='mx-1'>Medicine</span>
          </a>
        </div>
      </div>
    </section>
  )
}

export default Home