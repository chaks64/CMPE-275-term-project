import React, { useState, useEffect } from "react";
import Footer from "../Footer/Footer";
import NavBar from "../NavBar/NavBar";
import "./Login.css";
import axios from "axios";
import GoogleLogin from "react-google-login";
import { useNavigate } from "react-router-dom";
import Cookies from "js-cookie";
import { config } from "../../utils/utils";
import { Alert } from "react-bootstrap";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setpassword] = useState("");
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const [redirectVar, setRedirectVar] = useState("");

  let navigate = useNavigate();

  useEffect(() => {
    let redirect = "";
    if (localStorage.getItem("user") != null) {
      redirect = navigate("/home");
      setRedirectVar(redirect);
      console.log(redirectVar);
    }
  }, []);

  const responseGoogle = async (resp) => {
    console.log(resp);
    const data = {
      token: resp.tokenId,
      subId: resp.googleId,
    };
    console.log(data);
    // axios.defaults.withCredentials = true;
    const token1 = await axios
      .post(`${config.backendURL}/user/googlesignon`, data)
      .then((response) => {
        console.log(response.status);
        if (response.status === 206) {
          localStorage.setItem("token", resp.tokenId);
          localStorage.setItem("subId", resp.googleId);
          navigate("/googleSignup");
        } else {
          alert("login done");
          Cookies.set("user", response.data.email);
          localStorage.setItem("user", JSON.stringify(response.data));
          localStorage.setItem("userid", response.data.userId);
          localStorage.setItem("clock", new Date());
          navigate("/home");
        }
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

  const onSubmit = async (e) => {
    e.preventDefault();

    const data = {
      email: email,
      password: password,
    };
    // axios.defaults.withCredentials = true;
    const token1 = await axios
      .post(`${config.backendURL}/user/login`, data)
      .then((response) => {
        console.log(response.data.userId);
        localStorage.setItem("user", JSON.stringify(response.data));
        localStorage.setItem("userid", response.data.userId);
        localStorage.setItem("clock", new Date());
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
  };

  return (
    <>
      {redirectVar}
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
                {/* <a
                  class="btn btn-lg btn-google btn-block text-uppercase btn-outline"
                  href="#"
                >
                  <img src="https://img.icons8.com/color/16/000000/google-logo.png" />{" "}
                  Login Using Google
                </a> */}

                <GoogleLogin
                  clientId="155505368995-kkg9c40j38vb7c8lg05m2jke4rmtia4l.apps.googleusercontent.com"
                  buttonText="Login with Google"
                  onSuccess={responseGoogle}
                  onFailure={responseGoogle}
                  cookiePolicy={"single_host_origin"}
                />
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
