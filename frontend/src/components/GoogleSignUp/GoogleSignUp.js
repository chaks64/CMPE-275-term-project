import React, { useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./GoogleSignUp.css";
import { useFormik } from "formik";
import { validate, config } from "../../utils/utils";
import axios from "axios";
import "react-toastify/dist/ReactToastify.css";
import { useLocation, useNavigate } from "react-router-dom";
import { Alert } from "react-bootstrap";

const GoogleSignup = () => {
  const navigate = useNavigate();
  const [show, setShow] = useState(false);
  const { state } = useLocation();
  const [message, setMessage] = useState("");
  

  const initialValues = {
    email: "",
    password: "", 
    firstname: "",
    lastname: "",
    cpassword: "",
    desc: "",
    street: "",
    number: "",
    city: "",
    state: "",
    zip: "",
    accType: "",
    gender: "",
  };

  const formik = useFormik({
    initialValues,
    validate,
  });

  const errors = validate(formik.values);

  const onSubmit = async (e) => {
    e.preventDefault();
    console.log("here");
    const data = {
      email: formik.values.email,
      password: formik.values.password,
      accountType: formik.values.accType,
      fullName: formik.values.firstname,
      screenName: formik.values.lastname,
      gender: formik.values.gender,
      description: formik.values.desc,
      address: {
        street: formik.values.street,
        number: formik.values.number,
        city: formik.values.city,
        state: formik.values.state,
        zipcode: formik.values.zip,
      },
      token: localStorage.getItem("token"),
      subId: localStorage.getItem("subId"),
    };
    // axios.defaults.withCredentials = true;
    const token1 = await axios
      .post(`${config.backendURL}/user/googlesignup`, data)
      .then((response) => {
        console.log(response);
        navigate("/login");
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
          <h2 className="title">Join MJ Events</h2>
        </div>
        <form onSubmit={onSubmit}>
          <div className="main">
            <div className="left">
              <div className="input-group">
                <label className="label-name">Email</label>
                <input
                  id="email"
                  type="email"
                  name="email"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.email}
                />
                {formik.touched.email && errors.email ? (
                  <div className="error">{errors.email} </div>
                ) : null}
              </div>

              <div className="input-group">
                <label className="label-name">Account Type</label>
                <select
                  id="accType"
                  type="accType"
                  name="accType"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.accType}
                  defaultValue="preson"
                  required
                >
                  <option value="person">Person</option>
                  <option value="organization">Organization</option>
                </select>
              </div>

              <div className="input-group">
                <label className="label-name">Full Name</label>
                <input
                  id="firstname"
                  type="text"
                  name="firstname"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.firstname}
                />
                {formik.touched.firstname && errors.firstname ? (
                  <div className="error">{errors.firstname} </div>
                ) : null}
              </div>
              <div className="input-group">
                <label className="label-name">Screen Name</label>
                <input
                  id="lastname"
                  type="text"
                  name="lastname"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.lastname}
                />
                {formik.touched.lastname && errors.lastname ? (
                  <div className="error">{errors.lastname} </div>
                ) : null}
              </div>
              <div className="input-group">
                <label className="label-name">Description</label>
                <input
                  id="desc"
                  type="text"
                  name="desc"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.desc}
                />
              </div>

              <div className="input-group">
                <button className="submit-button">Join</button>
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
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.street}
                />
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts">
                    <label className="label-name">Apt No.</label>
                    <input
                      id="number"
                      type="number"
                      name="number"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.number}
                    />
                  </div>

                  <div className="parts">
                    <label className="label-name">City</label>
                    <input
                      id="city"
                      type="text"
                      name="city"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.city}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts">
                    <label className="label-name">State</label>
                    <input
                      id="state"
                      type="text"
                      name="state"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.state}
                    />
                  </div>
                  <div className="parts">
                    <label className="label-name">Zip Code</label>
                    <input
                      id="zip"
                      type="number"
                      name="zip"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.zip}
                    />
                  </div>
                </div>
              </div>

              {(() => {
                if (formik.values.accType !== "organization") {
                  return (
                    <div className="input-group">
                      <label className="label-name">Gender</label>
                      <select
                        id="gender"
                        // type="te"
                        name="gender"
                        className="form-input"
                        onBlur={formik.handleBlur}
                        onChange={formik.handleChange}
                        value={formik.values.gender}
                      >
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                        <option value="others">Others</option>
                      </select>
                    </div>
                  );
                }
              })()}

              <div className="input-group">
                <p className="bold-weight">Already a member? </p>
                <a className="link-to" href="/login">
                  Activate online account
                </a>
              </div>
            </div>
          </div>
        </form>
      </div>

      <Footer />
    </>
  );
};

export default GoogleSignup;
