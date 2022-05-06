import React, { useState } from "react";
import { Link } from "react-router-dom";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
import { SidebarData } from "./SidebarData";
import "./navbar.css";
import { IconContext } from "react-icons";
import { ReactComponent as Logo } from '../../imgs/logo1.svg'

const NavBar = () => {
  const [sidebar, setSidebar] = useState(false);
  const showSidebar = () => {
    setSidebar(!sidebar);
  };
  return (
    <>
      <IconContext.Provider value={{color:'#fff'}}>
        <div className="navbar">
          <Link to="#" className="menu-bars">
            <FaIcons.FaBars onClick={showSidebar} />
          </Link>
          {/* <img src="./Marriott_logo_PNG9.png" alt="Avatar" className="profileImg"/> */}
          <Logo className="logo"/>
        </div>
        <nav className={sidebar ? "nav-menu active" : "nav-menu"}>
          <ul className="nav-menu-items" onClick={showSidebar}>
            <li className="navbar-toggle">  
              <Link to="#" className="menu-bars">   
                <AiIcons.AiOutlineClose />
              </Link>
            </li>
            <li>
            <img src="https://gravatar.com/avatar/b447fc9b4586877dafcb5865e84d67e4?s=800&d=robohash&r=x" alt="Avatar" className="profileImg"/>
            
            </li>
            {SidebarData.map((item, index) => {
              return (
                <li key={index} className={item.cName}>
                  <Link to={item.path}>
                    {item.icon}
                    <span>{item.title}</span>
                  </Link>
                </li>
              );
            })}
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
};

export default NavBar;
