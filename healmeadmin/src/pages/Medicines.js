import React, { useState, useEffect } from 'react'
import { Link, useNavigate } from 'react-router-dom';
import ReadMoreReact from 'read-more-react';

function Medicines() {
    
        const [ medicines, setMedicines] = useState([]);
        const navigate = useNavigate();
    
        useEffect(() => {
            // GET
            fetch(`http://localhost:8083/api/medicine`, {
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
                setMedicines(data);
                console.log(medicines);
            })
            .catch(err => {
                console.log(err);
            })
        }, [])
    
      return (
        <div className="d-flex flex-row justify-content-center">
            <div className="d-flex flex-column align-items-center">
                <h2 id="book-list"className="text-center">Medicine List</h2>
                <div className="d-flex justify-content-evenly flex-wrap">
                    { medicines.map(medicine => (
                        <div id="card" className="card col-3 m-3 p-3">
                            <div key={medicine.id}>
                                <h3 className="card-title">{medicine.name}</h3>
                                <img className="card-img-top" src={medicine.image} width="250" height="250"/>
                                <ReadMoreReact text={medicine.description}/>
                                <button onClick={() => {navigate(`/medicine/${medicine.id}`)}}>Detail</button>
                                {/* <Link className="buttonLink" to={`/medicine/${medicine.id}`}>Detail</Link> */}
                            </div>
                        </div>
                    ))}
                </div>
                <button onClick={() => {navigate(`/medicine/add`)}}>Add medicine</button>
                {/* <div className="buttonLink">
                    <Link to={`/medicine/add`}>Add medicine</Link>
                </div> */}
            </div>
        </div>
      )
    }
    
    export default Medicines