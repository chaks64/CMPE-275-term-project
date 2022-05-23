import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import { useLocation, useNavigate } from "react-router-dom";
import "./eventDetails.css";
import { Alert } from "react-bootstrap";
import { Accordion } from "react-bootstrap";
import StarRatings from "react-star-ratings";
import ParticipantForum from "../EventForum/ParticipantForum";

const PartListsDets = () => {
  const location = useLocation();
  const { from } = location.state;
  const [details, setDetails] = useState(from);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [allow, setAllow] = useState(false);
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const [list, setList] = useState([]);
  const [rate, setRate] = useState(0);

  useEffect(() => {
    getEvents();
  }, []);

  const changeRating = (newRating, name) => {
    setRate(newRating);
  };

  const getEvents = async () => {
    console.log("here ", details);
    const list1 = await axios
      .get(`${config.backendURL}/event/partlist/${details.eventID}/approved`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
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

  const navigate = useNavigate();

  useEffect(() => {
    vefrify();
    // calcTime();
  }, []);

  const vefrify = () => {
    console.log(details.participateUser);
    var d = new Date(
      localStorage.getItem("clock")
    ); /* midnight in China on April 13th */
    d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
    let two = new Date(details.deadline);
    const diff = (two - d) / (1000 * 60 * 60 * 24);
    console.log(d, "  ", two);
    console.log(diff);
    if (details.user.userId === localStorage.getItem("userid")) {
      setAllow(true);
    } else if (details.participateUser.length === details.minParticpants) {
      setAllow(true);
    } else if (diff < 0) {
      setAllow(true);
    } else if (details.status === "cancel") {
      setAllow(true);
    }
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

  return (
    <>
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
          <table className="styled-table">
            <thead>
              <tr>
                <th className="text-center">Participant ID</th>
                <th className="text-center">Participant Email</th>
                <th className="text-center">Participant Name</th>
              </tr>
            </thead>
            <tbody>
              {list.length > 0 ? (
                list.map((user) => (
                  <>
                    <tr key={user.userId}>
                      <td className="text-center">{user.userId}</td>
                      <td className="text-center">{user.email}</td>
                      <td className="text-center">{user.fullName}</td>
                    </tr>
                    <tr>
                      <td align="center" colSpan="3">
                        <Accordion defaultActiveKey={6}>
                          <Accordion.Header>Review</Accordion.Header>
                          <Accordion.Body>
                            Review Participant
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
                                  <textarea></textarea>
                                </td>
                              </tr>
                              <tr>
                                <td align="center" colSpan="2">
                                  <button className="submit-button">
                                    Review
                                  </button>
                                </td>
                              </tr>
                            </table>
                          </Accordion.Body>
                        </Accordion>
                      </td>
                    </tr>
                  </>
                ))
              ) : (
                <h1>No Users</h1>
              )}
            </tbody>
          </table>
        </div>

        <div className="right">
          <h1> Participant Forum</h1>
          <ParticipantForum id={details.eventID} type="SignupForum" event={details} />
        </div>
      </div>
      <Footer />
    </>
  );
};

export default PartListsDets;
