import {useState} from "react";

export function BookCreator(props) {
    const emptyBook = {
        title: '',
        author: '',
        favorite: false,
    };

    const [newBook, setNewBook] = useState({...emptyBook});

    const handleOnChange = (event) => {
        if (event.target.name === 'titleText') {
            setNewBook({...newBook, title: event.target.value});
        }
        if (event.target.name === 'authorText') {
            setNewBook({...newBook, author: event.target.value});
        }
    };

    const handleOnSubmit = (event) => {
        event.preventDefault();

        props.createBook(newBook);

        // Clear the form.
        setNewBook({...emptyBook});
    };

    return <div>
        <form onChange={(event) => handleOnChange(event)}
              onSubmit={(event) => handleOnSubmit(event)}
        >
            <label>Title</label>
            <input type='text'
                   name='titleText'
                   value={newBook.title}
            />
            <br/>
            <label>Author</label>
            <input type='text'
                   name='authorText'
                   value={newBook.author}
            />
            <button type='submit'>Add Book</button>
        </form>
    </div>;
}