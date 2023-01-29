import { useState } from 'react';
import './style.css';


function InputBox(props){

    const [newInput, setNewInput] = useState("");
    
    const onchangeInput = (e) => {
        // console.log(newInput)
        setNewInput(e.target.value)
        props.onChange(e.target.id, e.target.value)
    }

    return(
        // <div className="d-flex flex-column InputBox m-1 p-1">
        // <div>
        <div className="form-floating mb-3 InputBox m-1 p-1">
            {(props.id === "description" || props.id === "address") ? <textarea className="form-control" id={props.id} value={newInput} onChange={onchangeInput} style={{height: "100px"}}/> 
            : <input className="form-control" type={props.type} id={props.id} value={newInput} onChange={onchangeInput}/>}
            <label for={props.id}>{props.title}</label>
            {/* <input type= */}
        </div>
        // {/* <div class="form-floating mb-3">
        //     <input type="email" class="form-control" id="floatingInput" placeholder="name@example.com"/>
        //     <label for="floatingInput">Email address</label>
        //     </div>
        // </div> */}
    )
}
export default InputBox;