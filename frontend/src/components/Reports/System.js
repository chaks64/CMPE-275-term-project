import React from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./reports.css";
// const NumberEasing = require('che-react-number-easing');
import CountUp from "react-countup";

const System = () => {
  return (
    <>
      <NavBar />
      <div className="reportContainer">
        <h1>
          <CountUp
            start={0}
            end={11.1134}
            duration={2.75}
            decimals={4}
          ></CountUp>
        </h1>
      </div>
      <Footer />
    </>
  );
};

export default System;
