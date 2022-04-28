import React from 'react';
import { Link } from 'react-router-dom';
import './Signup.css';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';

function Signup() {
  return (
    <div>
      <div className='leftdiv'>
        <div className='leftdiv-content'>
          <Link to="/">
            <img className='logo' src='eventcloudlogo.png' alt='Events' />
          </Link>
          <h1 className='leftdiv-text'>Create an account</h1>
          <form className='form'>
            <input className='email-input' placeholder='Email Address' />
            <button className='email-button'>Continue</button>
          </form>
          <div className='divider'>or</div>
          <button className='signin-google-button'>Sign in with Google</button>
        </div>
      </div>
      <div className='rightdiv'></div>
    </div>
  )
}

export default Signup