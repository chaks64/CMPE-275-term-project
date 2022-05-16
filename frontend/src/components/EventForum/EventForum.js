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
    var [render,setRender]=useState(0);
    const [messages,setMessages]=useState('');
    const userid = localStorage.getItem("userid");
    console.log(`logged in user id:`,userid)
    var flag=false;
    useEffect(()=>{
    // get all msgs related to this event
    console.log(`Fetching all messages from useEffect`,render)
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
        var forumType=props.type;
        var eventid=props.id;
        var msg=userMsg;
        console.log(msg,forumType,userid,eventid,timestamp);
        var api_data={msg,forumType,userid,eventid}
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
                <div className='inputBox-child-1'>
            <TextField id="msg" label="Type Something here" variant="outlined" onChange={handleChange}/>
            </div>
            <div className='inputBox-child-2'>
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