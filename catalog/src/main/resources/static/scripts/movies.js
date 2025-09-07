const current = window.location.search
const params = new URLSearchParams(current)

var url = `http://localhost:8080/api/movies?page=${params.get("page")}`


document.addEventListener("DOMContentLoaded", () => {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const catalog = document.getElementById("catalog")
            data.forEach(movie => {
                const movieElement = document.createElement("div")
                movieElement.id = "movie"
                console.log(movie);

                var poster = new Image(200, 200)
                poster.src = movie.poster_url
                
                var title = document.createElement("h5")
                title.textContent = movie.title
                var releaseDate = document.createElement("p")
                releaseDate.textContent = movie.releaseDate
                var rating = document.createElement("p")
                rating.textContent = movie.rating
                var textbreak = document.createElement("br")

                movieElement.appendChild(poster)
                movieElement.appendChild(textbreak)
                movieElement.appendChild(textbreak)
                movieElement.appendChild(title)
                movieElement.appendChild(textbreak)
                movieElement.appendChild(releaseDate)
                movieElement.appendChild(textbreak)
                movieElement.appendChild(rating)
                movieElement.appendChild(textbreak)

                catalog.appendChild(movieElement)
            })
        })
})