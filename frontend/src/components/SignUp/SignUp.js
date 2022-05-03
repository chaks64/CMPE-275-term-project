import React from 'react'
import './SignUp.css'
import { Container, Form } from "react-bootstrap";
import {useNavigate} from 'react-router-dom';

export default function SignUp() {

    let navigate = useNavigate();

    const login = () => {
        navigate('/login')
    }
    return (
        <>
        <div className='signup-class'>
        <div className='row'>
        <div class = "col-lg-6">
            <div className='dashboard-checkout'>
                <div className='name-class'>
                CLOUD EVENT CENTRE
                </div>
                <br></br>
                All Your Events, One Event Management Platform
            </div>
            
        </div>
        {/* </div>
        <div className='row'> */}
        <div class = "col-lg-6">
    
        <div className="container-fluid container-checkout">
            <br></br>
            
            <button type="submit" className="btn btn-primary button-class" onClick={login}>
              Login
            </button>
        <br></br>
        <Form className='form-class'>
            <br></br>
          <h3>Sign Up</h3>
            <br></br>
          <div className='row'>
          <div className="col-sm-6">
            <label>Email address</label>
            <input
              type="email"
              className="form-control"
              placeholder="Enter email"
              required
            />
            
          </div>
          <div className="col-sm-6">
            <label>Password</label>
            <input
              type="password"
              className="form-control"
              placeholder="Enter password"
              required
            />
          </div>
          </div>
          <br></br>
          <div className='row'>
          <div className="col-sm-6">
          <label>
          Account Type  
          <br></br>      
          <select>
            <option value="person">Person</option>
            <option value="organization">Organization</option>
          </select>
        </label>
          </div>
          <div className="col-sm-6">
          <label>
          Gender 
          <br></br>      
          <select>
            <option value="male">Male</option>
            <option value="female">Female</option>
            <option value="others">Others</option>
          </select>
        </label>
          </div>
          </div>
          <br></br>
          <div className='row'>
           <div className="col-sm-6">
            <label>Full name</label>
            <input
              type="text"
              className="form-control"
              placeholder="Full name"
            />
          </div>
          <div className="col-sm-6">
            <label>Screen name</label>
            <input
              type="text"
              className="form-control"
              placeholder="Screen name"
            />
          </div>
          </div>
          <br></br>
          <div className='row'>
          <div className="col-sm-6">
            <label>Street</label>
            <input
              type="text"
              className="form-control"
              placeholder="Street"
            />
          </div>
          <div className="col-sm-6">
            <label>City</label>
            <input
              type="text"
              className="form-control"
              placeholder="City"
            />
          </div>
          </div>
          <br></br>
          <div className='row'>
          <div className="col-sm-6">
            <label>State</label>
            <input
              type="text"
              className="form-control"
              placeholder="State"
            />
          </div>
          <div className="col-sm-6">
            <label>Zip Code</label>
            <input
              type="text"
              className="form-control"
              placeholder="Zip Code"
            />
          </div>
          </div>
          <br></br>
          <div className="col-sm-6">
            <label>Description</label>
            <input
              type="text"
              className="form-control"
              placeholder="Description"
            />
          </div>
         
          <br></br>
          <div className="col-sm-6">
            <button type="submit" className="btn btn-primary">
              Sign Up
            </button>
          </div>
        </Form>
      
        </div>
        </div>
        </div>
        </div>
        </>
      )
}