import React, { useState, useEffect } from 'react'
import { useParams, useNavigate } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import ReadMoreReact from 'read-more-react';

function Hospital() {

    const { id } = useParams();

    const navigate = useNavigate();

    const dispatch = useDispatch();

    // const hospital =  useSelector(state => state.hospital);

    const [ hospital, setHospital ] = useState({});

    const fetchHospital = () => {
        fetch(`http://localhost:8083/api/hospital/${id}`, {
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
            setHospital(data)
            dispatch({ type: "SET_HOSPITAL", payload: data})
        })
        .catch(err => {
            console.log(err.message)
        })
    }

    const remove = () => {
        fetch(`http://localhost:8083/api/hospital/${id}`, {
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
            navigate("/hospital");
        })
        .catch(err => {
            console.log(err);
        })
    }

    useEffect(() => {
        fetchHospital();
    }, []);

    return (
        <div className="card mb-3 mx-auto p-3 mt-4 insert" style={{maxWidth: "800px"}} key={hospital.id}>
            <div className="row g-0">
                <div className="col-md-4">
                    <h5 className="card-title">{hospital.name}</h5>
                    <img src={hospital.image} className="img-fluid rounded"/>
                </div>
                <div className="col-md-8">
                    <div className="card-body d-flex">
                        <div>
                            <h4>Description: </h4>
                            <p className="card-text">{hospital.description}</p>
                            <h4>Address: </h4>
                            <p className="card-text">{hospital.address}</p>
                        </div>
                        <div>
                            <button onClick={() => {navigate(`/hospital/edit`, { state: { hospital: hospital }})}}>Edit</button>
                            <button onClick={remove}>Remove</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}
export default Hospital;