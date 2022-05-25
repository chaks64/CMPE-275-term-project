import React, { useState, useEffect } from "react";
import TextField from "@mui/material/TextField";
import "./EventForum.css";
import { Button } from "@mui/material";
import axios from "axios";
import { config } from "../../utils/utils";
import MsgCard from "../MsgCard/MsgCard";
import PhotoCameraIcon from "@mui/icons-material/PhotoCamera";
import { red } from "@mui/material/colors";
import { ref, uploadBytes, getDownloadURL } from "firebase/storage";
import { storage_bucket } from "../../firebase/firebaseConfig";
//Todo
// 1. check why userid is undefined from localstorage
// 2. Upload image
const EventForum = (props) => {
  // console.log(`props value is:`, props);
  const {event}=props;
  console.log(`eventforum event: `,event)
  const [userMsg, setUserMsg] = useState();
  var [render, setRender] = useState(0);
  const [messages, setMessages] = useState("");
  const [allow, setAllow] = useState(false);
  const userid = localStorage.getItem("userid");
  const [selectedFile, setSelectedFile] = useState();
  const [image, setimage] = useState("");
  var organizer=false;
  if(event.user?.userId==userid){
//organizer is seding message
organizer=true;
  }

  // console.log(`logged in user id:`, userid);
  var flag = false;

  useEffect(() => {
    // get all msgs related to this event
    verify();
    console.log(`Fetching all messages from useEffect`, render);
    axios
      .get(`${config.backendURL}/forum/msgs/${props.id}/${props.type}`)
      .then((res) => {
        const { data } = res;
        setMessages(data);
        // console.log(data);
        if (messages !== "{}") {
          flag = true;
        }
      })
      .catch((error) => {
        console.log(error);
      });
  }, []);

  const verify = () => {
    let event = props.event;
    var d = new Date(
      localStorage.getItem("clock")
    ); /* midnight in China on April 13th */
    d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
    let two = new Date(event.deadline);
    const diff = (two - d) / (1000 * 60 * 60 * 24);
    console.log(d, "  ", two);
    console.log(diff);
    if (event.status === "cancel") {
      setAllow(true);
    } else if (diff < 0) {
      setAllow(true);
    }
  };

  const handleChange = (e) => {
    console.log(e);
    setUserMsg(e.target.value);
    console.log(`printing after setting`,userMsg)
  };

  const handleImage = (e) => {
    console.log(`Inside here 1`)
    // if (e.target.files[0]) {
    //   console.log(`Inside here 2`)
      // setTempImage(e.target.files[0]);
      if (e.target.files[0] != null) {
        console.log(`inside here 3`,e.target.files[0]);
        const storageRef = ref(storage_bucket, e.target.files[0].name);
        // 'file' comes from the Blob or File API
        uploadBytes(storageRef, e.target.files[0])
          .then((snapshot) => {
            return getDownloadURL(snapshot.ref);
          })
          .then((downloadURL) => {
            console.log("Download URL", downloadURL);
            setimage(downloadURL);
          });
      }
    // }
  };

  if (messages != "{}") {
    flag = true;
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    var timestamp = Date.now();
    var forumType = props.type;
    var eventid = String(props.id);
    var msg = userMsg;
    console.log(`final message`,userMsg)
    var img=image;
    // console.log(message, forumType, userid, eventid, timestamp);
    var api_data = { msg,img, forumType, userid, eventid };
    console.log(`createmsg data`,api_data);
    axios
      .post(`${config.backendURL}/forum/createMsg`, api_data)
      .then((res) => {
        console.log(res);
        setRender(render++);
        window.location.reload(false);
      })
      .catch((error) => {
        console.log(error);
        if (typeof userid == "undefined") {
          alert("Login in required!!");
        }
      });
  };

  return (
    <div className="parent">
      <div className="messages">
        {flag &&
          messages &&
          messages?.map((msg) => {
            // console.log(`Should print msg cards`, msg);
            return (
              <div>
                {" "}
                <MsgCard
                  key={msg.msgID}
                  id={msg.msgID}
                  msg={msg}
                  isOrganizer={organizer}
                />{" "}
              </div>
            );
          })}
      </div>
      <div className="inputBox">
        <div className="inputBox-child-1">
          <TextField
            id="msg"
            label="Type Something here"
            variant="outlined"
            onChange={handleChange}
          />
        </div>
        <div className="inputBox-child-2">
          <Button
            // disabled={allow}
            variant="contained"
            style={{
              backgroundColor: "#7C0200",
              height: "2rem",
              color: "#FFF",
              marginTop: "5px",
            }}
            onClick={handleSubmit}
          >
            Send
          </Button>
          <Button
          component="label"
            size="small"
            startIcon={
              <PhotoCameraIcon sx={{ color: red[900] }}></PhotoCameraIcon>
            }
            // onClick={handleImage}
          ><input
          type="file"
          hidden
          onChange={handleImage}
        /></Button>
        </div>
      </div>
    </div>
  );
};

export default EventForum;
