import { useState } from 'react';
import InputBox from '../components/InputBox';
import { useNavigate, useLocation } from 'react-router-dom';
import InputImage from '../components/InputImage';

function FormMedicine() {

    const location = useLocation();

    // dikasih store buat medicine ntar ngaturnya di button kalo ada berarti edit kalo gak ada berarti add

    const navigate = useNavigate();

    const [ inputMedicine, setInputMedicine ] = useState({ name: "", description: "", price: "", stocks: "", image: ""});

    const add = (input) => {
        
        fetch(`http://localhost:8083/api/save/medicine`, { 
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
        add(inputMedicine);
    }

    return(
        <div className="d-flex flex-row justify-content-center">
            <div id="form" className="d-flex flex-column align-items-center">
                <form className="d-flex flex-column align-items-center flex-wrap" onSubmit={apa}>
                    <h2>Input New Medicine</h2>
                    <InputBox type="text" title="Name" id="name" value={inputMedicine.name} onChange={onChangeMedicines}/>
                    <InputBox title="Description" id="description" value={inputMedicine.description} onChange={onChangeMedicines}/>
                    <InputImage folder="Obat" title="Medicine Image" id="image" type="file" value={inputMedicine.image} onChange={onChangeMedicines}/>
                    {/* <InputBox title="Medicine Image" id="image" value={inputMedicine.image} onChange={onChangeMedicines}/> */}
                    <InputBox type="number" title="Price" id="price" value={Number(inputMedicine.price)} onChange={onChangeMedicines}/>
                    <InputBox type="number" title="Stocks" id="stocks" value={Number(inputMedicine.stocks)} onChange={onChangeMedicines}/>
                    <button type="submit">Add</button>
                </form>
            </div>
        </div>
    )
}
export default FormMedicine;