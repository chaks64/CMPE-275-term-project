import React, { useState } from "react";
import { Card, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

const MyEventCard = ({ event }) => {
  // const user = JSON.parse(localStorage.getItem("user"));

  return (
    <>
      <Card>
        <Card.Header>{event.eventID}</Card.Header>
        <Card.Body>
          <Card.Title>{event.title}</Card.Title>
          <Card.Text>{event.description}</Card.Text>
          <Link
            to="/partList"
            state={{ event: event }}
            style={{ color: "white" }}
          >
            <Button variant="primary" style={{ backgroundColor: "black" }}>
              Participants
            </Button>
            <Link to="/appList" state={{ event: event }}>
              <Button
                variant="primary"
                style={{ backgroundColor: "black", margin: "5px" }}
              >
                Approvals
              </Button>
            </Link>
          </Link>
        </Card.Body>
      </Card>
    </>
  );
};

export default MyEventCard;
