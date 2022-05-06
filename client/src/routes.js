import React, { Component } from "react";
import { Route } from "react-router-dom";
import EventDashboard from "./components/EventDashboard";
import Register from "./components/Register";
import Signup from "./components/Signup";

class Routes extends Component {
    render() {
      return (
          <div>
        <Route exact path="/" component={Signup} />
        <Route exact path="/register" component={Register} />
        <Route exact path="/eventdash" component={EventDashboard} />
        </div>
        );
    }
  }
  
  export default Routes;