import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import axios from "axios";
import "./temp.css";
import moment from "moment";
import { Link } from "react-router-dom";
import EventNavbar from "./EventNavbar";
import { Button } from "@mui/material";

function EventDashboard() {
  const [events, setEvents] = useState([]);
  const [filteredEvents, setFilteredEvents] = useState([]);
  const [cityEvents, setCityEvents] = useState([]);
  const [cities, setCities] = useState([]);
  const [searchKey, setSearchKey] = useState("");
  const [type, setType] = useState("all");
  const [city, setCity] = useState("all");
  const [fromDate, setFromDate] = useState(
    moment(new Date()).format("YYYY-MM-DD")
  );
  const [toDate, setToDate] = useState("");

  // var location = [{ city: "San Jose" }];

  // function assignFromDate(e) {
  //   e.preventDefault();
  //   setFromDate(e.target.value);
  //   //setFromDate(e);
  //   console.log(e.target.value);
  //   console.log(new Date().toLocaleDateString("en-ca"));
  //   const events = filteredEvents.filter(
  //     (a) => moment(new Date()).subtract(new Date(e.target.value)).days() >= 0
  //   );
  //   setFilteredEvents(events);
  // }

  function ToDate(e) {
    setToDate(e);
    let events1 = filteredEvents.filter(
      (a) => new Date(a.endTime) - new Date(e) <= 0
    );
    events1 = events1.filter(
      (a) => new Date(a.endTime) - new Date(fromDate) >= 0
    );
    setFilteredEvents(events1);
  }

  useEffect(() => {
    try {
      // setLoading(true);
      axios
        .get("/api/event/all")
        .then((response) => {
          console.log(response.data);
          setEvents(response.data);
          setFilteredEvents(response.data);
          setCityEvents(
            response.data.filter(
              (li, idx, self) =>
                self.map((itm) => itm.address.city).indexOf(li.address.city) ===
                idx
            )
          );
          console.log("City Events: ", cityEvents);
          let location = [];
          response.data.forEach((event) => {
            // let loc = {
            //   city: event.address.city,
            // };
            if (!location.includes(event.address.city)) {
              location.push(event.address.city);
            }
          });
          // location = location.filter(
          //   (li, idx, self) =>
          //     self.map((itm) => itm.city).indexOf(li.city) === idx
          // );
          setCities(location);
        })
        .catch((err) => {
          console.log(err);
        });
      // setDuplicateEvents(eventsDup);

      // setLoading(false);
    } catch (error) {
      console.log(error);
      // setLoading(false);
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
    let eventsFiltered = events;
    // console.log(eventsFiltered);
    if (searchKey !== "") {
      // console.log(searchKey);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.title.toLowerCase().includes(searchKey.toLowerCase())
      );
      // console.log(eventsFiltered);
    }
    if (type !== "all") {
      // console.log(type);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.status.toLowerCase().includes(type.toLowerCase())
      );
      // console.log(eventsFiltered);
    }
    if (city !== "all") {
      // console.log(city);
      eventsFiltered = eventsFiltered.filter((event) =>
        event.address.city.toLowerCase().includes(city.toLowerCase())
      );
    }
    eventsFiltered = eventsFiltered.filter((event) => {
      let stDate = moment(event.startTime).format("YYYY-MM-DD");
      let rangeDate = moment(fromDate).format("YYYY-MM-DD");
      return stDate >= rangeDate;
    });
    setFilteredEvents(eventsFiltered);
  };

  const clear = () => {
    setSearchKey("");
    setType("all");
    setCity("all");
    let today = new Date();
    setFromDate(moment(today).format("YYYY-MM-DD"));
    setFilteredEvents(events);
    // console.log(filteredEvents);
  };

  return (
    <div>
      <EventNavbar />
      <div className="container">
        {/* Filters */}
        <div className="cont filters" style={{ height: "100px" }}>
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
                  setCity(e.target.value);
                }}
              >
                <option value="all">All</option>
                {cities.map((data, idx) => (
                  <option key={idx}>{data}</option>
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
                  id="start-date"
                  min={moment().subtract(10, "days").format("YYYY-MM-DD")}
                  value={fromDate}
                  onChange={(e) => {
                    setFromDate(e.target.value);
                    // assignFromDate(e);
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
                  id="end-date"
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
        {filteredEvents.length !== 0 ? (
          <div
            className="row events"
            style={{ padding: "20px", justifyContent: "flex-start" }}
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
                        {moment(room.startTime).format("MM-DD-YYYY hh:mm a")}
                      </h6>
                      <h6 className="card-subtitle mb-2 text-muted">
                        <em>Ends On:</em>{" "}
                        {moment(room.endTime).format("MM-DD-YYYY hh:mm a")}
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
                          onClick={() =>
                            localStorage.setItem("eventId", room.id)
                          }
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
        ) : (
          <div style={{ textAlign: "center", fontSize: "1.5rem" }}>
            No events found.
          </div>
        )}
      </div>
    </div>
  );
}

export default EventDashboard;
