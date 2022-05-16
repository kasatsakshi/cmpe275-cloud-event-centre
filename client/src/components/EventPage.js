import React, { useState, useEffect } from "react";
import Alert from "react-bootstrap/Alert";
import axios from "axios";
import "./EventPage.css";
import { useNavigate } from "react-router-dom";
import EventNavbar from "./EventNavbar";
import { useSelector } from "react-redux";
import styled from "styled-components";
import moment from "moment";

const Button = styled.button`
  width: 200px;
  border: none;
  padding: 15px 20px;
  background-color: black;
  color: white;
  cursor: pointer;
  margin-bottom: 10px;
  &:disabled {
    background-color: grey;
    cursor: not-allowed;
  }
`;

function EventPage() {
  const user = useSelector((state) => state.user.currentUser);
  const [loading, setLoading] = useState(false);
  const [event, setEvent] = useState("");
  const [showError, setShowError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [showSuccess, setShowSuccess] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const navigate = useNavigate();

  function getEvent() {
    try {
      setLoading(true);
      axios
        .get("/api/event/" + localStorage.getItem("eventId"))
        .then((response) => {
          console.log(response.data);
          setEvent(response.data);
        });
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  }

  useEffect(() => {
    getEvent();
  }, []);

  const signupEvents = () => {
    //e.preventDefault();
    axios
      .post(`/api/event/register`, null, {
        params: {
          userId: user.id,
          eventId: localStorage.getItem("eventId"),
        },
      })
      .then((response) => {
        if (event.admissionPolicy === "APPROVAL") {
          setShowSuccess(true);
          setSuccessMessage("Your request has been submitted for approval");
        } else {
          setShowSuccess(true);
          setSuccessMessage("Registered successfully!");
        }
        return response.status;
      })
      .catch((err) => {
        setShowError(true);
        setErrorMessage(err.response.data.message);
      });
  };

  return (
    <div>
      <EventNavbar />
      {showError ? (
        <Alert key="danger" variant="danger">
          {errorMessage}
        </Alert>
      ) : (
        <div></div>
      )}
      {showSuccess ? (
        <Alert key="success" variant="success">
          {successMessage}
        </Alert>
      ) : (
        <div></div>
      )}
      {event ? (
        <div className="event-page-container">
          <div className="event-container">
            <h1 className="event-title">{event.title}</h1>
            <div className="event-description">{event.description}</div>
            <div className="event-owner event-text">
              <b>Organized By</b> {event.creator.screenName}
            </div>
            <div
              className="event-date event-text"
              style={{ display: "flex", flexDirection: "column" }}
            >
              <div className="event-text">
                <b>Starts On</b>{" "}
                {moment(event.startTime).format("MM-DD-YYYY HH:mm")}
              </div>
              <div className="event-text">
                <b>Ends on</b>{" "}
                {moment(event.endTime).format("MM-DD-YYYY HH:mm")}
              </div>
              <div className="event-text">
                <b>Registration Deadline</b>{" "}
                {moment(event.deadline).format("MM-DD-YYYY HH:mm")}
              </div>
            </div>
            <div className="event-date">
              <div className="event-start event-text">
                <em>Min participant: </em> {event.minimumParticipants}
              </div>
              <div className="event-text">
                <em>Max participant: </em> {event.maximumParticipants}
              </div>
            </div>
            <div className="event-participants event-text">
              <em>Current number of participants: </em>{" "}
              {event.participants.length}
            </div>
            {event.fee > 0 ? (
              <div className="event-fee event-text">Fee: {event.fee}$</div>
            ) : (
              <div></div>
            )}
            <Button
              disabled={event.status !== "REGISTRATION_OPEN"}
              className="event-button"
              onClick={() => signupEvents()}
            >
              Sign Up
            </Button>
            <Button
              className="event-button"
              onClick={() => navigate("/signup-forum")}
            >
              Signup Forum
            </Button>
            <Button
              className="event-button"
              onClick={() => signupEvents()}
              disabled={event.status === "REGISTRATION_OPEN"}
            >
              Participant Forum
            </Button>
          </div>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}

export default EventPage;
