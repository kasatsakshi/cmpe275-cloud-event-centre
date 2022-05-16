import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import Register from "./Register";
import axios from "axios";
import "./temp.css";
import moment from "moment";
import { Link } from "react-router-dom";
import EventNavbar from "./EventNavbar";
import { Button } from "@mui/material";

function EventDashboard() {
  const [eventsDup, setEventsDup] = useState([]);
  const [events, setEvents] = useState([]);
  const [duplicateEvents, setDuplicateEvents] = useState([]);
  const [loading, setLoading] = useState(false);
  const [searchKey, setSearchKey] = useState("");
  const [location, setLocation] = useState([]);
  const [type, setType] = useState("all");
  const [city, setCity] = useState("all");

  const [filteredEvents, setFilteredEvents] = useState([]);
  const [fromDate, setFromDate] = useState(
    new Date().toLocaleDateString("en-ca")
  );
  const [toDate, setToDate] = useState("");

  var locat = [{ city: "San Jose" }];

  // function assignFromDate(e) {
  //   //console.log(e.target.value);
  //   setFromDate(e);
  //   //setFromDate(e);
  //   console.log(e);
  //   console.log(fromDate);
  //   const events = duplicateEvents.filter(
  //     (a) => new Date(a.startTime) - new Date(e) >= 0
  //   );
  //   setEventsDup(events);
  // }

  function ToDate(e) {
    setToDate(e);
    let events1 = duplicateEvents.filter(
      (a) => new Date(a.endTime) - new Date(e) <= 0
    );
    events1 = events1.filter(
      (a) => new Date(a.endTime) - new Date(fromDate) >= 0
    );
    setEventsDup(events1);
  }

  useEffect(() => {
    try {
      setLoading(true);
      axios
        .get("/api/event/all")
        .then((response) => {
          setEventsDup(response.data);
          setDuplicateEvents(response.data);
          console.log(response.data);
          setFilteredEvents(response.data);
          setEvents(
            response.data.filter(
              (li, idx, self) =>
                self.map((itm) => itm.address.city).indexOf(li.address.city) ===
                idx
            )
          );
        })
        .catch((err) => {
          console.log(err);
        });

      console.log(eventsDup);
      setDuplicateEvents(eventsDup);

      events.forEach((item) => {
        var loc = {
          city: item.address.city,
        };
        if (!locat.includes(loc)) {
          locat.push(loc);
        }
      });
      locat = locat.filter(
        (li, idx, self) => self.map((itm) => itm.city).indexOf(li.city) === idx
      );
      setLocation(locat);
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  }, []);

  // function filterBySearch() {
  //   const dupdate = duplicateEvents.filter((room) =>
  //     room.title.toLowerCase().includes(searchKey.toLowerCase())
  //   );
  //   // seteventsdup(dupdate);
  //   if (city !== "" && city !== "all") {
  //     const dupdate2 = dupdate.filter((room) =>
  //       room.address.city.toLowerCase().includes(city.toLowerCase())
  //     );
  //     setEventsDup(dupdate2);
  //   } else {
  //     setEventsDup(dupdate);
  //   }
  // }
  // function filterByStartDate() {
  //   const events = duplicateEvents.filter(
  //     (a) => new Date(a.startDate) - new Date() > 0
  //   );
  //   setEventsDup(events);
  // }
  // function filterByLocation(e) {
  //   setCity(e);
  //   setLocation(e);
  //   if (e !== "all") {
  //     const dupdate = duplicateEvents.filter((room) =>
  //       room.address.city.toLowerCase().includes(e.toLowerCase())
  //     );
  //     setEventsDup(dupdate);
  //   } else {
  //     setEventsDup(duplicateEvents);
  //   }
  // }

  // function filterByType(e) {
  //   setType(e);
  //   if (e !== "all") {
  //     const dupdate = duplicateEvents.filter((room) =>
  //       room.status.toLowerCase().includes(e.toLowerCase())
  //     );
  //     setEventsDup(dupdate);
  //   } else {
  //     setEventsDup(duplicateEvents);
  //   }
  // }

  const filter = () => {
    let eventsFiltered = eventsDup;
    console.log(eventsFiltered);
    if (searchKey !== "") {
      console.log(searchKey);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.title.toLowerCase().includes(searchKey.toLowerCase())
      );
      console.log(eventsFiltered);
    }
    if (type !== "all") {
      console.log(type);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.status.toLowerCase().includes(type.toLowerCase())
      );
      console.log(eventsFiltered);
    }
    if (city !== "all") {
      console.log(city);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.address.city.toLowerCase().includes(city.toLowerCase())
      );
    }
    setFilteredEvents(eventsFiltered);
  };

  const clear = () => {
    setSearchKey("");
    setType("all");
    setLocation("all");
    let today = new Date();
    setFromDate(moment(today).format("YYYY-MM-DD"));
    setFilteredEvents(eventsDup);
    console.log(filteredEvents);
  };

  return (
    <div>
      <EventNavbar />
      <div className="container">
        {/* Filters */}
        <div className="cont" style={{ height: "100px" }}>
          <div className="row bs p-3 m-5">
            <div className="col-md-2">
              <span style={{ opacity: "0.6", fontSize: "13px" }}>
                Search Event
              </span>
              <input
                type="text"
                className="form-control i2 m-2"
                placeholder="Search Events"
                value={searchKey}
                // onKeyUp={filterBySearch}
                onChange={(e) => {
                  setSearchKey(e.target.value);
                }}
              />
            </div>

            <div className="col-md-2">
              <span style={{ opacity: "0.6", fontSize: "13px" }}>
                Event City
              </span>
              <select
                className="form-control m-2"
                value={city}
                onChange={(e) => {
                  // filterByLocation(e.target.value);
                  // setLocation(e.target.value);
                  setCity(e.target.value);
                }}
              >
                <option value="all">All</option>
                {events.map((data, idx) => (
                  <option key={idx}>{data.address.city}</option>
                ))}
              </select>
            </div>

            <div className="col-md-2">
              <span style={{ opacity: "0.6", fontSize: "13px" }}>
                Event Status
              </span>
              <select
                className="form-control m-2"
                value={type}
                onChange={(e) => {
                  // filterByType(e.target.value);
                  setType(e.target.value);
                }}
              >
                <option value="all">All</option>
                <option value="ACTIVE">Active</option>
                <option value="FINISHED">Finished</option>
                <option value="REGISTRATION_OPEN">Open</option>
                <option value="REGISTRATION_CLOSED">Closed</option>
                <option value="CANCELLED">Cancelled</option>
              </select>
            </div>

            <div className="col-md-2">
              <div className="form-group">
                <span style={{ opacity: "0.6", fontSize: "13px" }}>
                  Start Date
                </span>
                <input
                  type="date"
                  name="from"
                  id="startdate"
                  min={new Date().toLocaleDateString("en-ca")}
                  value={fromDate}
                  onChange={(e) => {
                    setFromDate(e.target.value);
                  }}
                  className="form-control datepicker"
                />
              </div>
            </div>

            <div className="col-sm-2">
              <div className="form-group">
                <span style={{ opacity: "0.6", fontSize: "13px" }}>
                  End Date
                </span>
                <input
                  type="date"
                  name="to"
                  min={fromDate}
                  id="enddate"
                  value={toDate}
                  placeholder="Select Date"
                  onChange={(e) => ToDate(e.target.value)}
                  className="form-control datepicker"
                />
              </div>
            </div>
            <Button onClick={filter}>Filter</Button>
            <Button onClick={clear}>Reset Filters</Button>
          </div>
        </div>

        {/* Events Cards */}
        <div
          className="row"
          style={{ padding: "20px", justifyContent: "space-evenly" }}
        >
          {filteredEvents.map((room) => {
            return (
              <div key={room.id} className="row" style={{ padding: "20px" }}>
                <div
                  className="card"
                  style={{ width: "300px", margin: "20px" }}
                >
                  <div className="card-body">
                    <h5 className="card-title">{room.title}</h5>
                    <h6 className="card-subtitle mb-2 text-muted">
                      <em>Starts On:</em>{" "}
                      {moment(room.startTime).format("MM-DD-YYYY HH:mm")}
                    </h6>
                    <h6 className="card-subtitle mb-2 text-muted">
                      <em>Ends On:</em>{" "}
                      {moment(room.endTime).format("MM-DD-YYYY HH:mm")}
                    </h6>
                    <p href="#" className="card-link">
                      {room.address.city}
                    </p>
                    {room.status === "CANCELLED" ? (
                      <p
                        href="#"
                        className="card-link"
                        style={{ color: "red" }}
                      >
                        <b>Cancelled</b>
                      </p>
                    ) : room.status === "REGISTRATION_OPEN" ? (
                      <p
                        href="#"
                        className="card-link"
                        style={{ color: "green" }}
                      >
                        <b>Registration Open</b>
                      </p>
                    ) : room.status === "REGISTRATION_CLOSED" ? (
                      <p
                        href="#"
                        className="card-link"
                        style={{ color: "orange" }}
                      >
                        <b>Registration Closed</b>
                      </p>
                    ) : room.status === "ACTIVE" ? (
                      <p href="#" className="card-link">
                        <b>Event Started</b>
                      </p>
                    ) : room.status === "FINISHED" ? (
                      <p
                        href="#"
                        className="card-link"
                        style={{ color: "red" }}
                      >
                        <b>Finished</b>
                      </p>
                    ) : (
                      <p></p>
                    )}
                    <Link to="/event-page">
                      <button
                        onClick={() => localStorage.setItem("eventId", room.id)}
                      >
                        Details
                      </button>
                    </Link>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      </div>
    </div>
  );
}

export default EventDashboard;
