import { useState } from 'react'

function Movie(props){

    const [buttonText, setButtonText] = useState("Add to favourites")
    const [isFavourite, setExistsFavourite] = useState(true)
    const [modified, setModified] = useState(false)

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
            setButtonText("Remove from favourites")
            setExistsFavourite(true)
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
            setButtonText("Add to favourites")
            setExistsFavourite(false)
        })
        .catch(err => console.error(err))
        .finally(() => setModified(true))
    
    }

    return (
        <div className="movie" style={props.style}>
            <img src={props.movie.poster_url} alt={props.movie.title} height="200px" width="150px"></img>
            <p>{props.movie.title}</p>
            <p>{props.movie.releaseDate}</p>
            <p>{props.movie.rating}</p>
            {!props.isFavourite ? <button onClick={() => {
                isFavourite && modified ? deleteFavourite(props.movie.id) : setFavourite(props.movie.id)
            }}>{buttonText}</button> : <></> }
        </div>
    )
}

export default Movie;