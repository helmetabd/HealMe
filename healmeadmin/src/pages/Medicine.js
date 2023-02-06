import React, { useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';

function Medicine() {

    const { id } = useParams();

    const navigate = useNavigate();

    const dispatch = useDispatch();

    const medicine =  useSelector(state => state.medicine);

    const fetchMedicine = () => {
        fetch(`http://localhost:8083/api/medicine/${id}`, {
            headers: {
                'Content-Type': 'application/json',
                Authorization : `${localStorage.getItem("Authorization")}`
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json()
            } else {
                throw { message: 'Something went wrong', status: response.status }
            }
        })
        .then(data => {
            // console.log(data)
            // setMedicine(data)
            dispatch({ type: "SET_MEDICINE", payload: data})
        })
        .catch(err => {
            console.log(err.message)
        })
    }

    const remove = () => {
        fetch(`http://localhost:8083/api/medicine/${id}`, {
        headers: {
            'Content-Type': 'application/json',
            Authorization : `${localStorage.getItem("Authorization")}`
        },  
        method: 'DELETE'
        })
        .then(response => {
            return response;
        })
        .then(data => {
            navigate("/medicine");
        })
        .catch(err => {
            console.log(err);
        })
    }

    useEffect(() => {
        fetchMedicine();
    }, []);

    return (
        <div className="card mb-3 mx-auto p-3 mt-4 insert" style={{maxWidth: "800px"}} key={medicine.id}>
            <div className="row g-0">
                <div className="col-md-4">
                    <h5 className="card-title">{medicine.name}</h5>
                    <img src={medicine.image} className="img-fluid rounded-start"/>
                </div>
                <div className="col-md-8">
                    <div className="card-body d-flex">
                        <div>
                            <h4>Description: </h4>
                            <p className="card-text">{medicine.description}</p>
                        </div>
                        <div>
                            <h4>Stock: {medicine.stocks}</h4>
                            <h4>Price: Rp.{medicine.price}</h4>
                            <button onClick={() => {navigate("/medicine/edit")}}>Edit</button>
                            <button onClick={remove}>Remove</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Medicine;