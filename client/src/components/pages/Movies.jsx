import { useEffect, useState } from 'react';
import { Link, Navigate } from 'react-router-dom';

function Movies(){
    const [isLoading, setLoading] = useState(true)
    const [data, setData] = useState([])
    const [page, setPage] = useState(0)
    const [terms, setTerms] = useState("")
    const [totalPages, setTotalPages] = useState(327);

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
   
    const load = (p, text) => {
        fetch(`http://localhost:8080/api/movies?page=${p}&search=${text}`, {
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
        .catch(err => console.error(err))
        .finally(() => setLoading(false))
    }

    const getPageCount = (text) => {
        fetch(`http://localhost:8080/api/movies/count?search=${text}`, {
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`,
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, PATCH, DELETE'
            }
        })
        .then(response => response.json())
        .then(json => {
            setTotalPages(Math.ceil(json / 30))
        })
        .catch(err => console.error(err))
    }

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
    }

    const setKeywords = (event) => {
        setTerms(event.target.value);
    }

    useEffect(() => {
        getPageCount(terms)
        const loadCond = () => {
            if (page > totalPages){
                load(totalPages, terms)
                
            }
            else if (page <= 0){
                load(0, terms)
            }
            else{
                load(page, terms)
            }
        }
        loadCond()
    }, [page, terms, totalPages]);
    
    const movieCatalog = data.map(movie => {
        return (
        <div className="movie" style={styles.movie}>
            <img src={movie.poster_url} alt={movie.title} height="200px" width="150px"></img>
            <p>{movie.title}</p>
            <p>{movie.releaseDate}</p>
            <p>{movie.rating}</p>
            <button onClick={() => setFavourite(movie.id)}>Add to favourites</button>
        </div>
        )
    })

    return (
        isLoading ? <p>Loading...</p> :
        <>
            <button onClick={() => setPage(page-1)}>&lt;</button>
            <p style={styles.paragraph}>Page: {page} of {totalPages}</p>
            <button onClick={() => setPage(page+1)}>&gt;</button>

            <Link to="/profileauth">{localStorage.getItem("username")}</Link>
            <textarea value={terms} onChange={setKeywords}></textarea>

            <div className="catalog" style={styles.catalog}>
                {movieCatalog}
            </div>
        </>
    )
}

export default Movies