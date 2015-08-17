package it.jaschke.alexandria.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import it.jaschke.alexandria.R;

/**
 * Created by ilyarudyak on 8/17/15.
 */
public class FileUtils {

    public static String readAboutFile(Context c) throws IOException {

        // get InputStream from about.html file
        InputStream in = c.getResources().openRawResource(R.raw.about);

        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        while ((line = br.readLine()) != null) {
            sb.append(line.trim());
        }
        return sb.toString();
    }
}
