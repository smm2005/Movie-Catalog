# Movie Catalog APIs

## Endpoints

### Authentication

`POST /api/auth/refresh`

*Request:*
```json
headers: {
    Content-Type: 'application/json',
    Access-Control-Allow-Methods: '*',
    Access-Control-Allow-Headers: 'GET, PUT, POST, DELETE, PATCH'
},
body: {
    jwtToken: {token}
}
```


### Favourites

`GET /api/favourites`

`GET /api/favourites/first`

`GET /api/favourites?movie={id}`

`GET /api/favourites?page={number}`

`GET /api/favourites?user={id}`

`POST /api/favourites?id={number}`

`DELETE /api/favourites?id={number}`

### Login

`POST /api/login`

### Logout

`POST /api/logout`

### Movies

`GET /api/movies`

`GET /api/movies/count?search={text}`

`GET /api/movies?page={number}`

`GET /api/movies?page={number}&search={text}`

`GET /api/movies?id={number}`

`GET /api/movies/exists?id={number}`

### Recommendations

`GET /api/recs`

### Registration

`POST /api/register`

### Users

`POST /api/user`

`POST /api/user/all`

`GET /api/user/{id}`

`DELETE /api/user/{id}`