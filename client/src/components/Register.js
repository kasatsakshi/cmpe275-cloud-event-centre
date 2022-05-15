import React, { useEffect, useState } from "react";
import FormLabel from "@mui/material/FormLabel";
import TextField from "@mui/material/TextField";
import Radio from "@mui/material/Radio";
import RadioGroup from "@mui/material/RadioGroup";
import FormControlLabel from "@mui/material/FormControlLabel";
import styled from "styled-components";
import { useLocation, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import "./Register.css";
import { signup } from "../redux/user";

const Error = styled.span`
  color: red;
`;

function Register() {
  const [fullName, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [screenName, setScreenName] = useState("");
  const [gender, setGender] = useState("");
  const [accountType, setAccountType] = useState("");
  const [description, setDescription] = useState("");
  const [street, setStreet] = useState("");
  const [city, setCity] = useState("");
  const [state, setState] = useState("");
  const [zip, setZip] = useState("");
  const [provider, setProvider] = useState("");
  const [showError, setShowError] = useState(false);
  const [errorMsg, setErrorMessage] = useState("");
  const location = useLocation();
  const dispatch = useDispatch();
  const navigate = useNavigate();
  const { isFetching, error, errorMessage } = useSelector(
    (state) => state.user
  );

  const userProfile = location.state;

  useEffect(() => {
    setErrorMessage(errorMessage);
    if (userProfile) {
      setEmail(userProfile.email);
      setName(userProfile.name);
      setPassword(userProfile.sub);
      setProvider("google");
    } else {
      setEmail("");
      setName("");
      setPassword("");
      setScreenName("");
      setDescription("");
      setStreet("");
      setCity("");
      setState("");
      setZip("");
      setProvider("local");
    }
  }, [error, errorMessage]);

  const handleClick = (e) => {
    e.preventDefault();
    signup(dispatch, {
      fullName,
      email,
      password,
      screenName,
      gender,
      accountType,
      description,
      street,
      city,
      state,
      zip,
      provider,
    });
    setTimeout(() => {
      if (error) {
        setShowError(true);
      } else {
        navigate(`/verify-account`);
      }
    }, 2000);
  };
  return (
    <div
      className="register-container"
      style={{
        margin: "auto",
        marginTop: "25px",
        marginBottom: "25px",
        padding: "10px",
      }}
    >
      <form onSubmit={handleClick}>
        <h1>Create account</h1>
        <div className="register-radio">
          <RadioGroup
            row
            aria-labelledby="account-type"
            name="account-type-buttons"
          >
            <FormControlLabel
              onClick={(e) => setAccountType("PERSON")}
              value="person"
              control={<Radio />}
              label="Person"
            />
            <FormControlLabel
              onClick={(e) => setAccountType("ORGANISATION")}
              value="organization"
              control={<Radio />}
              label="Organization"
            />
          </RadioGroup>
        </div>
        <TextField
          required
          label="Full Name"
          id="name"
          placeholder="Enter your full name"
          className="register-input-fields"
          value={fullName}
          style={{ paddingBottom: 20 }}
          onChange={(e) => setName(e.target.value)}
        />
        <TextField
          required
          label="Email"
          id="email"
          placeholder="email"
          className="register-input-fields"
          value={email}
          style={{ paddingBottom: 20 }}
          onChange={(e) => {
            setEmail(e.target.value);
            setErrorMessage("");
            setShowError(false);
          }}
        />
        {provider == "local" ? (
          <TextField
            required
            label="Password"
            id="password"
            placeholder="password"
            type="password"
            value={password}
            className="register-input-fields"
            style={{ paddingBottom: 20 }}
            onChange={(e) => setPassword(e.target.value)}
          />
        ) : (
          <div></div>
        )}

        {accountType != "ORGANISATION" ? (
          <div className="register-radio">
            <FormLabel>Gender</FormLabel>
            <RadioGroup
              row
              aria-labelledby="demo-row-radio-buttons-group-label"
              name="row-radio-buttons-group"
            >
              <FormControlLabel
                onClick={(e) => setGender("female")}
                value="female"
                control={<Radio />}
                label="Female"
              />
              <FormControlLabel
                onClick={(e) => setGender("male")}
                value="male"
                control={<Radio />}
                label="Male"
              />
              <FormControlLabel
                onClick={(e) => setGender("other")}
                value="other"
                control={<Radio />}
                label="Other"
              />
            </RadioGroup>
          </div>
        ) : (
          <div></div>
        )}
        <TextField
          required
          label="Screen Name"
          id="ScreenName"
          placeholder="Screen Name"
          className="register-input-fields"
          value={screenName}
          style={{ paddingBottom: 20 }}
          onChange={(e) => setScreenName(e.target.value)}
        />
        <TextField
          label="Description"
          id="description"
          placeholder="Description"
          value={description}
          className="register-input-fields"
          style={{ paddingBottom: 20 }}
          onChange={(e) => setDescription(e.target.value)}
        />
        <TextField
          required
          label="Street"
          id="street"
          placeholder="Street"
          value={street}
          className="register-input-fields"
          style={{ paddingBottom: 20 }}
          onChange={(e) => setStreet(e.target.value)}
        />
        <TextField
          required
          label="City"
          id="city"
          placeholder="City"
          value={city}
          className="register-input-fields"
          style={{ paddingBottom: 20 }}
          onChange={(e) => setCity(e.target.value)}
        />
        <TextField
          required
          label="State"
          id="state"
          placeholder="State"
          value={state}
          className="register-input-fields"
          style={{ paddingBottom: 20 }}
          onChange={(e) => setState(e.target.value)}
        />
        <TextField
          required
          label="Zip Code"
          id="zip"
          placeholder="Zip"
          value={zip}
          className="register-input-fields"
          onChange={(e) => setZip(e.target.value)}
        />
        {showError ? (
          <Error style={{ fontSize: "1em", margin: "5px" }}>{errorMsg}</Error>
        ) : (
          <div></div>
        )}
        <button type="submit" className="register-button">
          Register
        </button>
      </form>
    </div>
  );
}

export default Register;
