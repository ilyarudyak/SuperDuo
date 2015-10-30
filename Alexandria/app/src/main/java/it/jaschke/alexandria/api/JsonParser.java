package it.jaschke.alexandria.api;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ilyarudyak on 8/13/15.
 */
public class JsonParser {

    public static Book getBookFromJson(String bookJsonString) throws JSONException {

        final String ITEMS = "items";

        final String VOLUME_INFO = "volumeInfo";

        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String DESC = "description";
        final String CATEGORIES = "categories";
        final String IMG_URL_PATH = "imageLinks";
        final String IMG_URL = "thumbnail";

        Book book = new Book();

        JSONObject bookJson = new JSONObject(bookJsonString);
        JSONArray bookArray;

        // no books found
        if (bookJson.has(ITEMS)) {
            bookArray = bookJson.getJSONArray(ITEMS);
        } else {
            return null;
        }

        JSONObject bookInfo = ((JSONObject) bookArray.get(0)).getJSONObject(VOLUME_INFO);

        String title = bookInfo.getString(TITLE);                       // (1) title
        book.setTitle(title);

        String subtitle = "";                                           // (2) subtitle
        if (bookInfo.has(SUBTITLE)) {
            subtitle = bookInfo.getString(SUBTITLE);
        }
        book.setSubtitle(subtitle);

        String imgUrl = "";                                             // (3) imgUrl
        if(bookInfo.has(IMG_URL_PATH) && bookInfo.getJSONObject(IMG_URL_PATH).has(IMG_URL)) {
            imgUrl = bookInfo.getJSONObject(IMG_URL_PATH).getString(IMG_URL);
        }
        book.setImgUrl(imgUrl);

        String desc="";                                                 // (4) description
        if(bookInfo.has(DESC)){
            desc = bookInfo.getString(DESC);
        }
        book.setDescription(desc);

        // if we don't have authors - authors array will stay empty
        if (bookInfo.has(AUTHORS)) {                                    // (5) authors
            List<String> authors = new ArrayList<>();
            JSONArray jsonArrayAuthors = bookInfo.getJSONArray(AUTHORS);
            for (int i = 0; i < jsonArrayAuthors.length(); i++) {
                authors.add(jsonArrayAuthors.getString(i));
            }
            book.setAuthors(authors);
        }

        if (bookInfo.has(CATEGORIES)) {                                 // (6) categories
            List<String> categories = new ArrayList<>();
            JSONArray jsonArrayCategories = bookInfo.getJSONArray(CATEGORIES);
            for (int i = 0; i < jsonArrayCategories.length(); i++) {
                categories.add(jsonArrayCategories.getString(i));
            }
            book.setCategories(categories);
        }

        return book;

    } // end of getBookFromJson()
}

