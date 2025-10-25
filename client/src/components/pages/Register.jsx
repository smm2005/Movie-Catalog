import { useState } from "react";
import { createRoot } from "react-dom/client";
import { Navigate } from "react-router-dom";

function Register () {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    function handleNameChange(e){
        setName(e.target.value);
    }

    function handleEmailChange(e){
        setEmail(e.target.value);
    }
    
    function handleUsernameChange(e){
        setUsername(e.target.value);
    }

    function handlePasswordChange(e){
        setPassword(e.target.value);
    }

    const registerUser = () => {
        fetch(`http://localhost:8080/api/register`, {
            method: "POST",
            headers: {
                'Content-type': 'application/json',
                'Access-Control-Allow-Origin': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                realname: name,
                email: email,
                username: username,
                password: password
            })
        })
        .then(response => response.json())
        .then(json => {
            console.log(json);
        })
        .catch((error) => console.error(error))
        .finally()
    }

    const redirectToLogin = (event) => {
        event.preventDefault();
        window.location.href = "/login";
    }

    return (
            <form action={registerUser} onSubmit={redirectToLogin}>
                <label>Name: </label>
                <input type="text" 
                       value={name} 
                       onChange={handleNameChange}
                       name="realname" />
                
                <label>Email: </label>
                <input type="text"
                       value={email}
                       onChange={handleEmailChange}
                       name="email" />

                <label>Username: </label>
                <input type="text"
                       value={username}
                       onChange={handleUsernameChange}
                       name="username" />

                <label>Password: </label>
                <input type="password"
                       value={password}
                       onChange={handlePasswordChange}
                       name="password" />

                <label>Confirm Password: </label>
                <input type="password" 
                       value={password}
                       onChange={handlePasswordChange}
                       name="confirm" />

                <input type="submit" value="Register"/>
            </form>
    )
}


export default Register;