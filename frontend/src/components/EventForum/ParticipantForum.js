import React,{useState,useEffect} from 'react';
import TextField from '@mui/material/TextField'
import './EventForum.css'
import { Button } from '@mui/material';
import axios from 'axios';
import { config } from "../../utils/utils";
import MsgCard from '../MsgCard/MsgCard';
import PhotoCameraIcon from '@mui/icons-material/PhotoCamera';
import { red } from '@mui/material/colors';
import { ref, uploadBytes, getDownloadURL } from "firebase/storage";
import { storage_bucket } from "../../firebase/firebaseConfig";

//Todo
// 1. check why userid is undefined from localstorage
// 2. Upload image
// 3. Post msg (backend error)
const ParticipantForum = (props) => {
    console.log(`props value is:`, props)
    const{event}=props;
    const [userMsg,setUserMsg]=useState();
    var [render,setRender]=useState(0);
    const [messages,setMessages]=useState('');
    const userid = localStorage.getItem("userid");
    console.log(`logged in user id:`,userid)
    const [image, setimage] = useState("");
    const [allow, setAllow] = useState(false);
    const[closeMsg,setCloseMsg] = useState("");
    const[thisevent,setThisEvent]=useState();

    var organizer=false;
    if(event.user?.userId==userid){
        //organizer is seding message
        organizer=true;
    }

    var flag=false;


    useEffect(()=>{
    // get all msgs related to this event
    verify();
    console.log(`Fetching all messages from useEffect`,render)
    axios.get(`${config.backendURL}/forum/messages/${props.id}/${props.type}`)
        .then((res)=>{
            const {data}=res;
            setMessages(data)
            console.log(data)
            if((messages)!='{}')
                {
                    flag=true;
                }
        })
        .catch((error)=>{
            console.log(error)
        })
    },[render]);

    const verify = () => {
        let event = props.event;
        var d = new Date(
          localStorage.getItem("clock")
        ); /* midnight in China on April 13th */
        d.toLocaleString("en-US", { timeZone: "America/Los_Angeles" });
        let two = new Date(event.endtDate);
        const diff = (two - d) / (1000 * 60 * 60 * 24);
        console.log(d, "  ", two);
        console.log(diff);
        if (event.status === "cancel") {
          setAllow(true);
          setCloseMsg('Event has been cancelled');
        } else if (diff < -72) {
          setAllow(true);
          setCloseMsg('Event ended 72 hours back');
        }
        else if (thisevent?.status == "closed") {
            setAllow(true);
          setCloseMsg('Organizer closed the participant forum');
          console.log(`Disabling send button as forum is closed`);
        }
      };

    const handleChange=(e)=>{
        console.log(e)
        setUserMsg(e.target.value);    
    };

    const closeForum=(e)=>{
      console.log(`closing forum`);
      axios.get(`${config.backendURL}/event/updatetoclose/${props.id}`)
      .then((res)=>{
        console.log(res)
        const {data}=res;
        setRender(render++);
        setThisEvent(data);
      })
      .catch((e)=>
      console.log(e))

    }

    const handleImage=(e)=>{
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

    }

    if((messages)!='{}')
    {
        flag=true;
    }

    const handleSubmit=(e)=>{
        e.preventDefault();
        var timestamp=Date.now();
        var forumType=props.type;
        var eventid=String(props.id);
        var msg=userMsg;
        console.log(msg,forumType,userid,eventid,timestamp);
        var img=image;
        var api_data={msg,img,forumType,userid,eventid}
        console.log(api_data);
        axios.post(`${config.backendURL}/forum/createMsg`,api_data)
        .then((res)=>{
            console.log(res);
            setRender(render++);
            window.location.reload(false);
        }).catch((error)=>{
            console.log(error)
            if(typeof(userid)=='undefined'){
                alert('Login in required!!')
            }
        })
    }

    return (
        <div className='parent'>
            <div className='messages'>
                {flag && messages &&
                    (messages?.map((msg) => {
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
                    }))}
            </div>
            <div className='inputBox'>
                <div className='inputBox-child-1'>
            <TextField id="msg" label="Type Something here" variant="outlined" onChange={handleChange}/>
            </div>
            <div className='inputBox-child-2'>
            <Button 
            disabled={allow}
            variant='contained' style={{
                backgroundColor: "#7C0200",
                height: "2rem",
                color: "#FFF",
                marginTop: "5px",
              }}
              onClick={handleSubmit}
              >Send</Button>
              <Button 
              component="label"
              size="small" startIcon={<PhotoCameraIcon sx={{ color: red[900] }}></PhotoCameraIcon> } 
            //   onClick={handleImage}
              >
                  <input
          type="file"
          hidden
          onChange={handleImage}
        />
            </Button>
           {organizer &&( <Button
            variant='contained' style={{
                backgroundColor: "#7C0200",
                height: "2rem",
                color: "#FFF",
                marginTop: "5px",
              }}
              onClick={closeForum}
            >Close Forum</Button>)}
            {closeMsg}
              </div>
            </div>
        </div>
    );
};

export default ParticipantForum;