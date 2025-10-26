import { useState, useEffect } from 'react';

function Favourites(){
    const [loading, setLoading] = useState(true)
    const [favourites, setFavourites] = useState([])
    const jwtToken = localStorage.getItem("jwtToken")

    let styles={

        favourites: {
            display: "inline-flex",
            flexWrap: "wrap",
            gridGap: "1.5em"
        },

        movie: {
            display: "block",
            margin: 0,
            padding: "2%",
            width: "250px",
        }
    }

    const getFavourites = (page) => {
        fetch(`http://localhost:8080/api/favourites?page=${page}`, {
            method: "GET",
            headers: {
                'Content-Type': 'application/json',
                'Authentication': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            }
        })
        .then(res => res.json())
        .then(json => {
            console.log(json)
            setFavourites(json.favourites)
        })
        .catch(error => {
            console.log(error)
        })
        .finally(() => setLoading(false))
    }

    useEffect(() => {
        getFavourites(0)
    }, [])

    const movieFavourites = favourites.length == 0 ? () => {return (<p>No favourites yet!</p>)} : favourites.map(movie => {
        return (
        <div className="movie" style={styles.movie}>
            <img src={movie.poster_url} alt={movie.title} height="200px" width="150px"></img>
            <p>{movie.title}</p>
            <p>{movie.releaseDate}</p>
            <p>{movie.rating}</p>
        </div>
        )
    })

    return (
        loading ? <p> Loading </p> :
        <>
            <div className="favourites" style={styles.favourites}>
                {movieFavourites}
            </div>
        </>
    )
}

export default Favourites;