package com.swf.server;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:3000",
        methods = {RequestMethod.DELETE,
                RequestMethod.GET,
                RequestMethod.OPTIONS,
                RequestMethod.POST,
                RequestMethod.PATCH})
public class BookController {

    private final BookRepository repository;

    BookController(BookRepository repository) {
        this.repository = repository;
    }

    @GetMapping("")
    public Iterable<Book> getAllBooksFromRepository() {
        return this.repository.findAll();
    }

    @GetMapping("{id}")
    public Book getBookByIdFromRepository(@PathVariable Long id) {
        return this.repository.findById(id).get();
    }

    @PostMapping("")
    public Book createBookInRepository(@RequestBody Book book) {
        return this.repository.save(book);
    }

    @PatchMapping("{id}")
    public Book patchBookInRepository(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book currentBook = this.repository.findById(id).get();
        currentBook.patch(updatedBook);
        currentBook = this.repository.save(currentBook);
        return currentBook;
    }

    @DeleteMapping("{id}")
    public String deleteBookFromRepository(@PathVariable Long id) {
        this.repository.deleteById(id);
        return "SUCCESS";
    }
}
