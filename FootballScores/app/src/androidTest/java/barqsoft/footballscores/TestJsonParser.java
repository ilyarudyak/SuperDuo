package barqsoft.footballscores;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

import barqsoft.footballscores.api.JsonParser;
import barqsoft.footballscores.api.Match;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class TestJsonParser extends AndroidTestCase {

    private static final String TAG = TestJsonParser.class.getSimpleName();

    public void testGetScoresJsonFromNetwork() throws Throwable {

        String jsonStr = TestUtils.getJsonScoreStr();
        List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr);
        Log.d(TAG, matchList.get(0).toString());
    }

    public void testGetScoresJsonFromNetworkMC() throws Throwable {

        String jsonStr = TestUtils.getJsonScoreStrMC();
        List<Match> matchList = JsonParser.parseScoresJsonStr(jsonStr);
        Match m = matchList.get(0);

        assertEquals("398", m.getLeague());
        assertEquals("2015-08-16", m.getDate());
        assertEquals("11:00", m.getTime());
        assertEquals("Manchester City FC", m.getHome());
        assertEquals("Chelsea FC", m.getAway());
        assertEquals("3", m.getHomeGoals());
        assertEquals("0", m.getAwayGoals());
        assertEquals("2", m.getMatchDay());
    }
}













