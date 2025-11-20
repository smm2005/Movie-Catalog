import {useState, useEffect} from 'react';


function Logout(props){
    
    const [loggedOut, setLoggedOut] = useState(false);

    const logout = (username) => {
        fetch('http://localhost:8080/api/logout', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Methods': '*',
                'Access-Control-Allow-Headers': '*'
            },
            body: JSON.stringify({
                username: username
            })
        })
        .then(res => res.json())
        .then()
        .finally(() => setLoggedOut(true))
    }

    useEffect(() => {
        logout(props.username)
    }, [])

    return (
        !loggedOut ? <p>Logging out...</p> :
        <>
            <h1>You have been logged out.</h1>
        </>
    )

}