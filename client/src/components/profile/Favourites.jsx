import { useState, useEffect } from 'react';
import Movie from '../Movie'

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
            <Movie style={styles.movie} movie={favourite.movie}/>
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