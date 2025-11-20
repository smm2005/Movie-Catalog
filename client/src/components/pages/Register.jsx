import { useState } from "react";
import { createRoot } from "react-dom/client";
import { Navigate } from "react-router-dom";

function Register () {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");

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

    function handleConfirmChange(e){
        setConfirm(e.target.value);
    }

    const registerUser = () => {
        if (password != confirm){
            console.log("ERROR: Confirmation needs to be the same as password")
        }
        else{
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
                    password: password,
                })
            })
            .then(response => response.json())
            .then(json => {
                console.log(json);
            })
            .catch((error) => console.error(error))
            .finally(() => window.location.href = "/login")
        }
    }

    return (
            <form action={registerUser}>
                <label>Name: </label>
                <input type="text" 
                       value={name} 
                       onChange={handleNameChange}
                       name="realname" />
                
                <br></br>

                <label>Email: </label>
                <input type="text"
                       value={email}
                       onChange={handleEmailChange}
                       name="email" />

                <br></br>

                <label>Username: </label>
                <input type="text"
                       value={username}
                       onChange={handleUsernameChange}
                       name="username" />

                <br></br>

                <label>Password: </label>
                <input type="password"
                       value={password}
                       onChange={handlePasswordChange}
                       name="password" />

                <br></br>

                <label>Confirm Password: </label>
                <input type="password" 
                       value={confirm}
                       onChange={handleConfirmChange}
                       name="confirm" />

                <br></br>

                <input type="submit" value="Register"/>
            </form>
    )
}


export default Register;