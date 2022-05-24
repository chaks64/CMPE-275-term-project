import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import { useLocation, useNavigate } from "react-router-dom";
import "./eventDetails.css";
import { Alert } from "react-bootstrap";
import ParticipantForum from "../EventForum/ParticipantForum";
import StarRatings from "react-star-ratings";

const PartEventDets = () => {
  const location = useLocation();
  const { from } = location.state;
  const [details, setDetails] = useState(from);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [allow, setAllow] = useState(false);
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const [rate, setRate] = useState(0);
  const [review, setReview] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    eventDetails();
    // vefrify();
    // calcTime();
  }, []);

  const handleReview = (e) => {
    setReview(e.target.value);
  };

  const eventDetails = async () => {
    const list1 = await axios
      .get(`${config.backendURL}/event/${details.eventID}`)
      .then((response) => {
        console.log(response.data);
        setDetails(response.data);
        vefrify();
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

  const changeRating = (newRating, name) => {
    setRate(newRating);
  };

  const vefrify = () => {
    console.log(details.participateUser);
    var d = new Date(
      localStorage.getItem("clock")
    ); /* midnight in China on April 13th */
    d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
    let two = new Date(details.startDate);
    let one = new Date(details.endtDate);
    const diff = (two - d) / (1000 * 60 * 60 * 24);
    const diff2 = (d - one)/(1000 * 60 * 60 * 24);
    console.log(d, "  ", two);
    console.log(diff2);
    if (diff > 0) {
      setAllow(true);
    } else if (diff2 > 7) {
      setAllow(true);
    }
  };

  const onSubmit = async (e) => {
    e.preventDefault();
    const data = {
      userid: details.user.userId,
      rating: rate,
      review: review,
      type: "organizer"
    };
    console.log(data);
    const list1 = await axios
      .post(`${config.backendURL}/review/post`, data)
      .then((response) => {
        console.log(response.data);
        setShow(false);
        alert("Reviewed Organizer");
      })
      .catch((error) => {
        console.log(error.response.data);
        setShow(true);
        if (error.response.data.errorDesc === undefined) {
          setMessage("Server error please try again");
        } else {
          setMessage(error.response.data.errorDesc);
        }
      });
  };

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
              <td>Start Date:</td>
              <td>{new Date(details.startDate).toDateString()}</td>
            </tr>
            <tr>
              <td>End Date:</td>
              <td>{new Date(details.endtDate).toDateString()}</td>
            </tr>
          </table>
          <div className={allow ? "hide": ""} style={{ border: "1px solid #1c1c1c" }}>
            Review Organizer
            <table>
              <tr>
                <td>Rate</td>
                <td>
                  <StarRatings
                    rating={rate}
                    starRatedColor="blue"
                    changeRating={changeRating}
                    numberOfStars={5}
                    name="rating"
                    starDimension="20px"
                  />
                </td>
              </tr>
              <tr>
                <td>Review</td>
                <td>
                  <textarea
                    value={review}
                    name="review"
                    onChange={handleReview}
                  />
                </td>
              </tr>
              <tr>
                <td align="center" colSpan="2">
                  <button className="submit-button" onClick={onSubmit}>Review</button>
                </td>
              </tr>
            </table>
          </div>
        </div>

        <div className="right" style={{ border: "1px solid" }}>
          <h1>Participant Forum</h1>
          <ParticipantForum
            id={details.eventID}
            type="SignupForum"
            event={details}
          />
        </div>
      </div>
      <Footer />
    </>
  );
};

export default PartEventDets;
