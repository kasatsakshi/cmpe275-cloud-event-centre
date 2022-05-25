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

function Account() {
  const user = useSelector((state) => state.user.currentUser);
  const [recieved, setRecieved] = useState([]);
  const [pending, setPending] = useState("");
  const [events,setEvents] = useState([]);

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

  function getEvent() {
    try {
      axios.get("/api/user/recievedrequests/" + user.id).then((response) => {
        console.log(response.data);
        setRecieved(response.data);
        var groupBy = function(xs, key,tit) {
          return xs.reduce(function(rv, x) {
            (rv[x[key][tit]] = rv[x[key][tit]] || []).push(x);
            return rv;
          }, {});
        };
       
        setEvents(groupBy(response.data, 'event','title'));
      });

      axios.get("/api/user/signuprequests/" + user.id).then((response) => {
        setPending(response.data);
      });
    } catch (error) {
      console.log(error);
    }
    finally{
    }
  }

  function groupfunc(){
    var groupBy = function(xs, key,tit) {
      return xs.reduce(function(rv, x) {
        (rv[x[key][tit]] = rv[x[key][tit]] || []).push(x);
        return rv;
      }, {});
    };
    setEvents(groupBy(recieved, 'event','title'));
  }

  const userpage = (user) =>{
    localStorage.setItem("approveuser",JSON.stringify(user));
  }

  useEffect(() => {
    getEvent();
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
                      Requested by: 
                      <Link to="/userprofile">
                      <a onClick={(e) => userpage(request.user)}>{request.user.fullName}</a>
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
    </div>
  );
}

export default Account;
