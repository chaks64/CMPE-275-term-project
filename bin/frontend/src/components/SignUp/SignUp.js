import React, { useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./SignUp.css";
import { useFormik } from "formik";
import { validate } from "../../utils/utils";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from "react-router-dom";
import { config } from "../../utils/utils";

const Signup = () => {
  const navigate = useNavigate();
  const [message, setMessage] = useState("");
  const [error, setError] = useState(false);

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
    };

    // axios.defaults.withCredentials = true;
    const token1 = await axios
      .post(`${config.backendURL}/user/signup`, data)
      .then((response) => {
        console.log(response);
        toast.info("Signup successfull!! Now Verify", {
          position: "top-center",
          autoClose: 5000,
          hideProgressBar: false,
          closeOnClick: true,
          pauseOnHover: true,
          draggable: true,
          progress: undefined,
        });
        navigate('/login')
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <NavBar />
      <ToastContainer
        position="top-center"
        autoClose={5000}
        hideProgressBar={false}
        newestOnTop={false}
        closeOnClick
        rtl={false}
        pauseOnFocusLoss
        draggable
        pauseOnHover
      />
      <div className="main-box">
        <div className="Heading">
          <h2 className="title">Join MJ Events</h2>
        </div>
        <form onSubmit={onSubmit}>
          <div className="main">
            <div className="left">
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
                <label className="label-name">Password</label>
                <input
                  id="password"
                  name="password"
                  type="password"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.password}
                />
                {formik.touched.password && errors.password ? (
                  <div className="error">{errors.password} </div>
                ) : null}
              </div>
              <div className="input-group">
                <label className="label-name">Confirm Password</label>
                <input
                  id="cpassword"
                  name="cpassword"
                  type="password"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.cpassword}
                />
                {formik.touched.cpassword && errors.cpassword ? (
                  <div className="error">{errors.cpassword} </div>
                ) : null}
              </div>

              <div className="input-group">
                <button className="submit-button">Join</button>
              </div>
            </div>
            <div className="right">
              <div className="input-group">
                <label className="label-name">Description</label>
                <input
                  id="desc"
                  type="desc"
                  name="desc"
                  className="form-input"
                  onBlur={formik.handleBlur}
                  onChange={formik.handleChange}
                  value={formik.values.desc}
                />
              </div>
              <div className="input-group">
                <label className="label-name">Street</label>
                <input
                  id="street"
                  type="street"
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
                      type="city"
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
                      type="state"
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
                      type="zip"
                      name="zip"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.zip}
                    />
                  </div>
                </div>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts">
                    <label className="label-name">Account Type</label>

                    <select
                      id="accType"
                      type="accType"
                      name="accType"
                      className="form-input"
                      onBlur={formik.handleBlur}
                      onChange={formik.handleChange}
                      value={formik.values.accType}
                    >
                      <option value="person">Person</option>
                      <option value="organization">Organization</option>
                    </select>
                  </div>
                  <div className="parts">
                    <label className="label-name">Gender</label>
                    <select
                      id="gender"
                      type="gender"
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
                </div>
              </div>

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

export default Signup;
