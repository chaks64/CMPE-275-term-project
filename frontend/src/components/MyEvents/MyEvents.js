import React, { useState, useEffect } from "react";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import "./myEvent.css";
import Footer from "../Footer/Footer";
import MyEventCard from "../MyEventCard/MyEventCard";

const MyEvents = () => {
  const [list, setList] = useState([]);

  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here");
    const user = localStorage.getItem("userid");
    const list1 = await axios
      .get(`${config.backendURL}/event/mylist/${user}`)
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
                  <MyEventCard key={i} event={event} />
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

export default MyEvents;
