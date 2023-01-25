import React, { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import ReadMoreReact from 'read-more-react';

function Hospitals() {
    
    const [ hospitals, setHospitals] = useState([]);
    const navigate = useNavigate();
    
        useEffect(() => {
            // GET
            fetch(`http://localhost:8083/api/hospital`, {
                headers: {
                    'Content-Type': 'application/json',
                    Authorization : `${localStorage.getItem("Authorization")}`
                }
            })
            .then(response => {
                if (response.status === 200) {
                    return response.json();
                } else {
                    return new Error(`Something went wront status code: ${response.status}`)
                }
            })
            .then(data => {
                setHospitals(data);
            })
            .catch(err => {
                console.log(err);
            })
        }, [])
    
      return (
        <div className="d-flex flex-row justify-content-center">
            <div className="d-flex flex-column align-items-center">
                <h2 id="book-list"className="text-center">Hospital List</h2>
                <div className="d-flex justify-content-evenly flex-wrap">
                    { hospitals.map(hospital => (
                        <div id="card" className="card col-3 m-3 p-3">
                            <div key={hospital.id}>
                                <h3 className="card-title">{hospital.name}</h3>
                                <img className="card-img-top" src={hospital.image} width="250" height="250"/>
                                <ReadMoreReact text={hospital.description}/>
                                <button onClick={() => {navigate(`/hospital/${hospital.id}`)}}>Detail</button>
                                {/* <Link className="buttonLink" to={`/hospital/${hospital.id}`}>Detail</Link> */}
                            </div>
                        </div>
                    ))}
                </div>
                <button onClick={() => {navigate(`/hospital/add`)}}>Add hospital</button>
                {/* <div className="buttonLink">
                    <Link to={`/hospital/add`}>Add Hospital</Link>
                </div> */}
            </div>
        </div>
      )
    }
    
    export default Hospitals