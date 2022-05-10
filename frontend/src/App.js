import "./App.css";
import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import SignUp from "./components/SignUp/SignUp";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login/Login";
import NavBar from "./components/NavBar/NavBar";
import Verify from "./components/Verify/Verify";
import Error from "./components/Verify/Error";
import GoogleSignup from "./components/GoogleSignUp/GoogleSignUp";
import Home from "./components/Home/Home";
<<<<<<< Updated upstream
import CreateEvent from "./components/CreateEvent/CreateEvent";

export const ThemeContext = React.createContext();
=======
import EventCard from "./components/EventCard/EventCard";

>>>>>>> Stashed changes

function App() {
  const [systemTime, setSystemTime] = useState(new Date());
  const [mimicTime, toggleMimicTime] = useState(false);
  return (
<<<<<<< Updated upstream
    <ThemeContext.Provider
      value={{ systemTime, setSystemTime, mimicTime, toggleMimicTime }}
    >
      <Routes>
        <Route path="/" element={<NavBar />} />
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<Login />} />
        <Route path="/verify" element={<Verify />} />
        <Route path="/error" element={<Error />} />
        <Route path="/googleSignup" element={<GoogleSignup />} />
        <Route path="/home" element={<Home />} />
        <Route path="/createEvent" element={<CreateEvent />} />
      </Routes>
    </ThemeContext.Provider>
=======
    <Routes>
      <Route path="/" element={<NavBar />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/login" element={<Login />} />
      <Route path="/verify" element={<Verify />} />
      <Route path="/error" element={<Error />} />
      <Route path="/googleSignup" element={<GoogleSignup />} />
      <Route path="/home" element={<Home />} />
        <Route path="/event/details" element={<EventCard />} />

    </Routes>
>>>>>>> Stashed changes
  );
}

export default App;
