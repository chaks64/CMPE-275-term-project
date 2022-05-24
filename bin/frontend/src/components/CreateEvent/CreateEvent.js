import React, { useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./CreateEvent.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { config } from "../../utils/utils";
import { Alert } from "react-bootstrap";

const CreateEvent = () => {
  const [eventInfo, setEventInfo] = useState({
    title: "",
    desc: "",
    start: "",
    end: "",
    deadline: "",
    street: "",
    number: "",
    city: "",
    state: "",
    zip: "",
    maxParticipants: 0,
    minParticipants: 0,
    fees: 0,
    policy: "auto",
  });

  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const onSubmit = async (e) => {
    e.preventDefault();
    if (eventInfo.maxParticipants < eventInfo.minParticipants) {
      alert("Minimum participants should be less than maximum participants");
    } else {
      const user = JSON.parse(localStorage.getItem("user"));
      console.log(user.userId);
      const data = {
        title: eventInfo.title,
        desc: eventInfo.desc,
        start: eventInfo.start,
        end: eventInfo.end,
        deadline: eventInfo.start,
        min: parseInt(eventInfo.minParticipants),
        max: parseInt(eventInfo.maxParticipants),
        policy: eventInfo.policy,
        fees: parseInt(eventInfo.fees),
        userid: user.userId,
        address: {
          street: eventInfo.street,
          number: eventInfo.number,
          city: eventInfo.city,
          state: eventInfo.state,
          zipcode: eventInfo.zip,
        },
      };
      const token1 = await axios
        .post(`${config.backendURL}/event/create`, data)
        .then((response) => {
          console.log(response.data);
          alert("event created");
          navigate("/home");
        })
        .catch((error) => {
          console.log(error.response.data);
          setShow(true);
          if (error.response.data.errorDesc !== undefined) {
            setMessage(error.response.data.errorDesc);
          } else {
            setMessage("Server error please try again");
          }
        });
    }
  };

  const handleStart = (event) => {
    console.log(eventInfo.start === "");
    var d = new Date(); /* midnight in China on April 13th */
    d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
    let two = new Date(event.target.value);
    const diff = (two - d) / (1000 * 60 * 60 * 24);
    console.log(d, "  ", two);
    console.log(diff);
    if (two < d) {
      alert("Please select Future date");
    } else {
      setEventInfo({ ...eventInfo, [event.target.name]: event.target.value });
    }
  };

  const handleEnd = (event) => {
    if (eventInfo.start === "") {
      alert("Please select Start date first");
    } else {
      var d = new Date(eventInfo.start);
      d.toLocaleString();
      let two = new Date(event.target.value);
      const diff = (two - d) / (1000 * 60 * 60 * 24);
      console.log(diff);
      if (two < d) {
        alert("End date should be greater than start date");
      } else {
        setEventInfo({ ...eventInfo, [event.target.name]: event.target.value });
      }
    }
  };

  const handleDead = (event) => {
    if (eventInfo.start === "") {
      alert("Please select Start date first");
    } else {
      let d = new Date(eventInfo.start);
      let two = new Date(event.target.value);
      const diff = (two - d) / (1000 * 60 * 60 * 24);
      console.log(d, "  ", two);
      console.log(diff);
      if (d < two) {
        alert("Registration deadline should be before event start date");
      } else {
        setEventInfo({ ...eventInfo, [event.target.name]: event.target.value });
      }
    }
  };

  const handleChange = (event) => {
    setEventInfo({ ...eventInfo, [event.target.name]: event.target.value });
  };
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
      <div className="main-box">
        <div className="Heading">
          <h2 className="title">Create New Event</h2>
        </div>
        <form onSubmit={onSubmit}>
          <div className="main">
            <div className="left">
              <div className="input-group">
                <label className="label-name">Title</label>
                <input
                  id="title"
                  type="text"
                  name="title"
                  className="form-input"
                  value={eventInfo.title}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="input-group">
                <label className="label-name">Description</label>
                <input
                  id="desc"
                  type="text"
                  name="desc"
                  className="form-input"
                  onChange={handleChange}
                  value={eventInfo.desc}
                />
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">Start Date</label>
                    <input
                      id="start"
                      type="datetime-local"
                      name="start"
                      className="form-input"
                      onChange={handleStart}
                      value={eventInfo.start}
                      required
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">End Date</label>
                    <input
                      id="end"
                      type="datetime-local"
                      name="end"
                      className="form-input"
                      onChange={handleEnd}
                      value={eventInfo.end}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <label className="label-name">Deadline</label>
                <input
                  id="deadline"
                  type="datetime-local"
                  name="deadline"
                  className="form-input"
                  onChange={handleDead}
                  value={eventInfo.deadline}
                />
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">Min Participants</label>
                    <input
                      id="minParticipants"
                      type="number"
                      name="minParticipants"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.minParticipants}
                      required
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">Max Participants</label>
                    <input
                      id="maxParticipants"
                      type="number"
                      name="maxParticipants"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.maxParticipants}
                      required
                    />
                  </div>
                </div>
              </div>
              <div className="input-group">
                <button className="submit-button">Create</button>
              </div>
            </div>
            <div className="right">
              <div className="input-group">
                <label className="label-name">Street</label>
                <input
                  id="street"
                  type="text"
                  name="street"
                  className="form-input"
                  onChange={handleChange}
                  value={eventInfo.street}
                />
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">Apt No.</label>
                    <input
                      id="number"
                      type="text"
                      name="number"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.number}
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">City</label>
                    <input
                      id="city"
                      type="text"
                      name="city"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.city}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">State</label>
                    <input
                      id="state"
                      type="text"
                      name="state"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.state}
                    />
                  </div>
                  <div className="parts1">
                    <label className="label-name">Zip Code</label>
                    <input
                      id="zip"
                      type="text"
                      name="zip"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.zip}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">Fees</label>
                    <input
                      id="fees"
                      type="text"
                      name="fees"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.fees}
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">Policy</label>
                    <select
                      id="policy"
                      name="policy"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.policy}
                    >
                      <option value="auto">Auto Approved</option>
                      <option value="approval">Approval Required</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>
      </div>
      <Footer />
    </>
  );
};

export default CreateEvent;
