import React from 'react';
import { Link } from 'react-router-dom';
import { logout } from "../redux/user";

function Sidebar() {
  return (
    <nav className="navbar navbar-expand-sm bg-dark navbar-dark" style={{ width: "100%" }}>
      <ul className="navbar-nav">
        <li className="nav-item">
          <Link to="/eventpage" className="nav-link">Details</Link>
        </li>
        <li className="nav-item">
          <Link to="/eventdash" className="nav-link">Sign up Forum</Link>
        </li>

      </ul>
    </nav>
  )
}

export default Sidebar