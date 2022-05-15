import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./temp.css";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { logout } from "../redux/user";
import eventLogo from "../eventcloudlogo.png";
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import TextField from '@mui/material/TextField';
import axios from "axios";
import moment from "moment";
import { AccountCircle } from '@mui/icons-material';

function EventNavbar() {
  const dispatch = useDispatch();
  const [mimicDate, setMimicDate] = React.useState(null);

  const mimicTime = (newValue) => {
    setMimicDate(newValue);
    console.log(newValue);
    console.log(moment(newValue).format('YYYY-MM-DD HH:mm:ss'))
    axios.post('/api/time', null, {
      params: {
        dateTime: moment(newValue).format('YYYY-MM-DD HH:mm:ss.SSSSSS')
      }
    })
  }

  return (
    <nav
      className="navbar navbar-expand-sm bg-dark navbar-dark"
      style={{ width: "100%", margin: "0px" }}
    >
      <ul className="navbar-nav">
        <li className="nav-item">
          <Link to="/" className="nav-link">
            <img className="navbar-logo" src={eventLogo} alt="Events" />
          </Link>
        </li>
        <li className="nav-item">
          <Link
            to="/event-dashboard"
            className="nav-link"
            style={{ height: "100%", margin: "10px" }}
          >
            Home
          </Link>
        </li>
        <li className="nav-item">
          <Link
            to="/create-event"
            className="nav-link"
            style={{ height: "100%", margin: "10px" }}
          >
            Create Event
          </Link>
        </li>
        <li className="nav-item">
          {/* <Link to ="/rest_login" className="nav-link">Logout</Link> */}
          <Link
            to="/"
            className="nav-link"
            style={{ height: "100%", margin: "10px" }}
            onClick={() => logout(dispatch)}
          >
            Logout
          </Link>
        </li>
        <li className="nav-item nav-date">
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DatePicker
              label="Mimic time"
              value={mimicDate}
              color="primary"
              onChange={(newValue) => {
                mimicTime(newValue)
              }}
              renderInput={(params) => <TextField {...params} />}
            />
          </LocalizationProvider>
        </li>
        <li>
          <Link to="/account" className="nav-link"
            style={{ height: "100%" }}><AccountCircle sx={{ width: 56, height: 40 }} className="nav-accountCircle" /></Link>
        </li>
      </ul>
    </nav>
  );
}

export default EventNavbar;
