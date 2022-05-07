import React from "react";
import EventCard from "../EventCard/EventCard";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import { MDBCol, MDBInput } from "mdbreact";
import "./home.css";

const Home = () => {
  return (
    <>
      <NavBar />
      <div className="search-class">
        <div class="input-group">
          <input
            type="search"
            class="form-control rounded"
            placeholder="Search"
            aria-label="Search"
            aria-describedby="search-addon"
          />
          <button type="button" className="btn btn-outline-primary">
            search
          </button>
        </div>
      </div>
      <div className="container main-one">
        <div className="row">
          {[...Array(10)].map((e, i) => {
            return (
              <div className="col-md-4 mt-3">
                <EventCard />
              </div>
            );
          })}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Home;
