import React, { Component } from "react";
import { Route } from "react-router-dom";
import EventDashboard from "./components/EventDashboard";
import Register from "./components/Register";
import SignIn from "./components/SignIn";

class Routes extends Component {
  render() {
    return (
      <div>
        <Route exact path="/" component={SignIn} />
        <Route exact path="/register" component={Register} />
        <Route exact path="/event-dashboard" component={EventDashboard} />
      </div>
    );
  }
}

export default Routes;
