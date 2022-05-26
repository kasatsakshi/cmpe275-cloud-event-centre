import "./EventPage.css";
import EventNavbar from "./EventNavbar";
import axios from "axios";
import React, { useState, useEffect } from "react";
import Card from "@mui/material/Card";
import CardActions from "@mui/material/CardActions";
import CardContent from "@mui/material/CardContent";
import Typography from "@mui/material/Typography";
import { useParams } from "react-router-dom";

function UserProfile({ user }) {
  // const { id } = useParams();
  // const [user, setUser] = useState([]);


  // async function getUser() {
  //   try {
  //     axios
  //       .get("/api/user", {
  //         params: {
  //           id
  //         }
  //       })
  //       .then((response) => {
  //         console.log(response.data);
  //         setUser(response.data);
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //       });

  //   } catch (error) {
  //     console.log(error);

  //   }
  // }
  // useEffect(async () => {
  //   await getUser()
  // }, []);

  return (
    <div>
      <div className="event-page-container">
        <div className="event-container">
          <h3 className="event-title">{user.fullName}</h3>
          <div className="event-description">{user.description}</div>
          <div
            className="event-date event-text"
            style={{ display: "flex", flexDirection: "column" }}
          >
            <div className="event-text">
              <b>Email:  </b> {user.email}
            </div>

            <div className="event-text">
              <b>Reviews:  </b>
            </div>

            {user && user.participantReviews && user.participantReviews.length > 0 ? (
              user.participantReviews.map((review) => {
                return (
                  <Card variant="outlined" sx={{ minWidth: 275 }}>
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
          </div>
        </div>
      </div>
    </div >
  );
}

export default UserProfile;