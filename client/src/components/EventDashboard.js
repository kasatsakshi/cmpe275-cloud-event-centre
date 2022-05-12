import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import './temp.css';
import { Link } from 'react-router-dom';
import EventNavbar from "./EventNavbar";

function EventDashboard() {


  const [hotels, sethotels] = useState([]);
  const [events, setEvents] = useState([]);
  const [duplicatehotes, setduplicatehotes] = useState([]);
  const [loading, setloading] = useState(false);
  const [searchkey, setsearchkey] = useState('');
  const [location, setLocation] = useState([])
  const [searchlockey, setsearchlockey] = useState('');
  const[type , settype]=useState('all')
  //const dateObj = new Date(2000, 0, 1);
  const [fromDate, setFromDate] = useState(new Date().toLocaleDateString('en-ca'));
  const [toDate, setToDate] = useState("");
  var locat=[{city: 'San Jose'}]; 

  function assignFromDate (e){
    //console.log(e.target.value);
    setFromDate(e);
    //setFromDate(e);
    console.log(e);
    console.log(fromDate);
    const events = duplicatehotes.filter(a => new Date(a.startTime) - new Date(e) >= 0);
     sethotels(events);
     //setduplicatehotes(events);
  };

  function ToDate(e){
    setToDate(e);
    const events = duplicatehotes.filter(a => new Date(a.endTime) - new Date(e) <= 0);
     sethotels(events);
  }


  useEffect(() => {
    try {
      setloading(true);
      axios.get("/api/event/all").then((response) => {
        sethotels(response.data);
        setduplicatehotes(response.data);
        console.log(response.data);
        setEvents(response.data.filter((li, idx, self) => self.map(itm => itm.address.city).indexOf(li.address.city) === idx));
      });
      //const rooms = axios.get("/api/event/all");
      
      console.log(hotels);
      //sethotels(rooms);
      setduplicatehotes(hotels)
      console.log(location);
      
      events.forEach((item) => {
        var loc = {
          "city": item.address.city
        };  
        if (!locat.includes(loc)){
          //console.log(loc);
          locat.push( loc );
          }
      });
      locat = locat.filter((li, idx, self) => self.map(itm => itm.city).indexOf(li.city) === idx);
       setLocation(locat);
       console.log(location);

       setloading(false);
    } catch (error) {
      console.log(error);
      setloading(false);
    }
  }, []);

  function filterBySearch()
  {
    const dupdate = duplicatehotes.filter(room=>room.title.toLowerCase().includes(searchkey))
    sethotels(dupdate)

  }
  function filterByStartDate()
{
  const events = duplicatehotes.filter(a => new Date(a.startDate) - new Date > 0);
  sethotels(events);
}
  function filterByLocation(e)
  {
    setLocation(e)
    if(e!=='all'){
      const dupdate = duplicatehotes.filter(room=>room.address.city.toLowerCase().includes(e.toLowerCase()))
      sethotels(dupdate)
    }
    else{
      sethotels(duplicatehotes)
    }
  }

  

  function filterByType(e)
  {
    settype(e)
    if(e!=='all'){
      const dupdate = duplicatehotes.filter(room=>room.status.toLowerCase().includes(e.toLowerCase()))
      sethotels(dupdate)
    }
    else{
      sethotels(duplicatehotes)
    }
   
  }

  return (
    <body>
      <EventNavbar/>
    <div className="container">
      <div className="cont" style={{height:'100px'}}>
        <div className="row bs p-3 m-5">
          

          <div className="col-md-2">
            <input
              type="text"
              className="form-control i2 m-2"
              placeholder='Search Rooms'
              value={searchkey}
              onKeyUp={filterBySearch}
              onChange={(e)=>{setsearchkey(e.target.value)}}
            />
          </div>


          <div className="col-md-2">
            
          <select className="form-control m-2" value={location} onChange={(e)=>{filterByLocation(e.target.value)}} >

          {events.map((data, idx) => (
            
<option key={idx}>{data.address.city}</option>
))}
            </select>
        </div>


          <div className="col-md-2">
            <select className="form-control m-2" value={type} onChange={(e)=>{filterByType(e.target.value)}} >

            <option value="all">All</option>
              <option value="ACTIVE">Active</option>
              <option value="FINISHED">Finished</option>
              <option value="REGISTRATION_OPEN">Open</option>
              <option value="CANCELLED">Cancelled</option>
            </select>
          </div>

          <div className="col-md-2">
        <div className="form-group">
          <span style={{ opacity: "0.6", fontSize: "13px" }}>from</span>
          <input
            type="date"
            name="from"
            id="startdate"
            min={new Date().toLocaleDateString('en-ca')}
            value={fromDate}
            onChange={(e)=>{assignFromDate(e.target.value)}}
            className="form-control datepicker"
            
          />
        </div>
      </div>

      <div className="col-sm-2">
        <div className="form-group">
          <span style={{ opacity: "0.6", fontSize: "13px" }}>to</span>
          <input
            type="date"
            name="to"
            min={fromDate}
            id="enddate"
            value={toDate}
            placeholder="Select Date"
            onChange={e => ToDate(e.target.value)}
            className="form-control datepicker"
            
          />
        </div>
      </div>


        </div>
      </div>

    

      <div className="row justify-content-center">
        {
          hotels.map((room) => {
            return (
                <div class="row">
                <div class="card" style={{width:'1002px'}}>
                <div class="card-body">
                  <h5 class="card-title">{room.title}</h5>
                  <h6 class="card-subtitle mb-2 text-muted">{room.startTime} to {room.endTime}</h6>
        
                  <p href="#" class="card-link">{room.address.city}</p>
                  <p href="#" class="card-link">{room.description}</p>
                  <Link to="/eventpage">
                        <button class="btn btn-primary" onClick={()=> localStorage.setItem('event_name',room.id)}>Click!</button>
                  </Link>
                </div>
              </div>
              </div>
            );
          })
        }
      </div>
      
    </div>
    </body>
  );
}

export default EventDashboard;
