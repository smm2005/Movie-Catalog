import {useState, useEffect} from 'react'
import Movie from '../Movie'

function Search(props){

    const [isLoading, setLoading] = useState(true)
    const [movies, setMovies] = useState([])
    const [page, setPage] = useState(0)

    let styles={

        searched: {
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

    const findMoviesFromSearch = (page, text) => {
        fetch(`http://localhost:8080/api/movies?page=${page}&search=${text}`)
        .then(res => res.json())
        .then(json => {
            setMovies(json)
        })
        .catch(err => console.log(err))
        .finally(() => setLoading(false));
    }

    useEffect(() => {
        var pageCount = Math.floor((movies.length) / 30)
        if (page > pageCount){
            findMoviesFromSearch(pageCount, props.text)
        }
        else if (page <= 0){
            findMoviesFromSearch(0, props.text)
        }
        else{
            findMoviesFromSearch(page, props.text)
        }
    }, [page, props.text])

    const searchedMovies = movies.map(movie => {
        return (
            <Movie movie={movie} style={styles.movie} />
        )
    })

    return (
        isLoading ? <p> Loading ... </p> :
        <div className="searched">
            {searchedMovies}
        </div>
    )


}

export default Search;