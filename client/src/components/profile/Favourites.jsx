import { useState, useEffect } from 'react';
import Movie from '../Movie'

function Favourites(){
    const [loading, setLoading] = useState(true)
    const [favourites, setFavourites] = useState([])
    const jwtToken = localStorage.getItem("jwtToken")

    let styles={

        favourites: {
            display: "flex",
            justifyContent: "space-evenly"
        },

        movie: {
            display: "block",
            margin: 0,
            padding: "1%",
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
            setFavourites(json)
            console.log(json)
        })
        .catch(error => {
            console.log(error)
        })
        .finally(() => setLoading(false))
    }

    useEffect(() => {
        getFavourites(0)
    }, [])

    const movieFavourites = favourites?.map(favourite => {
        return (
            <Movie style={styles.movie} movie={favourite.movie} isFavourite={true}></Movie>
        )
    })

    return (
        loading ? <p> Loading... </p> :
        <>
            <div className="favourites" style={styles.favourites}>
                {movieFavourites}
            </div>
        </>
    )
}

export default Favourites;