import React, { useState, useEffect } from "react";
import axios from "axios";
import { config } from "../../utils/utils";
import "./approval.css";
import { Alert } from "react-bootstrap";
import StarRatings from "react-star-ratings";

const Reviews = ({ userid, userType }) => {
  const [show, setShow] = useState(false);
  const [message, setMessage] = useState("");
  const [list, setList] = useState([]);
  const [repu, setRepu] = useState(0);

  useEffect(() => {
    getReviews();
  }, []);

  const getReviews = async () => {
    console.log("here ---------------------", userid);
    const list1 = await axios
      .get(`${config.backendURL}/review/show/${userid}/${userType}`)
      .then((response) => {
        console.log(response.data);
        setList(response.data);
        if (response.data.length > 0) {
          const average =
            response.data.reduce((total, next) => total + next.rating, 0) /
            response.data.length;
          setRepu(average);
        }
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

  return (
    <>
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
      <h1 style={{ textAlign: "center" }}>Reputation</h1>
      <div style={{ width: "60%", margin: "auto" }}>
        <h4 style={{ textAlign: "center" }}>
          Overall Reputaion{" "}
          <StarRatings
            rating={repu}
            starRatedColor="black"
            numberOfStars={5}
            name="rating"
            starDimension="20px"
          />
        </h4>
        <table className="styled-table">
          <thead>
            <tr>
              <th className="text-center">Rating</th>
              <th className="text-center">Review</th>
            </tr>
          </thead>
          <tbody>
            {list.length > 0 ? (
              list.map((user) => (
                <tr key={user.userId}>
                  <td className="text-center">
                    <StarRatings
                      rating={user.rating}
                      starRatedColor="black"
                      numberOfStars={5}
                      name="rating"
                      starDimension="20px"
                    />
                  </td>
                  <td className="text-center">{user.review}</td>
                </tr>
              ))
            ) : (
              <></>
            )}
          </tbody>
        </table>
      </div>
    </>
  );
};

export default Reviews;
