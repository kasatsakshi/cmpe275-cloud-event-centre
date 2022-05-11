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
    <Router>
      <div className="App">
        <header className="App-header">
        </header>
        {/* <Routes>
          <Route path="/" element={<Home />} />
        </Routes> */}
        <Routes>
          <Route path="/" element={<Signup />} />
        </Routes>
        <Routes>
          <Route path="/register" element={<Register />} />
        </Routes>
        <Routes>
          <Route path="/oauth" element={<Oauth />} />
        </Routes>
        <Routes>
          <Route path="/createevent" element={<CreateEvent />} />
        </Routes>
        <Routes>
        <Route path="/eventdash" element={<EventDashboard />}/>
        </Routes>
        <Routes>
        <Route path="/temp" element={<Temp />}/>
        </Routes>
        <Routes>
        <Route path="/eventpage" element={<EventPage />}/>
        </Routes>
      </div>
    </Router>
  );
}

export default App;