import { useState, useEffect } from 'react'
import Movie from '../Movie'

function Recommendations(){
    
    const [loading, setLoading] = useState(true)
    const [recs, setRecs] = useState([])
    const jwtToken = localStorage.getItem("jwtToken")

    let styles={

        recommendations: {
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
    
    const getRecommendations = () => {
        fetch(`http://localhost:8080/api/recs`, {
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
            setRecs(json)
            console.log(json)
        })
        .catch(err => console.log(err))
        .finally(() => setLoading(false))
    }

    useEffect(() => {
        getRecommendations()
    }, [])

    const recommendations = recs?.map(movie => {
        return (
            <Movie style={styles.movie} movie={movie} isFavourite={true}/>
        )
    })

    return (
        loading ? <p> Loading ... </p> :
        <>
            <div className="recs" style={styles.recommendations}>
                {recommendations.length > 0 ? recommendations : <p>No recommendations found</p>}
            </div>
        </>
    )
}

export default Recommendations;