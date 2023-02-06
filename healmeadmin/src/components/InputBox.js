import { useState } from 'react';
import './style.css';


function InputBox(props){

    const [newInput, setNewInput] = useState("");
    
    const onchangeInput = (e) => {
        setNewInput(e.target.value)
        props.onChange(e.target.id, e.target.value)
    }

    return(
        <div className="form-floating mb-3 InputBox m-1 p-1">
            {(props.id === "description" || props.id === "address") ? <textarea className="form-control" id={props.id} value={newInput} onChange={onchangeInput} style={{height: "100px"}}/> 
            : <input className="form-control" type={props.type} id={props.id} value={newInput} onChange={onchangeInput}/>}
            <label for={props.id}>{props.title}</label>
        </div>
    )
}
export default InputBox;