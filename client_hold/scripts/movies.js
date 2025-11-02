const current = window.location.search
const params = new URLSearchParams(current)

var url = ""
if (params.get("search") == null || params.get("search").trim().length == 0 || params.get("search") == ""){
    url = `/api/movies?page=${params.get("page")}`
}
else{
    url = `/api/movies?page=0&search=${params.get("search")}`
}

document.addEventListener("DOMContentLoaded", () => {
    fetch(url)
        .then(response => response.json())
        .then(data => {
            const catalog = document.getElementById("catalog")
            data.forEach(movie => {
                const movieElement = document.createElement("div")
                movieElement.className = "movie"
                movieElement.id = "movie"
                console.log(movie);

                var card_link = document.createElement("a")
                card_link.href = `/card?movie=${movie.id}`

                var poster = new Image(200, 300)
                poster.src = movie.poster_url
                
                var title = document.createElement("h5")
                title.textContent = movie.title
                var releaseDate = document.createElement("p")
                releaseDate.textContent = movie.releaseDate
                var rating = document.createElement("p")
                rating.textContent = movie.rating
                var textbreak = document.createElement("br")

                card_link.appendChild(poster)
                movieElement.appendChild(card_link)


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