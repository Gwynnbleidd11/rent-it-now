To start first launch backend application: https://github.com/Gwynnbleidd11/rent-it-now
Then you need to start frontend application: https://github.com/Gwynnbleidd11/rent-it-now-frontend
Go to the browser and use: http://localhost:8081/home

**In separate file "DATABASE_OPERATIONS.md" I am putting MySQL routine, procedure and all triggers to perform database operations,
along with all tables and necessary code.**

Application allows to search for movies (top search bar is not yet functional), display user page, display movie details,
add movie to cart, remove movie from cart, pay, to rent a movie. It also displays current weather of Cracow(hardcoded for now).

Things I will be implementing:
- user details change
- top 3 movies updating when top_level changes in movies table
- search button on top bar
- correction of how rents are displayed in user page
- possibility to extend the rent
- login/security

For the frontend to work correctly, I had to hardcode userId to 1. In case creating a user using the UserController's
createUser() method does not generate the user with the appropriate ID, you may need to create it manually.
Below is a code example for creating a user and a cart. (The cart should also be generated with the ID of 1).

INSERT INTO users (user_id, birth_date, email, password, firstname, lastname, phone_number)
VALUES (1, '1888-01-01', 'alan@gmail.com', 'alanPassword', 'Alan', 'Smith', '123-123-1234');

INSERT INTO carts (cart_id, transaction_id, user_id)
VALUES (1, null, 1);

Below is data to add few movies with POSTMAN to database:

{
    "title": "Alien",
    "director": "Ridley Scott",
    "cast": "Sigourney Weaver, Tom Skerritt, John Hurt",
    "publicationDate": "1979-06-22",
    "price": 9.99,
    "imdbMovieId": "tt0078748"
}

{
    "title": "Star Wars: Episode IV - A New Hope",
    "director": "George Lucas",
    "cast": "Mark Hamill, Harrison Ford, Carrie Fisher",
    "publicationDate": "1979-05-25",
    "price": 15.99,
    "imdbMovieId": "tt0076759"
}

{
    "title": "The Lord of the Rings: The Fellowship of the Ring",
    "director": "Peter Jackson",
    "cast": "Elijah Wood, Ian McKellen, Orlando Bloom",
    "publicationDate": "2001-12-19",
    "price": 14.99,
    "imdbMovieId": "tt0120737"
}

{
    "title": "Dune",
    "director": "Denis Villeneuve",
    "cast": "Timothee Chalamet, Rebecca Ferguson, Zendaya",
    "publicationDate": "2021-10-22",
    "price": 19.99,
    "imdbMovieId": "tt1160419"
}

{
    "title": "Rambo: First Blood",
    "director": "Ted Kotcheff",
    "cast": "Sylvester Stallone, Brian Dennehy, Richard Crenna",
    "publicationDate": "1982-10-22",
    "price": 9.99,
    "imdbMovieId": "tt0083944"
}

{
    "title": "Avatar",
    "director": "James Cameron",
    "cast": "Sam Worthington, Zoe Saldana, Sigourney Weaver",
    "publicationDate": "2009-12-18",
    "price": 11.59,
    "imdbMovieId": "tt0499549"
}

{
    "title": "The Shining",
    "director": "Stanley Kubrick",
    "cast": "Jack Nicholson, Shelley Duvall, Danny Lloyd",
    "publicationDate": "1980-05-23",
    "price": 7.99,
    "imdbMovieId": "tt0081505"
}

{
    "title": "The Dark Knight",
    "director": "Christopher Nolan",
    "cast": "Christian Bale, Heath Ledger, Aaron Eckhart",
    "publicationDate": "2008-07-18",
    "price": 11.99,
    "imdbMovieId": "tt0468569"
}