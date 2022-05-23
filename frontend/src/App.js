// import "./App.css";
import React, { useState } from "react";
import { Route, Routes } from "react-router-dom";
import SignUp from "./components/SignUp/SignUp";
// import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login/Login";
import NavBar from "./components/NavBar/NavBar";
import Verify from "./components/Verify/Verify";
import Error from "./components/Verify/Error";
import GoogleSignup from "./components/GoogleSignUp/GoogleSignUp";
import Home from "./components/Home/Home";
import CreateEvent from "./components/CreateEvent/CreateEvent";
import EventDetails from "./components/EventDetails/EventDetails";
import MyEvents from "./components/MyEvents/MyEvents";
import ApprovalList from "./components/ApprovalList/ApprovalList";
import Forum from "./components/Forum/Forum";
import RegEvents from "./components/RegEvent/RegEvents";
import EventForum from "./components/EventForum/EventForum";
import PartList from "./components/ApprovalList/PartList";

export const ThemeContext = React.createContext();

function App() {
  const [systemTime, setSystemTime] = useState(new Date());
  const [mimicTime, toggleMimicTime] = useState(false);
  return (
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
        <Route path="/eventDetails" element={<EventDetails />} />
        <Route path="/myEvents" element={<MyEvents />} />
        <Route path="/appList" element={<ApprovalList />} />
        <Route path="/forum/:eventid" element={<Forum />} />
        <Route path="/partList" element={<PartList />} />
        {/* <Route path="/forum" element={<Forum />} /> */}
        <Route path="/regEvent" element={<RegEvents />} />
        {/* <Route path="/participantForum" element={<RegEvents />} /> */}

      </Routes>
    </ThemeContext.Provider>
  );
}

export default App;
