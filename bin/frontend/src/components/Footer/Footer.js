import React from "react";
import "./footer.css";
import "font-awesome/css/font-awesome.min.css";
import { ListGroup } from "react-bootstrap";

function Footer(props) {
  return (
    <div className="footer">
      <div className="content-div">
        <p className="fa-2x">
          <ListGroup horizontal className="d-inline-flex p-2">
            <i className="fa fa-instagram icon"></i>
            <i className="fa fa-facebook-official icon"></i>
            <i className="fa fa-twitter icon"></i>
            <i className="fa fa-youtube-play icon"></i>
          </ListGroup>
        </p>
        <p>
          © 1996 – 2022 MJ International, Inc. All rights reserved.
          MJ Information
        </p>
      </div>
    </div>
  );
}

export default Footer;