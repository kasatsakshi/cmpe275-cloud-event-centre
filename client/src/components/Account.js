import React, { useState, useEffect } from 'react';
import { useSelector } from "react-redux";
import EventNavbar from './EventNavbar';
import './Account.css';
import axios from "axios";
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import styled from "styled-components";

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
  const [recieved, setRecieved] = useState("");
  const [pending, setPending] = useState("")

  const approve = (id) => {
    axios
      .put(`/api/eventrequest/accept/${id}`, null, {})
      .then((response) => {
        alert("Request Accepted");
        return response.status
      })
      .catch((err) => alert(JSON.stringify(err.response.data)));
  };

  const reject = (id) => {
    //e.preventDefault();
    axios
      .put(`/api/eventrequest/reject/${id}`, null, {})
      .then((response) => {
        alert("Request Rejected");
        return response.status
      })
      .catch((err) => alert(JSON.stringify(err.response.data)));
  };

  function getEvent() {
    try {
      axios
        .get("/api/user/recievedrequests/" + user.id)
        .then((response) => {
          console.log(response.data);
          setRecieved(response.data);
        });

      axios
        .get("/api/user/signuprequests/" + user.id)
        .then((response) => {
          setPending(response.data);
        });

    } catch (error) {
      console.log(error);
    }
  }

  useEffect(() => {
    getEvent();
  }, []);
  return (
    <div>
      <EventNavbar />
      <div className='account-container'>
        <h1>Hi {user.fullName}</h1>

        {recieved && recieved.length > 0 ?
          <div className='account-divs'>
            <h5>Recieved Requests</h5>
            {recieved.map((request) => {

              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h5" component="div">
                      {request.event.title}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} >
                      requested by: {request.user.fullName}
                    </Typography>
                  </CardContent>
                  <CardActions>
                    <Button
                      disabled={request.status != "PENDING"}
                      size="small"
                      onClick={() => approve(request.id)}>
                      Approve
                    </Button>
                    <Button size="small"
                      disabled={request.status != "PENDING"}
                      onClick={() => reject(request.id)}>
                      Reject
                    </Button>
                  </CardActions>
                </Card>
              );
            })}
          </div> : <div></div>}

        {pending && pending.length > 0 ?
          <div className='account-divs'>
            <h5>Requests Sent for Approval</h5>
            {pending.map((request) => {

              return (
                <Card variant="outlined" sx={{ minWidth: 275 }}>
                  <CardContent>
                    <Typography variant="h5" component="div">
                      {request.event.title}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} color="text.secondary">
                      by: {request.creator.fullName}
                    </Typography>
                    <Typography sx={{ mb: 1.5 }} color="text.secondary">
                      status: {request.status}
                    </Typography>
                  </CardContent>
                </Card>
              );
            })}
          </div> : <div></div>}

      </div>
    </div>
  )
}

export default Account