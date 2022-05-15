import React from "react";
import { Link } from "react-router-dom";
import eventLogo from "../eventcloudlogo.png";

export default function VerifyAccount() {
  return (
    <>
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
        </ul>
      </nav>
      <div style={{ padding: "20px" }}>
        <p style={{ fontSize: "1.5em", padding: "15px" }}>
          We have sent a verification link to your email id. Please verify your
          account to access our events.
        </p>
        <Link to="/">
          <button id="login">Back to Login</button>
        </Link>
      </div>
    </>
  );
}
