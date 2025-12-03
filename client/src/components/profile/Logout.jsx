import {useState, useEffect} from 'react';
import { Link } from 'react-router-dom'; 

function Logout(){
    
    const [loggedOut, setLoggedOut] = useState(false);
    const jwtToken = localStorage.getItem("jwtToken")

    const logout = () => {
        fetch('http://localhost:8080/api/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Methods': '*',
                'Access-Control-Allow-Headers': '*'
            },
            body: JSON.stringify({
                token: jwtToken
            })
        })
        .then(res => res.json())
        .then()
        .finally(() => setLoggedOut(true))
    }

    useEffect(() => {
        logout()
    }, [])

    return (
        !loggedOut ? <p>Logging out...</p> :
        <>
            <h1>You have been logged out.</h1>
            <Link to="/"><u>Return To Home Page</u></Link>
        </>
    )

}

export default Logout;