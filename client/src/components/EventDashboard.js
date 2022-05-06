import React, { useState, useEffect } from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import Register from "./Register";

function EventDashboard() {


  const rooms = [
    { 
        name:'event1',
        location:'san jose',
        type:'active'
    },
    { 
        name:'event2',
        location:'remote',
        type:'not active'
    },
    { 
        name:'event3',
        location:'remote',
        type:'active'
    }
  ];


  const [hotels, sethotels] = useState([]);
  const [duplicatehotes, setduplicatehotes] = useState([]);
  const [loading, setloading] = useState(false);
  const [searchkey, setsearchkey] = useState('');
  const [location, setLocation] = useState('all')
  const [searchlockey, setsearchlockey] = useState('');
  const[type , settype]=useState('all')
  

  useEffect(() => {
    try {
      setloading(true);
      //const rooms = await (await axios.get("/api/rooms/getallrooms")).data;
      console.log(rooms);
      sethotels(rooms);
      setduplicatehotes(rooms)
      setloading(false);
    } catch (error) {
      console.log(error);
      setloading(false);
    }
  }, []);

  function filterBySearch()
  {
    const dupdate = duplicatehotes.filter(room=>room.name.toLowerCase().includes(searchkey))
    sethotels(dupdate)
  }

  function filterByLocation(e)
  {
    setLocation(e)
    if(e!=='all'){
      const dupdate = duplicatehotes.filter(room=>room.location.toLowerCase().includes(e.toLowerCase()))
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
      const dupdate = duplicatehotes.filter(room=>room.type.toLowerCase().includes(e.toLowerCase()))
      sethotels(dupdate)
    }
    else{
      sethotels(duplicatehotes)
    }
   
  }

  return (
    <div className="mt-5">
      <div className="cont" style={{height:'100px'}}>
        <div className="row bs p-3 m-5">
          

          <div className="col-md-3">
            <input
              type="text"
              className="form-control i2 m-2"
              placeholder='Search Rooms'
              value={searchkey}
              onKeyUp={filterBySearch}
              onChange={(e)=>{setsearchkey(e.target.value)}}
            />
          </div>

          <div className="col-md-3">
            
<select className="form-control m-2" value={location} onChange={(e)=>{filterByLocation(e.target.value)}} >

<option value="all">All</option>
  <option value="Montery">Montery</option>
  <option value="san Jose">San Jose</option>
  <option value="san Francisco">San Francisco</option>
  
</select>
          </div>


          <div className="col-md-3">
            <select className="form-control m-2" value={type} onChange={(e)=>{filterByType(e.target.value)}} >

            <option value="all">All</option>
              <option value="delux">Delux</option>
              <option value="non-delux">Non Delux</option>
              
            </select>
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
                  <h5 class="card-title">{room.name}</h5>
                  <h6 class="card-subtitle mb-2 text-muted">Card subtitle</h6>
                  <p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>
                  <a href="#" class="card-link">Card link</a>
                  <a href="#" class="card-link">Another link</a>
                </div>
              </div>
              </div>
            );
          })
        )}
      </div>
      
    </div>
  );
}

export default EventDashboard;