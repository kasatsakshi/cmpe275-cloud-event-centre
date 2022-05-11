import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";
import axios from "axios";
import './temp.css';
function Temp() {


  const roomss = [
    { 
        name:'event1',
        location:'san jose',
        type:'active',
        startDate: '2022-05-12',
        endDate: '2022-05-14'
    },
    { 
        name:'event2',
        location:'remote',
        type:'not active',
        startDate: '2022-05-09',
        endDate: '2022-05-10'
    },
    { 
        name:'event3',
        location:'remote',
        type:'active',
        startDate: '2022-05-03',
        endDate: '2022-05-12'
    }
  ];


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
      
    <div>
      
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
   
    <div >

    <div required>
      <input type="radio" value="pickup" name="gender" checked/> Pick Up
      <input type="radio" value="delivery" name="gender" /> Delivery

    </div>

     <select >
                
                <option value="sanjose">San Jose</option>
                <option value="sanfrancisco">San Francisco</option>
                <option value="santaclara">Santa Clara</option>
      </select>
    
    </div>
    <ul>
      {hotels.map(country => (
        <li class="stunt">
             <div class="row">
            <div class="col-lg-4">
          <div class="card yash">
<img class="card-img-top" src="https://images.unsplash.com/photo-1590846406792-0adc7f938f1d?ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8cmVzdGF1cmFudHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80" alt="Card image cap"/>
<div class="card-body">
  <h5 class="card-title">{country.title}</h5>
  <p>{country.startTime}</p>
  <p>{country.status}</p>
  
  <button class="btn btn-primary">Click</button>


  
</div>
</div>
</div>
</div>
            
            
            
            </li>
      ))}
    </ul>
    </div>
  );
}

export default Temp;
