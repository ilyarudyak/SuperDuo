package barqsoft.footballscores.widget.collection;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import barqsoft.footballscores.R;

/**
 * Created by ilyarudyak on 8/24/15.
 */
public class CollectionWidgetProvider extends AppWidgetProvider {

    public static final String EXTRA_MATCH = "barqsoft.footballscores.widget.collection.MATCH";
    public static final String EXTRA_MATCHES = "barqsoft.footballscores.widget.collection.MATCHES";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {

            // create intent to start service
            Intent serviceIntent = new Intent(context, CollectionWidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            // set remote adapter; widget has only ListView with id == words
            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.collection_widget);
            widget.setRemoteAdapter(R.id.matches_list_view, serviceIntent);

            // and here we use manager to update remote views
            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
