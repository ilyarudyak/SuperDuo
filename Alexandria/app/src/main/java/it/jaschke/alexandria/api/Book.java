package it.jaschke.alexandria.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ilyarudyak on 8/13/15.
 */
public class Book {

    private String title;
    private String subtitle;
    private String imgUrl;
    private String description;
    private String isbn_13;

    private List<String> authors;
    private List<String> categories;

    public Book() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Book(String title, String subtitle,
                String imgurl, String description, String isbn_13,
                List<String> authors, List<String> categories) {
        this.authors = authors;
        this.categories = categories;
        this.description = description;
        this.imgUrl = imgurl;
        this.subtitle = subtitle;
        this.title = title;
        this.isbn_13 = isbn_13;

        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public List<String> getAuthors() {
        return authors;
    }
    public List<String> getCategories() {
        return categories;
    }
    public String getDescription() {
        return description;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public String getSubtitle() {
        return subtitle;
    }
    public String getTitle() {
        return title;
    }
    public String getIsbn_13() {
        return isbn_13;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setIsbn_13(String isbn_13) {
        this.isbn_13 = isbn_13;
    }

    @Override
    public String toString() {
        return "Book{" +
                "authors=" + authors +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", description='" + description + '\'' +
                ", categories=" + categories +
                '}';
    }
    public String getAuthorsAsString() {
        return authors.toString().replace("[" , "").replace("]", "");
    }
    public String getCategoriesAsString() {
        return categories.toString().replace("[" , "").replace("]", "");
    }
}
