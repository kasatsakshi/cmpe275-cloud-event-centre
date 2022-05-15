import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "./temp.css";
import { useDispatch } from "react-redux";
import { Link } from "react-router-dom";
import { logout } from "../redux/user";
import eventLogo from "../eventcloudlogo.png";

function EventNavbar() {
  const dispatch = useDispatch();
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
      </ul>
    </nav>
  );
}

export default EventNavbar;
