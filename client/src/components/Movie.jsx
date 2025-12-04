import { useState, useEffect } from 'react'

function Movie(props){

    const [buttonText, setButtonText] = useState("Add to favourites")
    const [isFavourite, setExistsFavourite] = useState(false)
    const [modified, setModified] = useState(false)
    const [verified, setVerified] = useState(false)

    const setFavourite = (id) => {
        fetch(`http://localhost:8080/api/favourites?id=${id}`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                username: localStorage.getItem("username")
            })
        })
        .then(res => res.json())
        .then(json => {
            console.log(json)
        })
        .catch(err => console.error(err))
        .finally(() => setModified(true))
    }

    const deleteFavourite = (id) => {
        fetch(`http://localhost:8080/api/favourites?id=${id}`, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify({
                username: localStorage.getItem("username")
            })
        })
        .then(res => res.text())
        .then(text => {
            console.log(text)
        })
        .catch(err => console.error(err))
        .finally(() => setModified(true))
    
    }

    const verifyFavourite = (id) => {
        fetch(`http://localhost:8080/api/movies/exists?id=${id}`, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            }
        })
        .then(res => res.text())
        .then(body => {
            if (body == "true") {
                setExistsFavourite(true)
                setButtonText("Remove from favourites")
            } else {
                setExistsFavourite(false)
                setButtonText("Add to favourites")
            }
        })
        .finally(() => setVerified(true))
    }

    useEffect(() => {
        verifyFavourite(props.movie.id)
    }, [verified, isFavourite, buttonText, modified])

    function handleButtonClick(e){
        e.preventDefault();
        if (verified){
            if (isFavourite){
                deleteFavourite(props.movie.id)
            }
            else {
                setFavourite(props.movie.id)
            }
        }
    }

    return (
        <div className="movie" style={props.style}>
            <img src={props.movie.poster_url} alt={props.movie.title} height="200px" width="150px"></img>
            <p>{props.movie.title}</p>
            <p>{props.movie.releaseDate}</p>
            <p>{props.movie.rating}</p>
            {!props.isFavourite ? <button onClick={handleButtonClick}>{verified && buttonText}</button> : <></> }
        </div>
    )
}

export default Movie;