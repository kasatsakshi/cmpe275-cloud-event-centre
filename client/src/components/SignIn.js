import React, { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import styled from "styled-components";
import { useDispatch, useSelector } from "react-redux";
import { login } from "../redux/user";
import jwt_decode from "jwt-decode";
import { GoogleLogin } from "@react-oauth/google";
import "./SignIn.css";
import { clearErrorMessage } from "../redux/userRedux";

const Error = styled.span`
  color: red;
`;

function SignIn() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [showError, setShowError] = useState(false);
  const [errorMsg, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  let { isFetching, error, errorMessage, currentUser } = useSelector(
    (state) => state.user
  );

  useEffect(() => {
    setErrorMessage(errorMessage);
  }, [error, errorMessage]);

  const handleSuccess = (googleData) => {
    console.log(googleData.credential);
    const decoded = jwt_decode(googleData.credential);
    console.log(decoded);
    const userProfile = decoded;

    setEmail(userProfile.email);
    console.log(userProfile, userProfile.email);
    login(dispatch, {
      email: userProfile.email,
      password: userProfile.sub,
      provider: "google",
    });
  };

  const handleGoogleSignUp = (googleData) => {
    console.log(googleData.credential);
    const userProfile = jwt_decode(googleData.credential);
    console.log(userProfile);

    setEmail(userProfile.email);
    console.log(userProfile, userProfile.email);
    login(dispatch, {
      email: userProfile.email,
      password: userProfile.sub,
      provider: "google",
    });

    setTimeout(() => {
      if (!currentUser) {
        dispatch(clearErrorMessage());
        navigate(`/register`, { state: userProfile });
      }
    }, 2000);
  };

  const handleFailure = async (googleData) => {
    alert("Google login failed. Please try again.");
    console.log("Login failed: ", googleData);
  };

  const handleClick = (e) => {
    e.preventDefault();
    dispatch(clearErrorMessage());
    login(dispatch, { email, password, provider: "local" });
    setTimeout(() => {
      if (!currentUser) {
        setShowError(true);
      }
    }, 1000);
  };

  const loginComponent = (
    <div className="form-container sign-in-container">
      <form onSubmit={handleClick}>
        <h2>Sign in</h2>
        <input
          style={{ margin: "10px" }}
          type="email"
          placeholder="Email"
          required
          autoFocus
          onChange={(e) => {
            setEmail(e.target.value);
            setShowError(false);
          }}
        />
        <input
          style={{ margin: "10px" }}
          type="password"
          placeholder="Password"
          required
          onChange={(e) => {
            setPassword(e.target.value);
            setShowError(false);
          }}
        />
        {showError ? (
          <Error style={{ fontSize: "1em", margin: "5px" }}>{errorMsg}</Error>
        ) : (
          <div></div>
        )}
        <div style={{ display: "flex", flexDirection: "column" }}>
          <button type="submit">Sign In</button>

          <div className="or-container">
            <div className="line-separator"></div>
            <div className="or-label">or</div>
            <div className="line-separator"></div>
          </div>

          <div className="social-container">
            <GoogleLogin
              onSuccess={handleSuccess}
              onError={handleFailure}
              theme="filled_blue"
              text="signin_with"
              width="100px"
              shape="pill"
            />
          </div>
        </div>
      </form>
    </div>
  );

  const signupComponent = (
    <div className="form-container sign-up-container">
      <form>
        <h2>Hello,</h2>
        <h3>Are you new here?</h3>
        <p>Enter your personal details and start your journey with us</p>
        <div style={{ display: "flex", flexDirection: "column" }}>
          <Link to="/register">
            <button id="signUp">Sign Up</button>
          </Link>

          <div className="or-container">
            <div className="line-separator"></div>
            <div className="or-label">or</div>
            <div className="line-separator"></div>
          </div>

          <div className="social-container">
            <GoogleLogin
              onSuccess={handleGoogleSignUp}
              onError={handleFailure}
              text="signup_with"
              theme="filled_blue"
              width="100px"
              shape="pill"
            />
          </div>
        </div>
      </form>
    </div>
  );

  return (
    <div
      className="container"
      id="container"
      style={{ marginTop: "50px", padding: "10px" }}
    >
      <div
        className="cec-header"
        style={{
          textAlign: "center",
          color: "#ff4b2bed",
          fontFamily: "monospace",
        }}
      >
        <h1>Cloud Event Centre</h1>
        <div className="line-separator"></div>
      </div>
      <div
        className="d-flex flex-sm-wrap forms"
        style={{
          margin: "10px",
        }}
      >
        {loginComponent}
        {signupComponent}
      </div>
    </div>
  );
}

export default SignIn;
