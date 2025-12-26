# Movie Catalog APIs

## Endpoints

### Authentication

> POST /api/auth/refresh

Validates a JWT token between pages accessed so as to prevent the end-user from having to log in again in order to access important application resources.

### Favourites

> GET /api/favourites

Accesses all favourites from every registered user in the application.

> GET /api/favourites/first

Accesses the first 5 favourites from every registered user in the application.

> GET /api/favourites?movie={id}

Retrieves a favourite based on the existence of a movie in the favourite repository. This API endpoint uses an ID parameter to refer to the movie's ID.

> GET /api/favourites?page={number}

Retrieves 5 favourites in a page provided by a page number as a parameter. For example, favourites with IDs 6, 7, 8, 9 and 10 will be on Page 1 (since pages are 0 indexed).

Favourites in any page `x` will have IDs ranging from `5x + 1` to `5x + 5`.

> GET /api/favourites?user={id}

Retrieves all favourites in the favourites repository associated with a user given their user ID.

> POST /api/favourites?id={number}

Searches the movie repository for a movie corresponding to the ID provided by the `number` parameter, then adds that movie to the movie repository, corresponding to the current user logged in.

> DELETE /api/favourites?id={number}

Removes a movie from the favourites repository given the movie ID provided by the `number` parameter.

### Login

> POST /api/login

Receives and authenticates log-in data from the client, then generates access and refresh JWT tokens for use within the application.

### Logout

> POST /api/logout

Receives log-out prompt from the client to end the user's application session and prevent access to resources without logging back in, through revoking an existing refresh token.

Since the refresh token is revoked, the access token can not make use of the refresh token to validate itself, causing the server to redirect to the login page so that the user can generate another access and refresh token.

### Movies

> GET /api/movies

Access all movies from the movie repository. 

> GET /api/movies/count?search={text}

Access the count of movies that contain the text provided as a parameter in it's title.

For example, if the `text` parameter takes in the string `termi`, some movies that are included in the response payload are `The Terminal` and `Terminator`.

> GET /api/movies?page={number}

Retrieves 30 movies in a page provided by a page number as a parameter. Pagination for movies will also start at the index of 0.

Movies in any page `x` will have IDs ranging from `30x + 1` to `30x + 30`.

> GET /api/movies?page={number}&search={text}

Retrieves all movies where the text in the search field exists somewhere within the title, with all matches being case-insensitive. For any amount of movie titles, `x`, that are matched by a provided text, there are `(x/30) + 1` pages.

> GET /api/movies?id={number}

Returns a movie from a repository of movies given the movie ID.

> GET /api/movies/exists?id={number}

Returns a boolean as a response, which determines whether the movie, identified based on the movie ID number provided as a parameter, exists as a favourite.

`true` - Movie does exist as a favourite

`false` - Movie does **not** exist as a favourite

### Recommendations

> GET /api/recs

Get the top 5 recommendations from the movie recommender as an iterable provided in the response payload.

### Registration

> POST /api/register

Submit registration data from the client through a RegistrationForm class and the save() method in the user repository.

### Users

> POST /api/user

Get the current user in a user repository given the username in the request payload. The POST method is used rather than GET since GET mappings do not support request bodies.

> GET /api/user/all

Get all users that have registered in an application.

> GET /api/user/{id}

Get a specific user out of all registered users given the user's ID in the user repository.

> DELETE /api/user/{id}

Delete a user's account out of all registered users