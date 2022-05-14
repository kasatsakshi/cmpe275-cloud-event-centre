import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import { login } from "../redux/user";
import './Signup.css';

const Error = styled.span`
  color: red;
`;

function Signup() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const dispatch = useDispatch();
  const { isFetching, error } = useSelector((state) => state.user);
  console.log(error);

  const handleClick = (e) => {
    e.preventDefault();
    login(dispatch, { email, password });
  };

  return (
    <div class="container" id="container" style={{ width: "800px" }}>
      <div class="form-container sign-in-container">
        <form action="#">
          <h1>Sign in</h1>
          <div class="social-container">
            <a class="btn btn-lg btn-google btn-block text-uppercase btn-outline" href="#">
              <img src="https://img.icons8.com/color/16/000000/google-logo.png" />
            </a>
          </div>
          <span>or use your account</span>
          <input type="email" placeholder="Email" onChange={(e) => setEmail(e.target.value)} />
          <input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
          {/* <a href="#">Forgot your password?</a> */}
          <button onClick={handleClick} disabled={isFetching}>Sign In</button>
          {error && <Error>Something went wrong! Try again</Error>}
        </form>
      </div>
      <div class="form-container sign-up-container">
        <form>
          {/* <div class="overlay-panel overlay-right"> */}
          <h1>Hello, are you new here!</h1>
          <p>Enter your personal details and start journey with us</p>
          <Link to='/register'><button id="signUp">Sign Up</button></Link>
          <div class="or-container">
            <div class="line-separator"></div>
            <div class="or-label">or sign up with google</div>
            <div class="line-separator"></div>
          </div>
          {/* </div> */}
          <div class="social-container">
            <a class="btn btn-lg btn-google btn-block text-uppercase btn-outline" href="#">
              <img src="https://img.icons8.com/color/16/000000/google-logo.png" />
            </a>
          </div>
        </form>
      </div>
    </div>
  )
}

export default Signup