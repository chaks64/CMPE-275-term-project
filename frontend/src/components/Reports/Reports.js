import React from "react";
import { Link } from "react-router-dom";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./reports.css";

const Reports = () => {
  return (
    <>
      <NavBar />

      <div class="container1">
        {/* <div class="row"> */}
          <div class="col-md-6">
            <div class="card1">
              <h3>Participation report</h3>
              <p>
                Statistics of all the participated events
              </p>
              <Link to="participant"><span>View Report</span></Link>
            </div>
          </div>
          <div class="col-md-6">
            <div class="card1">
              <h3>Organizer report</h3>
              <p>
                Statistics of all the organized events
              </p>
            </div>
          </div>
        {/* </div> */}
      </div>

      <Footer />
    </>
  );
};

export default Reports;
