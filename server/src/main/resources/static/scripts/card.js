const button = document.getElementById("favouriteButton")
const params = new URLSearchParams(window.location.search)
var movieId = params.get("movie")
var count = 1

button.addEventListener("click", () => {
    fetch(`/api/favourites?movie=${movieId}`, {
        method: "PUT",
        mode: "cors",
        credentials: "include",
        headers: {
            "Content-Type": "application/json; charset=UTF-8",
            "Authorization": "Bearer " + localStorage.getItem("token"),
        },
        body: JSON.stringify({
            "id": count,
            "date": null,
            "user": null,
            "movie": null
        })
    })
    .then(response => response.json())
    .then(data => {
        console.log(data)
    })
    .catch(error => {
        console.error("Error:", error)
    })

    count += 1
})