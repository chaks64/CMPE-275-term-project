import React from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import CountUp from "react-countup";

const Participant = () => {
  return (
    <>
      <NavBar />
      <div className="main-div">
        <div className="data-div">
          <div class="card bg-light m-5" style={{ width: "18rem" }}>
            <div class="card-header">Number of created events</div>
            <div class="card-body">
              <h1>
                <CountUp
                  start={0}
                  end={11.1134}
                  duration={2.75}
                  decimals={4}
                ></CountUp>
              </h1>
              <h5 class="card-title">events</h5>
            </div>
          </div>

          <div class="card bg-light m-5" style={{ width: "18rem" }}>
            <div class="card-header">Number of created events</div>
            <div class="card-body">
              <h1>
                <CountUp
                  start={0}
                  end={11.1134}
                  duration={2.75}
                  decimals={4}
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
