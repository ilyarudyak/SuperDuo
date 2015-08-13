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

    private List<String> authors;
    private List<String> categories;

    public Book() {
        authors = new ArrayList<>();
        categories = new ArrayList<>();
    }

    public Book(String title, String subtitle,
                String imgurl, String description,
                List<String> authors, List<String> categories) {
        this.authors = authors;
        this.categories = categories;
        this.description = description;
        this.imgUrl = imgurl;
        this.subtitle = subtitle;
        this.title = title;

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
}
