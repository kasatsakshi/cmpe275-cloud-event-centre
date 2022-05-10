import React from 'react';
import './Register.css';
import Radio from '@mui/material/Radio';
import RadioGroup from '@mui/material/RadioGroup';
import FormControlLabel from '@mui/material/FormControlLabel';
import FormControl from '@mui/material/FormControl';
import FormLabel from '@mui/material/FormLabel';
import TextField from '@mui/material/TextField';
import { useState, useEffect } from "react";
import axios from 'axios';
import DateTimePicker from 'react-datetime-picker';

function CreateEvent() {

  const [fromDate, setFromDate] = useState("");
  const [toDate, setToDate] = useState("");
  const [deadlinee, setDeadline] = useState("");
  const [value,setValue]= useState(new Date());

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    fee: '',
    admissionPolicy: '',
    startTime: '',
    endTime: '',
    deadline: '',
    minimumParticipants: '',
    maximumParticipants: '',
    creatorId: '',
    street: '',
    city: '',
    state: '',
    zip: '',
    stTime:'',
    enTime:'',
    dTime:''
  });

  const { title, description, fee, admissionPolicy, startTime, endTime, deadline, maximumParticipants, minimumParticipants, creatorId, street, city, state, zip,stTime,enTime,dTime } = formData;

  const onChange = e => setFormData({ ...formData, [e.target.name]: e.target.value });

  const onSubmit = async e => {
    e.preventDefault();
    console.log(stTime);
    console.log(formData);
    //register({email,password,location});
    axios.post(`/api/event`, null, { params: {
      title,
      description,
      fee,
      admissionPolicy,
      startTime:startTime+'T'+stTime,
      endTime:endTime+'T'+enTime,
      deadline: deadline+'T'+dTime,
      minimumParticipants,
      maximumParticipants,
      creatorId,
      street,
      city,
      state,
      zip
    }})
    .then(response => response.status)
    .catch(err => console.warn(err));
    alert("Event is created!");
  }

  const assignFromDate = e => {
    console.log(e.target.value);
    setFromDate(e.target.value);
    
  };

  return (
    <div class="container" id="container" style={{ marginTop: '600px' }}>
      <div className='form-container register-container'>
        <form onSubmit={e => onSubmit(e)}>
          <h1>Create Event</h1>
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
            id="title"
            className='register-input-fields'
            placeholder='Event'
            name='title'
            value={title}
            onChange={e => onChange(e)}
          />

          <TextField
            required
            id="description"
            placeholder='Description'
            className='register-input-fields'
            name='description'
            value={description}
            onChange={e => onChange(e)}
          />


          
              <span style={{ opacity: "0.6", fontSize: "13px", marginTop:"15px"  }}><b>from</b></span>
              <input
                type="date"
                name="startTime"
                id="startTime"
                min={new Date().toLocaleDateString('en-ca')}
                value={startTime}
                onChange={e=>onChange(e)}
                className="form-control datepicker"
                
              />
           
          <input 
            type="time" 
            name="stTime" 
            value={stTime}
            onChange={e=>onChange(e)}
            />

          
              <span style={{ opacity: "0.6", fontSize: "13px", marginTop:"15px"  }}><b>to</b></span>
              <input
                type="date"
                name="endTime"
                min={fromDate}
                id="endTime"
                value={endTime}
                placeholder="Select Date"
                onChange={e => onChange(e)}
                className="form-control datepicker"
              />
            


          <input 
            type="time" 
            name="enTime" 
            value={enTime}
            onChange={e=>onChange(e)}
            />

          
              <span style={{ opacity: "0.6", fontSize: "13px", marginTop:"15px" }}><b>Deadline</b></span>
              <input
                type="date"
                name="deadline"
                min={new Date().toLocaleDateString('en-ca')}
                max={fromDate}
                id="deadline"
                value={deadline}
                placeholder="Select Date"
                onChange={e => onChange(e)}
                className="form-control datepicker"
              />
           

          <input 
            type="time" 
            name="dTime" 
            value={dTime}
            onChange={e=>onChange(e)}
            />

          <TextField
            required
            id="name"
            placeholder='street'
            className='register-input-fields'
            name='street'
            value={street}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="name"
            placeholder='City'
            className='register-input-fields'
            name='city'
            value={city}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="name"
            placeholder='State'
            className='register-input-fields'
            name='state'
            value={state}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="name"
            placeholder='Zip'
            className='register-input-fields'
            name='zip'
            value={zip}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="creatorId"
            placeholder='Creater Id'
            className='register-input-fields'
            name='creatorId'
            value={creatorId}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="minPart"
            placeholder='Min Participants'
            className='register-input-fields'
            name='minimumParticipants'
            value={minimumParticipants}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="maxPart"
            placeholder='Max Particpants'
            className='register-input-fields'
            name='maximumParticipants'
            value={maximumParticipants}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="fee"
            placeholder='Fee'
            className='register-input-fields'
            name='fee'
            value={fee}
            onChange={e => onChange(e)}
          />
          <TextField
            required
            id="admissionPolicy"
            placeholder='Admission Poilicy'
            className='register-input-fields'
            name='admissionPolicy'
            value={admissionPolicy}
            onChange={e => onChange(e)}
          />

          <div className="form-group" >
                    <input type="submit"  className="form-submit"  />
            </div>
        </form>
      </div>
    </div>
  )
}

export default CreateEvent;