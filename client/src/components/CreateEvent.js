import React from 'react';
import './Register.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import TextField from '@mui/material/TextField';
import { useState, useEffect } from "react";

function CreateEvent() {

  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [deadline, setDeadline] = useState("");

  const assignFromDate = e => {
    console.log(e.target.value);
    setFromDate(e.target.value);
  };

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
            placeholder='Event'
            className='register-input-fields'
          />
          <TextField
            required
            id="name"
            placeholder='Description'
            className='register-input-fields'
          />
          <div className="col-sm-4">
        <div className="form-group">
          <span style={{ opacity: "0.6", fontSize: "13px" }}>from</span>
          <input
            type="date"
            name="from"
            id="startdate"
            min={new Date().toLocaleDateString('en-ca')}
            value={fromDate}
            onChange={assignFromDate}
            className="form-control datepicker"
            style={{ width: "150px" }}
          />
        </div>
      </div>
      <div className="col-sm-4">
        <div className="form-group">
          <span style={{ opacity: "0.6", fontSize: "13px" }}>to</span>
          <input
            type="date"
            name="to"
            min={fromDate}
            id="enddate"
            value={toDate}
            placeholder="Select Date"
            onChange={e => setToDate(e.target.value)}
            className="form-control datepicker"
            style={{ width: "150px" }}
          />
        </div>
      </div>

      <div className="col-sm-4">
        <div className="form-group">
          <span style={{ opacity: "0.6", fontSize: "13px" }}>to</span>
          <input
            type="date"
            name="dadline"
            min={new Date().toLocaleDateString('en-ca')}
            max = {fromDate}
            id="deadline"
            value={deadline}
            placeholder="Select Date"
            onChange={e => setDeadline(e.target.value)}
            className="form-control datepicker"
            style={{ width: "150px" }}
          />
        </div>
      </div>

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
          <TextField
            required
            id="minPart"
            placeholder='Min Participants'
            className='register-input-fields'
          />
          <TextField
            required
            id="maxPart"
            placeholder='Max Particpants'
            className='register-input-fields'
          />
          <button className='register-button'>Create Event</button>
        </form>
      </div>
    </div>
  )
}

export default CreateEvent;