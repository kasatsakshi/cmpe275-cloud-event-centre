import React, { useState, useEffect } from "react";
import Alert from "react-bootstrap/Alert";
import axios from "axios";
import "./EventPage.css";
import { useNavigate } from "react-router-dom";
import EventNavbar from "./EventNavbar";
import { useSelector } from "react-redux";
import styled from "styled-components";
import moment from "moment";
import {
  TabContent,
  TabPane,
  Nav,
  NavItem,
  NavLink as HeaderNavLink,
  Row,
  Col,
} from "reactstrap";
import SignUpForum from "./SignUpForum";
import ParticipantForum from "./ParticipantForum";

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
  const [activeTab, setActiveTab] = useState("1");
  const navigate = useNavigate();

  function getEvent() {
    try {
      setLoading(true);
      axios
        .get("/api/event/" + localStorage.getItem("eventId"))
        .then((response) => {
          // console.log(response.data);
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
        <div>
          <Row style={{ margin: "0", height: "100vh" }}>
            <Col
              xs={2}
              style={{
                padding: "0px",
                zIndex: "1",
              }}
            >
              <Nav
                vertical
                style={{
                  paddingTop: "10px",
                }}
              >
                <NavItem>
                  <HeaderNavLink
                    style={{
                      backgroundColor: "#fcfcfc",
                      color: "black",
                      fontFamily: `-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif`,
                      padding: "20px",
                      cursor: "pointer",
                    }}
                    onClick={() => {
                      setActiveTab("1");
                    }}
                  >
                    <p
                      className="black b"
                      style={{
                        margin: "auto",
                        textAlign: "center",
                        fontSize: "20px",
                        fontWeight: "bold",
                      }}
                    >
                      Details
                    </p>
                  </HeaderNavLink>
                  <hr style={{ margin: "5px" }} />
                </NavItem>
                <NavItem className="black">
                  <HeaderNavLink
                    style={{
                      backgroundColor: "#fcfcfc",
                      color: "black",
                      fontFamily: `-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif`,
                      padding: "20px",
                      cursor: "pointer",
                    }}
                    disabled={event.status !== "REGISTRATION_OPEN"}
                    onClick={() => {
                      setActiveTab("2");
                    }}
                  >
                    <p
                      className="black b"
                      style={{
                        margin: "auto",
                        textAlign: "center",
                        fontSize: "20px",
                        fontWeight: "bold",
                      }}
                    >
                      Signup Forum
                    </p>
                    {event.status !== "REGISTRATION_OPEN" ? (
                      <p
                        className="black b"
                        style={{
                          margin: "auto",
                          textAlign: "center",
                          fontSize: "10px",
                          fontWeight: "bold",
                          color: "gray",
                        }}
                      >
                        (Closed)
                      </p>
                    ) : (
                      ""
                    )}
                  </HeaderNavLink>
                  <hr style={{ margin: "5px" }} />
                </NavItem>
                <NavItem className="black">
                  <HeaderNavLink
                    style={{
                      backgroundColor: "#fcfcfc",
                      color: "black",
                      fontFamily: `-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif`,
                      padding: "20px",
                      cursor: "pointer",
                    }}
                    disabled={event.status === "REGISTRATION_OPEN"}
                    onClick={() => {
                      setActiveTab("3");
                    }}
                  >
                    <p
                      className="black b"
                      style={{
                        margin: "auto",
                        textAlign: "center",
                        fontSize: "20px",
                        fontWeight: "bold",
                      }}
                    >
                      Participant Forum{" "}
                    </p>
                    {event.status === "REGISTRATION_OPEN" ? (
                      <p
                        className="black b"
                        style={{
                          margin: "auto",
                          textAlign: "center",
                          fontSize: "10px",
                          fontWeight: "bold",
                          color: "gray",
                        }}
                      >
                        (Closed)
                      </p>
                    ) : (
                      ""
                    )}
                  </HeaderNavLink>
                  <hr style={{ margin: "5px" }} />
                </NavItem>
              </Nav>
            </Col>
            <Col>
              <TabContent activeTab={activeTab}>
                <TabPane tabId="1">
                  <div className="event-page-container">
                    <div className="event-container">
                      <h1 className="event-title">{event.title}</h1>
                      <div className="event-description">
                        {event.description}
                      </div>
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
                        <div className="event-fee event-text">
                          Fee: {event.fee}$
                        </div>
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
                      {/* <Button
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
                      </Button> */}
                    </div>
                  </div>
                </TabPane>
                <TabPane tabId="2">
                  <SignUpForum />
                </TabPane>
                <TabPane tabId="3">
                  <ParticipantForum />
                </TabPane>
              </TabContent>
            </Col>
          </Row>
        </div>
      ) : (
        <div></div>
      )}
    </div>
  );
}

export default EventPage;
