package barqsoft.footballscores.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import barqsoft.footballscores.api.ApiKey;

/**
 * Created by ilyarudyak on 8/13/15.
 */
public class NetworkUtils {

    public static final String TAG = NetworkUtils.class.getSimpleName();

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return
     */
    public static boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public static String getScoresJsonFromNetwork(String timeFrame) {

        HttpURLConnection urlConnection = null;

        try {
            URL url = buildUrl(timeFrame);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.addRequestProperty("X-Auth-Token", ApiKey.KEY);
            urlConnection.connect();

            InputStream in = urlConnection.getInputStream();
            return readStream(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "Can not build URL: ", e);
            return null;
        } catch (ProtocolException e) {
            Log.e(TAG, "Wrong protocol: ", e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Can not connect to server: ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    // helper methods
    private static URL buildUrl(String timeFrame) throws MalformedURLException {
        final String FORECAST_BASE_URL = "http://api.football-data.org/alpha/fixtures";
        final String QUERY_PARAM = "timeFrame";
//        final String API_KEY = "X-Auth-Token";

        Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                .appendQueryParameter(QUERY_PARAM, timeFrame)
//                .appendQueryParameter(API_KEY, ApiKey.KEY)
                .build();
        return new URL(builtUri.toString());
    }
    private static String readStream(InputStream in) throws IOException {
        StringBuilder sb = new StringBuilder();
        if (in == null) {
            return null;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return sb.toString();
    }
}
