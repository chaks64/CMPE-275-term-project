import React from 'react'
import ParticipantForum from '../EventForum/ParticipantForum';
import {  useParams } from "react-router-dom";
import Footer from '../Footer/Footer';
import NavBar from '../NavBar/NavBar';
export default function Forum() {
  const { eventid } = useParams();
  console.log(eventid)
  return (
    <div>
      <NavBar/>
      
      <div className='outer-box'>
      <h1>Participant's Forum</h1>
          <ParticipantForum id={eventid} type='ParticipantForum'/>
      </div>
      <Footer/>
  </div>
  )
}
