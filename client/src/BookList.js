import {Book} from "./Book";

export const BookList = (props) => {
    return <ul>
        {props.books.map((book) => {
            return <Book book={book}
                         key={book.id}
                         toggleFavorite={props.toggleFavorite}
                         deleteBook={props.deleteBook}
            />
        })}
    </ul>;
}