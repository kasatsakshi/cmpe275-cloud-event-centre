import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Home from './components/Home';
import Signup from './components/Signup';
import Register from './components/Register';
import Oauth from './components/Oauth';
import CreateEvent from './components/CreateEvent';
import EventDashboard from './components/EventDashboard';
import EventPage from './components/EventPage';
import Temp from './components/Temp';
import { useSelector } from "react-redux";


function App() {
  const user = useSelector((state) => state.user.currentUser);
  console.log("In app the user: " + JSON.stringify(user));
  return (
    <div className="App">
      <Router>
        <Routes>
          <Route path="/" element={user ? <Navigate to="eventdash" /> : <Signup />} />
          <Route path="/register" element={user ? <EventDashboard /> : <Register />} />
          <Route path="/createevent" element={<CreateEvent />} />
          <Route path="/eventdash" element={<EventDashboard />} />
          <Route path="/temp" element={<Temp />} />
          <Route path="/eventpage" element={<EventPage />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;