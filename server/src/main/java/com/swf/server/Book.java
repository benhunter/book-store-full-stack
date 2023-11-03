package com.swf.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String title;
    private String author;
    private Boolean favorite = false;

    public void patch(Book updatedBook) {
        if (updatedBook.getTitle() != null) {
            this.title = updatedBook.getTitle();
        }
        if (updatedBook.getAuthor() != null) {
            this.author = updatedBook.getAuthor();
        }
        if (updatedBook.getFavorite() != null) {
            this.favorite = updatedBook.getFavorite();
        }
    }
}
