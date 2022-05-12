import React, { useState, useEffect } from "react";
import NavBar from "../NavBar/NavBar";
import axios from "axios";
import { config } from "../../utils/utils";
import Footer from "../Footer/Footer";
import "./approval.css";
import { useNavigate } from "react-router-dom";

const ApprovalList = () => {
  let navigate = useNavigate();
  const [list, setList] = useState([]);
  useEffect(() => {
    getEvents();
  }, []);

  const getEvents = async () => {
    console.log("here");
    const user = localStorage.getItem("userid");
    const list1 = await axios
      .get(`${config.backendURL}/event/partlist/${user}/notapproved`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
      })
      .catch((error) => {
        console.log(error);
      });
  };

  const manageRequest = async (e) => {
    e.preventDefault();
    console.log(e.target.value);
    localStorage.setItem("clock",new Date());
    const data = {
      userid: localStorage.getItem("userid"),
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
                    <button value="approved" onClick={manageRequest}>
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
