package barqsoft.footballscores;

import android.test.AndroidTestCase;
import android.util.Log;

import java.util.List;

import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.utils.DataUtils;

/**
 * Created by ilyarudyak on 8/20/15.
 */
public class TestDataUtils extends AndroidTestCase {

    private static final String TAG = TestDataUtils.class.getSimpleName();

    public void testGetScoresJsonFromNetwork() throws Throwable {

        List<Match> matches = DataUtils.getMatchesToday(mContext);
        Log.d(TAG, matches.get(0).toString());
    }
}












