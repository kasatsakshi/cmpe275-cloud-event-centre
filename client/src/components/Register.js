import React from 'react';
import './Register.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import TextField from '@mui/material/TextField';

function Register() {
  return (
    <div class="container" id="container">
      <div className='form-container register-container'>
        <form action="#">
          <h1>Register Details</h1>
          <div className='register-radio'>
            {/* <FormLabel className='register-label' id="account-type">Account Type</FormLabel> */}
            <RadioGroup
              row
              aria-labelledby="account-type"
              name="account-type-buttons"
            >
              <FormControlLabel value="person" control={<Radio />} label="Person" />
              <FormControlLabel value="organization" control={<Radio />} label="Organization" />
            </RadioGroup>
          </div>
          <TextField
            required
            id="name"
            label="Required"
            defaultValue='Name'
            className='register-input-fields'
          />
          <div className='register-radio'>
            <FormLabel>Gender</FormLabel>
            <RadioGroup
              row
              aria-labelledby="demo-row-radio-buttons-group-label"
              name="row-radio-buttons-group"
            >
              <FormControlLabel value="female" control={<Radio />} label="Female" />
              <FormControlLabel value="male" control={<Radio />} label="Male" />
              <FormControlLabel value="other" control={<Radio />} label="Other" />
            </RadioGroup>
          </div>
          <TextField
            required
            id="name"
            placeholder='Screen Name'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='Description'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='Address1'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='City'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='State'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='Zip'
            className='register-input-fields'
          />
          <button className='register-button'>Register</button>
        </form>
      </div>
    </div>
  )
}

export default Register