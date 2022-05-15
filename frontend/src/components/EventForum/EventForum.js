import React,{useState,useEffect} from 'react';
import TextField from '@mui/material/TextField'
import './EventForum.css'
import { Button } from '@mui/material';
import axios from 'axios';
import { config } from "../../utils/utils";
import MsgCard from '../MsgCard/MsgCard';
import PhotoCameraIcon from '@mui/icons-material/PhotoCamera';
import { red } from '@mui/material/colors';

//Todo
// 1. check why userid is undefined from localstorage
// 2. Upload image
// 3. Post msg (backend error)
const EventForum = (props) => {
    console.log(`props value is:`, props)
    const [userMsg,setUserMsg]=useState();
    const [render,setRender]=useState(0);
    const [messages,setMessages]=useState('');
    const user = localStorage.getItem("user");
    const{userId}=user;
    console.log(user)
    console.log(userId)
    var flag=false;
    useEffect(()=>{
    // get all msgs related to this event
    console.log(`Fetching all messages from useEffect`)
    axios.get(`${config.backendURL}/forum/msgs/${props.id}/${props.type}`)
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
    },[render])

    const handleChange=(e)=>{
        console.log(e)
        setUserMsg(e.target.value);    
    };

    const handleImage=(e)=>{

    }

    if((messages)!='{}')
    {
        flag=true;
    }

    const handleSubmit=(e)=>{
        e.preventDefault();
        var timestamp=Date.now();
        console.log(JSON.stringify(user))
        var userid=user.userId;
        var forumType=props.type;
        var eventid=props.id;
        console.log(eventid, userid,userMsg,forumType);
        var api_data={
            userid,
            eventid,
            userMsg,
            forumType
        }
        console.log(api_data);
        axios.post(`${config.backendURL}/forum/createMsg`,api_data)
        .then(()=>{
            setRender(render++);
        }).catch((error)=>{
            console.log(error)
            // if(typeof(userId)=='undefined'){
            //     alert('Login in required!!')
            // }
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
                            // name={prod.name}
                            // price={prod.price}
                            // url={prod.url}
                            msg={msg}
                        />{" "}
                        </div>
                    );
                    }))}
            </div>
            <div className='inputBox'>
                <div className='inputBox-child'>
            <TextField id="msg" label="Type Something here" variant="outlined" onChange={handleChange}/>
            </div>
            <div className='inputBox-child'>
            <Button variant='contained' style={{
                backgroundColor: "#7C0200",
                height: "2rem",
                color: "#FFF",
                marginTop: "5px",
              }}
              onClick={handleSubmit}
              >Send</Button>
              <Button size="small" startIcon={<PhotoCameraIcon sx={{ color: red[900] }}></PhotoCameraIcon> } onClick={handleImage}>
            </Button>
              </div>
            </div>
        </div>
    );
};

export default EventForum;