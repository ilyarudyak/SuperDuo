package it.jaschke.alexandria;

import android.test.AndroidTestCase;
import android.util.Log;

import it.jaschke.alexandria.api.Book;
import it.jaschke.alexandria.api.JsonParser;

/**
 * Created by ilyarudyak on 8/13/15.
 */
public class TestJsonParser extends AndroidTestCase {

    public static final String TAG = TestJsonParser.class.getSimpleName();

    public void testGetType() throws Throwable {

        String js = TestUtils.getJsonStringLJ();
        Book b = JsonParser.getBookFromJson(js);
        Log.d(TAG, b.toString());
    }
}
