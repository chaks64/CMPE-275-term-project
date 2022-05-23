import React,{useState} from 'react';
import './MsgCard.css'
import ReplyIcon from '@mui/icons-material/Reply';
import { Button } from '@mui/material';
import { red } from '@mui/material/colors';
import SendIcon from '@mui/icons-material/Send';
// todo:
// 1. Display image
// 2. Post & Display replies


const MsgCard = (props) => {
    // console.log(`Printing data for`,props)
    const {msg}=props
    const [flag, setFlag] = useState(false);

    const handleReply=(e)=>{
        setFlag(!flag)
    }
    const handleSend=(e)=>{
    }
    return (
        <div>
            <div className='msg'>
            <span style={{paddingLeft:"5px"}}>{msg.msg}</span>
            <div className='buttons'>
            <Button size="small" startIcon={<ReplyIcon  sx={{ color: red[900] }} ></ReplyIcon> } onClick={handleReply}>
            </Button>
           { flag && (<div>
                    <input id='reply'></input>
                    <Button size="small" startIcon={<SendIcon  sx={{ color: red[900] }} ></SendIcon> } onClick={handleSend}>
                    </Button>
                    </div>
                    )}
            </div>
            </div>
            
        </div>
    );
};

export default MsgCard;