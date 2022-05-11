import React, { useState } from 'react';
import FormLabel from '@mui/material/FormLabel';
import TextField from '@mui/material/TextField';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import axios from 'axios';
import './Register.css';

function Register() {

  const [fullName, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [screenName, setScreenName] = useState('');
  const [gender, setGender] = useState('');
  const [accountType, setAccountType] = useState('');
  const [description, setDescription] = useState('');
  const [street, setStreet] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [zip, setZip] = useState('');

  const handleClick = (e) => {
    e.preventDefault();
    axios.post(`/api/user`, null, {
      params: {
        fullName,
        email,
        password,
        screenName,
        gender,
        accountType,
        description,
        street,
        city,
        state,
        zip
      }
    })
      .then(response => response.status)
      .catch(err => console.warn(err));
    alert("user registered");
  };

  return (

    <div className='register-container'>
      <form action="#">
        <h1>Register Details</h1>
        <div className='register-radio'>
          {/* <FormLabel className='register-label' id="account-type">Account Type</FormLabel> */}
          <RadioGroup
            row
            aria-labelledby="account-type"
            name="account-type-buttons"
          >
            <FormControlLabel onClick={(e) => setAccountType('PERSON')} value="person" control={<Radio />} label="Person" />
            <FormControlLabel onClick={(e) => setAccountType('ORGANISATION')} value="organization" control={<Radio />} label="Organization" />
          </RadioGroup>
        </div>
        <TextField
          required
          id="name"
          placeholder='Enter your full name'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setName(e.target.value)}
        />
        <TextField
          required
          id="email"
          placeholder='email'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setEmail(e.target.value)}
        />
        <TextField
          required
          id="password"
          placeholder='password'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setPassword(e.target.value)}
        />
        <div className='register-radio'>
          <FormLabel>Gender</FormLabel>
          <RadioGroup
            row
            aria-labelledby="demo-row-radio-buttons-group-label"
            name="row-radio-buttons-group"
          >
            <FormControlLabel onClick={(e) => setGender('female')} value="female" control={<Radio />} label="Female" />
            <FormControlLabel onClick={(e) => setGender('male')} value="male" control={<Radio />} label="Male" />
            <FormControlLabel onClick={(e) => setGender('other')} value="other" control={<Radio />} label="Other" />
          </RadioGroup>
        </div>
        <TextField
          required
          id="ScreenName"
          placeholder='Screen Name'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setScreenName(e.target.value)}
        />
        <TextField
          required
          id="description"
          placeholder='Description'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setDescription(e.target.value)}
        />
        <TextField
          required
          id="street"
          placeholder='Street'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setStreet(e.target.value)}
        />
        <TextField
          required
          id="city"
          placeholder='City'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setCity(e.target.value)}
        />
        <TextField
          required
          id="state"
          placeholder='State'
          className='register-input-fields'
          style={{ paddingBottom: 20 }}
          onChange={(e) => setState(e.target.value)}
        />
        <TextField
          required
          id="zip"
          placeholder='Zip'
          className='register-input-fields'
          onChange={(e) => setZip(e.target.value)}
        />
        <button onClick={handleClick} className='register-button'>Register</button>
      </form>
    </div>
  )
}

export default Register