package it.jaschke.alexandria;

import android.test.AndroidTestCase;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ilyarudyak on 8/8/15.
 */
public class TestUtils extends AndroidTestCase {

    public static final String TAG = TestUtils.class.getSimpleName();

    public static String getJsonStringLJ() throws Throwable {
        String filename = "assets/learning_java.json";
        return readFile(filename);
    }

    // helper functions
    private static String readFile(String filename) throws Throwable {
        InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(filename);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(
                new InputStreamReader(inputStream));
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        return sb.toString();
    }

}
