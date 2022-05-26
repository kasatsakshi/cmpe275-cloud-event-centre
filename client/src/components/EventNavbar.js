import React, { useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./temp.css";
import { useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { logout } from "../redux/user";
import eventLogo from "../eventcloudlogo.png";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DatePicker } from "@mui/x-date-pickers/DatePicker";
import TextField from "@mui/material/TextField";
import axios from "axios";
import moment from "moment";
import { AccountCircle } from "@mui/icons-material";

function EventNavbar() {
  const dispatch = useDispatch();
  const [mimicDate, setMimicDate] = React.useState(null);
  const { currentUser } = useSelector((state) => state.user);
  useEffect(() => {
    axios
      .get("/api/time")
      .then((res) => {
        // console.log(res.data);
        setMimicDate(moment(res.data).format("YYYY-MM-DD HH:mm:ss"));
      })
      .catch((err) => {
        console.log(err);
      });
  }, [mimicDate]);

  const mimicTime = (newValue) => {
    console.log(moment(newValue).format("YYYY-MM-DD HH:mm:ss"));
    axios
      .post("/api/time", null, {
        params: {
          dateTime: moment(newValue).format("YYYY-MM-DD HH:mm:ss"),
        },
      })
      .then((res) => {
        if (res.status === 200) {
          setMimicDate(newValue);
        }
      })
      .catch((err) => {
        console.log(err);
      });
  };

  return (
    // <nav
    //   className="navbar navbar-expand-sm "
    //   style={{
    //     width: "100%",
    //     margin: "0px",
    //     backgroundColor: "#e75d45",
    //     color: "white",
    //   }}
    // >
    <div
      className="navbar navbar-expand-sm "
      style={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        width: "100%",
        margin: "0px",
        backgroundColor: "#e75d45",
        color: "white",
      }}
    >
      <nav>
        <ul className="navbar-nav" style={{ color: "white" }}>
          <li className="nav-item nav-logo">
            <Link to="/" className="nav-link">
              <img className="navbar-logo" src={eventLogo} alt="Events" />
            </Link>
          </li>
          <li className="nav-item">
            <Link
              to="/event-dashboard"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
            >
              Home
            </Link>
          </li>
          <li className="nav-item">
            <Link
              to="/create-event"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
            >
              Create Event
            </Link>
          </li>
          <li className="nav-item">
            <Link
              to="/system-report"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
            >
              System Report
            </Link>
          </li>
          <li className="nav-item">
            <Link
              to="/user-report"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
            >
              User Report
            </Link>
          </li>
        </ul>
      </nav>
      <nav>
        <ul className="navbar-nav" style={{ color: "white" }}>
          <li className="nav-item nav-date">
            <LocalizationProvider dateAdapter={AdapterDateFns}>
              <DatePicker
                label="System Time"
                value={mimicDate}
                color="primary"
                onChange={(newValue) => {
                  mimicTime(newValue);
                }}
                renderInput={(params) => <TextField {...params} />}
              />
            </LocalizationProvider>
          </li>
          <li>
            <Link
              to="/account"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
            >
              <AccountCircle
                sx={{
                  marginLeft: "10px",
                  marginRight: "10px",
                }}
                className="nav-accountCircle"
              />
              {currentUser.screenName}
            </Link>
          </li>
          <li
            className="nav-item"
          // style={{ padding: "auto", marginLeft: "15px" }}
          >
            <Link
              to="/"
              className="nav-link"
              style={{ height: "100%", margin: "10px", color: "white" }}
              onClick={() => logout(dispatch)}
            >
              Logout
            </Link>
          </li>
        </ul>
      </nav>
    </div>
  );
}

export default EventNavbar;
