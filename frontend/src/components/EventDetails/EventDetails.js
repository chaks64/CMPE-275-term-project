import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import { useLocation } from "react-router-dom";
import "./eventDetails.css";
import { Button } from "react-bootstrap";
import Modal from "react-modal";
import BillPage from "./BillPage";
import EventForum from "../EventForum/EventForum";
const EventDetails = () => {
  const location = useLocation();
  const { from } = location.state;
  const [details, setDetails] = useState(from);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [allow, setAllow] = useState(false);
  const customStyles = {
    content: {
      top: "50%",
      left: "50%",
      right: "auto",
      bottom: "auto",
      marginRight: "-50%",
      transform: "translate(-50%, -50%)",
      // backgroundColor       : '#F0AA89'
    },
  };

  useEffect(() => {
    calcTime();
  }, []);

  const calcTime = () => {
    const temp =
      Math.abs(
        Date.parse(details.startDate) -
          Date.parse(localStorage.getItem("clock"))
      ) / 36e5;
    if (temp > 72) {
      setAllow(true);
    }
  };

  const onRegister = (e) => {
    e.preventDefault();




    if (details.policy === "auto" && details.fees > 0) {
      setModalIsOpenToTrue();
    } else if (details.policy === "auto" && details.fees === 0) {
      getEvents();
      alert("Registered");
    } else {
      getEvents();
      alert("Sent for approval");
      
    }
  };

  const setModalIsOpenToTrue = () => {
    console.log("here");
    setModalIsOpen(!modalIsOpen);
  };

  const getEvents = async () => {
    console.log("here");
    const user = JSON.parse(localStorage.getItem("user"));
    const data = {
      userid: user.userId,
      eventid: details.eventID,
    };
    const list1 = await axios
      .post(`${config.backendURL}/event/register`, data)
      .then((response) => {
        console.log(response.data);
        // setList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      {console.log(details.startDate)}
      {console.log(
        details.startDate,
        "++++++++++++++++++++++++++++++++",
        localStorage.getItem("clock")
      )}
      {console.log(
        Math.abs(
          Date.parse(details.startDate) -
            Date.parse(localStorage.getItem("clock"))
        ) / 36e5
      )}
      <NavBar />
      <div className="container1">
        <div className="left">
          <h1>Event ID: {details.eventID}</h1>
          <h1>Title: {details.title}</h1>
          <table className="center">
            <tr style={{ fontSize: "30px" }}>
              <td>Description:</td>
              <td>{details.description}</td>
            </tr>
            <tr>
              <td>Location:</td>
              <td>
                {details.address.street}, {details.address.number},{" "}
                {details.address.city}, {details.address.state},{" "}
                {details.address.zipcode}
              </td>
            </tr>
            <tr>
              <td>Min Participants:</td>
              <td>{details.minParticpants}</td>
            </tr>
            <tr>
              <td>Max Participants:</td>
              <td>{details.maxParticpants}</td>
            </tr>
            <tr>
              <td>Fees:</td>
              <td>${details.fees}</td>
            </tr>
            <tr>
              <td>Current Participants:</td>
              <td>{details.participateUser.length}</td>
            </tr>
          </table>
          <Button
            disabled={allow}
            variant="primary"
            style={{ backgroundColor: "black" }}
            onClick={onRegister}
          >
            Register
          </Button>

          <Modal
            isOpen={modalIsOpen}
            // style={customStyles}
            onRequestClose={() => setModalIsOpen(false)}
          >
            <button onClick={setModalIsOpenToTrue}>x</button>
            <BillPage details={details} />
          </Modal>
        </div>

        <div className="right">
          <h1>Forum</h1>
          <EventForum id={details.eventID} type='SignupForum'/>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default EventDetails;
