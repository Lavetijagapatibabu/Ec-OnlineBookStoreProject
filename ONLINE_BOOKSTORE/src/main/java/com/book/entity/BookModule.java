package com.book.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "books_table")
@NoArgsConstructor
@AllArgsConstructor
public class BookModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id_col")
    private Long id;

    @Column(name = "book_title_col", nullable = false, length = 150)
    private String title;

    @Column(name = "book_genre_col", length = 100)
    private String genre;

    @Column(name = "book_author_col", nullable = false, length = 100)
    private String authorName;
    
    @Column(name="book_price_col")
    private Double price;
}
