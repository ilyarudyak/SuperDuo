package barqsoft.footballscores.api;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import barqsoft.footballscores.utils.DataUtils;
import barqsoft.footballscores.utils.NetworkUtils;

/**
 * Created by yehya khaled on 3/2/2015.
 */
public class ScoresFetchService extends IntentService {

    public static final String TAG = ScoresFetchService.class.getSimpleName();

    // see here: http://api.football-data.org/documentation
    // filter for timeFrame for past or next 2 days
    public static final String PAST_2_DAYS = "p2";
    public static final String NEXT_2_DAYS = "n2";

    // if true we use test data instead of data from API
    private boolean isTestData = true;

    public ScoresFetchService() {
        super("ScoresFetchService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        if (isTestData) {
            getTestData();
        } else {
            getData(PAST_2_DAYS);
            getData(NEXT_2_DAYS);
        }
    }

    // helper methods
    private void getData(String timeFrame) {
        String jsonStr = NetworkUtils.getScoresJsonFromNetwork(timeFrame);
        if (jsonStr != null) {
            try {
                List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr, true);
                ContentValues[] values = Match.buildContentValues(matchList);
                DataUtils.insertMatches(getApplicationContext(), values);
            } catch (JSONException e) {
                Log.d(TAG, "Can not parse JSON: ", e);
            }
        }
    }
    private void getTestData() {

        try {
            String jsonStr = readFile("test_scores.json");

            List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr, true);
            Log.d(TAG, matchList.get(0).toString());
            ContentValues[] values = Match.buildContentValues(matchList);
            DataUtils.insertMatches(getApplicationContext(), values);
        } catch (IOException e) {
            Log.d(TAG, "Can not read file: ", e);
        } catch (JSONException e) {
            Log.d(TAG, "Can not parse JSON: ", e);
        }

    }
    private String readFile(String filename) throws IOException {
        InputStream inputStream = getApplicationContext()
                .getAssets().open(filename);
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

