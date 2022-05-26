import React, { useEffect, useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./reports.css";
import axios from "axios";
import { config } from "../../utils/utils";
import CountUp from "react-countup";

const Organizer = () => {
  const [stats, setStats] = useState({
    PercentageOfPaidEvents:0,
    NumberOfFinishedEvents:0,
    NumberOfFinishedPaidEvents:0,
    TotalParticipantsToMinimumParticipantsRatio:0,
    AverageParticipantsInFinishedEvents:0,
    TotalRevenueFinishedPaidEvents:0,
    NumberOfEventsCreated:0,
    NumOfCancelledEvents:0
  });

  useEffect(() => {
    getStats();
  }, []);

  const getStats = async () => {
    const list1 = await axios
      .get(`${config.backendURL}/user/organizerreport/${localStorage.getItem("userid")}`)
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
    <NavBar/>
      <div className="main-div">
        <center>
          <h1>Organizer Report Statistics</h1>
        </center>
        <div className="data-div">
          <div className="row">


          <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Number of created events</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.NumberOfEventsCreated}
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
                    end={stats.PercentageOfPaidEvents}
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
                    end={stats.NumOfCancelledEvents}
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
                    end={stats.TotalParticipantsToMinimumParticipantsRatio}
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
                    end={stats.NumberOfFinishedEvents}
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
                    end={stats.AverageParticipantsInFinishedEvents}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            
            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Number of paid events finished</div>
              <div class="card-body">
                <h1>
                  <CountUp
                    start={0}
                    end={stats.NumberOfFinishedPaidEvents}
                    duration={1}
                    decimals={0}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>

            <div class="card bg-light m-5 col-md-6" style={{ width: "18rem" }}>
              <div class="card-header">Total Revenue from finished events</div>
              <div class="card-body">
                <h1>
                 $ <CountUp
                    start={0}
                    end={stats.TotalRevenueFinishedPaidEvents}
                    duration={1}
                    decimals={1}
                  ></CountUp>
                </h1>
                <h5 class="card-title">events</h5>
              </div>
            </div>


          </div>
        </div>
      </div>
      <Footer/>
    </>
  );
};

export default Organizer;
