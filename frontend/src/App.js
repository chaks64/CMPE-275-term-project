import "./App.css";
import { Route, Routes } from "react-router-dom";
import SignUp from "./components/SignUp/SignUp";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import Login from "./components/Login/Login";
import NavBar from "./components/NavBar/NavBar";
import Verify from "./components/Verify/Verify";
import Error from "./components/Verify/Error";
import GoogleSignup from "./components/GoogleSignUp/GoogleSignUp";

function App() {
  return (
    <Routes>
      <Route path="/" element={<NavBar />} />
      <Route path="/signup" element={<SignUp />} />
      <Route path="/login" element={<Login />} />
      <Route path="/verify" element={<Verify />} />
      <Route path="/error" element={<Error />} />
      <Route path="/googleSignup" element={<GoogleSignup />} />
    </Routes>
  );
}

export default App;
