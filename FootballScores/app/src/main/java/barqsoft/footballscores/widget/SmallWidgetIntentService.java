package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.content.Intent;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class SmallWidgetIntentService extends IntentService {

    public SmallWidgetIntentService() {
        super("SmallWidgetIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

}
