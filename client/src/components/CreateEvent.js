import React from "react";
import "./Register.css";
import TextField from "@mui/material/TextField";
import { useState } from "react";
import axios from "axios";
import EventNavbar from "./EventNavbar";
import "./temp.css";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { cities, states } from "../places";

function CreateEvent() {
  const user = useSelector((state) => state.user.currentUser);

  const [fromDate, setFromDate] = useState("");
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    title: "",
    description: "",
    fee: "",
    admissionPolicy: "",
    startTime: "",
    endTime: "",
    deadline: "",
    minimumParticipants: "",
    maximumParticipants: "",
    creatorId: user.id,
    street: "",
    city: "San Jose",
    state: "California",
    zip: "",
    stTime: "",
    enTime: "",
    dTime: "",
  });

  const {
    title,
    description,
    fee,
    admissionPolicy,
    startTime,
    endTime,
    deadline,
    maximumParticipants,
    minimumParticipants,
    creatorId,
    street,
    city,
    state,
    zip,
    stTime,
    enTime,
    dTime,
  } = formData;

  const onChange = (e) =>
    setFormData({ ...formData, [e.target.name]: e.target.value });

  const onSubmit = async (e) => {
    e.preventDefault();
    console.log(stTime);
    console.log(formData);
    //register({email,password,location});
    axios
      .post(`/api/event/create`, {
        title,
        description,
        fee,
        admissionPolicy,
        startTime: startTime + "T" + stTime,
        endTime: endTime + "T" + enTime,
        deadline: deadline + "T" + dTime,
        minimumParticipants,
        maximumParticipants,
        creatorId,
        street,
        city,
        state,
        zip,
      })
      .then((response) => {
        console.log(response.status);
        alert("Event is created!");
        navigate("/event-dashboard");
      })
      .catch((err) => console.warn(err));
  };

  const assignFromDate = (e) => {
    console.log(e.target.value);
    setFromDate(e.target.value);
  };

  return (
    <>
      <EventNavbar />
      <div
        className="container"
        style={{
          width: "50%",
          marginTop: "25px",
          marginBottom: "25px",
          padding: "25px",
        }}
      >
        <div>
          <form onSubmit={(e) => onSubmit(e)}>
            <h2>Create Event</h2>
            <div className="register-radio"></div>

            <TextField
              required
              autoFocus
              id="title"
              label="Title"
              className="register-input-fields"
              placeholder="Event"
              name="title"
              value={title}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />

            <TextField
              id="description"
              placeholder="Description"
              label="Description"
              className="register-input-fields"
              name="description"
              value={description}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />
            <div
              style={{
                width: "100%",
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <span
                style={{
                  width: "20%",
                  opacity: "0.6",
                  fontSize: "13px",
                  margin: "15px",
                }}
              >
                <b>Start Date</b>
              </span>
              <input
                type="date"
                name="startTime"
                id="startTime"
                min={new Date().toLocaleDateString("en-ca")}
                value={startTime}
                onChange={(e) => onChange(e)}
                className="form-control datepicker"
                style={{ margin: "15px" }}
              />
              <input
                type="time"
                name="stTime"
                value={stTime}
                onChange={(e) => onChange(e)}
                style={{ margin: "15px", padding: "6px 12px 6px 12px" }}
              />
            </div>
            <div
              style={{
                width: "100%",
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <span
                style={{
                  width: "20%",
                  opacity: "0.6",
                  fontSize: "13px",
                  margin: "15px",
                }}
              >
                <b>End Date</b>
              </span>
              <input
                type="date"
                name="endTime"
                min={startTime}
                id="endTime"
                value={endTime}
                placeholder="Select Date"
                onChange={(e) => onChange(e)}
                className="form-control datepicker"
                style={{ margin: "15px" }}
              />
              <input
                type="time"
                name="enTime"
                value={enTime}
                onChange={(e) => onChange(e)}
                style={{ margin: "15px", padding: "6px 12px 6px 12px" }}
              />
            </div>
            <div
              style={{
                width: "100%",
                display: "flex",
                justifyContent: "space-between",
              }}
            >
              <span
                style={{
                  width: "20%",
                  opacity: "0.6",
                  fontSize: "13px",
                  margin: "15px",
                }}
              >
                <b>Deadline</b>
              </span>
              <input
                type="date"
                name="deadline"
                min={new Date().toLocaleDateString("en-ca")}
                max={endTime}
                id="deadline"
                value={deadline}
                placeholder="Select Date"
                onChange={(e) => onChange(e)}
                style={{ margin: "15px" }}
                className="form-control datepicker"
              />
              <input
                type="time"
                name="dTime"
                value={dTime}
                onChange={(e) => onChange(e)}
                style={{ margin: "15px", padding: "6px 12px 6px 12px" }}
              />
            </div>
            <TextField
              required
              id="name"
              placeholder="street"
              label="Street"
              className="register-input-fields"
              name="street"
              value={street}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />
            {/* <TextField
              required
              id="city"
              placeholder="City"
              label="City"
              className="register-input-fields"
              name="city"
              value={city}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            /> */}
            {/* <span style={{ opacity: "0.6", fontSize: "13px" }}>City</span> */}
            <select
              className="register-input-fields"
              name="city"
              value={city}
              onChange={(e) => onChange(e)}
            >
              <option value="City" disabled>
                Select City
              </option>
              {cities.map((data, idx) => (
                <option key={idx}>{data}</option>
              ))}
            </select>
            {/* <TextField
              required
              id="name"
              placeholder="State"
              label="State"
              className="register-input-fields"
              name="state"
              value={state}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            /> */}
            {/* <span style={{ opacity: "0.6", fontSize: "13px" }}>State</span> */}
            <select
              className="register-input-fields"
              name="state"
              value={state}
              onChange={(e) => onChange(e)}
            >
              <option value="State" disabled>
                Select State
              </option>
              {states.map((data, idx) => (
                <option key={idx}>{data}</option>
              ))}
            </select>
            <TextField
              required
              id="name"
              placeholder="Zip"
              label="Zip"
              className="register-input-fields"
              name="zip"
              value={zip}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />
            {/* <TextField
            required
            id="creatorId"
            placeholder='Creater Id'
            className='register-input-fields'
            name='creatorId'
            value={creatorId}
            onChange={e => onChange(e)}
          /> */}
            <TextField
              required
              id="minPart"
              placeholder="Min Participants"
              className="register-input-fields"
              label="Minimum Participants"
              name="minimumParticipants"
              value={minimumParticipants}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />
            <TextField
              required
              id="maxPart"
              placeholder="Max Particpants"
              className="register-input-fields"
              name="maximumParticipants"
              label="Maximum Participants"
              value={maximumParticipants}
              onChange={(e) => onChange(e)}
              style={{ margin: "10px" }}
            />
            <TextField
              required
              id="fee"
              placeholder="Fee"
              label="Fee"
              className="register-input-fields"
              name="fee"
              value={fee}
              style={{ margin: "10px" }}
              onChange={(e) => onChange(e)}
            />

            {/* <TextField
            required
            id="admissionPolicy"
            placeholder='Admission Poilicy'
            className='register-input-fields'
            name='admissionPolicy'
            value={admissionPolicy}
            onChange={e => onChange(e)}
          /> */}
            <div>
              <select
                value={admissionPolicy}
                name="admissionPolicy"
                onChange={(e) => onChange(e)}
                style={{ margin: "10px" }}
              >
                <option value="default">Admission Policy</option>
                <option value="FCFS">FCFS</option>
                <option value="APPROVAL">Approval Required</option>
              </select>
            </div>
            <div className="form-group" style={{ margin: "10px" }}>
              <button type="submit" className="form-submit">
                Create Event
              </button>
            </div>
          </form>
        </div>
      </div>
    </>
  );
}

export default CreateEvent;
