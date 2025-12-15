import { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';
import Error from '../Error';

function Login(){
    
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")
    const [activated, setActivated] = useState(false)
    const [message, setMessage] = useState("")

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
            localStorage.setItem("username", username)
            localStorage.setItem("jwtToken", data.token)
            console.log(data)
            window.location.href = "/movies"
        })
        .catch(err => {
            console.log(err)
            setMessage("Failed to login")
        })
        .finally(() => setActivated(true))
    }

    return (
        <div className="login">
            <h3>Sign In</h3>
            <form action={handleLogin}>
                <label>Username: </label>
                <input type="text" value={username} onChange={handleUsernameChange} />

                <br></br>

                <label>Password: </label>
                <input type="password" value={password} onChange={handlePasswordChange} />
                <br></br>
                
                <button type="submit">Submit</button>
            </form>
            <p>New user? Sign up <a onClick={() => window.location.href="/register"}>here</a></p>
            {activated && <Error message={message}></Error>}
        </div>
    )

}

export default Login;