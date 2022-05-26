import React, { useEffect, useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./reports.css";
import axios from "axios";
import { config } from "../../utils/utils";
import CountUp from "react-countup";

const Participant = () => {

  const [stats, setStats] = useState({
    noOfSignedUpEvents: 0,
    noOfRejectsAndApprovals: 0,
    noOfFinishedEvents: 0
  });

  useEffect(() => {
    getStats();
  }, []);

  const getStats = async () => {
    const list1 = await axios
      .get(`${config.backendURL}/user/participationReport/${localStorage.getItem("userid")}`)
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
        <div className="data-div">

          <div class="card bg-light m-5" style={{ width: "18rem" }}>
            <div class="card-header">Number of Signed-up events</div>
            <div class="card-body">
              <h1>
                <CountUp
                  start={0}
                  end={stats.noOfSignedUpEvents}
                  duration={1}
                  decimals={0}
                ></CountUp>
              </h1>
              <h5 class="card-title">events</h5>
            </div>
          </div>

          <div class="card bg-light m-5" style={{ width: "18rem" }}>
            <div class="card-header">Number of rejections/approvals for events</div>
            <div class="card-body">
              <h1>
                <CountUp
                  start={0}
                  end={stats.noOfRejectsAndApprovals}
                  duration={1}
                  decimals={0}
                ></CountUp>
              </h1>
              <h5 class="card-title">events</h5>
            </div>
          </div>

          <div class="card bg-light m-5" style={{ width: "18rem" }}>
            <div class="card-header">Number of finished events</div>
            <div class="card-body">
              <h1>
                <CountUp
                  start={0}
                  end={stats.noOfRejectsAndApprovals}
                  duration={1}
                  decimals={0}
                ></CountUp>
              </h1>
              <h5 class="card-title">events</h5>
            </div>
          </div>

        </div>
      </div>
      <Footer />
    </>
  );
};

export default Participant;
