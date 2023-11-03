import {useEffect, useState} from "react";
import {BookList} from "./BookList";
import {BookCreator} from "./BookCreator";

const API_URL = "http://localhost:8080/books";

const App = () => {
    const [books, setBooks] = useState([]);

    // Load books from the server after App is mounted.
    useEffect(() => {
        loadBooks();
    }, [])

    const loadBooks = () => {
        // GET all books from the server.
        fetch(API_URL)
            .then(response => response.json())
            .then(json => setBooks(json));
    }

    const createBook = (newBook) => {
        fetch(API_URL, {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newBook),
        })
            .then(response => loadBooks());
    }

    const toggleFavorite = (book) => {
        fetch(API_URL + '/' + book.id, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
            body: '{"favorite": "' + !book.favorite + '"}',
        })
            .then(response => loadBooks());
    }

    function deleteBook(bookId) {
        fetch(API_URL + '/' + bookId, {
            method: 'DELETE',
        })
            .then(response => loadBooks());
    }

    return (
        <div>
            <h1>My Library</h1>
            <BookCreator createBook={(newBook) => createBook(newBook)}/>
            <BookList books={books}
                      toggleFavorite={(book) => toggleFavorite(book)}
                      deleteBook={(bookId) => deleteBook(bookId)}
            />
        </div>
    );
}

export default App;
