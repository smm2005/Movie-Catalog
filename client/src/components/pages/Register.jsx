import { useState, useEffect } from "react";
import { createRoot } from "react-dom/client";
import { Navigate } from "react-router-dom";
import Error from "../Error.jsx"

function Register () {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirm, setConfirm] = useState("");
    
    const [alert, setAlert] = useState("");
    const [activated, setActivated] = useState(false);

    const [register, setRegister] = useState(true);

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

    function clearForm(){
        setName("")
        setEmail("")
        setUsername("")
        setPassword("")
        setConfirm("")
    }

    const registerUser = () => {
        if (password != confirm){
            setActivated(true)
            setAlert("Confirmation needs to be the same as password")
        }
        if (password > 50 || password < 8){
            setActivated(true)
            setAlert("Password needs to be between 8 to 50 characters long")
        }
        else {
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
            .then(response => response.text())
            .then(text => {
                if (text == "User has been registered") {
                    window.location.href = "/login"
                }
                else {
                    setAlert(text)
                }
            })
            .catch((error) => {
                setAlert(error)
            })
            .finally(() => {
                setActivated(true)
                clearForm()
            })
        }
    }

    useEffect(() => {
        console.log(alert)
    }, [alert])

    return (
        <div className="register">
            <h3>Sign Up</h3>
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

                <button type="submit">Register</button>
            </form>

            {activated && <Error message={alert}></Error>}
        </div>
    )
}


export default Register;