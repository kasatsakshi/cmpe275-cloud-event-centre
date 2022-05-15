import React, { useState, useEffect } from "react";
// import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import "./EventPage.css";
import { Link, useNavigate } from "react-router-dom";
import EventNavbar from "./EventNavbar";
import { useSelector } from "react-redux";
import styled from "styled-components";


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
  const [loading, setloading] = useState(false);
  const [event, setEvent] = useState("");

  const navigate = useNavigate();

  function getEvent() {
    try {
      setloading(true);
      axios
        .get("/api/event/" + localStorage.getItem("eventId"))
        .then((response) => {
          console.log(response.data);
          setEvent(response.data);
        });
      setloading(false);
    } catch (error) {
      console.log(error);
      setloading(false);
    }
  }

  useEffect(() => {
    getEvent();
  }, []);

  const signupevents = () => {
    //e.preventDefault();
    axios
      .post(`/api/event/register`, null, {
        params: {
          userId: user.id,
          eventId: localStorage.getItem("eventId"),
        },
      })
      .then((response) => response.status)
      .catch((err) => alert(err.response.data));
  };

  return (
    <div>
      <EventNavbar />
      {event ? (
        <div className="event-page-container">
          <div className="event-container">
            <h1 className="event-title">{event.title}</h1>
            <div className="event-description">{event.description}</div>
            <div className="event-owner event-text">
              by: {event.creator.screenName}
            </div>
            <div className="event-date event-text">
              <div className="event-start event-text">
                Start Time: {event.startTime}
              </div>
              <div className="event-text">End time: {event.endTime}</div>
            </div>

            <div className="event-fee event-text">Fee: {event.fee}$</div>
            <div className="event-date">
              <div className="event-start event-text">
                Min participant: {event.minimumParticipants}
              </div>
              <div className="event-text">
                Max participant: {event.maximumParticipants}
              </div>
            </div>
            <div className="event-participants event-text">
              Current number of participants: {event.participants.length}
            </div>
            <Button
              disabled={event.status !== "REGISTRATION_OPEN"}
              className="event-button"
              onClick={() => signupevents()}
            >
              Sign Up
            </Button>
            <Button className="event-button" onClick={() => navigate('/signup-forum')}>
              Signup Forum
            </Button>
            <Button className="event-button" onClick={() => signupevents()}>
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