import { useState } from 'react';
import InputBox from '../components/InputBox';
import { useNavigate } from 'react-router-dom';
import InputImage from '../components/InputImage';
import { useDispatch, useSelector } from 'react-redux';

function FormMedicine() {

    // dikasih store buat medicine ntar ngaturnya di button kalo ada berarti edit kalo gak ada berarti add

    const navigate = useNavigate();

    const dispatch = useDispatch();

    const medicine =  useSelector(state => state.medicine);

    console.log(medicine);

    const [ inputMedicine, setInputMedicine ] = useState({ id: medicine.id, name: medicine.name, description: medicine.description, price: medicine.price, stocks: medicine.stocks, image: medicine.image});

    const edit = (input) => {
        
        fetch(`http://localhost:8083/api/update/medicine/${medicine.id}`, { 
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
            navigate("/medicine");
        })
        .catch(err => {
            console.log(err);
        })
    }

    const onChangeMedicines = (id, value) => {
        setInputMedicine(state => {
          return { ...state, [id]: value}      
        })
    }

    const apa = (e) =>{
        e.preventDefault();
        console.log(inputMedicine);
        edit(inputMedicine);
    }

    return(
        <div className="d-flex flex-row justify-content-center">
            <div id="form" className="d-flex flex-column align-items-center sticky-top">
                <form className="d-flex flex-column align-items-center sticky-top flex-wrap" onSubmit={apa}>
                    <h2>Edit Data Medicine</h2>
                    <InputBox type="text" title="Name" id="name" value={inputMedicine.name} onChange={onChangeMedicines}/>
                    <InputBox title="Description" id="description" value={inputMedicine.description} onChange={onChangeMedicines}/>
                    <InputImage title="Medicine Image" id="image" type="file" value={inputMedicine.image} onChange={onChangeMedicines}/>
                    {/* <InputBox title="Medicine Image" id="image" value={inputMedicine.image} onChange={onChangeMedicines}/> */}
                    <InputBox type="number" title="Price" id="price" value={Number(inputMedicine.price)} onChange={onChangeMedicines}/>
                    <InputBox type="number" title="Stocks" id="stocks" value={Number(inputMedicine.stocks)} onChange={onChangeMedicines}/>
                    <button type="submit">Edit</button>
                </form>
            </div>
        </div>
    )
}
export default FormMedicine;