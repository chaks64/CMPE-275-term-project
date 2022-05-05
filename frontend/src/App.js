import "./App.css";
import { Route, Routes } from 'react-router-dom'
import SignUp from "./components/SignUp/SignUp";
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import Login from "./components/Login/Login";

function App() {
  return (
        // <div className="auth-wrapper">
        //   <div className="auth-inner">
            <Routes>
            <Route path="/" element={<SignUp/>}/>
            <Route path="/login" element={<Login/>}/>

            </Routes>
        //   </div>
        // </div>
  );
}

export default App;
