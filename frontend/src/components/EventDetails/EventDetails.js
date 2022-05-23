import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import { useLocation, useNavigate } from "react-router-dom";
import "./eventDetails.css";
import { Button } from "react-bootstrap";
import Modal from "react-modal";
import BillPage from "./BillPage";
import EventForum from "../EventForum/EventForum";
import { Alert } from "react-bootstrap";

const EventDetails = () => {
  const location = useLocation();
  const { from } = location.state;
  const [details, setDetails] = useState(from);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [allow, setAllow] = useState(false);
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");

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

  const navigate = useNavigate();

  useEffect(() => {
    eventDetails();
    vefrify();
    // calcTime();
  }, []);

  const eventDetails = async() =>{
    const list1 = await axios
      .get(`${config.backendURL}/event/${details.eventID}`)
      .then((response) => {
        console.log(response.data);
        setDetails(response.data);
      })
      .catch((error) => {
        console.log(error);
        setShow(true);
        if (error.response.data.errorDesc !== undefined) {
          setMessage(error.response.data.errorDesc);
        } else {
          setMessage("Server error please try again");
        }
      });
  }

  const vefrify = () =>{
    console.log(details.participateUser);
    var d = new Date(localStorage.getItem("clock")); /* midnight in China on April 13th */
    d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
    let two = new Date(details.deadline);
    const diff = (two - d) / (1000 * 60 * 60 * 24);
    console.log(d, "  ", two);
    console.log(diff);
    if(details.user.userId === localStorage.getItem("userid")){
      setAllow(true);
    } else if(details.participateUser.length === details.minParticpants){
      setAllow(true);
    } 
    else if(diff < 0){
      setAllow(true);
    } else if(details.status === 'cancel'){
      setAllow(true);
    }
  }

  // const calcTime = () => {
  //   const temp =
  //     Math.abs(
  //       Date.parse(details.startDate) -
  //         Date.parse(localStorage.getItem("clock"))
  //     ) / 36e5;
  //   if (temp > 72) {
  //     setAllow(true);
  //   }
  // };

  const onRegister = (e) => {
    e.preventDefault();
    if (details.policy === "auto" && details.fees > 0) {
      setModalIsOpenToTrue();
    } else {
      getEvents();
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
        if (details.policy === "auto" && details.fees === 0) {
          alert("Registered");
        } else {
          alert("Sent for approval");
        }
        navigate("/home");

        // setList(response.data);
      })
      .catch((error) => {
        console.log(error);
        setShow(true);
        if (error.response.data.errorDesc !== undefined) {
          setMessage(error.response.data.errorDesc);
        } else {
          setMessage("Server error please try again");
        }
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
      {show ? (
        <Alert
          variant="danger"
          onClose={() => setShow(false)}
          dismissible
          className="size"
        >
          <p>{message}</p>
        </Alert>
      ) : (
        console.log(<p></p>)
      )}
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

        <div className="right" style={{border:"1px solid"}}>
          <h1> Signup Forum</h1>
          <EventForum id={details.eventID} type="SignupForum" event={details}/>
        </div>
      </div>
      <Footer />
    </>
  );
};

export default EventDetails;
