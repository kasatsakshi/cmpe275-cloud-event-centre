import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import './temp.css';
import { Link } from 'react-router-dom';
import EventNavbar from "./EventNavbar";
import {useSelector} from "react-redux";

function EventPage() {
   const user = useSelector((state)=>state.user.currentUser);

  const [hotels, sethotels] = useState([]);

  const signupevents = ()  =>{
    //e.preventDefault();
    axios.post(`/api/event/register`, null, { params: {
        userId:user.id,
        eventId:localStorage.getItem("event_name"),
      }})
      .then(response => response.status)
      .catch(err => alert(err.response.data));
  }

  useEffect(() => {
    try {
        axios.get("/api/event/"+localStorage.getItem("event_name")).then((response) => {
        sethotels(response.data);
      });
      console.log(hotels);
    } catch (error) {
      console.log(error);
    }
  }, []);

  
  return (
    <body>
         <EventNavbar/>
    
    <div class="card text-center">
  <div class="card-header">
    {hotels.status}
  </div>
  <div class="card-body">
    <h5 class="card-title">{hotels.title}</h5>
    <p class="card-text">{hotels.description}</p>
    {/* <a href="#" class="btn btn-primary">Back</a> */}
    <button onClick={()=> signupevents()} className="btn btn-primary">Sign Up</button>
    <Link to="/eventdash">
        <a class="btn btn-primary">Back</a>
    </Link>
  </div>
  <div class="card-footer text-muted">
    {hotels.startTime} to {hotels.endTime}
  </div>
</div>

      
    </body>
  );
}

export default EventPage;
