import React, { useState, useEffect } from "react";
import { Card, Button } from "react-bootstrap";
import { Link } from "react-router-dom";

const EventCard = ({ event }) => {
  // const user = JSON.parse(localStorage.getItem("user"));
  const [role, setRole] = useState("person");
  const [dis, setDis] = useState(false);

  useEffect(() => {
    console.log(localStorage.getItem("clock"));
    console.log(new Date(event.startDate),"-------")
    let one = new Date(localStorage.getItem("clock"));
    let two = new Date(event.startDate);
    const diff = (two - one);

    console.log(diff);

    if(diff < 1){
      setDis(true);
    }
  }, []);

  return (
    <>
      {/* {console.log(user.accountType)} */}
      <Card>
        <Card.Header>{event.eventID}</Card.Header>
        <Card.Body>
          <Card.Title>{event.title}</Card.Title>
          <Card.Text>{event.description}</Card.Text>
          <Link
            to="/eventDetails"
            state={{ from: event }}
            style={{ color: "white" }}
          >
            <Button
              variant="primary"
              style={{ backgroundColor: "black" }}
            >
            Details
            </Button>
          </Link>
          <Link
            // to="/eventDetails"
            to={`/forum/${event.eventID}`}
            state={{ from: event }}
            style={{ color: "white" }}
          >
            <Button
            disabled={dis}
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
