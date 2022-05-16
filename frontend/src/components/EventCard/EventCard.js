import React, { useState } from "react";
import { Card, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

const EventCard = ({ event }) => {
  // const user = JSON.parse(localStorage.getItem("user"));
  const [role, setRole] = useState("person");

  return (
    <>
      {/* {console.log(user.accountType)} */}
      <Card>
        <Card.Header>{event.eventID}</Card.Header>
        <Card.Body>
          <Card.Title>{event.title}</Card.Title>
          <Card.Text>{event.description}</Card.Text>
          <Link
            to={`/forum/${event.eventID}`}
            state={{ from: event }}
            style={{ color: "white" }}
          >
            <Button
              variant="primary"
              style={{ backgroundColor: "black" }}
            >
            Participant Forum
            </Button>
          </Link>
        </Card.Body>
      </Card>
    </>
  );
};

export default EventCard;
