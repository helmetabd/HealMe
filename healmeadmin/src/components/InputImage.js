import React, { useState } from 'react'
import { storage } from '../firebase';
import { ref, uploadBytes, getDownloadURL } from "firebase/storage";

function InputImage(props) {

    const [ uploadImage, setUploadImage ] = useState(null);

    const upload = (e) => {
        const fileName = e.target.files[0].name;
        const storageRef = ref(storage, `Admin/${props.folder}/${fileName}`);
    
        uploadBytes(storageRef, e.target.files[0])
        .then((snapshot) => {
            return getDownloadURL(storageRef)
        })
        .then(downloadUrl => {
            console.log(downloadUrl);
            setUploadImage(downloadUrl);
            props.onChange(e.target.id, downloadUrl);
        })
        .catch(err => {
            console.log(err);
        })      
    }

  return (
    <div className="form-floating mb-3 InputBox m-1 p-1">
        <form>
            <input className="form-control" id={props.id} type={props.type} accept="image/*" placeholder="Upload Medicine Image" onChange={upload}/>
            <label htmlFor={props.id}>{props.title}</label>
        </form>
        <div>
            <img src={uploadImage} width="250" height="250" />
        </div>
    </div>
  )
}

export default InputImage