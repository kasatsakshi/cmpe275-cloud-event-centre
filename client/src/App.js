import logo from "./logo.svg";
import "./App.css";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import Home from "./components/Home";
import Register from "./components/Register";
import Oauth from "./components/Oauth";
import CreateEvent from "./components/CreateEvent";
import EventDashboard from "./components/EventDashboard";
import EventPage from "./components/EventPage";
import Temp from "./components/Temp";
import { useSelector } from "react-redux";
import SignIn from "./components/SignIn";
import VerifyAccount from "./components/VerifyAccount";

function App() {
  const user = useSelector((state) => state.user.currentUser);
  console.log("In app the user: " + JSON.stringify(user));
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route
            path="/"
            element={user ? <Navigate to="eventdash" /> : <SignIn />}
          />
          <Route
            path="/register"
            element={user ? <EventDashboard /> : <Register />}
          />
          <Route
            path="/createevent"
            element={user ? <CreateEvent /> : <Navigate to="/" />}
          />
          <Route
            path="/eventdash"
            element={user ? <EventDashboard /> : <Navigate to="/" />}
          />
          <Route path="/temp" element={<Temp />} />
          <Route
            path="/eventpage"
            element={user ? <EventPage /> : <Navigate to="/" />}
          />
          <Route
            path="/verifyAccount"
            element={user ? <EventPage /> : <VerifyAccount />}
          />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
