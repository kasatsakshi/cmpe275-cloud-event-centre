import React, { useState, useEffect } from "react";
import axios from "axios";

import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import Rating from "@mui/material/Rating"
import styled from "styled-components";
import { useSelector } from "react-redux";


const Input = styled.textarea`
  width: 40%;
  height: 40%;
  margin-left: 10px;
  padding-top: 3px;
`;

const Button = styled.button`
  width: 100px;
  border: none;
  padding: 15px 20px;
  background-color: black;
  color: white;
  cursor: pointer;
  margin-left: 30px;
  margin-top: 10px;
  position: absolute;
  &:disabled {
    background-color: grey;
    cursor: not-allowed;
  }
`;

function ParticipantReview({ eventData }) {
  const [reviewRating, setReviewRating] = useState(0);
  const [reviewMessage, setReviewMessage] = useState("");

  function submitReview(participantId) {
    axios
      .post(`/api/review/participant`, {
        userId: participantId,
        eventId: eventData.id,
        text: reviewMessage,
        rating: reviewRating
      })
      .then((response) => {
        console.log(response)
        window.location.reload();
      })
      .catch((err) => {
        alert("Something went wrong! Try again")
      });
  }

  function checkParticipantReview(participant) {
    let reviewed = false;
    participant && participant.participantReviews && participant.participantReviews.map((review) => {
      if (review.user.id === participant.id && eventData.id === review.event.id) {
        reviewed = true;
      }
    });

    return reviewed;
  }

  return (
    < div className="forum-container">
      <h1 className="forum-title">Add review for participants</h1>
      {eventData && eventData.participants && eventData.participants.length > 0 ? (
        eventData.participants.map((participant) => {
          return (
            <Card variant="outlined" sx={{ minWidth: 275, marginTop: 3 }}>
              <CardContent>
                {checkParticipantReview(participant) ? (
                  <Typography variant="h6" component="div">
                    <b>{participant.fullName}</b>
                    <p>Participant has been Reviewed</p>
                  </Typography>
                ) : (
                  <Typography variant="h6" component="div">
                    <b>{participant.fullName}</b>
                    <Rating
                      name="simple-controlled"
                      size="large"
                      sx={{ marginLeft: 5 }}
                      onChange={(event, newValue) => {
                        setReviewRating(newValue);
                      }}
                    ></Rating>
                    <Input
                      autoFocus
                      placeholder="Add your review here"
                      onChange={(e) => {
                        setReviewMessage(e.target.value)
                      }}
                    />
                    <Button onClick={() => submitReview(participant.id)}>
                      Submit
                    </Button>
                  </Typography>
                )
                }
              </CardContent>
            </Card>
          );
        })
      ) : (
        <div>No Participants</div>
      )
      }
    </div >



    //   <div>
    //   <Stack direction={"row"} spacing={10}>
    //     <h5>Organizer Reviews</h5>
    //     <h5>Organizer Reputation: {userInfo && userInfo.organizerReputation}</h5>
    //   </Stack>
    //   {userInfo && userInfo.organizerReviews && userInfo.organizerReviews.length > 0 ? (
    //     userInfo.organizerReviews.map((review) => {
    //       return (
    //         <Card variant="outlined" sx={{ minWidth: 275 }}>
    //           <CardContent>
    //             <Typography variant="h6" component="div">
    //               <b>{review.text}</b>
    //             </Typography>
    //           </CardContent>
    //         </Card>
    //       );
    //     })
    //   ) : (
    //     <div>No Organizer Reviews</div>
    //   )
    //   }
    // </div>


  );
}

export default ParticipantReview