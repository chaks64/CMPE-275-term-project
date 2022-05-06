import React, { useState } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./Login.css";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setpassword] = useState("");

  const onSubmit = async (e) => {
    e.preventDefault();

    const data = {
      email: email,
      password: password,
    };
    axios.defaults.withCredentials = true;
    const token1 = await axios
      .post(`http://localhost:8080/user/login`, data)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <NavBar />
      <div className="main-box">
        <div className="Heading">
          <h2 className="title">Login to MJ Events</h2>
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
                  onChange={(e) => setEmail(e.target.value)}
                  value={email}
                />
              </div>

              <div className="input-group">
                <label className="label-name">Password</label>
                <input
                  id="password"
                  type="password"
                  name="password"
                  className="form-input"
                  onChange={(e) => setpassword(e.target.value)}
                  value={password}
                />
              </div>

              <div className="input-group google-button">
                {/* <button type="button" class="login-with-google-btn">
                  Sign in with Google
                </button> */}
                <a
                  class="btn btn-lg btn-google btn-block text-uppercase btn-outline"
                  href="#"
                >
                  <img src="https://img.icons8.com/color/16/000000/google-logo.png" />{" "}
                  Login Using Google
                </a>
              </div>

              <div className="input-group">
                <div className="divide">
                  <div className="parts">
                    <button className="submit-button">Join</button>
                  </div>

                  <div className="parts">
                    <p className="bold-weight">New here? </p>
                    <a className="link-to" href="/signup">
                      Create new account
                    </a>
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
}
