export function Book(props) {
    const renderBookInfo = () => {
        return <>
            {props.book.title}, by {props.book.author}
        </>
    }
    const renderFavoriteOrNormal = () => {
        if (props.book.favorite) {
            return <strong>
                {renderBookInfo()}
            </strong>
        } else {
            return <>
                {renderBookInfo()}
            </>
        }
    };

    return <li>
        {renderFavoriteOrNormal()}
        <button onClick={() => props.toggleFavorite(props.book)}>Favorite</button>
        <button onClick={() => props.deleteBook(props.book.id)}>Remove</button>
    </li>
}