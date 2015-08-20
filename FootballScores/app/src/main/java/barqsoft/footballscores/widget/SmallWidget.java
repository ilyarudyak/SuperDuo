package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;

/**
 * Created by ilyarudyak on 8/20/15.
 */
public class SmallWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context,
                         AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);

        ComponentName me = new ComponentName(context, SmallWidget.class);
        appWidgetManager.updateAppWidget(me, buildUpdate(context, appWidgetIds));

    }

    private RemoteViews buildUpdate(Context context, int[] appWidgetIds) {

        RemoteViews updateViews = new RemoteViews(
                context.getPackageName(), R.layout.small_widget);

        Intent i = new Intent(context, SmallWidget.class);

        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_UPDATE_CURRENT);

//        updateViews.setImageViewResource(R.id.left_die,
//                IMAGES[(int) (Math.random() * 6)]);
//        updateViews.setOnClickPendingIntent(R.id.left_die, pi);
//        updateViews.setImageViewResource(R.id.right_die,
//                IMAGES[(int) (Math.random() * 6)]);
//        updateViews.setOnClickPendingIntent(R.id.right_die, pi);
//        updateViews.setOnClickPendingIntent(R.id.background, pi);

        return (updateViews);
    }
}
