package barqsoft.footballscores.widget.collection;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import barqsoft.footballscores.R;
import barqsoft.footballscores.api.Match;

/**
 * Created by ilyarudyak on 8/24/15.
 */
public class CollectionViewsFactory implements
        RemoteViewsService.RemoteViewsFactory {

    private List<Match> mMatches;
    private Context mContext;
    private int mAppWidgetId;

    public CollectionViewsFactory(Context context, Intent intent, List<Match> matches) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        mMatches = matches;
    }

    // build Remote Views
    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.small_widget);
        setRow(row, position);

        return row;
    }

    // helper methods
    private void setRow(RemoteViews row, int position) {
        row.setTextViewText(R.id.home_name, mMatches.get(position).getHome());
    }

    // simple getters
    @Override
    public int getViewTypeCount() {
        return 1;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return true;
    }
    @Override
    public int getCount() {
        return mMatches.size();
    }

    // empty methods
    @Override
    public void onCreate() {
    }
    @Override
    public void onDataSetChanged() {

    }
    @Override
    public void onDestroy() {

    }
    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

}
