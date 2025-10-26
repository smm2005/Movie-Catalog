import { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

function Login(){
    
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    function handleUsernameChange(e){
        setUsername(e.target.value)
    }

    function handlePasswordChange(e){
        setPassword(e.target.value)
    }

    const handleLogin = (e) => {
        fetch("http://localhost:8080/api/login", {
            method: "POST",
            headers: {
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            localStorage.setItem("jwtToken", data.token)
        })
        .catch(err => {
            console.log(err)
        })
        .finally(() => {
            window.location.href = "/movies";
        })
    }

    return (
        <>
            <form action={handleLogin}>
                <label>Username: </label>
                <input type="text" value={username} onChange={handleUsernameChange} />
                <label>Password: </label>
                <input type="password" value={password} onChange={handlePasswordChange} />
                <button type="submit">Submit</button>
            </form>
            <p>New user? Sign up <button onClick={() => window.location.href="/register"}>here</button></p>
        </>
    )

}

export default Login;