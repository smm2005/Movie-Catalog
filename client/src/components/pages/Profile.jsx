import { useState, useEffect } from 'react'
import { Link } from 'react-router-dom'
import User from '../profile/User'
import Favourites from '../profile/Favourites'
import Recommendations from '../profile/Recommendations'

function Profile(){

    const [name, setName] = useState("")
    const [pfp, setPfp] = useState("")
    const [isLoading, setLoading] = useState(true)

    const jwtToken = localStorage.getItem("jwtToken")
    const username = localStorage.getItem("username")

    const getUser = () => {
        fetch(`http://localhost:8080/api/user`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                username: username
            })
        })
        .then(res => res.json())
        .then(json => {
            setName(json.realname)
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
            <div className="userSettings">
                <User name={name} username={username} pfp={pfp} />
                <Link to="/logout">Log Out</Link>
            </div>
            <div className="favourites">
                <h2>Based on your favourites: </h2>
                <Favourites />
            </div>
            <div className="recs">
                <h2>We find that you will like: </h2>
                <Recommendations />
            </div>
        </div>
    )
}

export default Profile;