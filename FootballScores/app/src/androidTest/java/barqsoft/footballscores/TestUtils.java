package barqsoft.footballscores;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class TestUtils {

    public static String getJsonScoreStr() throws Throwable {
        String filename = "assets/scores.json";
        return readFile(filename);
    }

    public static String getJsonScoreStrMC() throws Throwable {
        String filename = "assets/scores2.json";
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
