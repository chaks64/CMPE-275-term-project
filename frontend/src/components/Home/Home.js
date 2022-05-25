import React, { useState, useEffect } from "react";
import EventCard from "../EventCard/EventCard";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./home.css";
import { ThemeContext } from "../../App";
import { Row, Col, Button } from "react-bootstrap";
import axios from "axios";
import { config } from "../../utils/utils";

const Home = () => {
  const [list, setList] = useState([]);
  const [search, setSearch] = useState({
    location:"",
    status:"",
    start:"",
    end:"",
    keyword:"",
    organizer:""
  });

  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here");
    const list1 = await axios
      .get(`${config.backendURL}/event/list`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const handleChange = (event) => {
    setSearch({ ...search, [event.target.name]: event.target.value });
  };

  const filterEvents = async() =>{
    console.log("here ",search);
    const data = {
      location: search.location,
      status: search.status,
      startTime: search.start,
      endtTime: search.end,
      keyword: search.keyword,
      organizer: search.organizer,
    }
    const list1 = await axios
      .post(`${config.backendURL}/event/search`,data)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  }

  return (
    <>
      <NavBar />
      <div className="search-class">
        <div className="search-inp">
          <div className="input-group">
            <label className="label-name">Location</label>
            <input
              id="location"
              type="text"
              className="form-input no-padding"
              placeholder="Location"
              name="location"
              value={search.location}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <label className="label-name">Status</label>
            <input
              id="status"
              type="text"
              className="form-input no-padding"
              placeholder="Status"
              name="status"
              value={search.status}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <label className="label-name">Start-time</label>
            <input
              id="start"
              type="datetime-local"
              className="form-input no-padding"
              name="start"
              value={search.start}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <label className="label-name">End-time</label>
            <input
              id="end"
              type="datetime-local"
              className="form-input no-padding"
              name="end"
              value={search.end}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <label className="label-name">Keyword</label>
            <input
              id="keyword"
              type="text"
              className="form-input no-padding"
              placeholder="Keyword"
              name="keyword"
              value={search.keyword}
              onChange={handleChange}
            />
          </div>
          <div className="input-group">
            <label className="label-name">Organizer</label>
            <input
              id="organizer"
              type="text"
              className="form-input no-padding"
              placeholder="Organizer"
              name="organizer"
              value={search.organizer}
              onChange={handleChange}
            />
          </div>
        </div>

        <div className="margin">
          <button type="button" onClick={filterEvents} className="btn btn-outline-primary">
            search
          </button>
        </div>
      </div>
      <div className="container main-one">
        <div className="row">
          {list.length > 0 ? (
            list.map((event, i) => {
              return (
                <div className="col-md-4 mt-3">
                  <EventCard key={i} event={event} />
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
