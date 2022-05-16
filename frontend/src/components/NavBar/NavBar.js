import React, { useState, useContext } from "react";
import { Link, useNavigate } from "react-router-dom";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import { SidebarData } from "./SidebarData";
import "./navbar.css";
import { IconContext } from "react-icons";
import { ReactComponent as Logo } from "../../imgs/logo1.svg";
import { ThemeContext } from "../../App";
import { Button, Col } from "react-bootstrap";
import Clock from "react-live-clock";

const NavBar = () => {
  const [temp, setTemp] = useState(localStorage.getItem("clock"));
  let navigate = useNavigate();
  // const [, setCount] = useState(0);
  const [sidebar, setSidebar] = useState(false);
  // const { systemTime, setSystemTime, mimicTime, toggleMimicTime } =
  //   useContext(ThemeContext);
  // let time = new Date().toLocaleTimeString();
  const [date, setDate] = useState(new Date().toLocaleTimeString());

  const onLogout = () => {
    localStorage.clear();
    navigate("/login");
  };
  // function useForceUpdate() {
  //   const [value, setValue] = useState(0); // integer state
  //   return () => setValue((value) => value + 1); // update the state to force render
  // }
  const [newTime, setNewTime] = useState(new Date());

  const updateTime = () => {
    let time = new Date().toLocaleTimeString();
    setDate(time);
  };
  setInterval(updateTime, 1000);

  const showSidebar = () => {
    setSidebar(!sidebar);
  };

  const changeTime = () => {
    console.log("here");
    setTemp(newTime);
    localStorage.setItem("clock", newTime);
    window.location.reload(false);
  };

  const setToCurrentTime = () => {
    setDate(new Date());
    localStorage.setItem("clock", newTime);
    window.location.reload(false);
  };
  return (
    <>
      <IconContext.Provider value={{ color: "#fff" }}>
        <div className="navbar">
          {/* <Link to="" className="menu-bars"> */}
            <FaIcons.FaBars className="menu-bars" onClick={showSidebar} />
          {/* </Link> */}
          <Logo className="logo" />

          <Col md={2}>
            <Clock
              date={`${temp}:00`}
              format={"YYYY-MM-DD, h:mm:ss A"}
              ticking={true}
              style={{ color: "white" }}
            />
          </Col>

          {/* <Col md={2}>
            <p style={{ color: "white" }}>
              System Time : {systemTime.toLocaleTimeString()}
            </p>
            <p style={{ color: "white" }}>
              System Date : {systemTime.toLocaleDateString()}
            </p>
          </Col> */}

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
              style={{ backgroundColor: "#7C0200", color: "white" ,marginTop:'4.2%'}}
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

            {(localStorage.getItem("userid") == null) ?
              <li className="nav-text">
              <Link to='/login'>
                <FaIcons.FaSignInAlt/>
                <span>Login</span>
              </Link>
            </li>
            : (
              SidebarData.map((item, index) => {
                return (
                  <li key={index} className={item.cName}>
                    <Link to={item.path}>
                      {item.icon}
                      <span>{item.title}</span>
                    </Link>
                  </li>
                );
              })
            )}
            <li className="nav-text">
              <Link to={"/login"}>
                <FaIcons.FaSignOutAlt />
                <span
                  style={{ color: "white", display: "block" }}
                  onClick={onLogout}
                >
                  Logout
                </span>
              </Link>
            </li>
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
};

export default NavBar;
