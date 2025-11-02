function Movie(props){
    return (
        <div className="movie" style={props.style}>
            <img src={props.movie.poster_url} alt={props.movie.title} height="200px" width="150px"></img>
            <p>{props.movie.title}</p>
            <p>{props.movie.releaseDate}</p>
            <p>{props.movie.rating}</p>
        </div>
    )
}

export default Movie;