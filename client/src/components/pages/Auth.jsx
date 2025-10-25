import { useState, useEffect } from 'react';
import { Navigate } from 'react-router-dom';

function Auth(){
    const [valid, setValid] = useState(true);
    const jwtToken = localStorage.getItem('jwtToken')

    useEffect(() => {
        fetch(`http://localhost:8080/api/auth/refresh`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify(
                {}
            )
        })
        .then(response => 
            response.json()
        )
        .then(json => {
            setValid(true)
            localStorage.setItem('jwtToken', token)
        })
        .catch(error => {
            console.log(error)
        })
    }, [])

    return (
        (!valid) ? <Navigate to="/login" /> : <Navigate to="/movies" /> 
    )
}

export default Auth;