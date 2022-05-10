import React, { useState } from "react";
import { Card, Button } from "react-bootstrap";

const EventCard = ({ event }) => {
    // const user = JSON.parse(localStorage.getItem("user"));
    // const [role,setRole] = useState("person");
  return (
    <>
        hiiiiiii
    {/*{console.log(user.accountType)}*/}
      <Card>
        <Card.Header>{event.id}</Card.Header>
        <Card.Body>
          <Card.Title>{event.title}</Card.Title>
          <Card.Text>
            {event.description}
          </Card.Text>
<<<<<<< Updated upstream
          
          <Button disabled={role==="person"? false : true} variant="primary" style={{backgroundColor:"black"}}>Go somewhere</Button>
=======
          {/*<Button disabled={role==="person"? false : true} variant="primary" style={{backgroundColor:"black"}}>Go somewhere</Button>*/}
            <Button variant="primary" style={{backgroundColor:"black"}}>Go somewhere</Button>
>>>>>>> Stashed changes
        </Card.Body>
      </Card>
    </>
  );
};

export default EventCard;
