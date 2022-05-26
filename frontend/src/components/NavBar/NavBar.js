import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import "./navbar.css";
import { IconContext } from "react-icons";
import { ReactComponent as Logo } from "../../imgs/logo1.svg";
import { Col } from "react-bootstrap";
import Clock from "react-live-clock";
import axios from "axios";
import { config } from "../../utils/utils";

const NavBar = () => {
  const [temp, setTemp] = useState(localStorage.getItem("clock"));
  let navigate = useNavigate();
  const [sidebar, setSidebar] = useState(false);

  const onLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const [newTime, setNewTime] = useState(new Date());

  const showSidebar = () => {
    setSidebar(!sidebar);
  };

  const changeTime = () => {
    console.log("here");
    setTemp(newTime);
    localStorage.setItem("clock", newTime);
    updateTime();
    
  };

  const setToCurrentTime = () => {
    // setDate(new Date());
    localStorage.setItem("clock", newTime);
    updateTime();
  };

  const updateTime = async (e) => {
    // e.preventDefault();
    console.log("here");
    const data = {
      time: temp,
    };
    const list1 = await axios
      .post(`${config.backendURL}/time/update`, data)
      .then((response) => {
        console.log(response.data);
        window.location.reload(false);
        
      })
      .catch((error) => {
        console.log(error);
       
      });
  };

  return (
    <>
      <IconContext.Provider value={{ color: "#fff" }}>
        <div className="navbar">
          <FaIcons.FaBars className="menu-bars" onClick={showSidebar} />
          <Logo className="logo" />

          <Col md={2}>
            <Clock
              date={`${temp}:00`}
              format={"YYYY-MM-DD, h:mm:ss A"}
              ticking={false}
              style={{ color: "white" }}
            />
          </Col>

          <Col md={2}>
            <input
              type="datetime-local"
              id="deptime"
              name="deptime"
              onChange={(e) => {
                setNewTime(e.target.value);
              }}
              style={{}}
            ></input>
            <button
              onClick={() => {
                changeTime();
              }}
              style={{
                backgroundColor: "#7C0200",
                height: "2rem",
                color: "#FFF",
                marginTop: "5px",
              }}
            >
              Update Date and Time
            </button>
          </Col>

          <Col>
            <button
              onClick={() => {
                setToCurrentTime();
              }}
              style={{
                backgroundColor: "#7C0200",
                color: "white",
                marginTop: "4.2%",
              }}
            >
              Set to Current Date and Time
            </button>
          </Col>
        </div>
        <nav className={sidebar ? "nav-menu active" : "nav-menu"}>
          <ul className="nav-menu-items" onClick={showSidebar}>
            <li className="navbar-toggle">
              {/* <Link to="#" className="menu-bars"> */}
              <AiIcons.AiOutlineClose className="menu-bars" />
              {/* </Link> */}
            </li>
            <li>
              <img
                src="https://gravatar.com/avatar/b447fc9b4586877dafcb5865e84d67e4?s=800&d=robohash&r=x"
                alt="Avatar"
                className="profileImg"
              />
            </li>

            {localStorage.getItem("userid") == null ? (
              <li className="nav-text">
                <Link to="/login">
                  <FaIcons.FaSignInAlt />
                  <span>Login</span>
                </Link>
              </li>
            ) : (
              <>
                <li className="nav-text">
                  <Link to={"/home"}>
                    <AiIcons.AiFillHome />
                    <span style={{ color: "white", display: "block" }}>
                      Home
                    </span>
                  </Link>
                </li>
                <li className="nav-text">
                  <Link to={"/createEvent"}>
                    <FaIcons.FaEdit />
                    <span style={{ color: "white", display: "block" }}>
                      Create Event
                    </span>
                  </Link>
                </li>
                <li className="nav-text">
                  <Link to={"/myEvents"}>
                    <AiIcons.AiOutlineOrderedList />
                    <span style={{ color: "white", display: "block" }}>
                      Organized Events
                    </span>
                  </Link>
                </li>
                <li
                  className={
                    JSON.parse(localStorage.getItem("user")).accountType ===
                    "person"
                      ? "nav-text"
                      : "hide"
                  }
                >
                  <Link to={"/regEvent"}>
                    <AiIcons.AiOutlineOrderedList />
                    <span style={{ color: "white", display: "block" }}>
                      Participated Events
                    </span>
                  </Link>
                </li>

                <li className="nav-text">
                  <Link to={"/reports"}>
                    <FaIcons.FaChartPie />
                    <span style={{ color: "white", display: "block" }}>
                      Reports
                    </span>
                  </Link>
                </li>

                <li className="nav-text">
                  <Link to={"/system"}>
                    <FaIcons.FaChartPie />
                    <span style={{ color: "white", display: "block" }}>
                      System Reports
                    </span>
                  </Link>
                </li>

                <li className="nav-text">
                  {/* <Link to={"/login"}> */}
                    <FaIcons.FaSignOutAlt />
                    <span
                      style={{ color: "white", display: "block" }}
                      onClick={onLogout}
                    >
                      Logout
                    </span>
                  {/* </Link> */}
                </li>
              </>
            )}
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
};

export default NavBar;
