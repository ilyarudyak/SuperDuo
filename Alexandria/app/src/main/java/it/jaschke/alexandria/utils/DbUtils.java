package it.jaschke.alexandria.utils;

import android.content.ContentValues;
import android.content.Context;

import java.util.List;

import it.jaschke.alexandria.api.Book;
import it.jaschke.alexandria.data.AlexandriaContract;

/**
 * Created by ilyarudyak on 8/14/15.
 */
public class DbUtils {

    public static void insertBookIntoDb(Context context, Book b) {
        insertBookDetailsIntoDb(context, b);
        insertAuthorsIntoDb(context, b);
        insertCategoriesIntoDb(context, b);
    }

    public static void insertBookDetailsIntoDb(Context context, Book b) {
        ContentValues cv= new ContentValues();
        cv.put(AlexandriaContract.BookEntry._ID, b.getIsbn_13());
        cv.put(AlexandriaContract.BookEntry.TITLE, b.getTitle());
        cv.put(AlexandriaContract.BookEntry.IMAGE_URL, b.getImgUrl());
        cv.put(AlexandriaContract.BookEntry.SUBTITLE, b.getSubtitle());
        cv.put(AlexandriaContract.BookEntry.DESC, b.getDescription());
        context.getContentResolver().insert(AlexandriaContract.BookEntry.CONTENT_URI, cv);

    }

    public static void insertAuthorsIntoDb(Context context, Book b)  {
        List<String> authors = b.getAuthors();
        for (int i = 0; i < authors.size(); i++) {
            ContentValues cv= new ContentValues();
            cv.put(AlexandriaContract.AuthorEntry._ID, b.getIsbn_13());
            cv.put(AlexandriaContract.AuthorEntry.AUTHOR, authors.get(i));
            context.getContentResolver().insert(AlexandriaContract.AuthorEntry.CONTENT_URI, cv);
        }
    }

    public static void insertCategoriesIntoDb(Context context, Book b)  {
        List<String> categories = b.getCategories();
        for (int i = 0; i < categories.size(); i++) {
            ContentValues cv= new ContentValues();
            cv.put(AlexandriaContract.CategoryEntry._ID, b.getIsbn_13());
            cv.put(AlexandriaContract.CategoryEntry.CATEGORY, categories.get(i));
            context.getContentResolver().insert(AlexandriaContract.CategoryEntry.CONTENT_URI, cv);
        }
    }
}
