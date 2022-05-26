import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import EventNavbar from "./EventNavbar";
import "./Account.css";
import axios from "axios";
import Box from "@mui/material/Box";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import styled from "styled-components";
import { Link } from "react-router-dom";
import { Stack } from "@mui/material";
import Modal from "@mui/material/Modal";
import UserProfile from "./UserProfile";

const Button = styled.button`
  width: 100px;
  border: none;
  padding: 5px 10px;
  background-color: black;
  margin-top: 0px;
  color: white;
  cursor: pointer;
  margin-bottom: 5px;
  &:disabled {
    background-color: grey;
    cursor: not-allowed;
  }
`;

const style = {
  position: "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 500,
  height: 400,
  bgcolor: "background.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

function Account() {
  const user = useSelector((state) => state.user.currentUser);
  const [recieved, setRecieved] = useState([]);
  const [pending, setPending] = useState("");
  const [events, setEvents] = useState([]);
  const [userInfo, setUserInfo] = useState();

  const [reviewOpen, setReviewOpen] = React.useState(false);
  const handleOpenNewReview = () => setReviewOpen(true);
  const handleCloseNewReview = () => setReviewOpen(false);

  const approve = (id) => {
    axios
      .put(`/api/eventrequest/accept/${id}`, null, {})
      .then((response) => {
        alert("Request Accepted");
        return response.status;
      })
      .catch((err) => alert(JSON.stringify(err.response.data)));
  };

  const reject = (id) => {
    axios
      .put(`/api/eventrequest/reject/${id}`, null, {})
      .then((response) => {
        alert("Request Rejected");
        return response.status;
      })
      .catch((err) => alert(JSON.stringify(err.response.data)));
  };

  async function getEvent() {
    try {
      axios.get("/api/user/recievedrequests/" + user.id).then((response) => {
        setRecieved(response.data);
        var groupBy = function (xs, key, tit) {
          return xs.reduce(function (rv, x) {
            (rv[x[key][tit]] = rv[x[key][tit]] || []).push(x);
            return rv;
          }, {});
        };

        setEvents(groupBy(response.data, 'event', 'title'));
      });

      axios.get("/api/user/signuprequests/" + user.id).then((response) => {
        setPending(response.data);
      });
    } catch (error) {
      console.log(error);
    }
  }

  async function getUserInfo() {
    try {
      axios.get("/api/user?id=" + user.id).then((response) => {
        setUserInfo(response.data);
      });
    } catch (error) {
      console.log(error);
    }
  }

  function groupfunc() {
    var groupBy = function (xs, key, tit) {
      return xs.reduce(function (rv, x) {
        (rv[x[key][tit]] = rv[x[key][tit]] || []).push(x);
        return rv;
      }, {});
    };
    setEvents(groupBy(recieved, 'event', 'title'));
  }

  const userpage = (user) => {
    localStorage.setItem("approveuser", JSON.stringify(user));
  }

  useEffect(async () => {
    await getEvent();
    await getUserInfo();
  }, []);
  return (
    <div>
      <EventNavbar />
      <div className="account-container">
        <h1>Hi {user.fullName}</h1>

        {recieved && recieved.length > 0 ? (
          <div className="account-divs">
            <h5>Recieved Requests</h5>

            {Object.entries(events).map(([key, value]) => (
              <Card key={key}>
                <CardContent>
                  <Typography variant="body2" color="text.secondary">

                    <Typography variant="h5" component="div">
                      {key}
                    </Typography>
                    {value.map((request) => {
                      return (
                        <Card variant="outlined" sx={{ minWidth: 275 }}>
                          <CardContent>

                            <Typography sx={{ mb: 1.5 }}>
                              <div><b>Name: {request.user.fullName}</b></div>
                              <b>
                                User reputation: {request.user.participantReputation}
                              </b>
                              <br />
                              <div>
                                <button onClick={(handleOpenNewReview)}>See Reviews</button>
                                <Modal
                                  id="reply-modal"
                                  open={reviewOpen}
                                  onClose={handleCloseNewReview}
                                  aria-describedby="modal-modal-description"
                                >
                                  <Box sx={style}>
                                    {request.user && request.user.participantReviews && request.user.participantReviews.length > 0 ? (
                                      request.user.participantReviews.map((review) => {
                                        return (
                                          <Card variant="outlined" sx={{ minWidth: 275, marginTop: 2 }}>
                                            <CardContent>
                                              <Typography variant="h6" component="div">
                                                <b>Rating</b>  : <b>{review.rating}</b>
                                              </Typography>
                                              <Typography variant="h6" component="div">
                                                <b>{review.event.title}</b>   : {review.text}
                                              </Typography>
                                            </CardContent>
                                          </Card>
                                        )
                                      })
                                    ) : (
                                      <p>No Participant Reviews</p>
                                    )
                                    }
                                  </Box>
                                </Modal>
                              </div>

                            </Typography>
                          </CardContent>
                          <CardActions>
                            {request.status === "PENDING" ? (
                              <>
                                <Button
                                  disabled={request.status != "PENDING"}
                                  size="small"
                                  onClick={() => approve(request.id)}
                                >
                                  Approve
                                </Button>
                                <Button
                                  size="small"
                                  disabled={request.status != "PENDING"}
                                  onClick={() => reject(request.id)}
                                >
                                  Reject
                                </Button>
                              </>
                            ) : (
                              <CardContent>
                                <Typography sx={{ mb: 1.5 }} color="text.secondary">
                                  {request.status}
                                </Typography>
                              </CardContent>
                            )}
                          </CardActions>
                        </Card>
                      );
                    })}
                  </Typography>
                </CardContent>
              </Card>
            ))}

            <p>-----------------------------------------------</p>

            {/* {recieved.map((request) => {
              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h5" component="div">
                      {request.event.title}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }}>
                      Requested by: 
                      <Link to="/userprofile">
                      <button onClick={(e) => userpage(request.user)}>{request.user.fullName}</button>
                      </Link>
                    </Typography>
                  </CardContent>
                  <CardActions>
                    {request.status === "PENDING" ? (
                      <>
                        <Button
                          disabled={request.status != "PENDING"}
                          size="small"
                          onClick={() => approve(request.id)}
                        >
                          Approve
                        </Button>
                        <Button
                          size="small"
                          disabled={request.status != "PENDING"}
                          onClick={() => reject(request.id)}
                        >
                          Reject
                        </Button>
                      </>
                    ) : (
                      <CardContent>
                        <Typography sx={{ mb: 1.5 }} color="text.secondary">
                          {request.status}
                        </Typography>
                      </CardContent>
                    )}
                  </CardActions>
                </Card>
              );
            })} */}
          </div>
        ) : (
          <div></div>
        )}

        <div>
          <Stack direction={"row"} spacing={10}>
            <h5>Organizer Reviews</h5>
            <h5>Organizer Reputation: {userInfo && userInfo.organizerReputation}</h5>
          </Stack>
          {userInfo && userInfo.organizerReviews && userInfo.organizerReviews.length > 0 ? (
            userInfo.organizerReviews.map((review) => {
              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h6" component="div">
                      <b>{review.text}</b>
                    </Typography>
                  </CardContent>
                </Card>
              );
            })
          ) : (
            <div>No Organizer Reviews</div>
          )
          }
        </div>

        <p>-----------------------------------------------</p>

        <div>
          <Stack direction={"row"} spacing={10}>
            <h5>Participant Reviews</h5>
            <h5>Participant Reputation: {userInfo && userInfo.participantReputation}</h5>
          </Stack>

          {userInfo && userInfo.participantReputation && userInfo.participantReputation.length > 0 ? (
            userInfo.participantReputation.map((review) => {
              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h6" component="div">
                      <b>{review.text}</b>
                    </Typography>
                  </CardContent>
                </Card>
              );
            })
          ) : (
            <div>No Participant Reviews</div>
          )
          }
        </div>

        <p>-----------------------------------------------</p>

        {pending && pending.length > 0 ? (
          <div className="account-divs">
            <h5>Requests Sent for Approval</h5>
            {pending.map((request) => {
              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h5" component="div">
                      {request.event.title}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} color="text.secondary">
                      Organizer :
                      <Link to="/orgprofile">
                        <a onClick={(e) => userpage(request.user)}>{request.user.fullName}</a>
                      </Link>
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} color="text.secondary">
                      Status: {request.status}
                    </Typography>
                  </CardContent>
                </Card>
              );
            })}
          </div>
        ) : (
          <div></div>
        )}
      </div>
    </div >
  );
}

export default Account;
