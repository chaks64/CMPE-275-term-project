import React, { useState, useEffect } from "react";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import Footer from "../Footer/Footer";
import "./approval.css";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";
import { Alert } from "react-bootstrap";

const ApprovalList = () => {
  const location = useLocation();
  let navigate = useNavigate();
  const [list, setList] = useState([]);
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const { event } = location.state;
  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here ",event);
    const user = localStorage.getItem("userid");
    const list1 = await axios
      .get(`${config.backendURL}/event/partlist/${event.eventID}/notapproved`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
      })
      .catch((error) => {
        console.log(error.response.data);
          setShow(true);
          if (error.response.data.errorDesc === undefined) {
            setMessage("Server error please try again");
          } else {
            setMessage(error.response.data.errorDesc);
          }
      });
  };

  const manageRequest = async (e) => {
    e.preventDefault();
    console.log(e.target.id);
    const data = {
      userid: e.target.id,
      status: e.target.value,
    };
    const list1 = await axios
      .post(`${config.backendURL}/event/manage`, data)
      .then((response) => {
        console.log(response.data);
        navigate("/home");
      })
      .catch((error) => {
        console.log(error);
      });
  };

  return (
    <>
      <NavBar />
      {show ? (
        <Alert
          variant="danger"
          onClose={() => setShow(false)}
          dismissible
          className="size"
        >
          <p>{message}</p>
        </Alert>
      ) : (
        console.log(<p></p>)
      )}
      <div>
        <table className="styled-table">
          <thead>
            <tr>
              <th className="text-center">Participant ID</th>
              <th className="text-center">Participant Email</th>
              <th className="text-center">Participant Name</th>
              <th className="text-center">Approve/Reject</th>
            </tr>
          </thead>
          <tbody>
            {list.length > 0 ? (
              list.map((user) => (
                <tr key={user.userId}>
                  <td className="text-center">{user.userId}</td>
                  <td className="text-center">{user.email}</td>
                  <td className="text-center">{user.fullName}</td>
                  <td className="text-center">
                    <button value="approved" id={user.userId} onClick={manageRequest}>
                      Approve
                    </button>{" "}
                    <button value="reject" onClick={manageRequest}>
                      Reject
                    </button>
                  </td>
                </tr>
              ))
            ) : (
              <h1>No Users</h1>
            )}
          </tbody>
        </table>
      </div>
      <Footer />
    </>
  );
};

export default ApprovalList;
