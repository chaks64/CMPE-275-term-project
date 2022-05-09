import React from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
// import "../SignUp/SignUp.css";

const Verify = () => {
  return (
    <>
      <NavBar />
      <center>
        <h1>Account/User verified...</h1>
        <a href="/signup">
          <h2>Click here to Login</h2>
        </a>
      </center>
      <Footer />
    </>
  );
};

export default Verify;
