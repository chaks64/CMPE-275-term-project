import React, { useEffect, useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./reports.css";
import axios from "axios";
import { config } from "../../utils/utils";
import CountUp from "react-countup";

const System = () => {
  const [stats, setStats] = useState({
    finishedEvents: 0,
    paidEvents: 0,
    partRatio: 0,
    cancelEvents: 0,
    createdEvents: 0,
    averagePart: 0
  });

  useEffect(() => {
    getStats();
  }, []);

  const getStats = async () => {
    const list1 = await axios
      .get(`${config.backendURL}/event/systemReport`)
      .then((response) => {
        console.log(response.data);
        setStats(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <NavBar />

      <div className="main-div">
      <center><h1>System Report Statistics</h1></center>
        <div className="data-div">
          
          <div className="row">

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Number of created events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.createdEvents}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Percentage of Paid events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.paidEvents}
                    duration={2.75}
                    decimals={2}
                  ></CountUp>
                  %
                </h1>
                <h5 class="card-title">of events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Number of canceled events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.cancelEvents}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Participantation Ratio (request/minimum)</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.partRatio}
                    duration={1}
                    decimals={2}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Finished Events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.finishedEvents}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Average No. of Participants in Finised Events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.averagePart}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

          </div>
        </div>
      </div>

      <Footer />
    </>
  );
};

export default System;
