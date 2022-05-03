import React from 'react'
import './Login.css'

export default function Login() {
    return (
        <>
            {/* <div className='sign-in-class'> */}
                {/* shweta */}
           
            <div className="container-fluid">
                <div className='container container-class'>
                    <div className='row'>
                        <div className="col-sm-6">
                            <form>
                            <div className='heading-class'>
                            <h3>Welcome Back!</h3>  
                            Sign in to access your account  
                            <p className="forgot-password text-right">
                                Not registered <a href="/">Create an account?</a>
                            </p>                          
                             
                            <button type="submit">
        <div class="social-media" onclick="showSpinner(event)">
          <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 20 20" fill="none" class="icon">
<g clip-path="url(#clip0)">
<path d="M4.43242 12.086L3.73625 14.6849L1.19176 14.7388C0.431328 13.3283 0 11.7146 0 9.99979C0 8.34154 0.403281 6.77779 1.11812 5.40088H1.11867L3.38398 5.81619L4.37633 8.06791C4.16863 8.67342 4.05543 9.32342 4.05543 9.99979C4.05551 10.7338 4.18848 11.4372 4.43242 12.086Z" fill="#FBBB00"></path>
<path d="M19.8252 8.13184C19.94 8.73676 19.9999 9.36148 19.9999 9.99996C19.9999 10.7159 19.9246 11.4143 19.7812 12.0879C19.2944 14.3802 18.0224 16.3818 16.2604 17.7983L16.2598 17.7978L13.4065 17.6522L13.0027 15.1313C14.1719 14.4456 15.0857 13.3725 15.567 12.0879H10.2197V8.13184H15.645H19.8252Z" fill="#518EF8"></path>
<path d="M16.2595 17.7979L16.2601 17.7985C14.5464 19.176 12.3694 20.0001 9.99965 20.0001C6.19141 20.0001 2.88043 17.8716 1.19141 14.7392L4.43207 12.0864C5.27656 14.3403 7.45074 15.9447 9.99965 15.9447C11.0952 15.9447 12.1216 15.6485 13.0024 15.1315L16.2595 17.7979Z" fill="#28B446"></path>
<path d="M16.383 2.30219L13.1434 4.95437C12.2319 4.38461 11.1544 4.05547 10 4.05547C7.39344 4.05547 5.17859 5.73348 4.37641 8.06812L1.11871 5.40109H1.11816C2.78246 2.1923 6.1352 0 10 0C12.4264 0 14.6511 0.864297 16.383 2.30219Z" fill="#F14336"></path>
</g>
<defs>
<clipPath id="clip0">
<rect width="20" height="20" fill="white"></rect>
</clipPath>
</defs>
</svg>

          <div data-react-class="components/spinner" data-react-props="{&quot;isShowing&quot;:true,&quot;className&quot;:&quot;spinner hidden&quot;}" data-react-cache-id="components/spinner-0"><div aria-live="off"><div class="sc-bdvvtL ghZHcT hpn-e sc-gsDKAQ bwVCvz spinner hidden" role="" aria-label="Loading…"></div></div></div>
          <span data-testid="google" class="brand-name mt-8">Google</span>
        </div>
</button>  
</div> 
<br></br>
<div className='border-layout'>
---------------------------------------OR------------------------------------------

</div>
<br></br>
  
                            <div className="mb-3">
                                <label>Email address</label>
                                <input
                                type="email"
                                className="form-control"
                                placeholder="Enter email"
                                />
                            </div>
                            <div className="mb-3">
                                <label>Password</label>
                                <input
                                type="password"
                                className="form-control"
                                placeholder="Enter password"
                                />
                            </div>
                            {/* <div className="mb-3">
                                <div className="custom-control custom-checkbox">
                                <input
                                    type="checkbox"
                                    className="custom-control-input"
                                    id="customCheck1"
                                />
                                <label className="custom-control-label" htmlFor="customCheck1">
                                    Remember me
                                </label>
                                </div>
                            </div> */}
                            <div className="d-grid">
                                <button type="submit" className="btn btn-primary">
                                Submit
                                </button>
                            </div>
                            {/* <p className="forgot-password text-right">
                                Forgot <a href="#">password?</a>
                            </p> */}
                            {/* </div> */}
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            {/* </div> */}
        </>
      )
}