import { useState } from 'react';
import InputBox from '../components/InputBox';
import { useNavigate } from 'react-router-dom';
import InputImage from '../components/InputImage';
import { useDispatch, useSelector } from 'react-redux';

function FormMedicine() {

    // dikasih store buat hospital ntar ngaturnya di button kalo ada berarti edit kalo gak ada berarti add

    const navigate = useNavigate();

    const dispatch = useDispatch();

    const hospital =  useSelector(state => state.hospital);

    console.log(hospital);

    const [ inputHospital, setInputHospital ] = useState({ id: hospital.id, name: hospital.name, description: hospital.description, address: hospital.address, image: hospital.image});

    const edit = (input) => {
        
        fetch(`http://localhost:8083/api/update/hospital/${hospital.id}`, { 
            method: 'PUT',
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
        console.log(inputHospital);
        edit(inputHospital);
    }

    return(
        <div className="d-flex flex-row justify-content-center">
            <div id="form" className="d-flex flex-column align-items-center">
                <form className="d-flex flex-column align-items-center flex-wrap" onSubmit={apa}>
                    <h2>Edit Data Hospital</h2>
                    <InputBox type="text" title="Name" id="name" value={inputHospital.name} onChange={onChangeHospitals}/>
                    <InputBox title="Description" id="description" value={inputHospital.description} onChange={onChangeHospitals}/>
                    <InputImage title="Medicine Image" id="image" type="file" value={inputHospital.image} onChange={onChangeHospitals}/>
                    {/* <InputBox title="Medicine Image" id="image" value={inputHospital.image} onChange={onChangeHospitals}/> */}
                    <InputBox title="Address" id="address" value={inputHospital.address} onChange={onChangeHospitals}/>
                    <button type="submit">Edit</button>
                </form>
            </div>
        </div>
    )
}
export default FormMedicine;