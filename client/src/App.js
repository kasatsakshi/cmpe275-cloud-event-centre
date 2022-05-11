import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
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
  return (
    <Router>
      <div className="App">
        <header className="App-header">
        </header>
        <Routes>
          <Route path="/" element={<Signup />} />
          <Route path="/register" element={<Register />} />
          <Route path="/createevent" element={<CreateEvent />} />
          <Route path="/eventdash" element={<EventDashboard />} />
          <Route path="/eventpage" element={<EventPage />} />
          <Route path="/temp" element={<Temp />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;