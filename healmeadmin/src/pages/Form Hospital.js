import { useState } from 'react';
import InputBox from '../components/InputBox';
import InputImage from '../components/InputImage';
import { useNavigate, useLocation, useParams } from 'react-router-dom';

function FormHospital() {

    const location = useLocation();

    const navigate = useNavigate();

    const [ inputHospital, setInputHospital ] = useState({ name: "", address: "", description: "", image: ""});

    const add = (input) => {
        
        fetch(`http://localhost:8083/api/save/hospital`, { 
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization : `${localStorage.getItem("Authorization")}`
            }, 
            body: JSON.stringify(input)
        })
        .then(response => {
            if (response.status === 200) {
                return response.json();
            }
        })
        .then(() => {
            navigate("/hospital");
        })
        .catch(err => {
            console.log(err);
        })
    }

    const onChangeHospitals = (id, value) => {
        setInputHospital(state => {
          return { ...state, [id]: value}      
        })
    }

    const apa = (e) =>{
        e.preventDefault();
        add(inputHospital);
    }

    return(
        <div className="d-flex flex-row justify-content-center">
            <div id="form" className="d-flex flex-column align-items-center">
                <form className="d-flex flex-column align-items-center flex-wrap" onSubmit={apa}>
                    <h2>Input New Hospital</h2>
                    <InputBox type="text" title="Name" id="name" value={inputHospital.name} onChange={onChangeHospitals}/>
                    <InputBox title="Description" id="description" value={inputHospital.description} onChange={onChangeHospitals}/>
                    <InputImage folder="RS" title="Hospital Image" id="image" type="file" value={inputHospital.image} onChange={onChangeHospitals}/>
                    {/* <InputBox title="Medicine Image" id="image" value={inputHospital.image} onChange={onChangeHospitals}/> */}
                    <InputBox title="Address" id="address" value={inputHospital.address} onChange={onChangeHospitals}/>
                    <button type="submit">Add</button>
                </form>
            </div>
        </div>
    )
}
export default FormHospital;