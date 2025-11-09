import { useState, useEffect } from 'react'
import User from '../profile/User'
import Favourites from '../profile/Favourites'
import Recommendations from '../profile/Recommendations'

function Profile(){

    const [name, setName] = useState("")
    const [username, setUsername] = useState("")
    const [pfp, setPfp] = useState("")
    const [isLoading, setLoading] = useState(true)

    const jwtToken = localStorage.getItem("jwtToken")

    const getUser = () => {
        fetch(`http://localhost:8080/api/user`, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            }
        })
        .then(res => res.json())
        .then(json => {
            setName(json.realname)
            localStorage.setItem("username", json.username)
            setUsername(json.username)
        })
        .catch(error => {
            console.log(error)
        })
        .finally(() => setLoading(false))
    }

    useEffect(() => {
        getUser()
    }, [])

    return(
        isLoading ? <p> Loading... </p> :
        <div>
            <User name={name} username={username} pfp={pfp} />
            <Favourites />
            <Recommendations />
        </div>
    )
}

export default Profile;