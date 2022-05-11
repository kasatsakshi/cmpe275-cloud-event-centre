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

function App() {
  return (
    <div className="App">
    <Router>
        <Routes>
          <Route path="/" element={<Signup />} />
          <Route path="/register" element={<Register />} />
          <Route path="/oauth" element={<Oauth />} />
          <Route path="/createevent" element={<CreateEvent />} />
          <Route path="/eventdash" element={<EventDashboard />}/>
          <Route path="/temp" element={<Temp />}/>
          <Route path="/eventpage" element={<EventPage />}/>
        </Routes>
    </Router>
    </div>
  );
}

export default App;