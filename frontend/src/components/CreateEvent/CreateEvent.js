import React, { useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./CreateEvent.css";

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
    maxParticipants: "",
    minParticipants: "",
    fees: 0,
    policy: "",
  });

  const handleChange = (event) => {
    setEventInfo({ ...eventInfo, [event.target.name]: event.target.value });
  };
  return (
    <>
      <NavBar />
      <div className="main-box">
        <div className="Heading">
          <h2 className="title">Create New Event</h2>
        </div>
        <form onSubmit="">
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
                      type="date"
                      name="start"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.start}
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">End Date</label>
                    <input
                      id="end"
                      type="date"
                      name="end"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.end}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts1">
                    <label className="label-name">Min Participants</label>
                    <input
                      id="min"
                      type="number"
                      name="min"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.minParticipants}
                    />
                  </div>

                  <div className="parts1">
                    <label className="label-name">Max Participants</label>
                    <input
                      id="max"
                      type="number"
                      name="max"
                      className="form-input"
                      onChange={handleChange}
                      value={eventInfo.maxParticipants}
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
                      <option value="approal">Approval Required</option>
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
