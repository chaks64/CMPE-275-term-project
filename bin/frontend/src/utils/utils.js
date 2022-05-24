export const config = {
  backendURL: "http://localhost:8080",
  frontendURL: "http://localhost:3000",
};

export const validate = (values) => {
    let errors = {};
  
    if (!values.email) {
      errors.email = "Email cannot be blank";
    } else if (!/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,4}$/i.test(values.email)) {
      errors.email = "Invalid email format";
    }
  
    if (!values.password) {
      errors.password = "Password cannot be blank";
    }

    if (!values.cpassword) {
        errors.cpassword = "Confirm Password cannot be blank";
    }

    if(values.cpassword !== values.password){
        errors.cpassword = "Password does not match";
    }

    if (!values.firstname) {
        errors.firstname = "Name cannot be blank";
    }

    if (!values.lastname) {
        errors.lastname = "Screen Name cannot be blank";
    }

    if(values.accType === 'organization'){
      if(!new RegExp('^' + values.firstname).test(values.lastname)){
        errors.lastname = "Screen Name should start with full name";
      }
    }

    
  
    return errors;
};