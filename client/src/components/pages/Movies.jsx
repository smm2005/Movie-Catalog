import { useEffect, useState } from 'react';
import { Navigate } from 'react-router-dom';

function Movies(){
    const [isLoading, setLoading] = useState(true)
    const [verifyBoolean, setVerify] = useState(false)
    const [data, setData] = useState([])
    const [page, setPage] = useState(0)

    var verifyBool = true

    const jwtToken = localStorage.getItem("jwtToken")

    let styles={

        paragraph: {
            display: "inline-block",
            width: "200px",
            position: "relative",
            margin: 0,
            padding: 0,
            textAlign: "center"
        },

        catalog: {
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

    const verify = () => {
        fetch(`http://localhost:8080/api/auth/verify`, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, PATCH'
            },
            body: JSON.stringify(
                {}
            )
        })
        .then(response => 
            response.json()
        )
        .then(json => {
            if (json.token == "Error"){
                setVerify(false)
            }
            else{
                setVerify(true)
            }
        })
        .catch(error => console.error(error))
        .finally(verifyBoolean)
    }

    const load = (p) => {
        fetch(`http://localhost:8080/api/movies?page=${p}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, PATCH, DELETE'
            }
        })
        .then(response => response.json())
        .then(json => {
            console.log(json)
            setData(json)
        })
        .catch(verify())
        .finally(() => setLoading(false))
    }


    useEffect(() => {
        if (page > 327){
            load(327)
        }
        else if (page <= 0){
            load(0)
        }
        else{
            load(page)
        }
    }, [page]);
    
    const movieCatalog = data.map(movie => {
        return (
        <div className="movie" style={styles.movie}>
            <img src={movie.poster_url} alt={movie.title} height="200px" width="150px"></img>
            <p>{movie.title}</p>
            <p>{movie.releaseDate}</p>
            <p>{movie.rating}</p>
        </div>
        )
    })

    console.log(verifyBoolean)
    
    return (
        !verifyBoolean ? <Navigate to="/login" /> :
        isLoading ? <p>Loading...</p> :
        <>
            <button onClick={() => setPage(page-1)}>&lt;</button>
            <p style={styles.paragraph}>Page: {page} of 327</p>
            <button onClick={() => setPage(page+1)}>&gt;</button>
            <div className="catalog" style={styles.catalog}>
                {movieCatalog}
            </div>
        </>
    )
}

export default Movies