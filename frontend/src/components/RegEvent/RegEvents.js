import React, { useState, useEffect } from "react";
import EventCard from "../EventCard/EventCard";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";

const RegEvents = () => {
  const [list, setList] = useState([]);

  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here");
    const user = localStorage.getItem("userid");
    const list1 = await axios
      .get(`${config.backendURL}/event/part/${user}`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <NavBar />
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
            <h1>No Partipated Events</h1>
          )}
        </div>
      </div>
      <Footer />
    </>
  );
};

export default RegEvents;
