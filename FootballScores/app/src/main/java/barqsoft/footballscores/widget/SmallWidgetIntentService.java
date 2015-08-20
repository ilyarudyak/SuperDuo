package barqsoft.footballscores.widget;

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
public class SmallWidgetIntentService extends IntentService {

    public static final String TAG = SmallWidgetIntentService.class.getSimpleName();

    public SmallWidgetIntentService() {
        super("SmallWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // retrieve all of the widget ids: these are the widgets we need to update
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this,
                SmallWidgetProvider.class));


        // get first match Today from DB call
        Match firstMatchToday;
        List<Match> matches = DataUtils.getMatchesToday(getApplicationContext());
        if (matches != null && matches.size() > 0) {
            firstMatchToday = matches.get(0);
        } else {
            Log.d(TAG, "no matches today");
            return;
        }

        for (int appWidgetId : appWidgetIds) {
            int layoutId = R.layout.small_widget;
            RemoteViews views = new RemoteViews(getPackageName(), layoutId);

            // set command titles
            views.setTextViewText(R.id.home_name, firstMatchToday.getHome());
            views.setTextViewText(R.id.away_name, firstMatchToday.getAway());

            // set command logos
            views.setImageViewResource(R.id.home_crest,
                    MiscUtils.getTeamCrestByTeamName(firstMatchToday.getHome()));
            views.setImageViewResource(R.id.away_crest,
                    MiscUtils.getTeamCrestByTeamName(firstMatchToday.getAway()));

            // set score
            views.setTextViewText(R.id.score_text_view,
                    firstMatchToday.getHomeGoals() + ":" +
                    firstMatchToday.getAwayGoals());

            // set time of match
            views.setTextViewText(R.id.time_text_view, firstMatchToday.getTime());

            // create an Intent to launch MainActivity
            Intent launchIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, 0);
            views.setOnClickPendingIntent(R.id.small_widget, pendingIntent);

            // tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);


        }
    }

}
