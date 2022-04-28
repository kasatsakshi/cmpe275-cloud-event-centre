import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css'

function Navbar() {
  return (
    <nav className='navbar'>
      {/* logo */}
      <Link to="/">
        <img className='navbar-logo' src='eventcloudlogo.png' alt='Events' />
      </Link>
      {/* right section */}
      <div className='navbar-rightSection'>
        <Link to='/login'><button className='button'>Login</button></Link>
        <Link to='/signup'><button className='button'>Signup</button></Link>
      </div>
    </nav >
  )
}

export default Navbar