package barqsoft.footballscores.widget.collection;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ilyarudyak on 8/24/15.
 */
public class CollectionWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new CollectionViewsFactory(getApplicationContext(), intent);
    }
}
