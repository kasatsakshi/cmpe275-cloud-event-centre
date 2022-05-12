import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import './temp.css';
import { useSelector, useDispatch } from "react-redux";
import { Link } from 'react-router-dom';
import { logout } from "../redux/user";

function EventNavbar() {

  const dispatch = useDispatch();
  return (
    <nav className="navbar navbar-expand-sm bg-dark navbar-dark" style={{ width: "100%" }}>
      <ul className="navbar-nav">
        <li className="nav-item">
          <Link to="/createevent" className="nav-link">Create Event</Link>
        </li>
        <li className="nav-item">
          <Link to="/eventdash" className="nav-link">Event Dashboard</Link>
        </li>
        <li className="nav-item">
          {/* <Link to ="/rest_login" className="nav-link">Logout</Link> */}
          <Link to="/" className="nav-link" onClick={() => logout(dispatch)}>Logout</Link>
        </li>
      </ul>
    </nav>
  );
}

export default EventNavbar;
