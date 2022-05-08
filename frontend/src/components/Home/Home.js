import React, { useState, useEffect } from "react";
import EventCard from "../EventCard/EventCard";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./home.css";
import { ThemeContext } from "../../App";
import { Row, Col, Button } from "react-bootstrap";
import axios from "axios";

const Home = () => {
  const [list, setList] = useState([]);

  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here");
    const list1 = await axios
      .get(`http://localhost:8080/event/list`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
        // console.log(list);
      })
      .catch((error) => {
        console.log(error);
        console.log("error");
      });
  };

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
          {list.length > 0 ? (
            list.map((event,i) => {
              return (
                <div className="col-md-4 mt-3">
                  <EventCard key={i} event={event}/>
                </div>
              );
            })
          ) : (
            <h1>No Events</h1>
          )}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default Home;
