import { useEffect, useState } from 'react';

function Login(){
    
    const [username, setUsername] = useState("")
    const [password, setPassword] = useState("")

    const jwtToken = localStorage.getItem("jwtToken").toString();

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
                'Authorization': `Bearer ${jwtToken}`,
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
        })
        .catch(err => {
            console.log(err)
        })
    }

    return (
        <form action={handleLogin}>
            <label>Username: </label>
            <input type="text" value={username} onChange={handleUsernameChange} />
            <label>Password: </label>
            <input type="password" value={password} onChange={handlePasswordChange} />
            <button type="submit">Submit</button>
        </form>
    )

}

export default Login;