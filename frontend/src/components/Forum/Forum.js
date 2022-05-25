import React from 'react'
import ParticipantForum from '../EventForum/ParticipantForum';
import {  useParams,useLocation } from "react-router-dom";
import Footer from '../Footer/Footer';
import NavBar from '../NavBar/NavBar';
export default function Forum() {
  const { eventid } = useParams();
  const location = useLocation()
  const { from } = location.state
  console.log(`getting props from link`,from)
  return (
    <div>
      <NavBar/>
      
      <div className='outer-box'>
      <h1>Participant's Forum</h1>
          <ParticipantForum id={eventid} type='ParticipantForum' event={from}/>
      </div>
      <Footer/>
  </div>
  )
}
