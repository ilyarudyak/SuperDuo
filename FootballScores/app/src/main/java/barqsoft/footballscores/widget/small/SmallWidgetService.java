package barqsoft.footballscores.widget.small;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.List;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;
import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.utils.DataUtils;
import barqsoft.footballscores.utils.MiscUtils;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SmallWidgetService extends IntentService {

    public static final String TAG = SmallWidgetService.class.getSimpleName();

    private RemoteViews mRemoteViews;
    private Match mMatch;

    public SmallWidgetService() {
        super("SmallWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // retrieve all of the widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                SmallWidgetProvider.class));


        // get first match Today from DB call
        mMatch = getFirstMatch();

        for (int appWidgetId : appWidgetIds) {

            mRemoteViews = new RemoteViews(getPackageName(), R.layout.small_widget);

            setRemoteViews();
            buildIntent();

            appWidgetManager.updateAppWidget(appWidgetId, mRemoteViews);

        }
    }

    // helper functions
    private void buildIntent() {
        Intent launchIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
        mRemoteViews.setOnClickPendingIntent(R.id.small_widget, pendingIntent);
    }
    private Match getFirstMatch() {
        List<Match> matches = DataUtils.getMatchesToday(getApplicationContext());
        if (matches != null && matches.size() > 0) {
            return matches.get(0);
        } else {
            Log.d(TAG, "no matches today");
            return null;
        }
    }
    private void setRemoteViews() {
        // set command titles
        mRemoteViews.setTextViewText(R.id.home_name, mMatch.getHome());
        mRemoteViews.setTextViewText(R.id.away_name, mMatch.getAway());

        // set command logos
        mRemoteViews.setImageViewResource(R.id.home_crest,
                MiscUtils.getTeamCrestByTeamName(mMatch.getHome()));
        mRemoteViews.setImageViewResource(R.id.away_crest,
                MiscUtils.getTeamCrestByTeamName(mMatch.getAway()));

        // set score
        mRemoteViews.setTextViewText(R.id.score_text_view, mMatch.getScore());

        // set time of match
        mRemoteViews.setTextViewText(R.id.time_text_view, mMatch.getTime());
    }

}
