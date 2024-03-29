import React from "react";
import * as FaIcons from "react-icons/fa";
import * as AiIcons from "react-icons/ai";
// import * as IoIcons from "react-icons/io";
import * as CgIcons from "react-icons/cg";

export const SidebarData = [
    {
        title: 'Home',
        path: '/home',
        icon: <AiIcons.AiFillHome />,
        cName: 'nav-text'
    },
    {
        title: 'My Events',
        path: '/myEvents',
        icon: <FaIcons.FaHotel />,
        cName: 'nav-text'
    },
    {
        title: 'Create Event',
        path: '/createEvent',
        icon: <CgIcons.CgProfile />,
        cName: 'nav-text'
    },
    {
        title: 'Part Events',
        path: '/regEvent',
        icon: <CgIcons.CgProfile />,
        cName: 'nav-text'
    }
]