import { useState, useEffect } from 'react'

function Movie(props){

    const [buttonText, setButtonText] = useState("Add to favourites")
    const [isFavourite, setExistsFavourite] = useState(false)
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
        .finally(() => setExistsFavourite(true))
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
        .finally(() => setExistsFavourite(false))
    
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
                console.log(id + " Movie is a favourite")
                setButtonText("Remove from favourites")
                setExistsFavourite(true)
            } else {
                console.log(id + " Movie is not a favourite")
                setButtonText("Add to favourites")
                setExistsFavourite(false)
            }
        })
        .finally(() => setVerified(true))
    }

    useEffect(() => {
        verifyFavourite(props.movie.id)
    }, [verified, isFavourite, buttonText])

    function handleButtonClick(e){
        e.preventDefault();
        if (isFavourite){
            setButtonText("Remove from favourites")
            deleteFavourite(props.movie.id)
        }
        else {
            setFavourite(props.movie.id)
            setButtonText("Add to favourites")
        }
    }

    return (
        <div className={`movie-${props.movie.id}`} style={props.style}>
            <img src={props.movie.poster_url} alt={props.movie.title} height="200px" width="150px"></img>
            <p>{props.movie.title}</p>
            <p>{props.movie.releaseDate}</p>
            <p>{props.movie.rating}</p>
            {!props.isFavourite && <button onClick={handleButtonClick}>{verified && buttonText}</button>}
        </div>
    )
}

export default Movie;