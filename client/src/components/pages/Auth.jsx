import { useState, useEffect } from 'react';
import { Navigate } from 'react-router-dom';

function Auth(props){
    const [valid, setValid] = useState(null);
    const jwtToken = localStorage.getItem("jwtToken")

    const refreshToken = () => {
        fetch(`http://localhost:8080/api/auth/refresh`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                token: jwtToken
            })
        })
        .then(response => 
            response.json()
        )
        .then(json => {
            localStorage.setItem("jwtToken", json.token)
            setValid(true)
        })
        .catch(error => {
            console.log(error)
            setValid(false)
        })
    }

    useEffect(() => {
        refreshToken()
    }, [])

    return (
        (!valid) ? <Navigate to="/login" /> : <Navigate to={props.to} />
    )
}

export default Auth;