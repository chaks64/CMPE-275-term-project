import React from 'react';
import './bill.css';

const BillPage = ({ details }) => {
    return (
        
        <div class="wrapper">
            
        <div class="title">Pay ${details.fees}</div>
        <div class="checkout_form">
           <div class="input_item">
            <input type="text" placeholder="Card Holder Name"/>
         </div>
         <div class="input_item">
           <input type="text" placeholder="0000 0000 0000 0000" data-mask="0000 0000 0000 0000"/>
        </div>
        <div class="input_grp">
          <div class="input_item">
            <input type="text" placeholder="MM / YY" data-mask="00 / 00"/>
          </div>
          <div class="input_item">
            <input type="text" placeholder="* * *" data-mask="0 0 0"/>
          </div>
        </div>
        <button>Proceed</button>
          {/* <div class="btn">
             <a href="#">proceed</a>
          </div> */}
      </div>
    </div>
    );
};

export default BillPage;