package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by ilyarudyak on 8/20/15.
 */
public class SmallWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        for (int appWidgetId : appWidgetIds) {
            appWidgetManager.updateAppWidget(appWidgetId,
                    buildUpdate(context));
        }
    }

    private RemoteViews buildUpdate(Context context) {

        RemoteViews remoteViews = new RemoteViews(
                context.getPackageName(), R.layout.small_widget);

        // Create an Intent to launch MainActivity
        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, i, 0);
        remoteViews.setOnClickPendingIntent(R.id.small_widget, pi);

        return (remoteViews);
    }
}
