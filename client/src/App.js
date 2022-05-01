import logo from './logo.svg';
import './App.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Home from './components/Home';
import Signup from './components/Signup';
import Register from './components/Register';
import Oauth from './components/Oauth';

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
      </div>
    </Router>
  );
}

export default App;
