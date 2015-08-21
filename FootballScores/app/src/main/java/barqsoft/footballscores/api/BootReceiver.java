/***
 * Copyright (c) 2012 CommonsWare, LLC
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. Unless required
 * by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS
 * OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * <p/>
 * From _The Busy Coder's Guide to Android Development_
 * https://commonsware.com/Android
 */

package barqsoft.footballscores.api;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

/**
 * We need our service to update data about matches at least 2 times per day.
 * So we created here alarm that fires with this frequency. We call this
 * method in 2 places: a) onReceive() - to start service on boot and
 * b) onCreate() in MainActivity - to start service when app is started.
 * We may also start service manually using Refresh button in Options Menu.
 * */
public class BootReceiver extends BroadcastReceiver {

    private static final long PERIOD = AlarmManager.INTERVAL_HALF_DAY;

    @Override
    public void onReceive(Context context, Intent i) {
        scheduleAlarms(context);
    }

    public static void scheduleAlarms(Context context) {

        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, ScoresService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        mgr.setRepeating(AlarmManager.ELAPSED_REALTIME,
                SystemClock.elapsedRealtime(), PERIOD, pi);
    }
}
