import { Link } from 'react-router-dom';

function Home () {
    return (
        <div className="container">
            <h1>Find YOUR taste in movies</h1>
            <Link to="/auth">Movies</Link>
            <Link to="/register">Sign Up</Link>
            <Link to="/login">Log In</Link>
        </div>
    )
}

export default Home