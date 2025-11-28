import { Link } from 'react-router-dom';

function Home () {

    console.log(localStorage.getItem("jwtToken"))

    let styles = {
        links: {
            display: "flex",
            justifyContent: "space-evenly"
        }
    }

    return (

        <div className="container">
            <h1>Find YOUR taste in movies</h1>
            <div className="links" style={styles.links}>
                <Link to="/movieauth">Enter Catalog</Link>
                <Link to="/register">Sign Up</Link>
                <Link to="/login">Log In</Link>
            </div>
        </div>
    )
}

export default Home