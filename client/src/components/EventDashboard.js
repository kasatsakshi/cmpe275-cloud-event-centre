import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import './temp.css';
import { Link } from 'react-router-dom';
import EventNavbar from "./EventNavbar";

function EventDashboard() {


  const [hotels, sethotels] = useState([]);
  const [duplicatehotes, setduplicatehotes] = useState([]);
  const [loading, setloading] = useState(false);
  const [searchkey, setsearchkey] = useState('');
  const [location, setLocation] = useState('all')
  const [searchlockey, setsearchlockey] = useState('');
  const[type , settype]=useState('all')
  //const dateObj = new Date(2000, 0, 1);
  const [fromDate, setFromDate] = useState(new Date().toLocaleDateString('en-ca'));
  const [toDate, setToDate] = useState("");

  function assignFromDate (e){
    //console.log(e.target.value);
    setFromDate(e);
    //setFromDate(e);
    console.log(e);
    console.log(fromDate);
    const events = duplicatehotes.filter(a => new Date(a.startDate) - new Date(e) >= 0);
     sethotels(events);
     setduplicatehotes(events);
  };

  function ToDate(e){
    setToDate(e);
    const events = duplicatehotes.filter(a => new Date(a.endDate) - new Date(e) <= 0);
     sethotels(events);
  }


  useEffect(() => {
    try {
      setloading(true);
      axios.get("/api/event/all").then((response) => {
        sethotels(response.data);
      });
      //const rooms = axios.get("/api/event/all");
      console.log(hotels);
      //sethotels(rooms);
      setduplicatehotes(hotels)
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

<option value="all">All</option>
  <option value="Montery">Montery</option>
  <option value="san Jose">San Jose</option>
  <option value="san Francisco">San Francisco</option>
  
</select>
          </div>


          <div className="col-md-2">
            <select className="form-control m-2" value={type} onChange={(e)=>{filterByType(e.target.value)}} >

            <option value="all">All</option>
              <option value="ACTIVE">Active</option>
              <option value="FINISHED">Finished</option>
              <option value="REGISTRATION_OPEN">Open</option>
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
            style={{ width: "150px" }}
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
            style={{ width: "150px" }}
          />
        </div>
      </div>


        </div>
      </div>

    

      <div className="row justify-content-center">
        {loading ? (
          <Register />
        ) : (
          hotels.map((room) => {
            return (
                <div class="row">
                <div class="card" style={{width:'1002px'}}>
                <div class="card-body">
                  <h5 class="card-title">{room.title}</h5>
                  <h6 class="card-subtitle mb-2 text-muted">{room.startTime}</h6>
                  <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                  <p href="#" class="card-link">{room.state}</p>
                  <p href="#" class="card-link">{room.description}</p>
                  <Link to="/eventpage">
                        <button class="btn btn-primary" onClick={()=> localStorage.setItem('event_name',room.id)}>Order!</button>
                  </Link>
                </div>
              </div>
              </div>
            );
          })
        )}
      </div>
      
    </div>
    </body>
  );
}

export default EventDashboard;
