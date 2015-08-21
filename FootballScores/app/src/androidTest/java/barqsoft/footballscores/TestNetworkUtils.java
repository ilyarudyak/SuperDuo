package barqsoft.footballscores;

import android.test.AndroidTestCase;
import android.util.Log;

import barqsoft.footballscores.api.ScoresService;
import barqsoft.footballscores.utils.NetworkUtils;

/**
 * Created by ilyarudyak on 8/19/15.
 */
public class TestNetworkUtils extends AndroidTestCase {

    private static final String TAG = TestNetworkUtils.class.getSimpleName();

    public void testGetScoresJsonFromNetwork() throws Throwable {

        String jsonStr = NetworkUtils.getScoresJsonFromNetwork(
                ScoresService.PAST_2_DAYS);
        Log.d(TAG, jsonStr.substring(0, 25));
    }
}
