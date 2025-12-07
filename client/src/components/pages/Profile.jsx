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

    let styles = {
        settings: {
            display: "flex",
            justifyContent: "space-evenly",
            margin: "auto",
            padding: "2%"
        }
    }

    return(
        isLoading ? <p> Loading... </p> :
        <div>
            <div className="userHeader">
                <User name={name} username={username} pfp={pfp} />
                <div className="userSetting" style={styles.settings}>
                    <Link to="/logout"><u>Log Out</u></Link>
                    <Link to="/movieauth"><u>Back To Movies</u></Link>
                </div>
            </div>
            <div className="favourites">
                <h2>Based on your 5 most recent favourites: </h2>
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