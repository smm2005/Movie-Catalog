import { useEffect, useState } from 'react';
import { Link, Navigate } from 'react-router-dom';
import Movie from '../Movie';

function Movies(){
    const [isLoading, setLoading] = useState(true)
    const [data, setData] = useState([])
    const [page, setPage] = useState(1)
    const [terms, setTerms] = useState("")
    const [totalPages, setTotalPages] = useState(328)

    const jwtToken = localStorage.getItem("jwtToken")

    let styles={

        header: {
            display: "flex",
            flexWrap: "wrap",
            justifyContent: "space-between"
        },

        search: {
            fontFamily: "system-ui, Avenir, Helvetica, Arial, sans-serif",
            height: "20px",
            width: "200px",
            borderRadius: "50px",
            margin: 0,
            padding: "2px",
            resize: "none"
        },

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
        },

        settings: {
            display: "block",
            margin: 0,
            width: "150px"
        },

        button: {
            margin: 0,
            visibility: "hidden"
        }
    }
   
    const load = (p, text) => {
        fetch(`http://localhost:8080/api/movies?page=${p-1}&search=${text}`, {
            headers: {
                'Content-Type': 'application/json',
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
                'Access-Control-Allow-Headers': '*',
                'Access-Control-Allow-Methods': 'GET, POST, PUT, PATCH, DELETE'
            }
        })
        .then(response => response.json())
        .then(json => {
            setTotalPages(Math.ceil(json / 30) + 1)
        })
        .catch(err => console.error(err))
    }

    const setKeywords = (event) => {
        setTerms(event.target.value);
    }

    const movieCatalog = data.map(movie => {
        return (
            <Movie key={movie.id} style={styles.movie} movie={movie} includeFavourite={true}></Movie>
        )
    })

    useEffect(() => {
        getPageCount(terms)
        if (page > totalPages){
            load(totalPages, terms)        
        }
        else if (page < 1){
            load(1, terms)
        }
        else{
            load(page, terms)
        }
    }, [page, terms, totalPages]);

    return (
        isLoading ? <p>Loading...</p> :
        <>
            <div className="header" style={styles.header}>
                <div className="pageSelect">
                    { (page > 1) && <button onClick={() => setPage(page-1)}>&lt;</button> }
                    <p style={styles.paragraph}>Page: {page} of {totalPages}</p>
                    { (page < totalPages) && <button onClick={() => setPage(page+1)}>&gt;</button> }
                </div>

                <textarea value={terms} onChange={setKeywords} style={styles.search}></textarea>
                
                <Link to="/profileauth">{localStorage.getItem("username")}</Link>
            </div>


            <div className="catalog" style={styles.catalog}>
                {movieCatalog}
            </div>
        </>
    )
}

export default Movies