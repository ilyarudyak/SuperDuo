package barqsoft.footballscores.widget.collection;

import android.content.Intent;
import android.widget.RemoteViewsService;

import java.util.List;

import barqsoft.footballscores.api.Match;
import barqsoft.footballscores.utils.DataUtils;

/**
 * Created by ilyarudyak on 8/24/15.
 */
public class CollectionWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        List<Match> todayMatches = DataUtils.getMatchesToday(getApplicationContext());
        return new CollectionViewsFactory(getApplicationContext(), intent, todayMatches);
    }
}
