import { useState, useEffect } from 'react';
import { Navigate } from 'react-router-dom';

function Auth(){
    const [token, setToken] = useState("Error")
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
            console.log(json)
            setToken(json.token)
        })
        .catch(error => console.error(error))
        .finally(() => console.log(token))
    }, [])

    if (token !== "Error"){
        localStorage.setItem('jwtToken', token)
    }

    return (
        (token === "Error") ? <Navigate to="/login" /> : <Navigate to="/movies" /> 
    )
}

export default Auth;